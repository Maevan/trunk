#coding=utf-8
'''
Created on 2012-11-8

@author: zhaojp
'''
from web import application, seeother, template, session, config;
urls = ('/count', "count",
        '/reset', "reset"
);

app_session = application(urls, locals());
t_session = session.Session(app_session, session.DiskStore('session'), initializer={'count':0});

config.session_parameters['cookie_name'] = 'webpy_session_id'; #保存session id的Cookie的名称
config.session_parameters['cookie_domain'] = None; #保存session id的Cookie的domain信息
config.session_parameters['timeout'] = 86400; #24 * 60 * 60, # 24 hours   in seconds  session的有效时间 ，以秒为单位
config.session_parameters['ignore_expiry'] = True;# 如果为True，session就永不过期
config.session_parameters['ignore_change_ip'] = True;#如果为true，就表明只有在访问该session的IP与创建该session的IP完全一致时，session才被允许访问。
config.session_parameters['secret_key'] = 'fLjUfxqXtfNoIldA0A0J';#密码种子，为session加密提供一个字符串种子
config.session_parameters['expired_message'] = 'Session expired';# session过期时显示的提示信息。

class count:
    def GET(self):
        t_session.count += 1;
        return str(t_session.count);
    
class reset:
    def GET(self):
        t_session.kill();
        return '';
