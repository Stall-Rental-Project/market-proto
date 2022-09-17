package com.srs.proto.constant;

import com.google.common.net.HttpHeaders;
import com.srs.proto.dto.GrpcPrincipal;
import io.grpc.Context;
import io.grpc.Metadata;

/**
 * @author duynt on 2/14/22
 */
public class GrpcConstant {

    public static final long JWT_EXPIRATION = 1000 * 60; // 1 minutes
    public static final String JWT_SIGNING_KEY = "eM4rk3t";
    public static final String JWT_BEARER_PREFIX = "Bearer";

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    //
    // Metadata Key constants
    //
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of(HttpHeaders.AUTHORIZATION,
            Metadata.ASCII_STRING_MARSHALLER);

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    //
    // Context Key constants
    //
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public static final Context.Key<GrpcPrincipal> IDENTITY_CONTEXT_KEY = Context.key("emarket_grpc_principal");

    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    //
    // Principal claims
    //
    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public static final String USER_ID = "user_id";
    public static final String EXTERNAL_ID = "external_id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ROLES = "roles";
    public static final String PERMISSIONS = "permissions";
    public static final String DIVISIONS = "divisions";
    public static final String MARKET_CODES = "market_codes";
    public static final String ROLE_IDS = "role_ids";
}
