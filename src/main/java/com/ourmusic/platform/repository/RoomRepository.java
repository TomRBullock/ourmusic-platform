package com.ourmusic.platform.repository;

import com.ourmusic.platform.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {

}
