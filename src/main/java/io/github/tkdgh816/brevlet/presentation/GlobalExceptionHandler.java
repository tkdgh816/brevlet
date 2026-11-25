package io.github.tkdgh816.brevlet.presentation;

import io.github.tkdgh816.brevlet.domain.ItemNotFoundException;
import io.github.tkdgh816.brevlet.domain.key.KeyGenerationFailedException;
import io.github.tkdgh816.brevlet.domain.url.ShortenUrlUnavailableException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String NOT_FOUND_PAGE = "/page/notfound.html";
    private static final String EXPIRED_PAGE = "/page/expired.html";

    @ExceptionHandler(value = ItemNotFoundException.class, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<ClassPathResource> handleItemNotFoundPage() {
        return htmlPage(HttpStatus.NOT_FOUND, NOT_FOUND_PAGE);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFoundException(ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(KeyGenerationFailedException.class)
    public ResponseEntity<String> handleKeyGenerationFailedException(KeyGenerationFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ShortenUrlUnavailableException.class)
    public ResponseEntity<ClassPathResource> handleShortenUrlExpiredException() {
        return htmlPage(HttpStatus.GONE, EXPIRED_PAGE);
    }

    private static ResponseEntity<ClassPathResource> htmlPage(HttpStatus status, String path) {
        return ResponseEntity.status(status)
                .contentType(MediaType.TEXT_HTML)
                .body(new ClassPathResource("static" + path));
    }
}
