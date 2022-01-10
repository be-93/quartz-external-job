package com.external.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "external_test")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExternalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder(builderMethodName = "externalBuilder")
    public ExternalTest(int id) {
        this.id = id;
    }

}
