package com.yz.common.core.utils;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.security.KeyStore;
import java.util.*;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

/**
 * http常用处理工具类
 * @author yangzhao at 2015年12月8日
 *
 */
public class HttpUtil {

	private static final Logger logger=LogManager.getLogger(HttpUtil.class);

	private final static String CHARSET_NAME = "UTF-8";


	/**
	 * request对象转字节
	 * @param request
	 * @return
	 */
	public static byte[] getRequestByteArray(HttpServletRequest request) {
		byte[] dataOrigin=null;
		InputStream is = null;
		try {
			int contentLength = request.getContentLength();
			if (contentLength <= 0) {
				return null;
			}
			dataOrigin= new byte[contentLength];
			is = request.getInputStream();
			is.read(dataOrigin);
		} catch (Exception e) {
			logger.error("request对象转字节失败！", e);
		}finally {
			try {
				if (is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataOrigin;
	}

	public static String getRequest(String url) {
		try {
			HttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			HttpEntity entity =  response.getEntity();
			String result = EntityUtils.toString(entity,CHARSET_NAME);
			return result;
		} catch (Exception e) {
			logger.error("http req error", e);
			return null;
		}
	}

	public static String postRequest(String url, List<NameValuePair> params) {
		try {
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			if(params!=null){
				post.setEntity(new UrlEncodedFormEntity(params, CHARSET_NAME));
			}
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder res = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null) {
				res.append(line);
			}
			return res.toString();
		} catch (Exception e) {
			logger.error("http req error=" + url, e);
			return null;
		}
	}

	public static String sendPost(String url, String cnt) {
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		StringEntity postEntity = new StringEntity(cnt,CHARSET_NAME);
		httpPost.setEntity(postEntity);
		httpPost.addHeader("Content-Type", "text/xml");
		HttpClient client = HttpClients.createDefault();
		try {
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (ConnectionPoolTimeoutException e) {
			logger.error("http get throw ConnectionPoolTimeoutException(wait time out)");
		} catch (ConnectTimeoutException e) {
			logger.error("http get throw ConnectTimeoutException");
		} catch (SocketTimeoutException e) {
			logger.error("http get throw SocketTimeoutException");
		} catch (Exception e) {
			logger.error("http get throw Exception");
		} finally {
			httpPost.abort();
		}
		return result;
	}

	public static String sendPost(String url, String cnt,Map<String,String> headers) {
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		StringEntity postEntity = new StringEntity(cnt,CHARSET_NAME);
		httpPost.setEntity(postEntity);
		httpPost.addHeader("Content-Type", "text/xml");
		HttpClient client = HttpClients.createDefault();
		if (!CollectionUtils.isEmpty(headers)){
			headers.forEach((k,v)->{
				httpPost.addHeader(k,v);
			});
		}
		try {
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (ConnectionPoolTimeoutException e) {
			logger.error("http get throw ConnectionPoolTimeoutException(wait time out)");
		} catch (ConnectTimeoutException e) {
			logger.error("http get throw ConnectTimeoutException");
		} catch (SocketTimeoutException e) {
			logger.error("http get throw SocketTimeoutException");
		} catch (Exception e) {
			logger.error("http get throw Exception");
		} finally {
			httpPost.abort();
		}
		return result;
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static Map<String, String> paraseURI(String uri) {
		try {
			if (!uri.startsWith("?")) {
				uri = "?".concat(uri);
			}
			HashMap<String, String> map = new HashMap<String, String>();
			List<NameValuePair> parse = URLEncodedUtils.parse(new URI(uri),CHARSET_NAME);
			for (NameValuePair nameValuePair : parse) {
				map.put(nameValuePair.getName(), nameValuePair.getValue());
			}
			return map;
		} catch (Exception e) {
			logger.error("parase uri error", e);
		}
		return null;
	}

	/**
	 * 表单提交post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPostForm(String url,List<NameValuePair> params){
		HttpClient client=HttpClients.createDefault();
		HttpPost post=new HttpPost(url);
		post.setHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		try {
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			String responseEntity=EntityUtils.toString(response.getEntity());
			return responseEntity;
		} catch (Exception e) {
			logger.error("http请求失败 ----",e);
		}
		return null;
	}

	/**
	 * 表单提交post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPostForm(String url,List<NameValuePair> params,Map<String,String> headers){
		HttpClient client=HttpClients.createDefault();
		HttpPost post=new HttpPost(url);
		post.setHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

		if (!CollectionUtils.isEmpty(headers)){
			headers.forEach((k,v)->{
				post.addHeader(k,v);
			});
		}

		try {
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			String responseEntity=EntityUtils.toString(response.getEntity());
			return responseEntity;
		} catch (Exception e) {
			logger.error("http请求失败 ----",e);
		}
		return null;
	}

	/**
	 * https请求
	 * @param certificatePath
	 * @param secretKey
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static CloseableHttpClient createSSL(String certificatePath,String secretKey){
		KeyStore keyStore=null;
		CloseableHttpClient httpclient=null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			 FileInputStream instream = new FileInputStream(new File(certificatePath));
		        try {
		            keyStore.load(instream, secretKey.toCharArray());
		        } finally {
		            instream.close();
		        }

		        // Trust own CA and all self-signed certs
		        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, secretKey.toCharArray()).build();
		        // Allow TLSv1 protocol only
		        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
       return httpclient;
	}
	/**
	 * 将请求参数Map类型转换为NameValuePair
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<NameValuePair> mapChangeNameValuePair(Map<String,String> map){
		Set keySet = map.keySet();
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		Iterator<String> iterator=keySet.iterator();
		while(iterator.hasNext()){
			String name=iterator.next();
			String value=map.get(name);
			nameValuePairs.add(new BasicNameValuePair(name, value));
		}
		return nameValuePairs;
	}
	/**
	 * Get方式获取图片
	 * @param url
	 * @param path 文件路径
	 * @return
	 */
	public static boolean downloadFile(String url,String path){
		boolean flag=false;
		HttpClient client = HttpClients.createDefault();
		HttpGet get=new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			FileOutputStream fos=new FileOutputStream(new File(path));
			entity.writeTo(fos);
			fos.close();
			flag=true;
		} catch (Exception e) {
			logger.error("图片保存失败----",e);
		}
		return flag;
	}
	/**
	 * 拼接请求参数
	 * @param map
	 * @return
	 */
	public static String mosaicRequestParams(Map<String,String> map){
		StringBuilder builder=new StringBuilder();
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = map.get(key);
			builder.append(key+"="+value+"&");
		}
		String params=builder.toString();
		params=params.substring(0, params.length()-1);
		return params;
	}

}
