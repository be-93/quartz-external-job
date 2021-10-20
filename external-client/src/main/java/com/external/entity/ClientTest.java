package com.external.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "client_test")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder(builderMethodName = "clientBuilder")
    public ClientTest(int id) {
        this.id = id;
    }

}
