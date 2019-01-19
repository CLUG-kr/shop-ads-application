from flask import Flask, request, send_from_directory, url_for, redirect, abort, jsonify, render_template, session, \
    make_response
import sqlite3
import socket
import os
import hashlib
import time

import selenium
from selenium import webdriver
from bs4 import BeautifulSoup
import socket
import datetime
import threading
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
import threading

from datetime import timedelta
UPLOAD_FOLDER = "./database"
userManageDB = 'userManage.db'
testDataDB = "testData.db"
app = Flask(__name__)
app.secret_key = 'We are Fried Chicken Dinner!!!!'

returnData = "return"

def update_thread():
    while(1):
        now = datetime.datetime.now()
        if now.minute == 59 :
            returnData = update_data()


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
        return data
    else :
        userKey = str(hashlib.md5((username+time).encode()).hexdigest())
        data = "<h1>success<h1>" + "<h1>" + userKey + "<h1>"
        resp = make_response(data)
        resp.set_cookie(username, userKey)
    conn.commit()
    conn.close()
    return resp

@app.route("/testData", methods=['GET'])
def testData_render():
    return render_template('test_data.html')
@app.route("/testData", methods=['POST','GET'])
def testData():
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,testDataDB))
    curs = conn.cursor()
    username = request.form['id']
    userKey = request.form['key']
    if username in request.cookies:
        cookieKey = request.cookies.get(username)
    else :
        return "<h1>wrong access<h1>"

    curs.execute("SELECT * FROM testData")
    Access = False
    print('\n'+"session"+str(cookieKey) + '\n')
    print("user"+str(userKey) + '\n')
    if str(cookieKey).lower() == str(userKey).lower():
        Access = True
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

@app.route("/logout", methods=['GET'])
def logout_render():
    return render_template('logout.html')
@app.route("/logout", methods=['POST','GET'])
def logout():
    username = request.form['id']
    if username in request.cookies:
        resp = make_response()
        resp.set_cookie(username, "null")
        return "<h1>logout</h1>"
    else :
        return "<h1>error_not_login</h1>"

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

@app.route("/test", methods=['GET'])
def testGS25():
    return returnData

def update_data():
    sUrl = "http://gs25.gsretail.com/gscvs/ko/products/event-goods"
    options = webdriver.ChromeOptions()
    options.add_argument('headless')
    options.add_argument('window-size=1920x1080')
    options.add_argument("disable-gpu")
    driver = webdriver.Chrome('chromedriver', options = options)
    driver.implicitly_wait(3)
    driver.get(sUrl)
    prevEndCount = "prev"
    global returnData
    while True:
        time.sleep(3)
        WebDriverWait(driver, 100).until(EC.invisibility_of_element_located((By.CLASS_NAME, "blockUI blockOverlay")))
        #driver.find_element_by_xpath('//*[ @ id = "TOTAL"]').click()
        el = driver.find_element_by_link_text("다음 페이지로 이동")
        WebDriverWait(driver, 100).until(EC.invisibility_of_element_located((By.CLASS_NAME, "blockUI blockOverlay")))
        time.sleep(1)
        while True:
            try:
                WebDriverWait(driver,100).until(EC.element_to_be_clickable((By.LINK_TEXT,"다음 페이지로 이동")))
                driver.execute_script("scroll(0, 1500)");
                time.sleep(1)
                print("t")
                driver.execute_script("arguments[0].click();", el)
                print(123)
                break;
            except selenium.common.exceptions.StaleElementReferenceException:
                time.sleep(1)

        '''
        for x in linkList:
            if x.getAttribute("href").contains('#next;'):
                x.click()
                break
        '''

        getHTML = driver.page_source
        soup = BeautifulSoup(getHTML, 'html.parser')
        getData = soup.findAll('div', {'class' : 'prod_box'})

        endCount = getData[3].find('p',{'class':'tit'}).getText()
        if endCount == prevEndCount:
            break;
        else :
            prevEndCount = endCount
            print('pass')
        for x in getData[3:11]:
            print(x)
            returnData += "<h1>" + x.find('p',{'class':'tit'}).getText() +"#"+ x.find('span').getText()+ x.find('div').getText()+"</h1>"
    driver.quit()
    return returnData

if __name__ == '__main__':
    IP = str(socket.gethostbyname(socket.gethostname()))
    DBinit()
    returnData = update_data()
    t = threading.Thread(target=update_thread)
    t.start()
    app.run(host = IP, port = 5010,debug=True)
