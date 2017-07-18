package com.kayac.lobi.libnakamap.components;

import android.content.res.TypedArray;
import com.kayac.lobi.libnakamap.components.MegamenuItem.MenuType;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;

public class SdkChatMegamenuFragment extends ChatMegamenuFragment {
    private static final int COLUMN_COUNT_PRIVATE = 3;
    private static final int COLUMN_COUNT_PUBLIC = 2;

    protected void displayUIWithGroupValue(GroupValue group) {
        if (group == null || !group.isPublic()) {
            this.mGridView.setNumColumns(3);
        } else {
            this.mGridView.setNumColumns(2);
        }
        this.mAdapter.setItems(getMenuItemList());
    }

    protected ArrayList<MegamenuItem> getMenuItemList() {
        TypedArray icon1Array = getResources().obtainTypedArray(R.array.lobi_chat_megamenu_icon_images_1);
        TypedArray icon2Array = getResources().obtainTypedArray(R.array.lobi_chat_megamenu_icon_images_2);
        String[] titles1 = getResources().getStringArray(R.array.lobi_chat_megamenu_label_1);
        String[] titles2 = getResources().getStringArray(R.array.lobi_chat_megamenu_label_2);
        ArrayList<MegamenuItem> items = new ArrayList();
        items.add(new MegamenuItem(MenuType.GROUP_DETAIL, icon1Array.getDrawable(0), icon2Array.getDrawable(0), titles1[0], titles2[0]));
        items.add(new MegamenuItem(MenuType.GROUP_SETTING, icon1Array.getDrawable(5), icon2Array.getDrawable(5), titles1[5], titles2[5]));
        if (!(this.mGroupValue == null || this.mGroupValue.isPublic())) {
            items.add(new MegamenuItem(MenuType.INVITE_FRIEND, icon1Array.getDrawable(2), icon2Array.getDrawable(2), titles1[2], titles2[2]));
        }
        icon1Array.recycle();
        icon2Array.recycle();
        return items;
    }
}
