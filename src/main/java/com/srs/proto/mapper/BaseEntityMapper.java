package com.srs.proto.mapper;

import com.google.protobuf.GeneratedMessageV3;

/**
 * @author duynt on 5/30/22
 */
public interface BaseEntityMapper<F extends GeneratedMessageV3, T> {
    T createEntity(F message);

    boolean updateEntity(F message, T entity);
}
