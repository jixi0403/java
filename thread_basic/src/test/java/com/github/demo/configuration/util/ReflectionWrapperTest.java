package com.github.demo.configuration.util;

import com.github.annotation.ConfigRoot;
import com.github.annotation.DisConfigField;
import com.github.configuration.util.ReflectionWrapper;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by error on 2017/3/30.
 */
public class ReflectionWrapperTest {
    public static final String stringCanonicalName = String.class.getCanonicalName();
    public static final String IntegerCanonicalName1 = Integer.class.getCanonicalName();
    public static final String IntegerCanonicalName2 = int.class.getCanonicalName();
    public static final String LongCanonicalName1 = Long.class.getCanonicalName();
    public static final String LongCanonicalName2 = long.class.getCanonicalName();
    public static final String DoubleCanonicalName1 = Double.class.getCanonicalName();
    public static final String DoubleCanonicalName2 = double.class.getCanonicalName();
    public static final String FloatCanonicalName1 = Float.class.getCanonicalName();
    public static final String FloatCanonicalName2 = float.class.getCanonicalName();

    ReflectionWrapper wrapper = new ReflectionWrapper("com.github.configuration.common");

    @Test
    public void testLoadClasses() throws IllegalAccessException, InstantiationException {
        Set<Class<?>> classSet = wrapper.getTypesAnnotateWith(ConfigRoot.class);
        for(Class<?> clazz : classSet) {
            Object obj = clazz.newInstance();
            Set<Field> fieldSet = wrapper.getFieldsAnnotatedWith(clazz, DisConfigField.class);
            for(Field f : fieldSet) {
                System.out.println(f.getName());
                DisConfigField fieldConfig = f.getAnnotation(DisConfigField.class);
                String remoteFieldName = fieldConfig.field();
                System.out.println(remoteFieldName);
                f.setAccessible(true);
                //f.set(obj, "s");
                System.out.println(f.getType());
                String type = getCanonicalName(f.getType());

                if(type.equals(stringCanonicalName)) {
                    f.set(obj, String.valueOf("1111"));
                } else if(type.equals(IntegerCanonicalName1) || type.equals(IntegerCanonicalName2)) {
                    f.set(obj, Integer.parseInt("1111"));
                }
            }

            System.out.println(obj);
        }
    }

    public static String getCanonicalName(Class clazz) {
        return clazz.getCanonicalName();
    }
}
