from flask import Flask, request, send_from_directory, url_for, redirect, abort, jsonify, render_template
import sqlite3
import socket
import os
import hashlib
UPLOAD_FOLDER = "./database"
userManageDB = 'userManage.db'
app = Flask(__name__)


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
        data = "<h1>success<h1>" + "<h1>" + str(hashlib.md5((username+time).encode()).hexdigest()) + "<h1>"
    conn.commit()
    conn.close()
    return data

def DBinit():
    if not os.path.isfile(os.path.join(UPLOAD_FOLDER, userManageDB)):
        conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER, userManageDB))
        curs = conn.cursor()
        curs.execute("CREATE TABLE  if not exists userManage(username, password)")
        conn.commit()
        conn.close()


if __name__ == '__main__':
    IP = str(socket.gethostbyname(socket.gethostname()))
    DBinit()
    app.run(host = IP, port = 5010,debug=True)
