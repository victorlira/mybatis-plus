/*
 * Copyright (c) 2011-2024, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.mybatisplus.generator.index;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 使用Lambda方式生成索引方法
 *
 * @author nieqiurong
 * @since 3.5.10
 */
public class DefaultGenerateMapperLambdaMethodHandler extends AbstractMapperMethodHandler {

    public static final Map<IColumnType, String> JAVA_TO_KOTLIN_TYPE = new HashMap<>();

    static {
        JAVA_TO_KOTLIN_TYPE.put(DbColumnType.BASE_INT, "Int");
        JAVA_TO_KOTLIN_TYPE.put(DbColumnType.INTEGER, "Int");
        JAVA_TO_KOTLIN_TYPE.put(DbColumnType.BASE_DOUBLE, "Double");
        JAVA_TO_KOTLIN_TYPE.put(DbColumnType.BASE_FLOAT, "Float");
        JAVA_TO_KOTLIN_TYPE.put(DbColumnType.BASE_LONG, "Long");
        JAVA_TO_KOTLIN_TYPE.put(DbColumnType.BASE_BOOLEAN, "Boolean");
        JAVA_TO_KOTLIN_TYPE.put(DbColumnType.BASE_CHAR, "Char");
    }


    @Override
    public Map<String, List<String>> getMethodList(TableInfo tableInfo) {
        Map<String, List<DatabaseMetaDataWrapper.Index>> indexlistMap = tableInfo.getIndexList().stream()
            .collect(Collectors.groupingBy(DatabaseMetaDataWrapper.Index::getName));
        String entityName = tableInfo.getEntityName();
        GlobalConfig globalConfig = tableInfo.getGlobalConfig();
        Set<Map.Entry<String, List<DatabaseMetaDataWrapper.Index>>> entrySet = indexlistMap.entrySet();
        Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<String, List<DatabaseMetaDataWrapper.Index>> entry : entrySet) {
            List<String> methodList = new ArrayList<>();
            String indexName = entry.getKey();
//            boolean compositeKey = false;
            List<DatabaseMetaDataWrapper.Index> indexList = entry.getValue();
            int indexSize = indexList.size();
            if ("PRIMARY".equals(indexName)) {
                if (indexSize == 1) {
                    // skip id -> selectById
                    continue;
                }
//                // 复合主键
//                compositeKey = indexList.size() > 1;
            }
            Map<String, TableField> tableFieldMap = tableInfo.getTableFieldMap();
            StringBuilder baseMethodNameBuilder = new StringBuilder();
            StringBuilder argsBuilder = new StringBuilder();
            StringBuilder baseWrapperBuilder = new StringBuilder();
            boolean uniqueKey = false;
            for (int i = 0; i < indexSize; i++) {
                DatabaseMetaDataWrapper.Index index = indexList.get(i);
                if (index.isUnique()) {
                    uniqueKey = true;
                }
                TableField tableField = tableFieldMap.get(index.getColumnName());
                baseMethodNameBuilder.append(tableField.getCapitalName());
                if (globalConfig.isKotlin()) {
                    if (indexSize > 1) {
                        baseWrapperBuilder.append("eq(ObjectUtils.isNotNull(").append(tableField.getPropertyName()).append(")").append(", ").append(entityName).append("::");
                    } else {
                        baseWrapperBuilder.append("eq(").append(entityName).append("::");
                    }
                    baseWrapperBuilder.append(",").append(" ").append(tableField.getPropertyName()).append(")");
                    argsBuilder.append(tableField.getPropertyName()).append(":").append(" ")
                        .append(JAVA_TO_KOTLIN_TYPE.getOrDefault(tableField.getColumnType(), tableField.getColumnType().getType()));
                    if (i < indexSize - 1) {
                        baseWrapperBuilder.append(".");
                        baseMethodNameBuilder.append("And");
                        argsBuilder.append(", ");
                    }
                } else {
                    if (indexSize > 1) {
                        baseWrapperBuilder.append("eq(ObjectUtils.isNotNull(").append(tableField.getPropertyName()).append(")").append(", ").append(entityName).append("::");
                    } else {
                        baseWrapperBuilder.append("eq(").append(entityName).append("::");
                    }
                    if ("boolean".equals(tableField.getPropertyType())) {
                        baseWrapperBuilder.append("is").append(tableField.getCapitalName());
                    } else {
                        baseWrapperBuilder.append("get").append(tableField.getCapitalName());
                    }
                    baseWrapperBuilder.append(",").append(" ").append(tableField.getPropertyName()).append(")");
                    argsBuilder.append(tableField.getColumnType().getType()).append(" ").append(tableField.getPropertyName());
                    if (i < indexSize - 1) {
                        baseWrapperBuilder.append(".");
                        baseMethodNameBuilder.append("And");
                        argsBuilder.append(", ");
                    }
                }
            }
            String baseMethodName = baseMethodNameBuilder.toString();
            String args = argsBuilder.toString();
            String baseWrapper = baseWrapperBuilder.toString();
            if (globalConfig.isKotlin()) {
                String selectByMethod = getKotlinSelectMethod(tableInfo, (indexSize > 1 || !uniqueKey),
                    "selectBy" + baseMethodName, args,
                    "KtQueryWrapper(" + tableInfo.getEntityName() + "::class.java)." + baseWrapper);
                String updateByMethod = getKotlinUpdateMethod(tableInfo,
                    "updateBy" + baseMethodName, tableInfo.getEntityName() + " entity" + ", " + args,
                    "KtUpdateWrapper(" + tableInfo.getEntityName() + "::class.java)." + baseWrapper);
                String deleteByMethod = getKotlinDeleteMethod(tableInfo,
                    "deleteBy" + baseMethodName, args,
                    "KtUpdateWrapper(" + tableInfo.getEntityName() + "::class.java)." + baseWrapper);
                methodList.add(selectByMethod);
                methodList.add(updateByMethod);
                methodList.add(deleteByMethod);
            } else {
                String selectByMethod = getSelectMethod(tableInfo, (indexSize > 1 || !uniqueKey),
                    "selectBy" + baseMethodName, args,
                    "Wrappers.<" + tableInfo.getEntityName() + ">lambdaQuery()." + baseWrapper);
                String updateByMethod = getUpdateMethod(tableInfo,
                    "updateBy" + baseMethodName, tableInfo.getEntityName() + " entity" + ", " + args,
                    "Wrappers.<" + tableInfo.getEntityName() + ">lambdaUpdate()." + baseWrapper);
                String deleteByMethod = getDeleteMethod(tableInfo,
                    "deleteBy" + baseMethodName, args,
                    "Wrappers.<" + tableInfo.getEntityName() + ">lambdaUpdate()." + baseWrapper);
                methodList.add(selectByMethod);
                methodList.add(updateByMethod);
                methodList.add(deleteByMethod);
            }
            result.put(indexName, methodList);
        }
        return result;
    }

}
