package com.remitly.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "swift_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftCode {
    @Id
    private String swiftCode;

    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;

    @Column(name = "is_headquarter")
    private boolean headquarterFlag;

    @ManyToOne
    @JoinColumn(name = "headquarter_code")
    private SwiftCode headquarter;

    @OneToMany(mappedBy = "headquarter", fetch = FetchType.EAGER)
    @Builder.Default
    private List<SwiftCode> branches = new ArrayList<>();


}