package com.example.spring_boot_demo.sex.weather;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;


//微信消息推送
public class wehook_msg {

    @Value("${wx.wehook}")
    private static String wehook;
    private String msg;

    public wehook_msg(String msg) {
        this.msg = msg;
    }

    public void main() throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(wehook);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
        String textMsg = "{" +
                "\"msgtype\":\"text\"," +
                "\"text\":{\"content\":\"" + msg + "\"}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
    }

    public void newmain() throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(wehook);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
        String textMsg = "{" +
                "\"msgtype\":\"markdown\"," +
                "\"markdown\":{\"content\":\"" + msg + "\"}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
    }
}
