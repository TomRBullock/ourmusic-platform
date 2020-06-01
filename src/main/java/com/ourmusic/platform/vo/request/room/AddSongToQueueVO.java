package com.ourmusic.platform.vo.request.room;

import com.wrapper.spotify.model_objects.specification.Track;
import lombok.Data;

@Data
public class AddSongToQueueVO {
    private Track track;
}
