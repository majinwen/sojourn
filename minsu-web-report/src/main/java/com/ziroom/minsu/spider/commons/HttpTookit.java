package com.ziroom.minsu.spider.commons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpTookit {

	public static Header[] responseHeaders = null;

	public static Header[] reqestHeaders = null;

	public static String doGet(String url, List<NameValuePair> params,
			CharEncoding charset,Header[] headers) {

		if (url != null && params != null && params.size() > 0) {
			int l = url.indexOf("?");
			if (l == -1) {
				url = url + "?";
			}
			String pm = "";
			for (NameValuePair p : params) {
				if (StringUtils.isBlank(pm)) {
					pm = p.getName() + "=" + p.getValue();
				} else {
					pm = pm + "&" + p.getName() + "=" + p.getValue();
				}
			}
			url = url + pm;
		}

		String responseContent = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			if (headers!=null&&headers.length>0) {
				for (Header header : headers) {
					httpget.addHeader(header);
				}
			}
			
			reqestHeaders = httpget.getAllHeaders();
			CloseableHttpResponse response = httpclient.execute(httpget);
			responseHeaders = response.getAllHeaders();
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseContent = EntityUtils.toString(entity,
							charset.value());
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public static String doPost(String url, List<NameValuePair> params,
			CharEncoding charset) {
		String responseContent = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(params, charset.value());
			httppost.setEntity(uefEntity);
			reqestHeaders = httppost.getAllHeaders();
			CloseableHttpResponse response = httpclient.execute(httppost);
			responseHeaders = response.getAllHeaders();
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseContent = EntityUtils.toString(entity,
							charset.value());
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public static String doPost(String url, String body, CharEncoding charset) {
		String responseContent = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity uefEntity = new StringEntity(body, charset.value());
			httppost.setEntity(uefEntity);
			reqestHeaders = httppost.getAllHeaders();
			CloseableHttpResponse response = httpclient.execute(httppost);
			responseHeaders = response.getAllHeaders();
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseContent = EntityUtils.toString(entity,
							charset.value());
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
}
