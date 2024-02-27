package com.example.shiftbet.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private String sub;
    private String name;
    private String givenName;
    private String picture;
    private String email;
    private boolean emailVerified;

}
