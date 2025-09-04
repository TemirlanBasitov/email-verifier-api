package com.emailVerifier.service;

import com.emailVerifier.controller.EmailVerificationResult;
import reactor.core.publisher.Mono;

public interface EmailVerificationService {

    Mono<EmailVerificationResult> verifyEmail(String email);

}
