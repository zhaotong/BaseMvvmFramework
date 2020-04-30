package com.base.mvvm.framework;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestType<T> {

    public T date;


    public void getType() {
        TestType<String> testType = new TestType<>();
        testType.date = "sss";

        Type genericSuperclass = testType.getClass().getGenericSuperclass();
        ParameterizedType genericSuperclass1 = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = genericSuperclass1.getActualTypeArguments();
        Log.d("TAG", "getType: ");
    }
}
