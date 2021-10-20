package com.core.repository;

import com.core.entity.CoreTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreTestRepository extends JpaRepository<CoreTest, Long> {
}
