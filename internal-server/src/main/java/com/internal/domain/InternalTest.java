package com.internal.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "internal_table")
public class InternalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    protected InternalTest() {
    }

    public InternalTest(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
