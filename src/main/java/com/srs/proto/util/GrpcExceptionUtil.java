
package com.srs.proto.util;

import com.market.common.Error;
import com.market.common.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author duynt on 2/14/22
 */
public class GrpcExceptionUtil {
    private static final Logger log = LoggerFactory.getLogger(GrpcExceptionUtil.class);

    public static Error asGrpcError(Exception exception) {
        Error.Builder builder = Error.newBuilder()
                .setCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .setException(exception.getClass().getSimpleName())
                .setMessage(getExceptionMessage(exception));

        if (exception.getCause() != null) {
            Throwable cause = exception.getCause();
            log.error(String.format("Cause: %s - %s", cause.getClass().getSimpleName(), cause.getMessage()));
        }

        return builder.build();
    }

    private static String getExceptionMessage(Exception e) {
        return StringUtils.hasText(e.getMessage()) ? e.getMessage() : "Unexpected error";
    }
}
