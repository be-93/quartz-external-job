package com.core.db;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class DataSourceProperty {

    @NotNull
    protected String jdbcUrl;
    @NotNull
    protected String username;
//    @NotNull
    protected String password;
    @NotNull
    protected String driverClassName;

}
