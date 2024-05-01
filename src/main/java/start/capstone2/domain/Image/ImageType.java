package start.capstone2.domain.Image;

import lombok.Getter;

@Getter
public enum ImageType {

    PORTFOLIO_CARD_IMAGE("포트폴리오 카드 이미지"), PORTFOLIO_IMAGE("포트폴리오 이미지");

    private final String description;

    private ImageType(String description) {
        this.description = description;
    }
}
