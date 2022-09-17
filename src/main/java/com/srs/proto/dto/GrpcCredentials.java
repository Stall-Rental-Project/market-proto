package com.srs.proto.dto;

import com.srs.proto.constant.GrpcConstant;
import com.srs.proto.provider.GrpcPrincipalProvider;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * @author duynt on 2/14/22
 */
public class GrpcCredentials extends CallCredentials {
    private static final Logger logger = LoggerFactory.getLogger(GrpcCredentials.class);

    private final GrpcPrincipal principal;

    public GrpcCredentials(GrpcPrincipal principal) {
        this.principal = principal;
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    //
    // Follows partial good practices ref at https://datatracker.ietf.org/doc/html/rfc7519
    // Here, we use symmetric signing key to reduce the cost of token signing/verifying
    // The reason is the token intentionally being used by intercommunication channels only
    //
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier applier) {
        final var jwt = GrpcPrincipalProvider.asGrpcToken(principal);

        executor.execute(() -> {
            try {
                var metadata = new Metadata();
                metadata.put(GrpcConstant.AUTHORIZATION_METADATA_KEY, String.format("%s %s", GrpcConstant.JWT_BEARER_PREFIX, jwt));
                applier.apply(metadata);
            } catch (Throwable e) {
                logger.error("Cannot apply gRPC request metadata. Exception {} - {}", e.getClass().getSimpleName(), e.getMessage());
                applier.fail(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // Do nothing
    }
}
