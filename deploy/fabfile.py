import os
from fabric.api import *
from fabric.context_managers import *
from fabric.contrib.console import confirm

# group servers
env.roledefs = {
    "zm_web_server" : ["root@120.25.122.107"]
}

@roles("zm_web_server")
def upload_web():
    run("sudo rm -r /opt/tomcat/webapps/ROOT")
    run("sudo mkdir /opt/tomcat/webapps/ROOT")
    put(os.getcwd() + "/../web/out/artifacts/web_war_exploded/RES", "/opt/tomcat/webapps/ROOT/", use_sudo=True)
    put(os.getcwd() + "/../web/out/artifacts/web_war_exploded/WEB-INF", "/opt/tomcat/webapps/ROOT/", use_sudo=True)
    run("sudo systemctl restart tomcat")
