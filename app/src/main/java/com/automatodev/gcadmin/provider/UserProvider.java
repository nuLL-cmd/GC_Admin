package com.automatodev.gcadmin.provider;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class UserProvider implements Parcelable {
    private String userName;;
    private String userEmail;
    private String userUser;
    private String userPhone;
    private String userUid;

    public UserProvider(String userName, String userEmail, String userUser, String userPhone, String userUid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userUser = userUser;
        this.userPhone = userPhone;
        this.userUid = userUid;
    }
    public UserProvider() {
    }

    protected UserProvider(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userUser = in.readString();
        userPhone = in.readString();
        userUid = in.readString();
    }

    public static final Creator<UserProvider> CREATOR = new Creator<UserProvider>() {
        @Override
        public UserProvider createFromParcel(Parcel in) {
            return new UserProvider(in);
        }

        @Override
        public UserProvider[] newArray(int size) {
            return new UserProvider[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserUser() {
        return userUser;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserUid() {
        return userUid;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName",userName);
        result.put("userEmail",userEmail);
        result.put("userUser", userUser);
        result.put("userPhone",userPhone);
        result.put("userUid",userUid);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userUser);
        dest.writeString(userPhone);
        dest.writeString(userUid);
    }
}
