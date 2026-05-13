package com.novacart.controller;

import com.novacart.entity.Address;
import com.novacart.service.AddressService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public Address addAddress(
            @RequestBody Address address,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return addressService.addAddress(
                address,
                email
        );
    }

    @GetMapping
    public List<Address> getUserAddresses(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return addressService.getUserAddresses(email);
    }
}