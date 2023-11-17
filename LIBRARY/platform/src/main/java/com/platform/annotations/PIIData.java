package com.platform.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.platform.security.PIIDataSerializer;
import com.platform.user.Permissions;

/**
 * Annotate PII fields to mask data in api response
 */
@JacksonAnnotationsInside
@JsonSerialize(using = PIIDataSerializer.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PIIData {

    Permissions[] allowedRolePermissions() default { Permissions.SUPER_USER };
    
    int visibleCharacters() default 0;

}