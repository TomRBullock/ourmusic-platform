package com.ourmusic.platform.controller;

public interface Endpoints {

    String ROOT = "/api";

    interface USER {
        String ROOT = Endpoints.ROOT + "/user";

        String CREATE = "/create";

    }

    interface ROOM {
        String ROOT = Endpoints.ROOT + "/room";


    }

}
