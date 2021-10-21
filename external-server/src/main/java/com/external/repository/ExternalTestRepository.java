package com.external.repository;

import com.external.entity.ExternalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalTestRepository extends JpaRepository<ExternalTest, Long> {
}
