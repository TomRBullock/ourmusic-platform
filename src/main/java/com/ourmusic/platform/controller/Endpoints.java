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

        String SETUP = "/setup";
//        String CREATE = "/create";
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

    }

}
