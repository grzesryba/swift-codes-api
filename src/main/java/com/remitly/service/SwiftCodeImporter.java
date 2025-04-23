package com.remitly.service;

import com.remitly.model.SwiftCode;
import com.remitly.repository.SwiftCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SwiftCodeImporter {
    private final SwiftCodeRepository repository;

    @PostConstruct
    public void init() {
        System.out.println("SwiftCodeImporter initialized!");
    }

    public void importFromCsv(String filePath) throws IOException {
        System.out.println(filePath);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            importFromCsv(reader);
        }
    }

    public void importFromCsv(Reader reader) throws IOException {
        try (CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim())) {


            List<SwiftCode> allCodes = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                SwiftCode code = new SwiftCode();
                code.setSwiftCode(record.get("SWIFT CODE"));
                code.setBankName(record.get("NAME"));
                code.setAddress(record.get("ADDRESS"));
                code.setCountryISO2(record.get("COUNTRY ISO2 CODE").toUpperCase());
                code.setCountryName(record.get("COUNTRY NAME").toUpperCase());
                code.setHeadquarterFlag(code.getSwiftCode().endsWith("XXX"));
                allCodes.add(code);
            }
            repository.saveAll(allCodes);

            establishBranchRelationships();
        }
    }

    private void establishBranchRelationships() {
        repository.clearAllHeadquarterRelationships();

        List<SwiftCode> allHQs = repository.findBySwiftCodeEndingWith("XXX");

        Map<String, SwiftCode> hqMap = allHQs.stream()
                .collect(Collectors.toMap(
                        hq -> hq.getSwiftCode().substring(0, 8),
                        hq -> hq
                ));

        repository.findAll().stream()
                .filter(code -> !code.isHeadquarterFlag())
                .filter(code -> code.getSwiftCode().length() >= 8)
                .forEach(branch -> {
                    String prefix = branch.getSwiftCode().substring(0, 8);
                    if (hqMap.containsKey(prefix)) {
                        SwiftCode hq = hqMap.get(prefix);
                        branch.setHeadquarter(hq);
                        repository.save(branch);
                    }
                });

        repository.flush();
    }
}