from flask import Flask, request, send_from_directory, url_for, redirect, abort, jsonify, render_template, session
import sqlite3
import socket
import os
import hashlib
from datetime import timedelta
UPLOAD_FOLDER = "./database"
userManageDB = 'userManage.db'
testDataDB = "testData.db"
app = Flask(__name__)
app.secret_key = 'We are Fried Chicken Dinner!!!!'


@app.before_request
def make_session_permanent():
    session.permanent = True
    app.permanent_session_lifetime = timedelta(minutes=5)
@app.route('/')
def hello_world():
    return render_template('index.html')

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
    curs.execute("insert into userManage values ('" + username + "', '" + dbPassword  + "')")
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
    dbPassword = hashlib.md5(password.encode()).hexdigest()
    curs.execute("SELECT * FROM userManage")
    Access = False
    for i in curs.fetchall():
        if i[0] == username:
            if i[1] == dbPassword:
                Access = True
                break

    if (Access == False):
        data = "<h1>fail<h1>"
    else :
        userKey = str(hashlib.md5((username+time).encode()).hexdigest())
        data = "<h1>success<h1>" + "<h1>" + userKey + "<h1>"
        session['username'] = userKey
    conn.commit()
    conn.close()
    return data

@app.route("/testData", methods=['GET'])
def testData_render():
    return render_template('test_data.html')
@app.route("/testData", methods=['POST','GET'])
def testData():
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,testDataDB))
    curs = conn.cursor()
    username = request.form['id']
    userKey = request.form['key']

    curs.execute("SELECT * FROM testData")

    Access = False
    print("session"+str(session.get('username')) + '\n')
    print("user"+str(userKey) + '\n')
    if str(session.get('username')).lower() == str(userKey).lower():
        Access = True
    if (Access == False):
        data = "<h1>wrong access<h1>"
    else :
        curs.execute("insert into testData values ('" + username + "', '" +"data1"+ "', '" +"data2"+"')")
        for i in curs.fetchall():
            if i[0] == username:
                data = data + "<h1>" + str(i[1]) + str(i[2]) + "</h1>"
    conn.commit()
    conn.close()
    return data

def DBinit():
    if not os.path.isdir(UPLOAD_FOLDER):
        os.mkdir(UPLOAD_FOLDER)
    if not os.path.isfile(os.path.join(UPLOAD_FOLDER, userManageDB)):
        conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, userManageDB))
        curs = conn.cursor()
        curs.execute("CREATE TABLE  if not exists userManage(username, password)")
        conn.commit()
        conn.close()
    if not os.path.isfile(os.path.join(UPLOAD_FOLDER, testDataDB)):
       conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, testDataDB))
       curs = conn.cursor()
       curs.execute("CREATE TABLE  if not exists testData(dataID,data1, data2)")
       conn.commit()
       conn.close()


if __name__ == '__main__':
    IP = str(socket.gethostbyname(socket.gethostname()))
    DBinit()
    app.run(host = IP, port = 5010,debug=True)
