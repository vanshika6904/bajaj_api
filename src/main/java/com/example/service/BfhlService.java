package com.example.service;

import com.example.dto.BfhlRequest;
import com.example.dto.BfhlResponse;

public interface BfhlService {

    BfhlResponse processData(BfhlRequest request);
}
