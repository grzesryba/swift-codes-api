package com.remitly.dto;

import java.util.List;

public record CountrySwiftCodesResponse(
        String countryISO2,
        String countryName,
        List<SwiftCodeResponse> swiftCodes
) {}