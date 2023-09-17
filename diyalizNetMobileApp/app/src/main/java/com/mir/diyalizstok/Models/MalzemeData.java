package com.mir.diyalizstok.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MalzemeData implements Parcelable {
    private String id;
    private String ad;


    public MalzemeData(String id, String ad) {
        this.id = id;
        this.ad = ad;
    }

    protected MalzemeData(Parcel in) {
        id = in.readString();
        ad = in.readString();
    }

    public static final Creator<MalzemeData> CREATOR = new Creator<MalzemeData>() {
        @Override
        public MalzemeData createFromParcel(Parcel in) {
            return new MalzemeData(in);
        }

        @Override
        public MalzemeData[] newArray(int size) {
            return new MalzemeData[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    @Override
    public String toString() {
        return ad; // ListView'de sadece malzeme adlarının gösterilmesi için
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ad);
    }
}
