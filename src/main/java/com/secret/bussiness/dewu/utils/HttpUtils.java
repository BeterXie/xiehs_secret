package com.secret.bussiness.dewu.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: chenyanfeng
 * @Date: 2018-11-23
 * @Time: 下午4:00
 */
public class HttpUtils {

    public static final String IP = "forward.xdaili.cn";//这里以正式服务器ip地址为准
    public static final int PORT = 80;//这里以正式服务器端口地址为准

    private static void setHttpHeaders(HttpGet httpGet) {
        //设置默认请求头 在浏览器登陆后，把cookie的内容复制到这里设置cookie，不然无法查询
        httpGet.setHeader("content-type", "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        //httpGet.setHeader("Wxapp-Login-Token", "64050799|d5c88efce6170d359ae5ee99c808dd55|1d013222|d99cdad6");
        //httpGet.setHeader("X-Auth-Token", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1dWlkIjoibnVsbCIsInVzZXJJZCI6MTU4MDk2MDUyMCwidXNlck5hbWUiOiLpo47mtYHmgp_pgZMzeHAiLCJleHAiOjE2NTU4MTUzMDcsImlhdCI6MTYyNDI3OTMwNywiaXNzIjoibnVsbCIsInN1YiI6Im51bGwifQ.qsYUdxbHDS6aV6BdnVbLh5sXi2hwDXBcgzPZSsKJnSrXpHzSK87msj68joJWob56UP1y-BoOfx1lzGtFn-uDVY77eOYbe4hGRAs_gi9IY_8NAEnG05upfQAivjLJm7MEBEh0TVngd_M1HWWX8YdyooNEn0KMaVyryfxAINp7nWkGKAyftl68nAJ1sRZd4wPds6Nwh-Ku-FDa2mN9TJwJPL62x1C7T6Gc_rMh-Oifb-_F3z3TBcLj0InXBZkrglOwIMY5pDLp3tA05uTNUH0DvX4YZVz0aYJ7k4UuitoBoTXgkvqZRxwKaas1aReSSvqFyjdhQWt6Jpzh1Fw0TEf-Hw");
        httpGet.setHeader("Host", "app.dewu.com");
        httpGet.setHeader("platform", "h5");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("appVersion", "4.4.0");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("AppId", "wxapp");
        //httpGet.setHeader("Wxapp-Login-Token", "1231365464646546512");
        //httpGet.setHeader("X-Remote-IP", "127.0.0.2");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("referer", "https://servicewechat.com/wx3c12cdd0ae8b1a7b/227/page-frame.html");
        int timestamp = (int) (new Date().getTime()/1000);
        String sign = authHeader("ZF20216298129cttmO4", "6a89e9c994884072a230ff19a62ad211", timestamp);
        httpGet.setHeader("Proxy-Authorization",sign);
    }

    private static void setDewuAppHttpHeaders(HttpGet httpGet) {
        //设置默认请求头 在浏览器登陆后，把cookie的内容复制到这里设置cookie，不然无法查询
        httpGet.setHeader("content-type", "application/x-www-form-urlencoded");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        httpGet.setHeader("Host", "app.poizon.com");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("appVersion", "4.4.0");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("AppId", "wxapp");

    }

    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1)
                + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
    }

    /**
     * http get 请求
     * @param url 请求URL
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, String> params,Map<String, String> iPparams) {
        HttpHost httpHost = new HttpHost(IP,PORT);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .setProxy(httpHost)
                .build();

        // 拼接请求URL
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (params != null && params.size() >= 0) {
            Set<String> keySet = params.keySet();
            boolean firstFlag = true;
            for (String key : keySet) {
                if (firstFlag) {
                    if ( StringUtils.isNotBlank(params.get(key)) ) {
                        sb.append("?" + key + "=" + params.get(key));
                        firstFlag = false;
                    }
                }else {
                    if ( StringUtils.isNotBlank(params.get(key))) {
                        sb.append("&" + key + "=" + params.get(key));
                    }
                }
            }
        }

        HttpGet httpGet = new HttpGet(sb.toString());
        //设置默认请求头
        setHttpHeaders(httpGet);
        httpGet.setConfig(requestConfig);

        //执行HTTP请求
        CloseableHttpClient httpClient = getHttpClient();
        HttpClientContext context = HttpClientContext.create();
        HttpResponse response = null;
        String res = null;
        try {
            response = httpClient.execute(httpGet, context);
            res = EntityUtils.toString(response.getEntity());
            httpClient.close();
        }catch (Exception ex){
            if (httpClient != null) {
                httpClient.close();
            }
        }finally {
            return  res;
        }
    }

    /**
     * http get 请求
     * @param url 请求URL
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, String> params) throws IOException {
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .build();

        // 拼接请求URL
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (params != null && params.size() >= 0) {
            Set<String> keySet = params.keySet();
            boolean firstFlag = true;
            for (String key : keySet) {
                if (firstFlag) {
                    if ( StringUtils.isNotBlank(params.get(key)) ) {
                        sb.append("?" + key + "=" + params.get(key));
                        firstFlag = false;
                    }
                }else {
                    if ( StringUtils.isNotBlank(params.get(key))) {
                        sb.append("&" + key + "=" + params.get(key));
                    }
                }
            }
        }

        HttpGet httpGet = new HttpGet(sb.toString());
        //设置默认请求头
        setHttpHeaders(httpGet);
        httpGet.setConfig(requestConfig);

        //执行HTTP请求
        CloseableHttpClient httpClient = getHttpClient();
        HttpClientContext context = HttpClientContext.create();
        HttpResponse response = httpClient.execute(httpGet, context);
        String s = EntityUtils.toString(response.getEntity());
        return s;
    }

    /**
     * http get 请求
     * @param url 请求URL
     * @return
     * @throws IOException
     */
    public static HttpResponse get(String url) throws IOException {
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .build();


        HttpGet httpGet = new HttpGet(url);
        //设置默认请求头
        //setHttpHeaders(httpGet);
        httpGet.setConfig(requestConfig);

        //执行HTTP请求
        CloseableHttpClient httpClient = getHttpClient();
        HttpClientContext context = HttpClientContext.create();
        HttpResponse response = httpClient.execute(httpGet, context);

        return response;
    }

    /**
     * http get 请求
     * @param url 请求URL
     * @param params 请求参数
     * @return
     * @throws IOException
     */
    public static HttpResponse getDewuAPP(String url, Map<String, String> params) throws IOException {
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).build();


        // 拼接请求URL
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (params != null && params.size() >= 0) {
            Set<String> keySet = params.keySet();
            boolean firstFlag = true;
            for (String key : keySet) {
                if (firstFlag) {
                    if ( StringUtils.isNotBlank(params.get(key)) ) {
                        sb.append("?" + key + "=" + params.get(key));
                        firstFlag = false;
                    }
                }else {
                    if ( StringUtils.isNotBlank(params.get(key))) {
                        sb.append("&" + key + "=" + params.get(key));
                    }
                }
            }
        }

        HttpGet httpGet = new HttpGet(sb.toString());
        //设置默认请求头
        setDewuAppHttpHeaders(httpGet);
        httpGet.setConfig(requestConfig);

        //执行HTTP请求
        CloseableHttpClient httpClient = getHttpClient();
        HttpClientContext context = HttpClientContext.create();
        HttpResponse response = httpClient.execute(httpGet, context);

        return response;
    }

    public static CloseableHttpClient getHttpClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();

    }

    public static String authHeader(String orderno, String secret, int timestamp){
        //拼装签名字符串
        String planText = String.format("orderno=%s,secret=%s,timestamp=%d", orderno, secret, timestamp);

        //计算签名
        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(planText).toUpperCase();

        //拼装请求头Proxy-Authorization的值
        String authHeader = String.format("sign=%s&orderno=%s&timestamp=%d", sign, orderno, timestamp);
        return authHeader;
    }



}
