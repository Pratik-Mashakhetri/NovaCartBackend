package com.novacart.service;

import com.novacart.dto.LoginRequest;
import com.novacart.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);
    
    String login(LoginRequest request);
}