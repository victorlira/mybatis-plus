package com.baomidou.mybatisplus.generator.jdbc;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseMetaDataWrapperTest {

    @Test
    void test() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:h2:mem:test;MODE=mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "sa", "").build();
        DatabaseMetaDataWrapper databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceConfig.getConn(), dataSourceConfig.getSchemaName());
        Map<String, DatabaseMetaDataWrapper.Column> columnsInfo = databaseMetaDataWrapper.getColumnsInfo(null, null, "USERS", true);
        Assertions.assertNotNull(columnsInfo);
        DatabaseMetaDataWrapper.Column name = columnsInfo.get("user_name");
        Assertions.assertTrue(name.isNullable());
        Assertions.assertEquals(JdbcType.VARCHAR, name.getJdbcType());
    }


    @Test
    void testIndexInfo() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/baomidou?serverTimezone=Asia/Shanghai", "root", "123456").build();
        DatabaseMetaDataWrapper databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceConfig.getConn(), dataSourceConfig.getSchemaName());
        List<DatabaseMetaDataWrapper.Index> indexList = databaseMetaDataWrapper.getIndex("t_user_info");
        Map<String, List<DatabaseMetaDataWrapper.Index>> indexMap = indexList.stream().collect(Collectors.groupingBy(DatabaseMetaDataWrapper.Index::getName));
        indexMap.forEach((k,v) ->{
            System.out.println(k+"---"+v);
        });
    }
}
