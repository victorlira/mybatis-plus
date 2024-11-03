package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

<#if enableGenerateIndexMethod>
import java.util.List;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
    <#if kotlin>
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
    <#else>
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
    </#if>
</#if>
<#if mapperAnnotationClass??>
import ${mapperAnnotationClass.name};
</#if>

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if mapperAnnotationClass??>
@${mapperAnnotationClass.simpleName}
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}> {
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
</#if>

<#list mapperMethodMap?keys as key>
    <#assign list=mapperMethodMap[key] />
    <#list list as method>
    /**
     * generate by ${key}
     */
    ${method}
    </#list>
</#list>
}

