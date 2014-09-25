package com.guilhermehott.http_post_file;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author guilherme.hott
 * 
 */
public class HttpXMLDataPost {
	static Properties props;

	public static void main(String[] args) {
		String fileName = "C:/storage/teste.xml";

		boolean success = XMLDataPost(fileName);
		System.out.println(success);
	}

	private static boolean XMLDataPost(String fileName) {

		boolean success = false;
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("proxybsb.cast.com.br", 3128), new UsernamePasswordCredentials("guilherme.hott",
				"getOcwcd14"));
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

		try {

			HttpHost target = new HttpHost("http://www.servidor2.danfeonline.com.br");
			HttpHost proxy = new HttpHost("proxybsb.cast.com.br", 3128);

			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

			// HttpClient httpclient = HttpClientBuilder.create().build();

			HttpPost httpPost = new HttpPost("/");
			httpPost.setConfig(config);

			File file = new File(fileName.trim());

			InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);
			reqEntity.setContentType("multipart/form-data");
			reqEntity.setChunked(true);

			httpPost.setEntity(reqEntity);

			System.out.println("Executing request " + httpPost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(target, httpPost);
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
			httpclient.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return success;

	}
}
