package com.external.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "external_table")
public class ExternalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    protected ExternalTest() {
    }

    public ExternalTest(String name) {
        this.name = name;
    }
}
