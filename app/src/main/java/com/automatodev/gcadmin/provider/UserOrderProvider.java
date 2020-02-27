package com.automatodev.gcadmin.provider;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class UserOrderProvider implements Parcelable {

    private String userName;
    private String userEmail;
    private String userUser;
    private String userPhone;
    private String userUid;
    private String userUrlPhoto;
    private double totalPayment;
    private Timestamp timestamp;
    private String typePayment;
    private int qtdItens;
    private String status;

    private String userStreet;
    private String userNumberHome;
    private int userCep;
    private String userComplement;
    private String userSector;
    private String userCity;
    private String userState;


    public UserOrderProvider() {
    }

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

    public String getUserUrlPhoto() {
        return userUrlPhoto;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public int getQtdItens() {
        return qtdItens;
    }

    public String getStatus() {
        return status;
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


    protected UserOrderProvider(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userUser = in.readString();
        userPhone = in.readString();
        userUid = in.readString();
        userUrlPhoto = in.readString();
        totalPayment = in.readDouble();
        typePayment = in.readString();
        qtdItens = in.readInt();
        status = in.readString();
        userStreet = in.readString();
        userNumberHome = in.readString();
        userCep = in.readInt();
        userComplement = in.readString();
        userSector = in.readString();
        userCity = in.readString();
        userState = in.readString();
    }

    public static final Creator<UserOrderProvider> CREATOR = new Creator<UserOrderProvider>() {
        @Override
        public UserOrderProvider createFromParcel(Parcel in) {
            return new UserOrderProvider(in);
        }

        @Override
        public UserOrderProvider[] newArray(int size) {
            return new UserOrderProvider[size];
        }
    };

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
        dest.writeString(userUrlPhoto);
        dest.writeDouble(totalPayment);
        dest.writeString(typePayment);
        dest.writeInt(qtdItens);
        dest.writeString(status);
        dest.writeString(userStreet);
        dest.writeString(userNumberHome);
        dest.writeInt(userCep);
        dest.writeString(userComplement);
        dest.writeString(userSector);
        dest.writeString(userCity);
        dest.writeString(userState);
    }
}
