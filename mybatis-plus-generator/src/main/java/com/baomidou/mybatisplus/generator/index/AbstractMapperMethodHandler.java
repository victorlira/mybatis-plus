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

import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 * @author nieqiurong
 * @since 3.5.10
 */
//TODO 待定修改方法体
public abstract class AbstractMapperMethodHandler implements IGenerateMapperMethodHandler {

    public String getSelectMethod(TableInfo tableInfo, boolean returnList, String methodName, String args, String returnCode) {
        return returnList ? getSelectListMethod(tableInfo, methodName, args, returnCode) : getSelectOneMethod(tableInfo, methodName, args, returnCode);
    }

    public String getKotlinSelectMethod(TableInfo tableInfo, boolean returnList, String methodName, String args, String returnCode) {
        return returnList ? getKotlinSelectListMethod(tableInfo, methodName, args, returnCode) : getKotlinSelectOneMethod(tableInfo, methodName, args, returnCode);
    }

    public String getSelectListMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "default" + " " +
            "List<" + tableInfo.getEntityName() + ">" + " " + methodName + "(" + args + ")" + " " + "{" + "\n" +
            "        return " + "selectList(" + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

    public String getKotlinSelectListMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "fun " + methodName + "(" + args + ")" + " :List<" + tableInfo.getEntityName() + ">?" + " {" + "\n" +
            "        return " + "selectList(" + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

    public String getSelectOneMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "default" + " " +
            tableInfo.getEntityName() + " " + methodName + "(" + args + ")" + " " + "{" + "\n" +
            "        return " + "selectOne(" + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

    public String getKotlinSelectOneMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "fun " + methodName + "(" + args + ")" + " :" + tableInfo.getEntityName() + "?" + " {" + "\n" +
            "        return " + "selectOne(" + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

    public String getUpdateMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "default" + " " +
            "int" + " " + methodName + "(" + args + ")" + " " + "{" + "\n" +
            "        return update(entity, " + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

    public String getKotlinUpdateMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "fun " + methodName + "(" + args + ")" + " :Int?" + " {" + "\n" +
            "        return " + "update(" + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

    public String getDeleteMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "default" + " " +
            "int" + " " + methodName + "(" + args + ")" + " " + "{" + "\n" +
            "        return delete(" + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

    public String getKotlinDeleteMethod(TableInfo tableInfo, String methodName, String args, String returnCode) {
        return "fun " + methodName + "(" + args + ")" + " :Int?" + " {" + "\n" +
            "        return " + "delete(" + returnCode + ")" + ";" + "\n" +
            "    }\n";
    }

}
