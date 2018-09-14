package uz.mold.uzum;// 13.06.2016

import android.os.Parcel;

class ParcelPrinter implements Printer {

    private final Parcel source;

    public ParcelPrinter(Parcel source) {
        this.source = source;
    }

    @Override
    public void printOpen() {
        this.source.writeByte((byte) UzumToken.OPEN);
    }

    @Override
    public void printClose() {
        this.source.writeByte((byte) UzumToken.CLOSE);
    }

    @Override
    public void print(String str) {
        this.source.writeByte((byte) UzumToken.STRING);
        this.source.writeString(str == null ? "" : str);
    }
}
