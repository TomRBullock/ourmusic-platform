package com.ourmusic.platform.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import ourmusic.spotify.client.SpotifyClient;
import ourmusic.spotify.client.vo.SpotifyCredentials;

@Configuration
@Slf4j
@PropertySources({
        @PropertySource("classpath:application.yml")
})
public class SpotifyConfig {

    @Autowired
    private Environment env;

    @Bean public SpotifyClient spotifyClient() {
        log.debug("Spotify Client: Loading configs...");

        String callbackUri = env.getRequiredProperty("spotify.callback-uri");
        String clientId = env.getRequiredProperty("spotify.client-id");
        String clientSecret = env.getRequiredProperty("spotify.client-secret");

        SpotifyCredentials spotifyCredentials = new SpotifyCredentials(clientId, clientSecret, callbackUri);
        return new SpotifyClient(spotifyCredentials);
    }

}
