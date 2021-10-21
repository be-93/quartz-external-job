package com.internal.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "internal_test")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InternalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder(builderMethodName = "internalBuilder")
    public InternalTest(int id) {
        this.id = id;
    }

}
