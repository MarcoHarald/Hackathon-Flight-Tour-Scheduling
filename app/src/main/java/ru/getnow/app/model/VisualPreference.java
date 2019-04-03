package ru.getnow.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VisualPreference implements Parcelable {
    private String tabDescription;
    private String[] possibleSelections;
    private int[] iconIds;

    public VisualPreference(String tabDescription, String[] possibleSelections, int[] iconIds) {
        this.tabDescription = tabDescription;
        this.possibleSelections = possibleSelections;
        this.iconIds = iconIds;
    }

    protected VisualPreference(Parcel in) {
        tabDescription = in.readString();
        possibleSelections = in.createStringArray();
        iconIds = in.createIntArray();
    }

    public static final Creator<VisualPreference> CREATOR = new Creator<VisualPreference>() {
        @Override
        public VisualPreference createFromParcel(Parcel in) {
            return new VisualPreference(in);
        }

        @Override
        public VisualPreference[] newArray(int size) {
            return new VisualPreference[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tabDescription);
        dest.writeStringArray(possibleSelections);
        dest.writeIntArray(iconIds);
    }

    public String getTabDescription() {
        return tabDescription;
    }

    public String[] getPossibleSelections() {
        return possibleSelections;
    }

    public int[] getIconIds() {
        return iconIds;
    }
}
