package kr.co.shortenuriservice.application;

import kr.co.shortenuriservice.domain.LackOfShortenUrlKeyException;
import kr.co.shortenuriservice.domain.NotFoundShortenUrlException;
import kr.co.shortenuriservice.domain.ShortenUrl;
import kr.co.shortenuriservice.domain.ShortenUrlRepository;
import kr.co.shortenuriservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenuriservice.presentation.ShortenUrlCreateResponseDto;
import kr.co.shortenuriservice.presentation.ShortenUrlInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleShortenUrlService {

	private ShortenUrlRepository shortenUrlRepository;

	@Autowired
	public SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
		this.shortenUrlRepository = shortenUrlRepository;
	}

	public ShortenUrlCreateResponseDto generateShortenUrl(ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {

		String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
		String shortenUrlKey = getUniqueShortenUrlKey();

		ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
		shortenUrlRepository.saveShortenUrl(shortenUrl);

		ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(shortenUrl);

		return shortenUrlCreateResponseDto;
	}

	public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
		ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

		if(null == shortenUrl)
			throw new NotFoundShortenUrlException();

		shortenUrl.increaseRedirectCount();
		shortenUrlRepository.saveShortenUrl(shortenUrl);

		String originalUrl = shortenUrl.getOriginalUrl();

		return originalUrl;
	}

	public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
		ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

		if(null == shortenUrl)
			throw new NotFoundShortenUrlException();

		ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);

		return shortenUrlInformationDto;
	}

	public String getUniqueShortenUrlKey() {
		final int MAX_RETRY_COUNT = 5;
		int count = 0;

		while(count++ < MAX_RETRY_COUNT) {
			String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
			ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

			if(null == shortenUrl)
				return shortenUrlKey;
		}

		throw new LackOfShortenUrlKeyException();
	}

}
