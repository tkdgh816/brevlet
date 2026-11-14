package io.github.tkdgh816.brevlet.dto.url;

import io.github.tkdgh816.brevlet.domain.url.ShortenUrl;

public record ShortenUrlInfoResponse(
        String originalUrl,
        String shortenUrlKey,
        Long redirectCount
) {
    public static ShortenUrlInfoResponse from(ShortenUrl shortenUrl){
        return new ShortenUrlInfoResponse(shortenUrl.getOriginalUrl(), shortenUrl.getShortenUrlKey(), shortenUrl.getRedirectCount());
    }
}
