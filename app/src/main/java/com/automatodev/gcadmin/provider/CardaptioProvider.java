package com.automatodev.gcadmin.provider;

import android.os.Parcel;
import android.os.Parcelable;

public class CardaptioProvider implements Parcelable {
    String dishUid;
    String dishName;
    String dishDescOne;
    String dishDescTwo;
    String dishDescTree;
    String dishDescFour;
    String dishDescFive;
    double dishValue;
    String dishUrlPhoto;

    public CardaptioProvider(String dishUid, String dishName, String dishDescOne,
                             String dishDescTwo, String dishDescTree, String dishDescFour,
                             String dishDescFive, double dishValue, String dishUrlPhoto) {
        this.dishUid = dishUid;
        this.dishName = dishName;
        this.dishDescOne = dishDescOne;
        this.dishDescTwo = dishDescTwo;
        this.dishDescTree = dishDescTree;
        this.dishDescFour = dishDescFour;
        this.dishDescFive = dishDescFive;
        this.dishValue = dishValue;
        this.dishUrlPhoto = dishUrlPhoto;
    }

    protected CardaptioProvider(Parcel in) {
        dishUid = in.readString();
        dishName = in.readString();
        dishDescOne = in.readString();
        dishDescTwo = in.readString();
        dishDescTree = in.readString();
        dishDescFour = in.readString();
        dishDescFive = in.readString();
        dishValue = in.readDouble();
        dishUrlPhoto = in.readString();
    }

    public static final Creator<CardaptioProvider> CREATOR = new Creator<CardaptioProvider>() {
        @Override
        public CardaptioProvider createFromParcel(Parcel in) {
            return new CardaptioProvider(in);
        }

        @Override
        public CardaptioProvider[] newArray(int size) {
            return new CardaptioProvider[size];
        }
    };

    public void setDishUid(String dishUid) {
        this.dishUid = dishUid;
    }

    public void setDishUrlPhoto(String dishUrlPhoto) {
        this.dishUrlPhoto = dishUrlPhoto;
    }

    public CardaptioProvider() {
    }

    public String getDishUid() {
        return dishUid;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishDescOne() {
        return dishDescOne;
    }

    public String getDishDescTwo() {
        return dishDescTwo;
    }

    public String getDishDescTree() {
        return dishDescTree;
    }

    public String getDishDescFour() {
        return dishDescFour;
    }

    public String getDishDescFive() {
        return dishDescFive;
    }

    public double getDishValue() {
        return dishValue;
    }

    public String getDishUrlPhoto() {
        return dishUrlPhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dishUid);
        dest.writeString(dishName);
        dest.writeString(dishDescOne);
        dest.writeString(dishDescTwo);
        dest.writeString(dishDescTree);
        dest.writeString(dishDescFour);
        dest.writeString(dishDescFive);
        dest.writeDouble(dishValue);
        dest.writeString(dishUrlPhoto);
    }
}
