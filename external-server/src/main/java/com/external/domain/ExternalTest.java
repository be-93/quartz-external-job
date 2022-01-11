package com.external.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "external")
public class ExternalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public ExternalTest() {
    }
}
