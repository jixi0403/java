package com.github.configuration.util;

import com.github.annotation.ConfigRoot;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by error on 2017/3/30.
 */
public class ReflectionWrapper {
    private final String packageName;
    private final Reflections reflections;

    public ReflectionWrapper(String packageName) {
        this.packageName = packageName;
        reflections = new Reflections(packageName);
    }

    public Set<Class<?>> getTypesAnnotateWith(Class clazz) {
        return reflections.getTypesAnnotatedWith(clazz);
    }

    public Set<Field> getFieldsAnnotatedWith(Class clazz, Class annotation) {
        return ReflectionUtils.getAllFields(clazz, ReflectionUtils.withAnnotation(annotation));
    }
}
