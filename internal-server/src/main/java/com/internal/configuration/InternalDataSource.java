package com.internal.configuration;

import com.core.db.DataSourceFactory;
import com.core.db.DataSourceProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InternalDataSource extends DataSourceFactory{

    final private DataSourceProperty dataSourceProperty;


}
