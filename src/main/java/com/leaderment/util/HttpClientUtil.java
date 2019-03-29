package com.leaderment.util;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;



public class HttpClientUtil {

   // private HttpConnectionManager httpConnectionManager=new HttpConnectionManager();
    private static PoolingHttpClientConnectionManager clientConnectionManager=null;
    private static CloseableHttpClient httpClient=null;
    private static RequestConfig config = RequestConfig.custom()
    		.setConnectTimeout(60000)
    		.setSocketTimeout(60000)
    		.setCookieSpec(CookieSpecs.STANDARD)
    		.build()
    		;

    /**
     * 创建httpclient连接池并初始化
     */
    @PostConstruct
    private void init(){
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();
        clientConnectionManager =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        clientConnectionManager.setMaxTotal(50);
        clientConnectionManager.setDefaultMaxPerRoute(25);
    }
    public static CloseableHttpClient getHttpClient(boolean isSSL){
        if(httpClient == null){
            synchronized (HttpClientUtil.class){
                if(httpClient == null){
                    CookieStore cookieStore = new BasicCookieStore();
                    BasicClientCookie cookie = new BasicClientCookie("sessionID", "######");
                    cookie.setDomain("#####");
                    cookie.setPath("/");
                    cookieStore.addCookie(cookie);
                    HttpClientBuilder httpClientBuilder =HttpClients.custom().setConnectionManager(clientConnectionManager).setDefaultCookieStore(cookieStore)
                    		.setDefaultRequestConfig(config)
                    		.setRetryHandler(httpRequestRetryHandler);
                    		/*.disableAutomaticRetries();*/
                            /*.setRedirectStrategy(new LaxRedirectStrategy());*/
                    if (isSSL) {
        				SSLContext sslContext;
						try {
							sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
								@Override
								public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
									return true;
								}
							     
							 }).build();
							httpClientBuilder=httpClientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext));
						} catch (KeyManagementException e) {
							e.printStackTrace();
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						} catch (KeyStoreException e) {
							e.printStackTrace();
						}
        				
        			}
                    httpClient =httpClientBuilder.build();
                    
                }
            }
        }
        return httpClient;
    }
 
    public static String doGet(String url, Map<String, String> param,Map<String, String> headers) {
    	
        // 创建Httpclient对象
        CloseableHttpClient httpclient=null;
        if(url.indexOf("https:")>-1){
        	httpclient = getHttpClient(true);
        }else{
        	httpclient = getHttpClient(false);
        }
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            //判断是否增加header
            if(headers!=null&&headers.size()>0){
            	for(Map.Entry<String,String> entry:headers.entrySet())
            		httpGet.setHeader(entry.getKey(), entry.getValue());
            }

            // 执行请求
            response = httpclient.execute(httpGet);
           /* for(Header header:response.getAllHeaders()){
         	   System.out.println(header.getName()+":"+header.getValue());
            }*/
           // System.out.println(response.getStatusLine().getStatusCode());
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                //resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                //httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null,null);
    }

    public static String doPost(String url, Map<String, String> param,Map<String, String> headers) {
        // 创建Httpclient对象
    	CloseableHttpClient httpclient;
        if(url.indexOf("https:")>-1){
         httpclient = getHttpClient(true);
        }else{
        	httpclient = getHttpClient(false);
        }
        httpclient = getHttpClient(false);
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //判断是否增加header
            if(headers!=null&&headers.size()>0){
            	for(Map.Entry<String,String> entry:headers.entrySet())
            	httpPost.setHeader(entry.getKey(), entry.getValue());
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null,null);
    }

    /**
     * 请求的参数类型为json
     * @param url
     * @param json
     * @return
     */
    public static String doPostJson(String url, String json,Map<String, String> headers) {
        // 创建Httpclient对象
    	CloseableHttpClient httpclient;
        if(url.indexOf("https:")>-1){
         httpclient = getHttpClient(true);
        }else{
        	httpclient = getHttpClient(false);
        }
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //判断是否增加header
            if(headers!=null&&headers.size()>0){
            	for(Map.Entry<String,String> entry:headers.entrySet())
            		httpPost.setHeader(entry.getKey(), entry.getValue());
            }
            System.out.println(json);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(resultString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    public static String doPutJson(String url, String json,Map<String, String> headers) {
        // 创建Httpclient对象
    	CloseableHttpClient httpclient;
        if(url.indexOf("https:")>-1){
        	httpclient = getHttpClient(true);
        }else{
        	httpclient = getHttpClient(false);
        }
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPut httpPut = new HttpPut(url);
            //判断是否增加header
            if(headers!=null&&headers.size()>0){
            	for(Map.Entry<String,String> entry:headers.entrySet())
            		httpPut.setHeader(entry.getKey(), entry.getValue());
            }
            System.out.println(json);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPut.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPut);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(resultString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    public static String doDelete(String url, String json,Map<String, String> headers) {
        // 创建Httpclient对象
    	CloseableHttpClient httpclient;
		if(url.indexOf("https:")>-1){
	      httpclient = getHttpClient(true);
	    }else{
	      httpclient = getHttpClient(false);
	    }
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Put请求
            HttpDelete httpDetele = new HttpDelete(url);
            //判断是否增加header
            if(headers!=null&&headers.size()>0){
            	for(Map.Entry<String,String> entry:headers.entrySet())
            		httpDetele.setHeader(entry.getKey(), entry.getValue());
            }
            // 执行http请求
            response = httpClient.execute(httpDetele);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    static HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext
                    .adapt(context);
            HttpRequest request = clientContext.getRequest();
            	// 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    };


}