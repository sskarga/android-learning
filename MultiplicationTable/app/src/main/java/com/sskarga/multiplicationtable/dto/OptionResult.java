package com.sskarga.multiplicationtable.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class OptionResult implements Parcelable {

    private Integer countQuestion;
    private Integer countRightAns;

    public Integer getCountQuestion() {
        return countQuestion;
    }

    public void setCountQuestion(Integer countQuestion) {
        this.countQuestion = countQuestion;
    }

    public Integer getCountRightAns() {
        return countRightAns;
    }

    public void setCountRightAns(Integer countRightAns) {
        this.countRightAns = countRightAns;
    }

    public OptionResult() {
    }

    public OptionResult(Integer countQuestion, Integer countRightAns) {
        this.countQuestion = countQuestion;
        this.countRightAns = countRightAns;
    }

    protected OptionResult(Parcel in) {
        if (in.readByte() == 0) {
            countQuestion = null;
        } else {
            countQuestion = in.readInt();
        }
        if (in.readByte() == 0) {
            countRightAns = null;
        } else {
            countRightAns = in.readInt();
        }
    }

    public static final Creator<OptionResult> CREATOR = new Creator<OptionResult>() {
        @Override
        public OptionResult createFromParcel(Parcel in) {
            return new OptionResult(in);
        }

        @Override
        public OptionResult[] newArray(int size) {
            return new OptionResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (countQuestion == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(countQuestion);
        }
        if (countRightAns == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(countRightAns);
        }
    }
}
