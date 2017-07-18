package com.kayac.lobi.libnakamap.functional;

import java.util.ArrayList;
import java.util.List;

public class Functional {

    public interface Predicater<T> {
        boolean predicate(T t);
    }

    public interface Reducer<A, B> {
        B reduce(B b, A a);
    }

    public static <A, B> B reduce(List<A> list, B initValue, Reducer<A, B> reducer) {
        if (list == null) {
            return initValue;
        }
        B value = initValue;
        for (A a : list) {
            value = reducer.reduce(value, a);
        }
        return value;
    }

    public static <T> List<T> filter(List<T> list, Predicater<T> predicater) {
        List<T> result = new ArrayList();
        for (T t : list) {
            if (predicater.predicate(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
