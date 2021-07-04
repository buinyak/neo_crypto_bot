package com.gb.neobot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Coins {
    private Bitki data;

    @Data
    public class Bitki {
        @JsonProperty("BTC")
        private Coin BTC;
        @JsonProperty("ETH")
        private Coin ETH;
        @JsonProperty("BNB")
        private Coin BNB;
        @JsonProperty("DOGE")
        private Coin DOGE;
        @JsonProperty("DOT")
        private Coin DOT;
        @JsonProperty("ADA")
        private Coin ADA;

        @Data
        public class Coin {
            private String symbol;
            private String total_supply;
            private Quote quote;

            @Data
            public class Quote {
                @JsonProperty("USD")
                private Val USD;

                @Data
                public class Val {
                    private Double price;
                    private Double percent_change_1h;
                    private Double percent_change_24h;
                    private Double percent_change_7d;
                }

            }
        }
    }
}
