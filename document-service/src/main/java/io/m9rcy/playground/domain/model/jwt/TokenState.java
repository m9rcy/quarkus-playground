package io.m9rcy.playground.domain.model.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenState {

    private String token;
    private String refreshToken;
    private Long tokenTTL;
    private Long issueDate;
}
