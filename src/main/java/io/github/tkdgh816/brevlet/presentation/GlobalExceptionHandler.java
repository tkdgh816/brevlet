package io.github.tkdgh816.brevlet.presentation;

import io.github.tkdgh816.brevlet.domain.ItemNotFoundException;
import io.github.tkdgh816.brevlet.domain.key.KeyGenerationFailedException;
import io.github.tkdgh816.brevlet.domain.url.ShortenUrlUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFoundException(ItemNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(KeyGenerationFailedException.class)
    public ResponseEntity<String> handleKeyGenerationFailedException(KeyGenerationFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ShortenUrlUnavailableException.class)
    public ResponseEntity<String> handleShortenUrlExpiredException(ShortenUrlUnavailableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }
}
