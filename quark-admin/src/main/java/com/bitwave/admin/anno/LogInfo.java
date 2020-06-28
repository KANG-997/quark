package com.bitwave.admin.anno;


import com.bitwave.enums.LogCode;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo {

    String value() default "";

    LogCode operatorType() default LogCode.UNDEFINE;

}
