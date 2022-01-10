package com.external.domain.querydsl;

import com.core.jpa.querydsl.QueryDslRepositorySupportWrapper;
import com.external.domain.ExternalTest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.external.domain.QExternalTest.externalTest;

@Repository
public class QueryDslExternalImpl extends QueryDslRepositorySupportWrapper implements QueryDslExternalRepository {

    public QueryDslExternalImpl() {
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
    public List<ExternalTest> findAllExternalTest() {
        return factory.selectFrom(externalTest).fetch();
    }
}
