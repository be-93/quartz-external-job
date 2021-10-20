package com.core.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "core_test")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoreTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder(builderMethodName = "coreBuilder")
    public CoreTest(int id) {
        this.id = id;
    }

}
