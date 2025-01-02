package kr.co.shortenuriservice.domain;

import kr.co.shortenuriservice.domain.ShortenUrl;

public interface ShortenUrlRepository {
	void saveShortenUrl(ShortenUrl shortenUrl);
	ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey);
}
