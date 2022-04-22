package cn.lqservice.logreq.annotion;

import java.lang.annotation.*;

/**
 * @author Qi
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoLog {
}
