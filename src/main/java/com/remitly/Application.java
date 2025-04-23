package com.remitly;

import com.remitly.service.SwiftCodeImporter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(SwiftCodeImporter importer) {
        return args -> {
            try {
                System.out.println("Starting SWIFT codes import...");
                importer.importFromCsv("data/swift_codes.csv");
                System.out.println("SWIFT codes imported successfully");
            } catch (IOException e) {
                System.err.println("IOException during SWIFT codes import: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Failed to import SWIFT codes: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
