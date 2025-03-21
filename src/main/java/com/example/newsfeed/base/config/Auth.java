package com.example.newsfeed.base.config;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)//런하는 동안만 사용가능
@Documented
public @interface Auth {
}
