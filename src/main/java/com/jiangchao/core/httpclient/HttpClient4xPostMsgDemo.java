package com.jiangchao.core.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClient4xPostMsgDemo {

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost("http://jsondata.25pp.com/index.html");
            httpPost.addHeader("tunnel-command", "4261425200");
//            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":0}", ""));
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpPost.setEntity(new StringEntity("{\"dcType\":1,\"resType\":1,\"listType\":0,\"catId\":0,\"perCount\":15,\"page\":0}"));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(String.format("status code: %d, content: %s", response.getStatusLine(), EntityUtils.toString(entity)));
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

}
