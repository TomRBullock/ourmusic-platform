package com.ourmusic.platform.model;

import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class UserSpotifyCredentials extends BaseDocument {
    private @Indexed(unique = true) String userId;

    private String  accessToken;
    private String  tokenType;
    private String  scope;
    private String  refreshToken;

    private Integer expiresIn;
    private Instant accessTokenRequestDate;
    private Instant accessTokenExpireDate;
}
