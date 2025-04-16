package com.browna.teller_back.payload;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthenticationResponse {
    private String token;
}
