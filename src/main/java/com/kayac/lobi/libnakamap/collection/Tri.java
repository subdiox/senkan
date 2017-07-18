package com.kayac.lobi.libnakamap.collection;

public class Tri<T, E, K> {
    public final T first;
    public final E second;
    public final K third;

    public Tri(T first, E second, K third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
