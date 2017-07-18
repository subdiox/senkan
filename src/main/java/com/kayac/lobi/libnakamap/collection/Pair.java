package com.kayac.lobi.libnakamap.collection;

public class Pair<T, E> {
    public final T first;
    public final E second;

    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return super.equals(o);
        }
        Pair<?, ?> p = (Pair) o;
        return p.first.equals(this.first) && p.second.equals(this.second);
    }

    public int hashCode() {
        return (this.first.hashCode() * 31) + this.second.hashCode();
    }

    public String toString() {
        return String.format("(%s, %s", new Object[]{this.first, this.second});
    }
}
