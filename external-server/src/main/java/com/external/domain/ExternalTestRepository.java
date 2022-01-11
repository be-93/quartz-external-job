package com.external.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalTestRepository extends JpaRepository<ExternalTest, Long> {
}
