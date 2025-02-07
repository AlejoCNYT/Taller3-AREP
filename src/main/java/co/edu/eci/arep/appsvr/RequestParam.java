package co.edu.eci.arep.appsvr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@RequestParam(value = "name", defaultValue = "world")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam
{
    String value();
    String defaultValue();
}
