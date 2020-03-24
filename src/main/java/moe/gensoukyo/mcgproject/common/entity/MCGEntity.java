package moe.gensoukyo.mcgproject.common.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author drzzm32
 * @date 2020/3/24
 * @apiNote 每个实体类都应该使用这个注解，来说明自己的实体名称
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MCGEntity {
    String value();
}
