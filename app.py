from flask import Flask, request, render_template,make_response
import sqlite3
import os
import hashlib
import crawlingConvince as CC
import socket
import datetime
import threading
UPLOAD_FOLDER = "./database"
storeManageDB = 'storeManage.db'
addressManageDB = 'addressManage.db'
userManageDB = 'userManage.db'
testDataDB = "testData.db"
app = Flask(__name__)
app.secret_key = 'We are Fried Chicken Dinner!!!!'

returnDataCU = ""
returnDataGS25 = ""
loginData = {}
loginType = {}
def update_thread():
    while(1):
        now = datetime.datetime.now()
        global returnDataGS25
        global returnDataCU
        if now.minute == 59 and now.second == 59:
            returnDataCU = CC.updateCU()
            returnDataGS25 = CC.updateGS25()

def make_data():
    count = 0
    global returnDataGS25
    global returnDataCU
    while count == 0:
        returnDataCU = CC.updateCU()
        returnDataGS25 = CC.updateGS25()
        if returnDataGS25 != "error":
            count = 1
        if returnDataCU != "error":
            count = 1

@app.route('/')
def hello_world():

    return render_template('index.html')

@app.route('/update')
def update_convinceData():
    make_data()
    return 'true'

@app.route("/signUp", methods=['GET'])
def signUp_render():
    return render_template('sign_up.html')
@app.route("/signUp", methods=['POST'])
def signUp():
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,userManageDB))
    curs = conn.cursor()
    username = request.form['id']
    password = request.form['pw']
    dbPassword = hashlib.md5(password.encode()).hexdigest()
    curs.execute("SELECT * FROM userManage")

    Access = True
    for i in curs.fetchall():
        if i[0] == username:
            Access = False
            break
    if (Access == False):
        data = '<h1>fail<h1>'
        conn.commit()
        conn.close()
        return data
    curs.execute("insert into userManage values ('" + username + "', '" + dbPassword + "', '" + 'user' + "')")
    conn.commit()
    conn.close()
    data = "<h1>success<h1>"
    return data

@app.route("/ownerSignUp", methods=['GET'])
def ownerSignUp_render():
    return render_template('owner_sign_up.html')
@app.route("/ownerSignUp", methods=['POST'])
def ownerSignUp():
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,userManageDB))
    curs = conn.cursor()
    username = request.form['id']
    password = request.form['pw']
    address = request.form['address']
    addressX = request.form['addressX']
    addressY = request.form['addressY']
    dbPassword = hashlib.md5(password.encode()).hexdigest()
    curs.execute("SELECT * FROM userManage")
    Access = True
    for i in curs.fetchall():
        if i[0] == username:
            Access = False
            break
    if (Access == False):
        data = '<h1>fail<h1>'
        conn.commit()
        conn.close()
        return data
    curs.execute("insert into userManage values ('" + username + "', '" + dbPassword  + "', '" + 'owner'+"')")
    conn.commit()
    conn.close()
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, storeManageDB))
    curs = conn.cursor()
    curs.execute("CREATE TABLE if not exists " + 'owner_'+username+"(itemName, itemPrice, event)")
    conn.commit()
    conn.close()
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, addressManageDB))
    curs = conn.cursor()
    address = address.split(" ")
    if address[0] in ['광주' , '대구', '인천', '부산', '울산', '대전']:
        address[0]+='광역시'
    if address[0] == '충북':
        address[0] = '충청북도'
    if address[0] == '충남':
        address[0] = '충청남도'
    if address[0] == '전남':
        address[0] = '전라남도'
    if address[0] == '전북':
        address[0] = '전라북도'
    if address[0] == '경남':
        address[0] = '경상남도'
    if address[0] == '경북':
        address[0] = '경상북도'
    if address[0] == '강원':
        address[0]= '강원도'
    if address[0] == '경기':
        address[0] = '경기도'
    if address[0] == '서울':
        address[0] = '서울특별시'
    addressS = ""
    for x in address :
        addressS += x+'_'
        if x[len(x)-1] == '리' or x[len(x)-1] == '동':
            break;
    print(addressS)
    curs.execute("CREATE TABLE if not exists " + addressS + "(name,dataX, dataY)")
    curs.execute("insert into "+addressS+" values ('" + username + "', '" + addressX + "', '" + addressY + "')")
    conn.commit()
    conn.close()
    data = "<h1>success<h1>"
    return data

@app.route("/signIn", methods=['GET'])
def signIp_render():
    return render_template('sign_in.html')
@app.route("/signIn", methods=['POST','GET'])
def signIn():
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,userManageDB))
    curs = conn.cursor()
    username = request.form['id']
    password = request.form['pw']
    time = request.form['time']
    global loginData
    dbPassword = hashlib.md5(password.encode()).hexdigest()
    curs.execute("SELECT * FROM userManage")
    Access = False
    for i in curs.fetchall():
        if i[0] == username:
            if i[1] == dbPassword:
                Access = True
                loginType[username] = i[2]
                break

    if (Access == False):
        data = "<h1>fail<h1>"
        return data
    else :
        userKey = str(hashlib.md5((username+time).encode()).hexdigest())
        data = "<h1>success</h1>" + "<h1>" + userKey + "</h1>" + "<h1>" + loginType[username] + "</h1>"
        loginData[username] = userKey
        print(username + '#' + userKey)
        resp = make_response(data)
        resp.set_cookie(username, userKey)
    conn.commit()
    conn.close()
    return resp

@app.route("/testData", methods=['GET'])
def testData_render():
    return render_template('test_data.html')


def userAuthenticate(userKey, cookieKey):
    print('\n' + "session" + str(cookieKey) + '\n')
    print("user" + str(userKey) + '\n')
    if str(cookieKey).lower() == str(userKey).lower():
        return True
    else :
        return False


@app.route("/testData", methods=['POST','GET'])
def testData():
    global loginData

    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,testDataDB))
    curs = conn.cursor()
    username = request.form['id']
    if username in loginData:
        userKey = loginData[username]
    else :
        return "<h1>wrong access<h1>"
    if username in request.cookies:
        cookieKey = request.cookies.get(username)
    else :
        return "<h1>wrong access<h1>"
    Access= userAuthenticate(userKey, cookieKey)
    curs.execute("SELECT * FROM testData")
    if Access == False:
        data = "<h1>wrong access<h1>"
    elif Access == True:
        data = "<h1>testData</h1>"
        curs.execute("insert into testData values ('" + username + "', '" +"data1"+ "', '" +"data2"+"')")
        curs.execute("SELECT  * FROM testData")
        for i in curs.fetchall():
            if i[0] == username:
                data = data + "<h1>" + str(i[1]) + str(i[2]) + "</h1>"
    conn.commit()
    conn.close()
    return data

@app.route("/ownerItemUpload", methods=['GET'])
def ownerItemUpload_render():
    return render_template('owner_item_upload.html')

@app.route("/ownerItemUpload", methods=['POST','GET'])
def ownerItemUpload():
    global loginData
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,storeManageDB))
    curs = conn.cursor()
    username = request.form['id']
    itemName = request.form['itemName']
    itemPrice = request.form['itemPrice']
    itemEvent = request.form['event']
    userKey = loginData[username]
    if username in request.cookies:
        cookieKey = request.cookies.get(username)
    else :
        return "<h1>wrong access<h1>"
    Access= userAuthenticate(userKey, cookieKey)
    if Access == False:
        data = "<h1>wrong access<h1>"
    elif Access == True:
        if isOwner(username):
            curs.execute("SELECT  * FROM owner_" + username)
            for i in curs.fetchall():
                if i[0] == itemName:
                    data = "<h1>fail</h1>"
                    return data
            data = "<h1>success</h1>"
            curs.execute("insert into "+'owner_'+username+" values ('" + itemName + "', '" +itemPrice+ "', '" +itemEvent+"')")
            curs.execute("SELECT  * FROM owner_"+username)
            for i in curs.fetchall():
                if i[0] == itemName:
                    data = data + "<h1>" + str(i[0]) + str(i[1])+ str(i[2]) + "</h1>"
        else :
            data = '<h1>isNotOwner'
    conn.commit()
    conn.close()
    return data

@app.route("/ownerItemDownload", methods=['GET'])
def ownerItemDownload_render():
    return render_template('owner_item_download.html')

@app.route("/ownerItemDownload", methods=['POST','GET'])
def ownerItemDownload():
    global loginData
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,storeManageDB))
    curs = conn.cursor()
    username = request.form['id']
    itemName = request.form['itemName']
    itemPrice = request.form['itemPrice']
    itemEvent = request.form['event']
    userKey = loginData[username]
    if username in request.cookies:
        cookieKey = request.cookies.get(username)
    else :
        return "<h1>wrong access<h1>"
    Access= userAuthenticate(userKey, cookieKey)
    if Access == False:
        data = "<h1>wrong access<h1>"
    elif Access == True:
        if isOwner(username):
            curs.execute("SELECT  * FROM owner_" + username)
            for i in curs.fetchall():
                if i[0] == itemName:
                    data = "<h1>fail</h1>"
                    return data
            data = "<h1>success</h1>"
            curs.execute("insert into "+'owner_'+username+" values ('" + itemName + "', '" +itemPrice+ "', '" +itemEvent+"')")
            curs.execute("SELECT  * FROM owner_"+username)
            for i in curs.fetchall():
                if i[0] == itemName:
                    data = data + "<h1>" + str(i[0]) + str(i[1])+ str(i[2]) + "</h1>"
        else :
            data = '<h1>isNotOwner'
    conn.commit()
    conn.close()
    return data

@app.route("/ownerItemEdit", methods=['GET'])
def ownerItemEdit_render():
    return render_template('owner_item_upload.html')

@app.route("/ownerItemEdit", methods=['POST','GET'])
def ownerItemEdit():
    global loginData
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,storeManageDB))
    curs = conn.cursor()
    username = request.form['id']
    itemName = request.form['itemName']
    itemPrice = request.form['itemPrice']
    itemEvent = request.form['event']
    userKey = loginData[username]
    if username in request.cookies:
        cookieKey = request.cookies.get(username)
    else :
        return "<h1>wrong access<h1>"
    Access= userAuthenticate(userKey, cookieKey)
    if Access == False:
        data = "<h1>wrong access<h1>"
    elif Access == True:
        if isOwner(username):
            curs.execute("SELECT  * FROM owner_" + username)
            for i in curs.fetchall():
                if i[0] == itemName:
                    data = "<h1>fail</h1>"
                    return data
            data = "<h1>success</h1>"
            curs.execute("insert into "+'owner_'+username+" values ('" + itemName + "', '" +itemPrice+ "', '" +itemEvent+"')")
            curs.execute("SELECT  * FROM owner_"+username)
            for i in curs.fetchall():
                if i[0] == itemName:
                    data = data + "<h1>" + str(i[0]) + str(i[1])+ str(i[2]) + "</h1>"
        else :
            data = '<h1>isNotOwner'
    conn.commit()
    conn.close()
    return data

def isOwner(ID):
    if loginType[ID] == 'owner':
        return True
    else :
        return False

@app.route("/loadStore", methods=['GET'])
def loadStore_render():
    return render_template('load_store.html')

@app.route("/loadStore", methods=['POST','GET'])
def loadStore():
    global loginData
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,addressManageDB))
    curs = conn.cursor()
    address = request.form['address']
    curs.execute("select * from "+address+"")
    data = ""
    for i in curs.fetchall():
        data += "<h1>"+i[0]+'!'+i[1]+'!'+i[2]+"</h1>"
    conn.commit()
    conn.close()
    return data

def isOwner(ID):
    if loginType[ID] == 'owner':
        return True
    else :
        return False
@app.route("/logout", methods=['GET'])
def logout_render():
    return render_template('logout.html')
@app.route("/logout", methods=['POST','GET'])
def logout():
    username = request.form['id']
    if username in request.cookies:
        resp = make_response()
        resp.set_cookie(username, "null")
        if username in loginData:
            loginData[username] = None
            loginType[username] = None
        return "<h1>logout</h1>"
    else :
        return "<h1>error_not_login</h1>"

@app.route("/inputAddress", methods=['GET'])
def inputAddress_render():
        return render_template('input_address(daum).html')

def DBinit():
    if not os.path.isdir(UPLOAD_FOLDER):
        os.mkdir(UPLOAD_FOLDER)
    if not os.path.isfile(os.path.join(UPLOAD_FOLDER, userManageDB)):
        conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, userManageDB))
        curs = conn.cursor()
        curs.execute("CREATE TABLE  if not exists userManage(username, password, type)")
        conn.commit()
        conn.close()
    if not os.path.isfile(os.path.join(UPLOAD_FOLDER, testDataDB)):
       conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, testDataDB))
       curs = conn.cursor()
       curs.execute("CREATE TABLE  if not exists testData(dataID,data1, data2)")
       conn.commit()
       conn.close()
    if not os.path.isfile(os.path.join(UPLOAD_FOLDER, testDataDB)):
        conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, testDataDB))
        curs = conn.cursor()
        curs.execute("CREATE TABLE  if not exists testData(dataID,data1, data2)")
        conn.commit()
        conn.close()
    if not os.path.isfile(os.path.join(UPLOAD_FOLDER, storeManageDB)):
        conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, testDataDB))
        curs = conn.cursor()

@app.route("/test", methods=['GET'])
def test():
    return returnDataCU

@app.route("/testCU", methods=['GET'])
def testCU():
    return returnDataCU

@app.route("/testGS25", methods=['GET'])
def testGS25():
    return returnDataGS25


if __name__ == '__main__':
    IP = str(socket.gethostbyname(socket.gethostname()))
    DBinit()
    returnDataCU = CC.updateCU()
    returnDataGS25 = CC.updateGS25()
    #t = threading.Thread(target=update_thread)
    #t.start()
    app.run(host = IP, port = 5010, debug=True)
