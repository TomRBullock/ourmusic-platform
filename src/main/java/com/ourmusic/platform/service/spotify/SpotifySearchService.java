package com.ourmusic.platform.service.spotify;

import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.AbstractModelObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpotifySearchService {

    ResponseEntity<List<AbstractModelObject>> search(String searchTerm, String roomCode, ModelObjectType type);

}
