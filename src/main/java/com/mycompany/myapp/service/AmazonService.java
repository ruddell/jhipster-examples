package com.mycompany.myapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AmazonService {

    private final Logger log = LoggerFactory.getLogger(AmazonService.class);

    public String uploadFile(byte[] pdf, String fileName) {
        return "custom url";
    }
}
