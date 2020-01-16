package com.automatodev.gcadmin.provider;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class UsersProvider implements Parcelable {
    private String userName;;
    private String userEmail;
    private String userUser;
    private String userPhone;
    private String userUid;

    private String userStreet;
    private String userNumberHome;
    private int userCep;
    private String userComplement;
    private String userSector;
    private String userCity;
    private String userState;

    public UsersProvider(String userName, String userEmail, String userUser,
                         String userPhone, String userUid, String userEstreet,
                         String userNumberHome, int userCep, String userComplement,
                         String userSector, String userCity, String userState) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userUser = userUser;
        this.userPhone = userPhone;
        this.userUid = userUid;
        this.userStreet = userEstreet;

        this.userNumberHome = userNumberHome;
        this.userCep = userCep;
        this.userComplement = userComplement;
        this.userSector = userSector;
        this.userCity = userCity;
        this.userState = userState;
    }

    public UsersProvider() {
    }

    protected UsersProvider(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userUser = in.readString();
        userPhone = in.readString();
        userUid = in.readString();

        userStreet = in.readString();
        userNumberHome = in.readString();
        userCep = in.readInt();
        userComplement = in.readString();
        userSector = in.readString();
        userCity = in.readString();
        userState = in.readString();
    }

    public static final Creator<UsersProvider> CREATOR = new Creator<UsersProvider>() {
        @Override
        public UsersProvider createFromParcel(Parcel in) {
            return new UsersProvider(in);
        }

        @Override
        public UsersProvider[] newArray(int size) {
            return new UsersProvider[size];
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

    public String getUserStreet() {
        return userStreet;
    }

    public String getUserNumberHome() {
        return userNumberHome;
    }

    public int getUserCep() {
        return userCep;
    }

    public String getUserComplement() {
        return userComplement;
    }

    public String getUserSector() {
        return userSector;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserState() {
        return userState;
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

        dest.writeString(userStreet);
        dest.writeString(userNumberHome);
        dest.writeInt(userCep);
        dest.writeString(userComplement);
        dest.writeString(userSector);
        dest.writeString(userCity);
        dest.writeString(userState);
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName",userName);
        result.put("userEmail",userEmail);
        result.put("userUser", userUser);
        result.put("userPhone",userPhone);
        result.put("userUid",userUid);

        result.put("userStreet",userStreet);
        result.put("userNumberHome",userNumberHome);
        result.put("userCep",userCep);
        result.put("userComplement",userComplement);
        result.put("userSector",userSector);
        result.put("userCity",userCity);
        result.put("userState",userSector);

        return result;
    }

}
