package com.ctd.springboot.cloud.annotation.enable.global.error;

import com.ctd.springboot.cloud.controller.error.CustomErrorController;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author LYJ
 * @date 2020/8/16 15:02
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(CustomErrorController.class)
public @interface EnableGlobalErrorController {
}
