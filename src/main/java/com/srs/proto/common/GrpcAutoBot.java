package com.srs.proto.common;

import com.market.market.MarketType;
import com.srs.proto.dto.GrpcPrincipal;

import java.util.List;

/**
 * @author duynt on 3/28/22
 */
public class GrpcAutoBot extends GrpcPrincipal {
    private static final GrpcAutoBot instance;

    public static final String EMAIL = "autobot@qcemarket.com";
    public static final String EXTERNAL_ID = "emarket-autobot";

    static {
        instance = new GrpcAutoBot();
        instance.setExternalId("emarket-autobot");
        instance.setUsername("autobot@qcemarket.com");
        instance.setEmail("autobot@qcemarket.com");
        instance.setFirstName("eMarket");
        instance.setLastName("System");
        instance.setRoles(List.of("SYSTEM_ADMIN"));
        instance.setDivisions(List.of(
                MarketType.MARKET_TYPE_PUBLIC_VALUE,
                MarketType.MARKET_TYPE_PRIVATE_VALUE,
                MarketType.MARKET_TYPE_HAWKER_VALUE
        ));
    }

    private GrpcAutoBot() {
    }

    public static GrpcAutoBot getInstance() {
        return instance;
    }
}
