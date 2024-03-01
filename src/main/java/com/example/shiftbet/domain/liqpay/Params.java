package com.example.shiftbet.domain.liqpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Params {
    @JsonProperty("version")
    private int version;

    @JsonProperty("public_key")
    private String publicKey;

    @JsonProperty("action")
    private String action;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("language")
    private String language;
}
