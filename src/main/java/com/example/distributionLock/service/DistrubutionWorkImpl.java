package com.example.distributionLock.service;

import org.springframework.stereotype.Service;

@Service
public class DistrubutionWorkImpl implements  DistributionWorkService {
    @Override
    public String work1() {
        return "OK";
    }
}
