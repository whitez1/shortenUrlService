package kr.co.shortenuriservice.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import kr.co.shortenuriservice.application.SimpleShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenUrlRestController {

	private SimpleShortenUrlService simpleShortenUrlService;

	@Autowired
	public ShortenUrlRestController(SimpleShortenUrlService simpleShortenUrlService) {
		this.simpleShortenUrlService = simpleShortenUrlService;
	}

	@RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
	public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(@Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto) {
		ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
		return ResponseEntity.ok(shortenUrlCreateResponseDto);
	}

	@RequestMapping(value = "/{shortenUrlKey}", method = RequestMethod.GET)
	public ResponseEntity<?> redirectShortenUrl(@PathVariable String shortenUrlKey) throws URISyntaxException {
		String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

		URI redirectUri = new URI(originalUrl);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(redirectUri);

		return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}

	@RequestMapping(value = "/shortenUrl/{shortenUrlKey}", method = RequestMethod.GET)
	public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(@PathVariable String shortenUrlKey) {
		ShortenUrlInformationDto shortenUrlInformationDto = simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey);
		return ResponseEntity.ok(shortenUrlInformationDto);
	}

}
