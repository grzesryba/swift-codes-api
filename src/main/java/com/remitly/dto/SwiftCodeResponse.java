package com.remitly.dto;

public record SwiftCodeResponse(
        String address,
        String bankName,
        String countryISO2,
        String countryName,
        boolean isHeadquarter,
        String swiftCode
) {}