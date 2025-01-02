package kr.co.shortenuriservice.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import kr.co.shortenuriservice.domain.NotFoundShortenUrlException;
import kr.co.shortenuriservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenuriservice.presentation.ShortenUrlCreateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleShortenUrlServiceTest {

	@Autowired
	SimpleShortenUrlService simpleShortenUrlService;

	@Test
	@DisplayName("URL을 단축한 후 단축된 URL 키로 조회하면 원래 URL이 조회되어야 한다.")
	void shortenUrlAddTest() {
		String expectedOriginalUrl = "https://www.google.com/";
		ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(expectedOriginalUrl);

		ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
		String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

		String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

		assertTrue(originalUrl.equals(expectedOriginalUrl));
	}

	@Test
	@DisplayName("존재하지 않는 단축 URL을 조회하면 NotFoundShortenUrlException 예외가 발생해야 한다.")
	void throwNotFoundShortenUrlExceptionTest() {
		String notExistShortenUrlKey = "11111111";

		assertThrows(NotFoundShortenUrlException.class, () -> {
			simpleShortenUrlService.getOriginalUrlByShortenUrlKey(notExistShortenUrlKey);
		});

		assertThrows(NotFoundShortenUrlException.class, () -> {
			simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(notExistShortenUrlKey);
		});
	}

}
