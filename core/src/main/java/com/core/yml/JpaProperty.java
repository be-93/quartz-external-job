package com.core.yml;

import lombok.Data;

@Data
public class JpaProperty {
    protected String ddlAuto;
    protected boolean showSql;
    protected boolean formatSql;
    protected String databasePlatform;
    protected String persistenceUnitName;
    protected String entityPath;
}
