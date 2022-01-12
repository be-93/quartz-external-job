package com.internal.yml;

import com.core.yml.JpaProperty;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "spring.datasource.internal.jpa")
public class InternalJpaProperty extends JpaProperty {

    @Value("${spring.datasource.internal.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Value("${spring.datasource.internal.jpa.properties.hibernate.order_inserts}")
    private boolean orderInserts;

    @Value("${spring.datasource.internal.jpa.properties.hibernate.order_updates}")
    private boolean orderUpdates;
}
