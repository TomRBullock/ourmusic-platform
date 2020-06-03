package com.ourmusic.platform.util.converter;

import com.ourmusic.platform.model.submodel.AlbumImageObject;
import com.ourmusic.platform.model.submodel.TrackObject;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class trackToTrackObjectUtil {

    public static TrackObject trackToTrackObject(Track track) {
        TrackObject trackObject = new TrackObject();
        trackObject.setUri(track.getUri());
        trackObject.setName(track.getName());
        trackObject.setDurationMs(track.getDurationMs());
        trackObject.setType(track.getType());
        trackObject.setArtistName(track.getArtists()[0].getName());

        List<AlbumImageObject> imageObjects = new ArrayList<>();

        Image[] images = track.getAlbum().getImages();
        Arrays.stream(images)
                .forEachOrdered(image -> {
                    AlbumImageObject albumImageObject = new AlbumImageObject();
                    albumImageObject.setHeight(image.getHeight());
                    albumImageObject.setWidth(image.getWidth());
                    albumImageObject.setUrl(image.getUrl());
                    imageObjects.add(albumImageObject);
                });


        trackObject.setImages(imageObjects);

        return trackObject;
    }



}
