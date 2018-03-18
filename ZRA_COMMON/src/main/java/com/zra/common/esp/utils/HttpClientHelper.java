package com.zra.common.esp.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yuyi
 *
 */
public class HttpClientHelper {
	private static final int[] DEFAULT_ACCEPTABLE_CODES = new int[] {
			HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_NOT_MODIFIED,
			HttpURLConnection.HTTP_MOVED_TEMP,
			HttpURLConnection.HTTP_MOVED_PERM, HttpURLConnection.HTTP_ACCEPTED };
	private static int connectionTimeout = 1000 * 30 *2;
	private static int readTimeout = 1000 * 30 *2;
	private static boolean followRedirects = true;

	public static String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
	public static String CONTENT_TYPE_JSON = "application/json";
	public static String CONTENT_TYPE_XML = "text/xml";

	public static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	public static String hmacSha1(byte[] key, byte[] data)
			throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data);
		return new String(Base64.encode(rawHmac));
	}

	public static String jsonCallWithHmacSha1(String url, String jsonStr,
			String key) throws InvalidKeyException, NoSuchAlgorithmException,
			IOException {
		String signature = HttpClientHelper.hmacSha1(key.getBytes(),
				jsonStr.getBytes("utf-8"));

		Map<String, String> headers = new HashMap<String, String>();
		//headers.put("Content-Signature", "signatureAlg=HmacSHA1;signature=" + signature);
		headers.put("Content-Signature", "HMAC-SHA1 " + signature);
		return HttpClientHelper.jsonCall(url, headers, jsonStr);
	}

	public static String jsonCall(String url, Map<String, String> headers,
			String jsonStr) throws IOException {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.put("Content-Type", CONTENT_TYPE_JSON);
		headers.put("Accept", "application/json");
		return new String(call(url, "POST", headers, jsonStr.getBytes("utf-8")), "utf-8");
	}

	public static byte[] formCall(String url, String method,
			Map<String, String> headers, Map<String, String> params)
			throws IOException {
		StringBuffer paramData = new StringBuffer();
		int i = 0;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (i > 0) {
				paramData.append("&");
			}
			paramData.append(entry.getKey()).append("=")
					.append(URLDecoder.decode(entry.getValue(), "utf-8"));
			i++;
		}
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.put("Content-Type", CONTENT_TYPE_FORM);
		return call(url, method, headers, paramData.toString().getBytes());
	}

	public static byte[] call(String url, String method,
			Map<String, String> headers, byte[] input) throws IOException {
		HttpURLConnection connection = null;
		try {
			final URL jsonServiceURL = new URL(url);
			connection = (HttpURLConnection) jsonServiceURL.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			connection.setReadTimeout(readTimeout);
			connection.setConnectTimeout(connectionTimeout);
			connection.setInstanceFollowRedirects(followRedirects);

			for (Map.Entry<String, String> header : headers.entrySet()) {
				connection.setRequestProperty(header.getKey(),
						header.getValue());
			}

			if (input != null && input.length > 0) {
				connection.setRequestProperty("Content-Length",
						Integer.toString(input.length));
				connection.getOutputStream().write(input);
				connection.getOutputStream().flush();
				connection.getOutputStream().close();
			}

			final int responseCode = connection.getResponseCode();

			boolean acceptable = false;
			for (final int acceptableCode : DEFAULT_ACCEPTABLE_CODES) {
				if (responseCode == acceptableCode) {
					acceptable = true;
					break;
				}
			}

			if (acceptable) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				copy(connection.getInputStream(), output);
				return output.toByteArray();
			} else {
				throw new IOException(responseCode + " "
						+ connection.getResponseMessage());
			}
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static long copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

}
