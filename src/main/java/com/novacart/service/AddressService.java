package com.novacart.service;

import java.util.List;

import com.novacart.entity.Address;

public interface AddressService {

    Address addAddress(
            Address address,
            String email
    );

    List<Address> getUserAddresses(
            String email
    );
}