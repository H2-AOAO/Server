package kr.sesac.aoao.server.user.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.sesac.aoao.server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Profile extends BaseEntity {

    private static final int RESOURCE_KEY_LENGTH = 255;
    private static final int RESOURCE_URL_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String resourceKey;

    @Column(nullable = false, length = RESOURCE_URL_LENGTH)
    private String resourceUrl;

    public Profile(String resourceKey, String resourceUrl) {
        validateResourceKey(resourceKey);
        validateResourceUrl(resourceUrl);
        this.resourceKey = resourceKey;
        this.resourceUrl = resourceUrl;
    }

    private void validateResourceKey(String resourceKey) {
        if (resourceKey.length() > RESOURCE_KEY_LENGTH) {
            throw new IllegalArgumentException(String.format("리소스의 key 의 길이가 %d자 이상입니다. [%s]", RESOURCE_KEY_LENGTH, resourceKey));
        }
    }

    private void validateResourceUrl(String resourceUrl) {
        if (resourceUrl.length() > RESOURCE_URL_LENGTH) {
            throw new IllegalArgumentException(String.format("리소스의 URL 의 길이가 %d자 이상입니다. [%s]", RESOURCE_URL_LENGTH, resourceUrl));
        }
    }
}
