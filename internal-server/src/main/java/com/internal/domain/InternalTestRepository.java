package com.internal.domain;

import com.internal.domain.InternalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalTestRepository extends JpaRepository<InternalTest, Long> {
}
