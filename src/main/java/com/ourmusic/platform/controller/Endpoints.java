package com.ourmusic.platform.controller;

public interface Endpoints {

    String ROOT = "/api";

    interface UTIL {
        String REF_PARAM = "ref";
        String REF_VAR   = "/{ref}";

        String ID_PARAM = "id";
        String ID_VAR   = "/{id}";
    }

    interface USER {
        String ROOT = Endpoints.ROOT + "/user";

        String CREATE = "/create";

    }

    interface ROOM {
        String ROOT = Endpoints.ROOT + "/room";
        String VALIDATE = "/validate";

        String SONG = "/song";
        String QUEUE = "/queue";

        String JOIN = "/join";

        interface ADMIN {
            String ROOT = ROOM.ROOT + "/admin";
            String ACTIVATE = "/activate";
            String TOGGLE_PLAY = "/toggle-play";
        }

        interface VOTES {
            String ROOT = ROOM.ROOT + UTIL.ID_VAR + "/vote";
            String ADD = "/add";
            String REMOVE = "/remove";
            String SKIP = "/skip";
        }

    }

    interface SPOTIFY {
        String ROOT = Endpoints.ROOT + "/spotify";

        interface AUTHORIZATION {
            String ROOT = SPOTIFY.ROOT + "/auth";
            String CODE = "/code";
            String IS_CONNECTED = "/is-connected";
        }

        interface USER {
            String ROOT = SPOTIFY.ROOT + "/user";
        }

        interface SEARCH {
            String ROOT = SPOTIFY.ROOT + "/search";
        }

    }

}
