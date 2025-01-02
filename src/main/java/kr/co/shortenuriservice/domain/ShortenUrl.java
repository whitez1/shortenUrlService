package kr.co.shortenuriservice.domain;

import java.util.Random;

public class ShortenUrl {
	private String originalUrl;
	private String shortenUrlKey;
	private Long redirectCount;

	public ShortenUrl(String originalUrl, String shortenUrlKey) {
		this.originalUrl = originalUrl;
		this.shortenUrlKey = shortenUrlKey;
		this.redirectCount = 0L;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public String getShortenUrlKey() {
		return shortenUrlKey;
	}

	public Long getRedirectCount() {
		return redirectCount;
	}

	public static String generateShortenUrlKey() {
		String base56Characters = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
		Random random = new Random();
		StringBuilder shortenUrlKey = new StringBuilder();

		for(int count = 0; count < 8; count++) {
			int base64CharactersIndex = random.nextInt(0, base56Characters.length());
			char base56Character = base56Characters.charAt(base64CharactersIndex);
			shortenUrlKey.append(base56Character);
		}

		return shortenUrlKey.toString();
	}

	public void increaseRedirectCount() {
		this.redirectCount += 1;
	}
}
