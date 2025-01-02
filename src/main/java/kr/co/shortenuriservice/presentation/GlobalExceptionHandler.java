package kr.co.shortenuriservice.presentation;

import kr.co.shortenuriservice.domain.LackOfShortenUrlKeyException;
import kr.co.shortenuriservice.domain.NotFoundShortenUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundShortenUrlException.class)
	public ResponseEntity<String> handleNotFoundShortenUrlException(NotFoundShortenUrlException ex) {
		return new ResponseEntity<>("단축 URL을 찾지 못했습니다.", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(LackOfShortenUrlKeyException.class)
	public ResponseEntity<String> handleLackOfShortenUrlKeyException(LackOfShortenUrlKeyException ex) {
		return new ResponseEntity<>("단축 URL 자원이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
