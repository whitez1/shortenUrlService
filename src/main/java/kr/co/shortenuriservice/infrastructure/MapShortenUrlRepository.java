package kr.co.shortenuriservice.infrastructure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kr.co.shortenuriservice.domain.ShortenUrl;
import kr.co.shortenuriservice.domain.ShortenUrlRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MapShortenUrlRepository implements ShortenUrlRepository {

	private Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();

	@Override
	public void saveShortenUrl(ShortenUrl shortenUrl) {
		shortenUrls.put(shortenUrl.getShortenUrlKey(), shortenUrl);
	}

	@Override
	public ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey) {
		ShortenUrl shortenUrl = shortenUrls.get(shortenUrlKey);
		return shortenUrl;
	}
}
