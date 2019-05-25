from flask import Flask
import os
import json
import uwsgi
app = Flask(__name__)

@app.route('/')
def hello_world():
    if os.environ.get('SECRET') is None:
        greeting = '<h1>Hello World in Production!</h1> <br/>' \
                + 'Number of uWSGI workers: ' + str(uwsgi.numproc) + '<br/>'  \
                + os.getenv('MY_ENV_VAR', "no environment variables found. <br/>")
    else:
        greeting = '<h1>Hello World in Production!</h1> <br/>' \
                + 'Number of uWSGI workers: ' + str(uwsgi.numproc) + '<br/>' \
                + os.getenv('MY_ENV_VAR', "no environment variables found. <br/>") + \
                + 'I have a secret: ' + str(os.environ.get('SECRET'))

    return greeting 

if __name__ == '__main__':
    app.run(host='0.0.0.0')
