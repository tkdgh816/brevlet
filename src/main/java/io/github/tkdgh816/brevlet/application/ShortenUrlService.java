package io.github.tkdgh816.brevlet.application;

import io.github.tkdgh816.brevlet.domain.key.ShortenUrlKeyGenerator;
import io.github.tkdgh816.brevlet.domain.key.KeyGenerationFailedException;
import io.github.tkdgh816.brevlet.domain.url.ShortenUrl;
import io.github.tkdgh816.brevlet.domain.url.ShortenUrlRepository;
import io.github.tkdgh816.brevlet.dto.url.CreateShortenUrlRequest;
import io.github.tkdgh816.brevlet.dto.url.ShortenUrlInfoResponse;
import io.github.tkdgh816.brevlet.domain.url.ShortenUrlNotFoundException;
import io.github.tkdgh816.brevlet.dto.url.ShortenUrlResponse;
import org.springframework.stereotype.Service;

@Service
public class ShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;
    private final ShortenUrlKeyGenerator shortenUrlKeyGenerator;

    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository, ShortenUrlKeyGenerator shortenUrlKeyGenerator) {
        this.shortenUrlRepository = shortenUrlRepository;
        this.shortenUrlKeyGenerator = shortenUrlKeyGenerator;
    }

    private static final int MAX_GENERATE_COUNT = 5;

    public ShortenUrlResponse generateShortenUrl(CreateShortenUrlRequest request) {
        // shortenUrlKey 및 shortenUrl 생성
        String originalUrl = request.originalUrl();
        String shortenUrlKey = getUniqueShortenUrlKey();

        // ShortenUrl 도메인 객체 생성 및 레포지토리 저장
        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        shortenUrlRepository.save(shortenUrl);

        // 요청 응답
        return ShortenUrlResponse.from(shortenUrl);
    }

    public ShortenUrlInfoResponse getShortenUrlInfoByKey(String shortenUrlKey) {
        // 레포지토리에서 key에 해당하는 ShortenUrl 요청
        ShortenUrl shortenUrl = getByKey(shortenUrlKey);

        // 요청 응답
        return ShortenUrlInfoResponse.from(shortenUrl);
    }

    public String getOriginalUrlByKey(String shortenUrlKey) {
        //
        ShortenUrl shortenUrl = getByKey(shortenUrlKey);
        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.save(shortenUrl);
        return shortenUrl.getOriginalUrl();
    }

    private ShortenUrl getByKey(String shortenUrlKey) {
        return shortenUrlRepository.findByKey(shortenUrlKey)
                .orElseThrow(() -> new ShortenUrlNotFoundException("단축 URL(%s)를 찾을 수 없습니다.".formatted(shortenUrlKey)));
    }

    private String getUniqueShortenUrlKey() {
        String shortenUrlKey = "";
        int count = 0;
        while (count++ < MAX_GENERATE_COUNT) {
            shortenUrlKey = shortenUrlKeyGenerator.generate();
            if (shortenUrlRepository.findByKey(shortenUrlKey).isEmpty()) {
                break;
            }
        }

        if (count > MAX_GENERATE_COUNT) {
            throw new KeyGenerationFailedException("단축 URL을 만들 수 없습니다.");
        }

        return shortenUrlKey;
    }
}
