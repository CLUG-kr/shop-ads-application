from flask import Flask, request, send_from_directory, url_for, redirect, abort, jsonify, render_template
import sqlite3
import json
import socket
import os
UPLOAD_FOLDER = "./"

app = Flask(__name__)


@app.route('/')
def hello_world():
    return render_template('index.html')

@app.route("/signUp", methods=['GET'])
def signUp_render():
    return render_template('sign_up.html')
@app.route("/signUp", methods=['POST'])
def signUp():
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,'userManage.db'))
    curs = conn.cursor()
    username = request.form['id']
    password = request.form['pw']
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
    curs.execute("insert into userManage values ('" + username + "', '" + password  + "')")
    conn.commit()
    conn.close()
    data = "<h1>success<h1>"
    return data

@app.route("/signIn", methods=['POST','GET'])
def signIn():
    conn = sqlite3.connect(os.path.join(UPLOAD_FOLDER,'userManage.db'))
    curs = conn.cursor()
    username = request.form['id']
    password = request.form['pw']

    curs.execute("SELECT * FROM userManage")

    Access = False
    for i in curs.fetchall():
        if i[0] == username:
            if i[1] == password:
                Access = True
                break

    if (Access == False):
        data = "<h1>fail<h1>"
    else :
        data = "<h1>success<h1>"
    conn.commit()
    conn.close()
    return data
if __name__ == '__main__':
    IP = str(socket.gethostbyname(socket.gethostname()))
    app.run(host = IP, port = 5010,debug=True)
