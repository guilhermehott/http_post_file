package com.guilhermehott.http_post_file;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author guilherme.hott
 * 
 */
public class HttpXMLDataPost {
	static Properties props;

	public static void main(String[] args) {
		String fileName = "C:/Users/Guilherme/Downloads/CAMMINARE - NFE EM XML/teste.xml";

		boolean success = XMLDataPost(fileName);
		System.out.println(success);
	}

	private static boolean XMLDataPost(String fileName) {

		boolean success = false;

		try {

			HttpClient httpclient = HttpClientBuilder.create().build();

//			HttpPost httpPost = new HttpPost("http://www.servidor2.danfeonline.com.br");
			HttpGet httpGet = new HttpGet("http://www.servidor2.danfeonline.com.br");

			File file = new File(fileName.trim());

			InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);
			reqEntity.setContentType("multipart/form-data");
			reqEntity.setChunked(true);

//			httpPost.setEntity(reqEntity);

			System.out.println("Executing request " + httpGet.getRequestLine());
			CloseableHttpResponse response = (CloseableHttpResponse) httpclient.execute(httpGet);
			HttpEntity resEntity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == 200) {
				success = true;
			}
			if (resEntity != null) {
				System.out.println("Response content length: " + resEntity.getContentLength());
				System.out.println("Chunked?: " + resEntity.isChunked());
			}
			EntityUtils.consume(resEntity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return success;

	}
}
