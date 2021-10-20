package com.external.repository;

import com.external.entity.ClientTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTestRepository extends JpaRepository<ClientTest, Long> {
}
