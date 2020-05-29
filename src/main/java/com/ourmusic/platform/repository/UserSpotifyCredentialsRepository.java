package com.ourmusic.platform.repository;

import com.ourmusic.platform.model.User;
import com.ourmusic.platform.model.UserSpotifyCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserSpotifyCredentialsRepository extends MongoRepository<UserSpotifyCredentials, String> {

    Optional<UserSpotifyCredentials> findByUserId(String userId);

}
