package com.ourmusic.platform.service.spotify;

import com.ourmusic.platform.model.UserSpotifyCredentials;
import com.ourmusic.platform.repository.UserSpotifyCredentialsRepository;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ourmusic.spotify.client.SpotifyClient;
import ourmusic.spotify.client.vo.AuthURIResponse;

import java.time.Instant;
import java.util.Optional;

@Service
public class SpotifyAuthorizationServiceImpl extends SpotifyBaseService implements SpotifyAuthorizationService {

    @Autowired private UserSpotifyCredentialsRepository userSpotifyCredentialsRepository;

    @Override
    public ResponseEntity<AuthURIResponse> authUser() {
        AuthURIResponse uri = spotifyClient.authCodeUri().authorizationCodeUri_Sync();
        if (uri.getUri() == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(uri);
    }

    @Override
    public User accessTokenFromCode(String userId, String code) {
        AuthorizationCodeCredentials authorizationCodeCredentials = spotifyClient.authCodeRequest().authorizationCode_Sync(code);

        if (authorizationCodeCredentials == null) {
            return null;
        }

        Optional<UserSpotifyCredentials> existingTokenOpt = userSpotifyCredentialsRepository.findByUserId(userId);
        UserSpotifyCredentials userSpotifyCredentials = existingTokenOpt.orElse(new UserSpotifyCredentials());
        userSpotifyCredentials.setUserId(userId);

        saveUserSpotifyCredentials(userSpotifyCredentials, userId, authorizationCodeCredentials);
        return getSpotifyUserDetailsWithAccessCode(authorizationCodeCredentials.getAccessToken());
    }

    @Override
    public boolean isSpotifyAuthorised(String userId) {
        Optional<String> validAccessTokenOpt = getValidAccessToken(userId);
        return validAccessTokenOpt.isPresent();
    }

    @Override
    public Optional<String> getValidAccessToken(String userId) {
        Optional<UserSpotifyCredentials> existingTokenOpt = userSpotifyCredentialsRepository.findByUserId(userId);

        if (existingTokenOpt.isPresent()) {
            UserSpotifyCredentials userSpotifyCredentials = existingTokenOpt.get();

            if (userSpotifyCredentials.getAccessTokenExpireDate().isAfter(Instant.now())) {
                return Optional.of(userSpotifyCredentials.getAccessToken());
            }

            UserSpotifyCredentials newCredentials = updateUserCredentials(userSpotifyCredentials, userId);
            return Optional.of(newCredentials.getAccessToken());
        }

        return Optional.empty();
    }

    private UserSpotifyCredentials updateUserCredentials(UserSpotifyCredentials userSpotifyCredentials, String userId) {
        AuthorizationCodeCredentials authorizationCodeCredentials = spotifyClient.authCodeRefresh().authorizationCodeRefresh_Sync(userSpotifyCredentials.getRefreshToken());
        return saveUserSpotifyCredentials(userSpotifyCredentials, userId, authorizationCodeCredentials);
    }

    private UserSpotifyCredentials saveUserSpotifyCredentials(UserSpotifyCredentials userSpotifyCredentials, String userId, AuthorizationCodeCredentials codeCredentials) {

        userSpotifyCredentials.setAccessToken(codeCredentials.getAccessToken());
        userSpotifyCredentials.setTokenType(codeCredentials.getTokenType());
        userSpotifyCredentials.setScope(codeCredentials.getScope());
        userSpotifyCredentials.setRefreshToken(codeCredentials.getRefreshToken());

        Integer expiresIn = codeCredentials.getExpiresIn();
        Instant now = Instant.now();
        Instant expiresTime = now.plusSeconds(expiresIn);

        userSpotifyCredentials.setExpiresIn(expiresIn);
        userSpotifyCredentials.setAccessTokenRequestDate(now);
        userSpotifyCredentials.setAccessTokenExpireDate(expiresTime);

        return userSpotifyCredentialsRepository.save(userSpotifyCredentials);

    }


}
