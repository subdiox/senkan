package com.kayac.lobi.libnakamap.value;

public interface GroupInterface {

    public enum GroupType {
        CATEGORY,
        GROUP
    }

    String getDescription();

    GroupType getGroupType();

    String getIcon();

    String getName();

    String getUid();
}
