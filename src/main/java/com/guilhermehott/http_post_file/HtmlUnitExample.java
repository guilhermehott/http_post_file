package com.guilhermehott.http_post_file;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author guilherme.hott
 *
 */
public class HtmlUnitExample {
	public static void main(String[] args) {
		final WebClient webClient = new WebClient(BrowserVersion.CHROME, "proxybsb.cast.com.br", 3128);

	    //set proxy username and password 
	    final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
	    credentialsProvider.addNTLMCredentials("guilherme.hott", "getOcwcd14", "proxybsb.cast.com.br", 3128, "BSBDSKDOPGPS208", "CASTBSB");

	    HtmlPage page = null;
		try {
			page = webClient.getPage("http://www.guilhermehott.com/");
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(page.getTitleText());
	    webClient.closeAllWindows();
	}
}
