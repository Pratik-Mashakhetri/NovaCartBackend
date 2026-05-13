package com.novacart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String mobileNumber;

    private String addressLine;

    private String city;

    private String state;

    private String pincode;

    private String country;

    @ManyToOne
    private User user;
}