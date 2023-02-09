package com.niugiaogiao.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RestTemplateConf {

	@Bean
	public HttpClientConnectionManager httpClientConnectionManager() {
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		poolingHttpClientConnectionManager.setMaxTotal(100);
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(50);
		return poolingHttpClientConnectionManager;
	}

	@Bean
	public HttpClient httpClient(HttpClientConnectionManager httpClientConnectionManager) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setConnectionManager(httpClientConnectionManager);
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));

		// set request header
		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader("Content-Type", "application/json"));
		headers.add(new BasicHeader("Accept", "*/*"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate, br"));
		headers.add(new BasicHeader("Connection", "keep-alive"));
		httpClientBuilder.setDefaultHeaders(headers);
		return httpClientBuilder.build();
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
		try {
			TrustStrategy acceptingTrustStrategy = ((x509Certificates, authType) -> true);
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
			CloseableHttpClient sshHttpClient = httpClientBuilder.build();
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setHttpClient(sshHttpClient);
			factory.setConnectTimeout(3000);
			return factory;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(clientHttpRequestFactory);
		return restTemplate;
	}
}
