package com.internal.repository.querydsl;

import com.core.jpa.querydsl.QueryDslRepositorySupportWrapper;
import com.internal.entity.InternalTest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.internal.entity.QInternalTest.internalTest;

@Repository
public class QueryDslInternalImpl extends QueryDslRepositorySupportWrapper implements QueryDslInternalRepository {

    public QueryDslInternalImpl() {
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
    public List<InternalTest> findAllInternalTest() {
        return factory.selectFrom(internalTest).fetch();
    }
}
