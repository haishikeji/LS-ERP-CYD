package cn.px.tea.security.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义权限及多数据源切换(包括日志记录）
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    /**
     * 验证用户是否具备某权限（默认取其访问相对路径）
     */
    String permi() default "";

}
