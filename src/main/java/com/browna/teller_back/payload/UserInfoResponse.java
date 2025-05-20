package com.browna.teller_back.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    private String username;
    private String firstName;
    private String lastName;
}
