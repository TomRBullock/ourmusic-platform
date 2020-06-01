package com.ourmusic.platform.repository;

import com.ourmusic.platform.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findAllByHostId(String hostId);

    Optional<Room> findByHostIdAndActiveIsTrue(String hostId);

    Optional<Room> findByCodeAndActiveIsTrue(String code);

    Optional<Room> findByCode(String Code);

    long deleteByIdAndHostId(String id, String hostId);

}
