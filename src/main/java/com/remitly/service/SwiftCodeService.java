package com.remitly.service;

import com.remitly.exception.*;
import com.remitly.model.SwiftCode;
import com.remitly.repository.SwiftCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    public SwiftCode getSwiftCodeDetails(String swiftCode) {
        return swiftCodeRepository.findById(swiftCode)
                .orElseThrow(() -> new SwiftCodeNotFoundException("SWIFT code not found: " + swiftCode));
    }

    public List<SwiftCode> getSwiftCodesByCountry(String countryISO2) {
        if (!countryISO2.matches("^[A-Z]{2}$")) {
            throw new InvalidInputException("Country code must be 2 uppercase letters");
        }
        return swiftCodeRepository.findByCountryISO2(countryISO2);
    }

    public SwiftCode addSwiftCode(SwiftCode swiftCode) {
        validateSwiftCodeInput(swiftCode);

        if (swiftCodeRepository.existsBySwiftCode(swiftCode.getSwiftCode())) {
            throw new SwiftCodeAlreadyExistsException(
                    "SWIFT code already exists: " + swiftCode.getSwiftCode());
        }

        swiftCode.setCountryISO2(swiftCode.getCountryISO2().toUpperCase());
        swiftCode.setCountryName(Optional.ofNullable(swiftCode.getCountryName())
                .map(String::toUpperCase)
                .orElse(null));

        boolean isHeadquarter = swiftCode.getSwiftCode().endsWith("XXX");
        swiftCode.setHeadquarterFlag(isHeadquarter);

        if (!isHeadquarter && swiftCode.getSwiftCode().length() >= 8) {
            String potentialHeadquarterCode = swiftCode.getSwiftCode().substring(0, 8) + "XXX";
            swiftCodeRepository.findById(potentialHeadquarterCode)
                    .ifPresent(headquarter -> {
                        swiftCode.setHeadquarter(headquarter);
                        headquarter.getBranches().add(swiftCode);
                        swiftCodeRepository.save(headquarter);
                    });
        }

        return swiftCodeRepository.save(swiftCode);
    }

    public void deleteSwiftCode(String swiftCode) {
        if (!swiftCodeRepository.existsBySwiftCode(swiftCode)) {
            throw new SwiftCodeNotFoundException("SWIFT code not found: " + swiftCode);
        }

        swiftCodeRepository.findById(swiftCode).ifPresent(code -> {
            if (code.isHeadquarterFlag() && !code.getBranches().isEmpty()) {
                throw new IllegalStateException(
                        "Cannot delete headquarter with existing branches");
            }
        });

        swiftCodeRepository.deleteById(swiftCode);
    }

    private void validateSwiftCodeInput(SwiftCode swiftCode) {
        if (!swiftCode.getSwiftCode().matches("^[A-Z0-9]{8}([A-Z0-9]{3})?$")) {
            throw new InvalidInputException(
                    "Invalid SWIFT code format. Must be 8 or 11 alphanumeric characters");
        }

        if (!swiftCode.getCountryISO2().matches("^[A-Z]{2}$")) {
            throw new InvalidInputException(
                    "Country code must be exactly 2 uppercase letters");
        }

        if (swiftCode.getBankName() == null || swiftCode.getBankName().isBlank()) {
            throw new InvalidInputException("Bank name is required");
        }
    }
}