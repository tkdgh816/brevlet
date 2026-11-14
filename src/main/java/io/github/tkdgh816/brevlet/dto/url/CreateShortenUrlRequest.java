package io.github.tkdgh816.brevlet.dto.url;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreateShortenUrlRequest(
        @NotNull
        @URL(regexp = "^https?://(?:[\\w-]+(?:\\.[\\w-]+)*\\.[a-zA-Z]{2,}|localhost|(?:\\d{1,3}\\.){3}\\d{1,3})(?::\\d{1,5})?(?:[/?#]\\S*)?$")
        String originalUrl
) {

}
