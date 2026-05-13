package com.novacart.serviceimpl;

import com.novacart.entity.Address;
import com.novacart.entity.User;
import com.novacart.exception.ResourceNotFoundException;
import com.novacart.repository.AddressRepository;
import com.novacart.repository.UserRepository;
import com.novacart.service.AddressService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl
        implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Address addAddress(
            Address address,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    @Override
    public List<Address> getUserAddresses(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        return addressRepository.findByUser(user);
    }
}