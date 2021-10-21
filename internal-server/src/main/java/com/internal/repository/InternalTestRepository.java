package com.internal.repository;

import com.internal.entity.InternalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalTestRepository extends JpaRepository<InternalTest, Long> {
}
