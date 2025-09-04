package com.emailVerifier.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationResult {
    private boolean validFormat;
    private boolean hasMX;
    private boolean isDisposable;
    private String domain;
}
