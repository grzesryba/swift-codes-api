package com.remitly.dto;

import java.util.List;

public record HeadquarterResponse(
        String address,
        String bankName,
        String countryISO2,
        String countryName,
        boolean isHeadquarter,
        String swiftCode,
        List<SwiftCodeResponse> branches
) {}