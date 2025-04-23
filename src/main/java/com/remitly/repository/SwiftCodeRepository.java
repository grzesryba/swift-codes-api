package com.remitly.repository;

import com.remitly.model.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {
    List<SwiftCode> findByCountryISO2(String countryISO2);

    boolean existsBySwiftCode(String swiftCode);

    List<SwiftCode> findBySwiftCodeEndingWith(String suffix);

    @Modifying
    @Query("UPDATE SwiftCode s SET s.headquarter = null")
    void clearAllHeadquarterRelationships();
}