package com.kayac.lobi.libnakamap.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.components.ListRow.OneLineSmall;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends Dialog {
    private boolean mButtonAttached;
    private View mViewContent;

    public static final class EditTextContent extends FrameLayout {
        public EditTextContent(Context context, String hintText, CharSequence initText, boolean oneline) {
            View v;
            super(context);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
            if (oneline) {
                v = inflater.inflate(R.layout.lobi_custom_dialog_content_online_edittext, this, true);
            } else {
                v = inflater.inflate(R.layout.lobi_custom_dialog_content_edittext, this, true);
            }
            EditText t = (EditText) v.findViewById(R.id.lobi_custom_dialog_content_edittext);
            DebugAssert.assertNotNull(t);
            if (hintText != null) {
                t.setHint(hintText);
            }
            if (initText != null) {
                t.setText(initText);
            }
        }

        public String getText() {
            EditText t = (EditText) findViewById(R.id.lobi_custom_dialog_content_edittext);
            DebugAssert.assertNotNull(t);
            return t.getText().toString();
        }
    }

    public static final class FooterWithButton extends FrameLayout {
        public void setPositiveButton(String title, OnClickListener listener) {
            Button button = (Button) findViewById(R.id.lobi_custom_dialog_footer_positive_button);
            DebugAssert.assertNotNull(button);
            button.setVisibility(0);
            button.setText(title);
            button.setOnClickListener(listener);
            showButtonSplitIfNecessary();
        }

        public void setNegativeButton(String title, OnClickListener listener) {
            Button button = (Button) findViewById(R.id.lobi_custom_dialog_footer_negative_button);
            DebugAssert.assertNotNull(button);
            button.setVisibility(0);
            button.setText(title);
            button.setOnClickListener(listener);
            showButtonSplitIfNecessary();
        }

        public void setCancelButton(String title, OnClickListener listener) {
            Button button = (Button) findViewById(R.id.lobi_custom_dialog_footer_cancel_button);
            DebugAssert.assertNotNull(button);
            button.setVisibility(0);
            button.setText(title);
            button.setOnClickListener(listener);
            View split = findViewById(R.id.lobi_custom_dialog_footer_cancel_button_split);
            DebugAssert.assertNotNull(split);
            split.setVisibility(0);
            showButtonSplitIfNecessary();
        }

        public FooterWithButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_dialog_footer_with_button, this, true);
        }

        public FooterWithButton(Context context) {
            this(context, null);
        }

        private void showButtonSplitIfNecessary() {
            Button button = (Button) findViewById(R.id.lobi_custom_dialog_footer_negative_button);
            DebugAssert.assertNotNull(button);
            boolean negativeIsVisible = button.getVisibility() == 0;
            button = (Button) findViewById(R.id.lobi_custom_dialog_footer_positive_button);
            DebugAssert.assertNotNull(button);
            boolean positiveIsVisible;
            if (button.getVisibility() == 0) {
                positiveIsVisible = true;
            } else {
                positiveIsVisible = false;
            }
            if (negativeIsVisible && positiveIsVisible) {
                View split = findViewById(R.id.lobi_custom_dialog_footer_button_split);
                DebugAssert.assertNotNull(split);
                split.setVisibility(0);
            }
        }
    }

    public static final class ImageContent extends FrameLayout {
        public ImageContent(Context context, String url) {
            super(context);
            ImageLoaderView i = (ImageLoaderView) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_dialog_content_image, this, true).findViewById(R.id.lobi_custom_dialog_content_image);
            DebugAssert.assertNotNull(i);
            i.loadImage(url);
        }
    }

    public static final class MultiLineAdapter extends BaseAdapter {
        private final Context mContext;
        private List<String> mItems = new ArrayList();

        static final class Holder {
            public final TextView text;

            public static final class Builder {
                private TextView text;

                public void setText(android.widget.TextView r1) {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r0 = this;
                    r0.text = r1;
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Holder.Builder.setText(android.widget.TextView):void");
                }

                public com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Holder build() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r2 = this;
                    r0 = new com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineAdapter$Holder;
                    r1 = r2.text;
                    r0.<init>(r1);
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Holder.Builder.build():com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineAdapter$Holder");
                }
            }

            public Holder(android.widget.TextView r1) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.text = r1;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Holder.<init>(android.widget.TextView):void");
            }

            public void bind(com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Value r3) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r2 = this;
                r0 = r2.text;
                r1 = r3.text;
                r0.setText(r1);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Holder.bind(com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineAdapter$Value):void");
            }
        }

        public static final class Value {
            public final String text;

            public Value(java.lang.String r1) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.text = r1;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Value.<init>(java.lang.String):void");
            }
        }

        public MultiLineAdapter(android.content.Context r2) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r1.<init>();
            r0 = new java.util.ArrayList;
            r0.<init>();
            r1.mItems = r0;
            r1.mContext = r2;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.<init>(android.content.Context):void");
        }

        public void setItems(java.util.List<java.lang.String> r1) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r0 = this;
            r0.mItems = r1;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.setItems(java.util.List):void");
        }

        public java.util.List<java.lang.String> getItems() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r0 = r1.mItems;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.getItems():java.util.List<java.lang.String>");
        }

        public int getCount() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r0 = r1.mItems;
            r0 = r0.size();
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.getCount():int");
        }

        public java.lang.Object getItem(int r2) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r0 = r1.mItems;
            r0 = r0.get(r2);
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.getItem(int):java.lang.Object");
        }

        public long getItemId(int r3) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r2 = this;
            r0 = (long) r3;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.getItemId(int):long");
        }

        public android.view.View getView(int r5, android.view.View r6, android.view.ViewGroup r7) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r4 = this;
            r2 = 0;
            if (r6 != 0) goto L_0x001e;
        L_0x0003:
            r3 = r4.mContext;
            r2 = listRowBuilder(r3);
        L_0x0009:
            r0 = r2.getTag();
            r0 = (com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.Holder) r0;
            r1 = new com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineAdapter$Value;
            r3 = r4.getItem(r5);
            r3 = (java.lang.String) r3;
            r1.<init>(r3);
            r0.bind(r1);
            return r2;
        L_0x001e:
            r2 = r6;
            r2 = (com.kayac.lobi.libnakamap.components.ListRow) r2;
            goto L_0x0009;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
        }

        private static final com.kayac.lobi.libnakamap.components.ListRow listRowBuilder(android.content.Context r13) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r12 = 0;
            r11 = 8;
            r3 = new com.kayac.lobi.libnakamap.components.ListRow;
            r3.<init>(r13);
            r8 = com.kayac.lobi.sdk.R.drawable.lobi_custom_dialog_list_row_selector;
            r3.setRowBackgraound(r8);
            r5 = new android.widget.LinearLayout$LayoutParams;
            r8 = -1;
            r9 = r13.getResources();
            r10 = com.kayac.lobi.sdk.R.dimen.lobi_custom_dialog_list_row_height;
            r9 = r9.getDimensionPixelSize(r10);
            r5.<init>(r8, r9);
            r8 = com.kayac.lobi.sdk.R.id.lobi_list_row_area;
            r6 = r3.findViewById(r8);
            r6 = (android.widget.LinearLayout) r6;
            r6.setLayoutParams(r5);
            r8 = com.kayac.lobi.sdk.R.drawable.lobi_custom_dialog_list_row_selector;
            r3.setBackgroundResource(r8);
            r3.setIndexVisibility(r11);
            r8 = r3.getContentArea(r12);
            r8.setVisibility(r11);
            r8 = 2;
            r8 = r3.getContentArea(r8);
            r8.setVisibility(r11);
            r1 = new com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineAdapter$Holder$Builder;
            r1.<init>();
            r8 = com.kayac.lobi.sdk.R.id.lobi_list_row_center_content_view_area;
            r0 = r3.findViewById(r8);
            r0 = (android.widget.FrameLayout) r0;
            r4 = r0.getLayoutParams();
            r4 = (android.widget.LinearLayout.LayoutParams) r4;
            r8 = 16;
            r4.gravity = r8;
            r0.setLayoutParams(r4);
            r2 = new com.kayac.lobi.libnakamap.components.ListRow$OneLine;
            r2.<init>(r13);
            r8 = 1;
            r3.setContent(r8, r2);
            r8 = com.kayac.lobi.sdk.R.id.lobi_line_0;
            r7 = r2.findViewById(r8);
            r7 = (android.widget.TextView) r7;
            r8 = r13.getResources();
            r9 = com.kayac.lobi.sdk.R.dimen.lobi_text_large;
            r8 = r8.getDimensionPixelSize(r9);
            r8 = (float) r8;
            r7.setTextSize(r12, r8);
            r1.setText(r7);
            r8 = r1.build();
            r3.setTag(r8);
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineAdapter.listRowBuilder(android.content.Context):com.kayac.lobi.libnakamap.components.ListRow");
        }
    }

    public static final class MultiLineWithCheckboxAdapter extends BaseAdapter {
        private final Context mContext;
        private List<Pair<String, Boolean>> mItems = new ArrayList();

        static final class Holder {
            public final CustomCheckbox checkbox;
            public final TextView text;

            public static final class Builder {
                private CustomCheckbox checkbox;
                private TextView text;

                public void setText(android.widget.TextView r1) {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r0 = this;
                    r0.text = r1;
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Holder.Builder.setText(android.widget.TextView):void");
                }

                public void setChechbox(com.kayac.lobi.libnakamap.components.CustomCheckbox r1) {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r0 = this;
                    r0.checkbox = r1;
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Holder.Builder.setChechbox(com.kayac.lobi.libnakamap.components.CustomCheckbox):void");
                }

                public com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Holder build() {
                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                    /*
                    r3 = this;
                    r0 = new com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineWithCheckboxAdapter$Holder;
                    r1 = r3.text;
                    r2 = r3.checkbox;
                    r0.<init>(r1, r2);
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Holder.Builder.build():com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineWithCheckboxAdapter$Holder");
                }
            }

            public Holder(android.widget.TextView r1, com.kayac.lobi.libnakamap.components.CustomCheckbox r2) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.text = r1;
                r0.checkbox = r2;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Holder.<init>(android.widget.TextView, com.kayac.lobi.libnakamap.components.CustomCheckbox):void");
            }

            public void bind(com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Value r3) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r2 = this;
                r0 = r2.text;
                r1 = r3.text;
                r0.setText(r1);
                r0 = r2.checkbox;
                r1 = r3.checked;
                r0.setChecked(r1);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Holder.bind(com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineWithCheckboxAdapter$Value):void");
            }
        }

        public static final class Value {
            public final boolean checked;
            public final String text;

            public Value(java.lang.String r1, boolean r2) {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.text = r1;
                r0.checked = r2;
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Value.<init>(java.lang.String, boolean):void");
            }
        }

        public MultiLineWithCheckboxAdapter(android.content.Context r2) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r1.<init>();
            r0 = new java.util.ArrayList;
            r0.<init>();
            r1.mItems = r0;
            r1.mContext = r2;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.<init>(android.content.Context):void");
        }

        public void setItems(java.util.List<com.kayac.lobi.libnakamap.collection.Pair<java.lang.String, java.lang.Boolean>> r1) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r0 = this;
            r0.mItems = r1;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.setItems(java.util.List):void");
        }

        public java.util.List<com.kayac.lobi.libnakamap.collection.Pair<java.lang.String, java.lang.Boolean>> getItems() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r0 = r1.mItems;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.getItems():java.util.List<com.kayac.lobi.libnakamap.collection.Pair<java.lang.String, java.lang.Boolean>>");
        }

        public int getCount() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r0 = r1.mItems;
            r0 = r0.size();
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.getCount():int");
        }

        public java.lang.Object getItem(int r2) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r1 = this;
            r0 = r1.mItems;
            r0 = r0.get(r2);
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.getItem(int):java.lang.Object");
        }

        public long getItemId(int r3) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r2 = this;
            r0 = (long) r3;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.getItemId(int):long");
        }

        public android.view.View getView(int r7, android.view.View r8, android.view.ViewGroup r9) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r6 = this;
            r3 = 0;
            if (r8 != 0) goto L_0x002a;
        L_0x0003:
            r4 = r6.mContext;
            r3 = listRowBuilder(r4);
        L_0x0009:
            r0 = r3.getTag();
            r0 = (com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.Holder) r0;
            r1 = r6.getItem(r7);
            r1 = (com.kayac.lobi.libnakamap.collection.Pair) r1;
            r2 = new com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineWithCheckboxAdapter$Value;
            r4 = r1.first;
            r4 = (java.lang.String) r4;
            r5 = r1.second;
            r5 = (java.lang.Boolean) r5;
            r5 = r5.booleanValue();
            r2.<init>(r4, r5);
            r0.bind(r2);
            return r3;
        L_0x002a:
            r3 = r8;
            r3 = (com.kayac.lobi.libnakamap.components.ListRow) r3;
            goto L_0x0009;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
        }

        private static final com.kayac.lobi.libnakamap.components.ListRow listRowBuilder(android.content.Context r13) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r5 = new com.kayac.lobi.libnakamap.components.ListRow;
            r5.<init>(r13);
            r7 = new android.widget.LinearLayout$LayoutParams;
            r10 = -1;
            r11 = r13.getResources();
            r12 = com.kayac.lobi.sdk.R.dimen.lobi_custom_dialog_list_row_height;
            r11 = r11.getDimensionPixelSize(r12);
            r7.<init>(r10, r11);
            r10 = com.kayac.lobi.sdk.R.id.lobi_list_row_area;
            r8 = r5.findViewById(r10);
            r8 = (android.widget.LinearLayout) r8;
            r8.setLayoutParams(r7);
            r10 = com.kayac.lobi.sdk.R.drawable.lobi_custom_dialog_list_row_selector;
            r5.setBackgroundResource(r10);
            r10 = 8;
            r5.setIndexVisibility(r10);
            r10 = 0;
            r10 = r5.getContentArea(r10);
            r11 = 8;
            r10.setVisibility(r11);
            r1 = new com.kayac.lobi.libnakamap.components.CustomDialog$MultiLineWithCheckboxAdapter$Holder$Builder;
            r1.<init>();
            r10 = com.kayac.lobi.sdk.R.id.lobi_list_row_center_content_view_area;
            r0 = r5.findViewById(r10);
            r0 = (android.widget.FrameLayout) r0;
            r6 = r0.getLayoutParams();
            r6 = (android.widget.LinearLayout.LayoutParams) r6;
            r10 = 16;
            r6.gravity = r10;
            r0.setLayoutParams(r6);
            r2 = new com.kayac.lobi.libnakamap.components.ListRow$OneLine;
            r2.<init>(r13);
            r10 = 1;
            r5.setContent(r10, r2);
            r10 = com.kayac.lobi.sdk.R.id.lobi_line_0;
            r9 = r2.findViewById(r10);
            r9 = (android.widget.TextView) r9;
            r10 = 0;
            r11 = r13.getResources();
            r12 = com.kayac.lobi.sdk.R.dimen.lobi_text_large;
            r11 = r11.getDimensionPixelSize(r12);
            r11 = (float) r11;
            r9.setTextSize(r10, r11);
            r1.setText(r9);
            r10 = com.kayac.lobi.sdk.R.id.lobi_list_row_right_content_view_area;
            r0 = r5.findViewById(r10);
            r0 = (android.widget.FrameLayout) r0;
            r6 = r0.getLayoutParams();
            r6 = (android.widget.LinearLayout.LayoutParams) r6;
            r10 = 16;
            r6.gravity = r10;
            r0.setLayoutParams(r6);
            r4 = android.view.LayoutInflater.from(r13);
            r10 = com.kayac.lobi.sdk.R.layout.lobi_list_row_content_checkbox;
            r11 = 0;
            r3 = r4.inflate(r10, r11);
            r3 = (com.kayac.lobi.libnakamap.components.CustomCheckbox) r3;
            r10 = 0;
            r3.setFocusable(r10);
            r10 = 0;
            r3.setClickable(r10);
            r10 = 2;
            r5.setContent(r10, r3);
            r1.setChechbox(r3);
            r10 = r1.build();
            r5.setTag(r10);
            return r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.components.CustomDialog.MultiLineWithCheckboxAdapter.listRowBuilder(android.content.Context):com.kayac.lobi.libnakamap.components.ListRow");
        }
    }

    public static final class TextCheckBoxContent extends FrameLayout {
        public TextCheckBoxContent(Context context, String str, String checkBoxStr) {
            super(context);
            TextView t = (TextView) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_dialog_content_text_and_checkbox, this, true).findViewById(R.id.lobi_custom_dialog_content_text);
            DebugAssert.assertNotNull(t);
            t.setText(str);
            ListRow listRow = (ListRow) findViewById(R.id.lobi_custom_dialog_content_checkbox_text);
            ((OneLineSmall) listRow.getContent(1)).setText(0, checkBoxStr);
            CustomCheckbox b = (CustomCheckbox) listRow.getContent(0);
            b.setFocusable(false);
            b.setClickable(false);
            b.setChecked(true);
        }

        public View getCheckBoxArea() {
            return ((ListRow) findViewById(R.id.lobi_custom_dialog_content_checkbox_text)).findViewById(R.id.lobi_list_row_area);
        }

        public void setCheck() {
            boolean z = false;
            CustomCheckbox b = (CustomCheckbox) ((ListRow) findViewById(R.id.lobi_custom_dialog_content_checkbox_text)).getContent(0);
            if (!b.isChecked()) {
                z = true;
            }
            b.setChecked(z);
        }

        public boolean isChecked() {
            return ((CustomCheckbox) ((ListRow) findViewById(R.id.lobi_custom_dialog_content_checkbox_text)).getContent(0)).isChecked();
        }
    }

    public static final class TextContent extends FrameLayout {
        public TextContent(Context context, String str) {
            super(context);
            TextView t = (TextView) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_dialog_content_text, this, true).findViewById(R.id.lobi_custom_dialog_content_text);
            DebugAssert.assertNotNull(t);
            t.setText(str);
        }
    }

    public static void appendDialogFragment(FragmentActivity activity, DialogFragment fragment, String tag) {
        if (!activity.isFinishing()) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.add((Fragment) fragment, tag);
            transaction.commitAllowingStateLoss();
        }
    }

    public static final CustomDialog createMultiLineDialog(Context context, List<String> items, OnItemClickListener listener) {
        View listView = new ListView(context);
        listView.setDivider(new ColorDrawable(context.getResources().getColor(R.color.lobi_pearl_gray)));
        listView.setDividerHeight(1);
        MultiLineAdapter adapter = new MultiLineAdapter(context);
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
        return new CustomDialog(context, listView);
    }

    public static final CustomDialog createMultiLineDialog(Context context, List<String> items, OnItemClickListener listener, boolean canceledOnTouchOutside) {
        CustomDialog dialog = createMultiLineDialog(context, items, listener);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return dialog;
    }

    public static final CustomDialog createMultiLineDialog(Context context, String msg, List<String> buttonItems, OnItemClickListener listener) {
        ListView listView = new ListView(context);
        listView.setDivider(new ColorDrawable(context.getResources().getColor(R.color.lobi_pearl_gray)));
        listView.setDividerHeight(1);
        MultiLineAdapter adapter = new MultiLineAdapter(context);
        adapter.setItems(buttonItems);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
        return new CustomDialog(context, listView, msg);
    }

    public static final CustomDialog createMultiLineWithSingleSelectDialog(Context context, List<Pair<String, Boolean>> items, OnItemClickListener listener) {
        View listView = new ListView(context);
        listView.setDivider(new ColorDrawable(context.getResources().getColor(R.color.lobi_pearl_gray)));
        listView.setDividerHeight(1);
        MultiLineWithCheckboxAdapter adapter = new MultiLineWithCheckboxAdapter(context);
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
        return new CustomDialog(context, listView);
    }

    public static final CustomDialog createTextDialog(Context context, String text) {
        return new CustomDialog(context, new TextContent(context, text));
    }

    public static final CustomDialog createImageDialog(Context context, String img) {
        return new CustomDialog(context, new ImageContent(context, img));
    }

    public View getViewContent() {
        return this.mViewContent;
    }

    public void setTitle(String title) {
        View titleArea = findViewById(R.id.lobi_custom_dialog_title_area);
        DebugAssert.assertNotNull(titleArea);
        titleArea.setVisibility(0);
        TextView text = (TextView) findViewById(R.id.lobi_custom_dialog_title);
        DebugAssert.assertNotNull(text);
        text.setText(title);
    }

    public void showTitle() {
        View titleArea = findViewById(R.id.lobi_custom_dialog_title_area);
        DebugAssert.assertNotNull(titleArea);
        titleArea.setVisibility(0);
    }

    public void hideTitle() {
        View titleArea = findViewById(R.id.lobi_custom_dialog_title_area);
        DebugAssert.assertNotNull(titleArea);
        titleArea.setVisibility(8);
    }

    public void setPositiveButton(String title, OnClickListener listener) {
        buttonAttachIfNecessary();
        FooterWithButton footer = (FooterWithButton) findViewById(R.id.lobi_custom_dialog_footer_view);
        DebugAssert.assertNotNull(footer);
        footer.setPositiveButton(title, listener);
    }

    public void setNegativeButton(String title, OnClickListener listener) {
        buttonAttachIfNecessary();
        FooterWithButton footer = (FooterWithButton) findViewById(R.id.lobi_custom_dialog_footer_view);
        DebugAssert.assertNotNull(footer);
        footer.setNegativeButton(title, listener);
    }

    public void setCancelButton(String title, OnClickListener listener) {
        buttonAttachIfNecessary();
        FooterWithButton footer = (FooterWithButton) findViewById(R.id.lobi_custom_dialog_footer_view);
        DebugAssert.assertNotNull(footer);
        footer.setCancelButton(title, listener);
    }

    public void hideFooter() {
        FooterWithButton footer = (FooterWithButton) findViewById(R.id.lobi_custom_dialog_footer_view);
        DebugAssert.assertNotNull(footer);
        footer.setVisibility(8);
    }

    private CustomDialog(Context context, int theme) {
        super(context, theme);
        this.mButtonAttached = false;
        this.mViewContent = null;
    }

    public CustomDialog(Context context, View content) {
        boolean z = false;
        this(context, R.style.lobi_custom_dialog);
        View view = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_dialog, null, false);
        LinearLayout root = (LinearLayout) view.findViewById(R.id.lobi_custom_dialog_root);
        DebugAssert.assertNotNull(root);
        View oldContent = view.findViewById(R.id.lobi_custom_dialog_content_view);
        DebugAssert.assertNotNull(oldContent);
        int index = getIndex(root, oldContent);
        if (index != -1) {
            z = true;
        }
        DebugAssert.assertTrue(z);
        root.removeView(oldContent);
        content.setLayoutParams(oldContent.getLayoutParams());
        root.addView(content, index);
        setContentView(view);
        this.mViewContent = content;
        LayoutParams lp = getWindow().getAttributes();
        lp.width = -1;
        getWindow().setAttributes(lp);
    }

    public CustomDialog(Context context, View content, String msg) {
        this(context, content);
        ViewGroup parent = (ViewGroup) getViewContent().getParent();
        parent.findViewById(R.id.lobi_custom_dialog_msg_area).setVisibility(0);
        ((TextView) parent.findViewById(R.id.lobi_custom_dialog_msg)).setText(msg);
    }

    private int getIndex(ViewGroup root, View view) {
        for (int i = 0; i < root.getChildCount(); i++) {
            if (view == root.getChildAt(i)) {
                return i;
            }
        }
        return -1;
    }

    private void buttonAttachIfNecessary() {
        boolean z = true;
        if (!this.mButtonAttached) {
            this.mButtonAttached = true;
            LinearLayout root = (LinearLayout) findViewById(R.id.lobi_custom_dialog_root);
            DebugAssert.assertNotNull(root);
            View oldFooter = findViewById(R.id.lobi_custom_dialog_footer_view);
            DebugAssert.assertNotNull(oldFooter);
            int index = getIndex(root, oldFooter);
            if (index == -1) {
                z = false;
            }
            DebugAssert.assertTrue(z);
            root.removeView(oldFooter);
            FooterWithButton newFooter = new FooterWithButton(getContext());
            newFooter.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            newFooter.setId(oldFooter.getId());
            root.addView(newFooter, index);
        }
    }

    @Deprecated
    public void setTitle(CharSequence title) {
    }

    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }
}
