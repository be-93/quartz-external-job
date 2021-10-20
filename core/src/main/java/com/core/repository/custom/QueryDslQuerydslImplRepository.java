package com.core.repository.custom;

import com.core.entity.CoreTest;
import com.core.jpa.querydsl.QueryDslRepositorySupportWrapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class QueryDslQuerydslImplRepository extends QueryDslRepositorySupportWrapper implements QueryDslCustomRepository {

    public QueryDslQuerydslImplRepository() {
        super(Class.class);
    }

    private EntityManager entityManager;
    private JPAQueryFactory factory;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        factory = new JPAQueryFactory(entityManager);
        super.setEntityManager(entityManager);
    }

    @Override
    public List<CoreTest> findAllCoreTest() {
        return null;
//        return factory.selectFrom(QCore)
    }
}
