package com.fanli.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

/**
 * User: cjp
 * Date: 2008-12-16
 * Time: 10:47:31
 */
public class HttpClientUtil {
    static DefaultHttpClient httpClient = new DefaultHttpClient();

    public static String get(String url, String encoding) throws Exception {
//        System.out.println(url);
        url = StringUtils.replace(url, " ", "%20");
        HttpGet httpGet = new HttpGet(url);
        HttpResponse res = httpClient.execute(httpGet);
        return getContent(res, encoding);
    }

    public static String get(String url, String encoding, DefaultHttpClient client) throws Exception {
//        System.out.println(url);
        url = StringUtils.replace(url, " ", "%20");
        HttpGet httpGet = new HttpGet(url);
        HttpResponse res = client.execute(httpGet);
        return getContent(res, encoding);
    }


    public static String post(String url, StringEntity se, String host, String referer, String encoding) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(se);
        httpPost.setHeader("Host", host);
        httpPost.setHeader("Referer", referer);
        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Language", "zh-cn");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("UA-CPU", "x86");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; InfoPath.2; CIBA)");

        HttpResponse response = httpClient.execute(httpPost);

        return getContent(response, encoding);
    }

    public static String getContent(HttpResponse res, String encoding) throws Exception {
        HttpEntity ent = res.getEntity();
        String result = IOUtils.toString(ent.getContent(), encoding);
        ent.consumeContent();
        return result;
    }

    public static InputStream getStream(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse res = httpClient.execute(httpGet);
        return res.getEntity().getContent();
    }
}
