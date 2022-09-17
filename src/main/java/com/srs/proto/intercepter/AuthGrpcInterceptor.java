package com.srs.proto.intercepter;

import com.srs.proto.constant.GrpcConstant;
import com.srs.proto.provider.GrpcPrincipalProvider;
import io.grpc.*;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthGrpcInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthGrpcInterceptor.class);

    private static final JwtParser jwtParser = Jwts.parser().setSigningKey(GrpcConstant.JWT_SIGNING_KEY.getBytes());

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {
        var key = metadata.get(GrpcConstant.AUTHORIZATION_METADATA_KEY);

        Status status;

        if (key == null || key.isBlank()) {
            status = Status.UNAUTHENTICATED.withDescription("gRPC token is missing");
            log.error("Error when intercepting gRPC call. Token is missing");
        } else if (!key.startsWith(GrpcConstant.JWT_BEARER_PREFIX)) {
            status = Status.UNAUTHENTICATED.withDescription("Invalid gRPC token type");
            log.error("Error when intercepting gRPC call. Invalid token found");
        } else {
            try {
                var jwtToken = key.substring(GrpcConstant.JWT_BEARER_PREFIX.length()).trim();
                var jws = jwtParser.parseClaimsJws(jwtToken);
                var principal = GrpcPrincipalProvider.asGrpcPrincipal(jws);
                var context = Context.current().withValue(GrpcConstant.IDENTITY_CONTEXT_KEY, principal);
                return Contexts.interceptCall(context, call, metadata, next);
            } catch (Exception e) {
                log.error("Error when parsing gRPC token. {} - {}", e.getClass().getSimpleName(), e.getMessage());
                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
            }
        }

        log.warn("Cancelling gRPC call due to failure when intercepting");

        // Cancel the call
        call.close(status, new Metadata());

        return new ServerCall.Listener<>() {
            // Do nothing
        };
    }
}
