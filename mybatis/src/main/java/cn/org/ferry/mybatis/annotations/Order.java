package cn.org.ferry.mybatis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 字段排序
 * @author: ferry
 **/

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    /**
     * 升降序
     * @return
     */
    String value() default "ASC";

    /**
     * 优先级, 值小的优先
     * @return
     */
    int priority() default 1;
}
