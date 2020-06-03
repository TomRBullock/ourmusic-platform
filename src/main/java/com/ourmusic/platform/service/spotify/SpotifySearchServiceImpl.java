package com.ourmusic.platform.service.spotify;

import com.ourmusic.platform.model.Room;
import com.ourmusic.platform.repository.RoomRepository;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.AbstractModelObject;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SpotifySearchServiceImpl extends SpotifyBaseService implements SpotifySearchService {

    @Autowired private SpotifyAuthorizationService spotifyAuthorizationService;
    @Autowired private RoomRepository roomRepository;

    @Override
    public ResponseEntity<List<AbstractModelObject>> search(String searchTerm, String roomCode, ModelObjectType type) {

        Optional<Room> roomOpt = roomRepository.findByCodeAndActiveIsTrue(roomCode);
        if (!roomOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Room room = roomOpt.get();

        Optional<String> validAccessTokenOpt = spotifyAuthorizationService.getValidAccessToken(room.getHostId());
        if (!validAccessTokenOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        String accessToken = validAccessTokenOpt.get();

        SearchResult searchResult = spotifyClient.searchItem().searchItem_Sync(accessToken, searchTerm, type.getType());

        return ResponseEntity.ok(getListOfItems(searchResult, type));
    }

    private List<AbstractModelObject> getListOfItems(SearchResult searchResult, ModelObjectType type) {

        if (type.equals(ModelObjectType.TRACK)) {
            return Arrays.asList(searchResult.getTracks().getItems());
        }

        if (type.equals(ModelObjectType.ALBUM)) {
            return Arrays.asList(searchResult.getAlbums().getItems());
        }

        if (type.equals(ModelObjectType.ARTIST)) {
            return Arrays.asList(searchResult.getArtists().getItems());
        }

        if (type.equals(ModelObjectType.PLAYLIST)) {
            return Arrays.asList(searchResult.getPlaylists().getItems());
        }

        return new ArrayList<>();
    }

}
