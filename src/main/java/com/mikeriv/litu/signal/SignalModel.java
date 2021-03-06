package com.mikeriv.litu.signal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by mlrivera on 2/12/17.
 */

public class SignalModel implements Parcelable {

    // TODO other signal data

    public enum SignalModelValueType {
        X_VALUES,
        Y_VALUES,
        Z_VALUES
    }

    private float[] mValuesX;
    private float[] mValuesY;
    private float[] mValuesZ;

    public SignalModel(float[] valuesX, float[] valuesY, float[] valuesZ) {
        if (valuesX == null || valuesY == null || valuesZ == null) {
            throw new IllegalArgumentException("Values should not be null");
        }
        if (valuesX.length != valuesY.length
                && valuesX.length != valuesZ.length) {
            throw new IllegalArgumentException("Values must be the same length");
        }
        mValuesX = valuesX;
        mValuesY = valuesY;
        mValuesZ = valuesZ;
    }

    /**
     * Returns the values in the range startIndex to endIndex exclusive
     * @param startIndex
     * @param endIndex
     * @param type
     * @return
     */
    public float[] getValuesInRange(
            int startIndex,
            int endIndex,
            SignalModelValueType type) {
        if (startIndex < 0) {
            return null;
        }
        // Either take the endIndex if it is valid in our array or take
        endIndex = Math.max(Math.min(endIndex, getSize()), 0);

        float[] values = getValuesForType(type);
        if (values == null) {
            return null;
        }
        float[] rangeOfValues = Arrays.copyOfRange(values, startIndex, endIndex + 1);
        return rangeOfValues;
    }

    /**
     * Returns an array of the values for a particular type
     * @param type
     * @return
     */
    public float[] getValues(SignalModelValueType type) {
       return getValuesInRange(0, getSize(), type);
    }

    public int getSize() {
        if (mValuesX == null) {
            return 0;
        }
        return mValuesX.length;
    }

    protected SignalModel(Parcel in) {
        mValuesX = in.createFloatArray();
        mValuesY = in.createFloatArray();
        mValuesZ = in.createFloatArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloatArray(mValuesX);
        dest.writeFloatArray(mValuesY);
        dest.writeFloatArray(mValuesZ);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SignalModel> CREATOR = new Creator<SignalModel>() {
        @Override
        public SignalModel createFromParcel(Parcel in) {
            return new SignalModel(in);
        }

        @Override
        public SignalModel[] newArray(int size) {
            return new SignalModel[size];
        }
    };

    private float[] getValuesForType(SignalModelValueType type) {
        switch (type) {
            case X_VALUES:
                return mValuesX;
            case Y_VALUES:
                return mValuesY;
            case Z_VALUES:
                return mValuesZ;
        }
        throw new IllegalStateException("Undefined SignalModelValueType!");
    }

}
