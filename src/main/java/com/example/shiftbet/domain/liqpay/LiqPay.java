package com.example.shiftbet.domain.liqpay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.UUID;

public class LiqPay {
    private final String privateKey;
    private final String publicKey;

    public LiqPay() {
        this.publicKey = "sandbox_i61653057925";
        this.privateKey = "sandbox_69cBBM8QayMMIjgdSJ8jOTrTOsXmhGlRziuigHye";
    }

    private String sign(String data) {
        return Base64.getEncoder().encodeToString(DigestUtils.sha1(data.getBytes()));
    }

    public Params payParams(double amount) {
        return payParams(amount, "Top up Balance", UUID.randomUUID().toString());
    }

    public Params payParams(double amount, String description, String orderId) {
        Params param = new Params();
        param.setVersion(3);
        param.setPublicKey(publicKey);
        param.setAction("pay");
        param.setAmount(amount);
        param.setCurrency("USD");
        param.setDescription(description);
        param.setOrderId(orderId);
        param.setLanguage("uk");
        return param;
    }

    public String getData(Params param) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(param);
        return Base64.getEncoder().encodeToString(json.getBytes());
    }

    public String getSignature(Params param) throws JsonProcessingException {
        String data = getData(param);
        String signature = sign(privateKey + data + privateKey);
        return signature;
    }

    public boolean verify(Notify notify, Params params) throws JsonProcessingException {
        String data = getData(params);
        String signature = sign(privateKey + data + privateKey);
        return notify.getData().equals(data) && notify.getSignature().equals(signature);
    }
}
