package io.github.tkdgh816.brevlet.presentation;

import io.github.tkdgh816.brevlet.application.ShortenUrlService;
import io.github.tkdgh816.brevlet.domain.key.ShortenUrlKeyRule;
import io.github.tkdgh816.brevlet.dto.url.CreateShortenUrlRequest;
import io.github.tkdgh816.brevlet.dto.url.ShortenUrlInfoResponse;
import io.github.tkdgh816.brevlet.dto.url.ShortenUrlResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ShortenUrlController {
    private final ShortenUrlService shortenUrlService;

    public ShortenUrlController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<ShortenUrlResponse> createShortenUrl(@RequestBody @Valid CreateShortenUrlRequest request) {
        ShortenUrlResponse response = shortenUrlService.generateShortenUrl(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortenUrlKey}")
    public ResponseEntity<?> redirectShortenUrl(@PathVariable @Pattern(regexp = ShortenUrlKeyRule.KEY_REGEX) String shortenUrlKey) throws URISyntaxException {
        String originalUrl = shortenUrlService.getOriginalUrlByKey(shortenUrlKey);
        URI redirectUri = new URI(originalUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/shorten-url/{shortenUrlKey}")
    public ResponseEntity<ShortenUrlInfoResponse> getShortenUrlInfo(@PathVariable @Pattern(regexp = ShortenUrlKeyRule.KEY_REGEX) String shortenUrlKey) {
        ShortenUrlInfoResponse response = shortenUrlService.getShortenUrlInfoByKey(shortenUrlKey);
        return ResponseEntity.ok().body(response);
    }
}
