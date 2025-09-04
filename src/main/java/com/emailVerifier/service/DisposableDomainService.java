package com.emailVerifier.service;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DisposableDomainService {

    private final Set<String> disposableDomains;

    public DisposableDomainService() {
        this.disposableDomains = loadDomainsFromClasspath();
    }

    private Set<String> loadDomainsFromClasspath() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("disposable-domains.txt").getInputStream()))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load bundled disposable domains", e);
        }
    }

    public boolean isDisposable(String domain) {
        return disposableDomains.contains(domain.toLowerCase());
    }
}

