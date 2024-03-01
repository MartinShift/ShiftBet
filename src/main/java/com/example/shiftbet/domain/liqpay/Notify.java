package com.example.shiftbet.domain.liqpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notify
{
    private String data;
    private String signature;
    private double amount;
}
