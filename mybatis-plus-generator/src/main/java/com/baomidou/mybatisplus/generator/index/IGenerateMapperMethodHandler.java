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

import java.util.List;
import java.util.Map;

/**
 * Mapper层方法生成处理器
 *
 * @author nieqiurong
 * @since 3.5.10
 */
public interface IGenerateMapperMethodHandler {

    /**
     * 获取生成方法
     *
     * @param tableInfo 表信息
     * @return 索引方法
     */
    Map<String, List<String>> getMethodList(TableInfo tableInfo);

}
