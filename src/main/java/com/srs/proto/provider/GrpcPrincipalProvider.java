package com.srs.proto.provider;

import com.srs.proto.constant.GrpcConstant;
import com.srs.proto.dto.GrpcCredentials;
import com.srs.proto.dto.GrpcPrincipal;
import io.jsonwebtoken.*;

import java.sql.Timestamp;
import java.util.*;

import static com.srs.proto.constant.GrpcConstant.*;

/**
 * @author duynt on 2/14/22
 */
public class GrpcPrincipalProvider {

    /**
     * Utility method that serves as a shortcut to attach current user principal to gRPC context metadata before sending out to server
     * <p>
     * Only used by gRPC client before sending out RPC
     */
    public static GrpcCredentials asGrpcCredentials(GrpcPrincipal principal) {
        return new GrpcCredentials(principal);
    }

    /**
     * Utility method to retrieve user principal from current gRPC context
     * <p>
     * Only used by gRPC server
     */
    public static GrpcPrincipal getGrpcPrincipal() {
        return GrpcConstant.IDENTITY_CONTEXT_KEY.get();
    }

    /**
     * PLEASE NOTE: Don't use this method directly in microservice code, it is intended to be used by GrpcAuthInterceptor only.
     * The only purpose of this is to make it ease to update alongside the asGrpcToken() function below whenever having changes in GrpcPrincipal
     *
     * @param jws Obtained from gRPC context metadata sent along with RPC call by client side
     * @return User principal to continue process at gRPC server side
     * @see com.srs.proto.intercepter.AuthGrpcInterceptor
     * @see GrpcPrincipal
     */
    @SuppressWarnings("unchecked")
    public static GrpcPrincipal asGrpcPrincipal(Jws<Claims> jws) {
        Claims claims = jws.getBody();

        GrpcPrincipal identity = new GrpcPrincipal();

        identity.setUserId(UUID.fromString(claims.get(USER_ID, String.class)));
//        identity.setExternalId(claims.get(EXTERNAL_ID, String.class));

        identity.setUsername(claims.get(USERNAME, String.class));
        identity.setEmail(claims.get(EMAIL, String.class));
        identity.setFirstName(claims.get(FIRST_NAME, String.class));
        identity.setLastName(claims.get(LAST_NAME, String.class));

        identity.setRoles(claims.get(ROLES, List.class));

        identity.setPermissions(claims.get(PERMISSIONS, List.class));

//        identity.setDivisions(claims.get(DIVISIONS, List.class));
//
        identity.setMarketCodes(claims.get(MARKET_CODES, List.class));

        identity.setRoleIds(claims.get(ROLE_IDS, List.class));

        return identity;
    }

    /**
     * PLEASE NOTE: Don't use this method directly in microservice code, it is intended to be used by GrpcCredentials only
     * <p>
     * Zip user principal as JWT to send along with RPC call
     */
    public static String asGrpcToken(GrpcPrincipal principal) {
        Map<String, Object> additionalClaims = new LinkedHashMap<>();

        additionalClaims.put(USER_ID, principal.getUserId().toString());
//        additionalClaims.put(EXTERNAL_ID, principal.getExternalId());

        additionalClaims.put(EMAIL, principal.getEmail());
        additionalClaims.put(USERNAME, principal.getUsername());
        additionalClaims.put(FIRST_NAME, principal.getFirstName());
        additionalClaims.put(LAST_NAME, principal.getLastName());

        additionalClaims.put(ROLES, principal.getRoles());
        additionalClaims.put(PERMISSIONS, principal.getPermissions());
//        additionalClaims.put(DIVISIONS, principal.getDivisions());
        additionalClaims.put(MARKET_CODES, principal.getMarketCodes() != null ? principal.getMarketCodes() : new ArrayList<String>());
        additionalClaims.put(ROLE_IDS, principal.getRoleIds());

        Date now = getCurrentTimestamp();

        return Jwts.builder()
                .setSubject(principal.getUserId().toString())
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(getExpirationTimestamp())
                .setHeaderParam(JwsHeader.ALGORITHM, SignatureAlgorithm.HS256)
                .setClaims(additionalClaims)
                .signWith(SignatureAlgorithm.HS256, GrpcConstant.JWT_SIGNING_KEY)
                .compact();
    }

    private static Date getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private static Date getExpirationTimestamp() {
        return new Timestamp(System.currentTimeMillis() + GrpcConstant.JWT_EXPIRATION);
    }

}
