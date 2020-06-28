package com.bitwave.admin.anno;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface verifyParams {
}
