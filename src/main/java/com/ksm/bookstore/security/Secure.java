package com.ksm.bookstore.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 * Annotation to mark a method or type as secured and what role needs to secure
 * this function.
 * <p>
 * @author <a href="mailto:elefkof@ksmpartners.com">Emil Lefkof</a>
 */
@InterceptorBinding
@Inherited
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
public @interface Secure {

   @Nonbinding
   String[] value();

}
