package ir.serenade.sesame.domain.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BaseDomain implements Serializable {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);

    }
}