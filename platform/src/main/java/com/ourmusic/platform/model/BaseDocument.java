package com.ourmusic.platform.model;

import lombok.Data;
import org.springframework.data.annotation.*;

import java.time.Instant;

@Data
public abstract class BaseDocument {
    private @Id                 String  id;
    private @CreatedDate        Instant createdAt;
    private @CreatedBy          String  createdBy;
    private @LastModifiedDate   Instant lastModifiedAt;
    private @LastModifiedBy     String  lastModifiedBy;
}
