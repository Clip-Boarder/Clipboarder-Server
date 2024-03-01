package com.clipboarder.clipboarder.security;

import com.clipboarder.clipboarder.entity.ClipboarderUser;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class OAuthAttributes {
    private Map<String, Object> attributes;     // OAuth2 반환하는 유저 정보
    private String nameAttributesKey;
    private String name;
    private String email;
    private String profileImageUrl;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributesKey,
                           String name, String email, String profileImageUrl) {
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    public static OAuthAttributes of(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .profileImageUrl(String.valueOf(attributes.get("picture")))
                .attributes(attributes)
                .nameAttributesKey("sub")
                .build();
    }

    public ClipboarderUser toEntity() {
        return ClipboarderUser.builder()
                .email(email)
                .name(name)
                .role("ROLE_USER")
                .build();
    }
}
