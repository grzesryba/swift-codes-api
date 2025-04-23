//package com.remitly.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.remitly.dto.CountrySwiftCodesResponse;
//import com.remitly.dto.HeadquarterResponse;
//import com.remitly.dto.SwiftCodeResponse;
//import com.remitly.model.SwiftCode;
//import com.remitly.service.SwiftCodeService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = SwiftCodeController.class)
//@Import(SwiftCodeService.class)
//public class SwiftCodeControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SwiftCodeService swiftCodeService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void shouldReturnHeadquarterDetailsWithBranches() throws Exception {
//        SwiftCode hq = SwiftCode.builder()
//                .swiftCode("BANKPLPWXXX")
//                .bankName("Bank")
//                .address("Main St")
//                .countryISO2("PL")
//                .countryName("POLAND")
//                .headquarterFlag(true)
//                .build();
//
//        SwiftCode branch = SwiftCode.builder()
//                .swiftCode("BANKPLPW001")
//                .bankName("Bank")
//                .address("Branch St")
//                .countryISO2("PL")
//                .countryName("POLAND")
//                .headquarterFlag(false)
//                .headquarter(hq)
//                .build();
//        hq.setBranches(List.of(branch));
//
//        Mockito.when(swiftCodeService.getSwiftCodeDetails("BANKPLPWXXX")).thenReturn(hq);
//
//        mockMvc.perform(get("/v1/swift-codes/BANKPLPWXXX"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isHeadquarter").value(true))
//                .andExpect(jsonPath("$.branches", hasSize(1)));
//    }
//
//    @Test
//    void shouldReturnBranchDetails() throws Exception {
//        SwiftCode branch = SwiftCode.builder()
//                .swiftCode("BANKPLPW001")
//                .address("Branch St")
//                .bankName("Bank")
//                .countryISO2("PL")
//                .countryName("POLAND")
//                .headquarterFlag(false)
//                .build();
//
//
//
//        Mockito.when(swiftCodeService.getSwiftCodeDetails("BANKPLPW001")).thenReturn(branch);
//
//        mockMvc.perform(get("/v1/swift-codes/BANKPLPW001"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isHeadquarter").value(false));
//    }
//
//    @Test
//    void shouldReturnCountrySwiftCodes() throws Exception {
//        SwiftCode branch = SwiftCode.builder()
//                .swiftCode("BANKPLPW001")
//                .address("Branch St")
//                .bankName("Bank")
//                .countryISO2("PL")
//                .countryName("POLAND")
//                .headquarterFlag(false)
//                .build();
//
//
//        Mockito.when(swiftCodeService.getSwiftCodesByCountry("PL"))
//                .thenReturn(List.of(branch));
//
//        mockMvc.perform(get("/v1/swift-codes/country/PL"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.countryISO2").value("PL"))
//                .andExpect(jsonPath("$.swiftCodes", hasSize(1)));
//    }
//
//    @Test
//    void shouldAddSwiftCode() throws Exception {
//        SwiftCode input = SwiftCode.builder()
//                .swiftCode("BANKPLPWXXX")
//                .address("Main St")
//                .bankName("Bank")
//                .countryISO2("PL")
//                .countryName("POLAND")
//                .headquarterFlag(true)
//                .build();
//
//
//        Mockito.when(swiftCodeService.addSwiftCode(any(SwiftCode.class))).thenReturn(input);
//
//        mockMvc.perform(post("/v1/swift-codes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.swiftCode").value("BANKPLPWXXX"));
//    }
//
//    @Test
//    void shouldDeleteSwiftCode() throws Exception {
//        mockMvc.perform(delete("/v1/swift-codes/BANKPLPWXXX"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("SWIFT code deleted successfully: BANKPLPWXXX"));
//    }
//}
