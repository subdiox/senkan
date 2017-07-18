package com.kayac.lobi.libnakamap.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.net.security.HashUtils;
import com.kayac.lobi.libnakamap.utils.DeviceUUID.OnGetUUID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

class DeviceUUIDIml implements OnAccountsUpdateListener {
    private static final String S = "40a8eefe-2489-4b5d-bf28-1da84b1e41ba";
    private static final String T = "070e24cd-34bb-4d16-844d-e8ac8fe55a10";
    private final Context mContext;
    private final OnGetUUID mOnGetUUID;

    DeviceUUIDIml(Context context, OnGetUUID onGetUUID) {
        this.mContext = context;
        this.mOnGetUUID = onGetUUID;
    }

    void getUUID() {
        if (this.mContext.getPackageManager().checkPermission("android.permission.GET_ACCOUNTS", this.mContext.getPackageName()) == 0) {
            AccountManager.get(this.mContext).addOnAccountsUpdatedListener(this, null, true);
            return;
        }
        this.mOnGetUUID.onGetUUID(HashUtils.hmacSha1(T, UUID.randomUUID().toString()));
    }

    public void onAccountsUpdated(Account[] accounts) {
        AccountManager.get(this.mContext).removeOnAccountsUpdatedListener(this);
        Pattern emailPattern = Pattern.compile("([a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256})\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");
        List<String> emails = new ArrayList();
        for (Account account : accounts) {
            String accountName = account.name;
            if (emailPattern.matcher(accountName).matches() && !emails.contains(accountName)) {
                emails.add(accountName);
            }
        }
        Collections.sort(emails);
        this.mOnGetUUID.onGetUUID(HashUtils.hmacSha1(T, S + TextUtils.join(",", emails.toArray(new String[0]))));
    }
}
