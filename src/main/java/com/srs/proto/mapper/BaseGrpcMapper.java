package com.srs.proto.mapper;

import com.google.protobuf.GeneratedMessageV3;

/**
 * @author duynt on 4/7/22
 */
public interface BaseGrpcMapper<F, T extends GeneratedMessageV3> {
    T toGrpcMessage(F entity);
}
