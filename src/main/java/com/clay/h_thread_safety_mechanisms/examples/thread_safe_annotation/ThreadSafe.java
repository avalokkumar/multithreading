package com.clay.h_thread_safety_mechanisms.examples.thread_safe_annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadSafe {
    // No additional implementation required
}
