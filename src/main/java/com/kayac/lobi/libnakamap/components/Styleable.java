package com.kayac.lobi.libnakamap.components;

import android.view.View;
import android.view.ViewGroup;

public interface Styleable {

    public static final class Function {
        private Function() {
        }

        public static void setChildenStyleIter(Style style, View root) {
            if (root instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) root;
                for (int c = vg.getChildCount(); 0 < c; c++) {
                    View child = vg.getChildAt(0);
                    if (child instanceof Styleable) {
                        ((Styleable) child).setStyle(style);
                    } else {
                        setChildenStyleIter(style, child);
                    }
                }
            }
        }
    }

    public static class Style {
        private int mColor = 0;

        public int getColor() {
            return this.mColor;
        }
    }

    void setStyle(Style style);
}
