package com.emailVerifier.service;

import com.emailVerifier.controller.EmailVerificationResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final DisposableDomainService disposableDomainService;

    @Override
    public Mono<EmailVerificationResult> verifyEmail(String email) {
        boolean validFormat = email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");

        String domain = email.substring(email.indexOf("@") + 1);
        boolean isDisposable = disposableDomainService.isDisposable(domain);

        return Mono.fromCallable(() -> {
            boolean hasMX = hasMXRecord(domain);
            return new EmailVerificationResult(validFormat, hasMX, isDisposable, domain);
        });
    }

    private boolean hasMXRecord(String domain) {
        try {
            Record[] records = new Lookup(domain, Type.MX).run();
            return records != null && records.length > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
