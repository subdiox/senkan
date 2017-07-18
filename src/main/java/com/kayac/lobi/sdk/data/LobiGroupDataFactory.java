package com.kayac.lobi.sdk.data;

import com.kayac.lobi.libnakamap.value.GroupDetailValue;

public class LobiGroupDataFactory {
    public static final String EXTRA_GROUP_DATA = "extra_group_data";

    public static LobiGroupData create(GroupDetailValue detailValue) {
        return new LobiGroupData(detailValue);
    }
}
