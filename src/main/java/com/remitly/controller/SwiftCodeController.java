package com.remitly.controller;

import com.remitly.dto.CountrySwiftCodesResponse;
import com.remitly.dto.HeadquarterResponse;
import com.remitly.dto.SwiftCodeResponse;
import com.remitly.model.SwiftCode;
import com.remitly.service.SwiftCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
@RequiredArgsConstructor
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;

    @GetMapping("/{swiftCode}")
    public ResponseEntity<?> getSwiftCodeDetails(@PathVariable String swiftCode) {
        SwiftCode code = swiftCodeService.getSwiftCodeDetails(swiftCode);

        if (code.isHeadquarterFlag()) {
            return ResponseEntity.ok(mapToHeadquarterResponse(code));
        }
        return ResponseEntity.ok(mapToSwiftCodeResponse(code));
    }

    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<CountrySwiftCodesResponse> getSwiftCodesByCountry(
            @PathVariable String countryISO2) {
        List<SwiftCode> codes = swiftCodeService.getSwiftCodesByCountry(countryISO2);

        if (codes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CountrySwiftCodesResponse(
                countryISO2,
                codes.get(0).getCountryName(),
                codes.stream().map(this::mapToSwiftCodeResponse).toList()
        ));
    }

    @PostMapping
    public ResponseEntity<SwiftCodeResponse> addSwiftCode(@RequestBody SwiftCode swiftCode) {
        SwiftCode savedCode = swiftCodeService.addSwiftCode(swiftCode);
        return ResponseEntity.ok(mapToSwiftCodeResponse(savedCode));
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<String> deleteSwiftCode(@PathVariable String swiftCode) {
        swiftCodeService.deleteSwiftCode(swiftCode);
        return ResponseEntity.ok("SWIFT code deleted successfully: " + swiftCode);
    }

    private SwiftCodeResponse mapToSwiftCodeResponse(SwiftCode code) {
        return new SwiftCodeResponse(
                code.getAddress(),
                code.getBankName(),
                code.getCountryISO2(),
                code.getCountryName(),
                code.isHeadquarterFlag(),
                code.getSwiftCode()
        );
    }

    private HeadquarterResponse mapToHeadquarterResponse(SwiftCode code) {
        return new HeadquarterResponse(
                code.getAddress(),
                code.getBankName(),
                code.getCountryISO2(),
                code.getCountryName(),
                true,
                code.getSwiftCode(),
                code.getBranches().stream()
                        .map(this::mapToSwiftCodeResponse)
                        .toList()
        );
    }
}