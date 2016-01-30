package de.jackwhite20.cobra.server.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Produces {

    ContentType type() default ContentType.ALL;

    enum ContentType {
        TEXT_HTML("text/html"),
        APPLICATION_JSON("application/json"),
        TEXT_XML("text/xml"),
        APPLICATION_XML("application/xml"),
        ALL("*/*");

        private String type;

        ContentType(String type) {

            this.type = type;
        }

        public String type() {

            return type;
        }
    }
}
