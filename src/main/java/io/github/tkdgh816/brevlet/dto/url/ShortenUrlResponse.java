package io.github.tkdgh816.brevlet.dto.url;

import io.github.tkdgh816.brevlet.domain.url.ShortenUrl;

public record ShortenUrlResponse(
        String originalUrl,
        String shortenUrlKey
) {

    public static ShortenUrlResponse from(ShortenUrl shortenUrl){
        return new ShortenUrlResponse(shortenUrl.getOriginalUrl(), shortenUrl.getShortenUrlKey());
    }
}
