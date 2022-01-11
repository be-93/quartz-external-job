package com.internal.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "internal")
public class InternalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public InternalTest() {
    }
}
