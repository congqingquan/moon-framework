package org.moon.framework.core.utils.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 明月   on 2018-11-07 / 18:56
 *
 * @Description: HTTP通讯的工具类
 */
public class HttpClientUtils {

    /**
     * UTF-8 Encode
     */
    public static final String ENCODE_UTF8 = "UTF-8";

    /**
     * Content-Type
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * application/json
     */
    public static final String CONTENT_TYPE_APPLICATION_JSON = ContentType.APPLICATION_JSON.getMimeType();

    /**
     * application/xml
     */
    public static final String CONTENT_TYPE_APPLICATION_XML = ContentType.APPLICATION_XML.getMimeType();

    /**
     * application/x-www-form-urlencoded
     */
    public static final String CONTENT_TYPE_APPLICATION_FORM_URLENCODED = ContentType.APPLICATION_FORM_URLENCODED
            .getMimeType();

    /**
     * 默认请求超时时长
     */
    private static final Integer DEFAULT_SOCKET_TIMEOUT = 2000;

    /**
     * 默认传输超时时长
     */
    private static final Integer DEFAULT_CONNECT_TIMEOUT = 2000;

    /**
     * 默认配置
     */
    private static final RequestConfig DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
            .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();

    /**
     * 无法实例化
     */
    private HttpClientUtils() {
    }

    /**
     * Post请求传输JSON数据
     */
    public static String httpPostJson(String url, Object jsonParam) {
        return httpPostJson(url, jsonParam, null);
    }

    public static String httpPostJson(String url, Object jsonParam, Map<String, String> headers) {
        return basicHttpPostMethod(url, JSON.toJSONString(jsonParam), CONTENT_TYPE_APPLICATION_JSON, headers);
    }

    /**
     * Post请求传输JSON数据
     */
    public static String httpPostJson(String url, String jsonParam) {
        return httpPostJson(url, jsonParam, null);
    }

    public static String httpPostJson(String url, String jsonParam, Map<String, String> headers) {
        // 检查JSON参数的格式是否正确
        try {
            JSON.parse(jsonParam);
        } catch (JSONException e) {
            throw new IllegalArgumentException("String类型的Json参数格式有误,请检查");
        }
        return basicHttpPostMethod(url, jsonParam, CONTENT_TYPE_APPLICATION_JSON, headers);
    }

    /**
     * Post请求传输表单数据
     * 注意：参数格式 -> name=Jack&sex=1&type=2,这样的k/v形式,分隔符为&
     */
    public static String httpPostForm(String url, String formParam) {
        return httpPostForm(url, formParam, null);
    }

    public static String httpPostForm(String url, String formParam, Map<String, String> headers) {
        return basicHttpPostMethod(url, formParam, CONTENT_TYPE_APPLICATION_FORM_URLENCODED, headers);
    }

    /**
     * 通用的Post请求函数
     *
     * @param url         路径
     * @param postData    请求参数
     * @param contentType ContentType Header
     * @param headers     请求头
     * @return
     */
    private static String basicHttpPostMethod(String url, String postData, String contentType,
                                              Map<String, String> headers) {
        // Connection Object
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // Post Request
        HttpPost httpPost = new HttpPost(url);
        // Add headers
        addHeaders(httpPost, headers);
        // 设置请求和传输超时时间
        httpPost.setConfig(DEFAULT_REQUEST_CONFIG);
        try {
            if (null != postData) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(postData, ENCODE_UTF8);
                entity.setContentEncoding(ENCODE_UTF8);
                entity.setContentType(contentType);
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功,并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返的响应数据
                return EntityUtils.toString(result.getEntity(), ENCODE_UTF8);
            } else {
                System.err.println("post请求提交失败:" + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * Get请求,若有URL参数请拼接在URL路径中
     */
    public static String httpGet(String url) {
        return httpGet(url, null);
    }

    public static String httpGet(String url, Map<String, String> headers) {
        // Connection Object
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // Post Request
        HttpGet httpGet = new HttpGet(url);
        // Add headers
        addHeaders(httpGet, headers);
        // 设置请求和传输超时时间
        httpGet.setConfig(DEFAULT_REQUEST_CONFIG);
        try {
            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // 请求发送成功,并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返的响应数据
                return EntityUtils.toString(response.getEntity(), ENCODE_UTF8);
            } else {
                System.err.println("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * 添加请求头
     */
    private static void addHeaders(final HttpRequestBase request, Map<String, String> headers) {
        if (null == headers || headers.size() < 1)
            return;
        else
            headers.keySet().stream().forEach((headerName) -> {
                request.addHeader(headerName, headers.get(headerName));
            });
    }
}