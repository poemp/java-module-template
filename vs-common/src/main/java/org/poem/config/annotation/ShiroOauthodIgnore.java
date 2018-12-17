package org.poem.config.annotation;

import java.lang.annotation.*;

/**
 * 注释
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShiroOauthodIgnore {

    /**
     * 取消的地址
     * @return
     */
    String value() default "";
}
