package com.kayac.lobi.libnakamap.utils;

import com.kayac.lobi.libnakamap.utils.GalleryUtil.ImageData;
import java.util.ArrayList;
import java.util.List;

public class SelectionManager {
    private static SelectionManager mManager;
    private static List<ImageData> mSelection = new ArrayList();
    private static boolean mToSend = false;

    public static synchronized SelectionManager getManager() {
        SelectionManager selectionManager;
        synchronized (SelectionManager.class) {
            if (mManager == null) {
                mManager = new SelectionManager();
            }
            selectionManager = mManager;
        }
        return selectionManager;
    }

    public final void setSelectItem(ImageData data) {
        mSelection.add(data);
    }

    public final void SetUnSelectItem(ImageData data) {
        if (mSelection.contains(data)) {
            mSelection.remove(data);
        }
    }

    public final void setData(List<ImageData> data) {
        mSelection = data;
    }

    public final List<ImageData> getSelection() {
        return mSelection;
    }

    public final List<ImageData> takeSelectionCopy() {
        ArrayList<ImageData> list = new ArrayList();
        if (mSelection != null && mSelection.size() > 0) {
            for (ImageData image : mSelection) {
                list.add(new ImageData(image.getId(), image.getUrl(), image.getType(), image.getDateTaken(), image.getAlbum()));
            }
        }
        return list;
    }

    public final ImageData getFirstSelection() {
        if (mSelection.size() > 0) {
            return (ImageData) mSelection.get(0);
        }
        return null;
    }

    public final void deleteSelection() {
        mSelection.clear();
        clearToSend();
    }

    public final boolean hasSelection() {
        if (mSelection.size() > 0) {
            return true;
        }
        return false;
    }

    public final int getSelectionSize() {
        return mSelection.size();
    }

    public final boolean getIsToSend() {
        return mToSend;
    }

    public final void setTosend() {
        mToSend = true;
    }

    public final void clearToSend() {
        mToSend = false;
    }
}
