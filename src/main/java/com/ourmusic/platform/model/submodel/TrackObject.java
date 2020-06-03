package com.ourmusic.platform.model.submodel;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.miscellaneous.Restrictions;
import com.wrapper.spotify.model_objects.specification.*;
import lombok.Data;

import java.util.List;

@Data
public class TrackObject {
//    private final AlbumSimplified album;
//    private final ArtistSimplified[] artists;
//    private final CountryCode[] availableMarkets;
//    private final Integer discNumber;
    private Integer durationMs;
//    private final Boolean explicit;
//    private final ExternalId externalIds;
//    private final ExternalUrl externalUrls;
//    private final String href;
//    private final String id;
//    private final Boolean isPlayable;
//    private final TrackLink linkedFrom;
//    private final Restrictions restrictions;
    private String name;
//    private final Integer popularity;
//    private final String previewUrl;
//    private final Integer trackNumber;
    private ModelObjectType type;
    private String uri;

    private List<AlbumImageObject> images;
    private String artistName;

}
