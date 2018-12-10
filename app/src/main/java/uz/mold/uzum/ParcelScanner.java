package uz.mold.uzum;// 13.06.2016

import android.os.Parcel;

class ParcelScanner implements Scanner {

    private final Parcel source;
    private String val;
    private byte[] valBytes;
    private int token;
    private boolean peeked;

    public ParcelScanner(Parcel source) {
        this.source = source;
    }

    @Override
    public String val() {
        return val;
    }

    @Override
    public byte[] valBytes() {
        return valBytes;
    }

    private boolean readNew() {
        boolean hasValue = this.source.dataAvail() != 0;
        if (hasValue) {
            this.token = this.source.readByte();
            switch (this.token) {
                case UzumToken.STRING:
                    this.val = source.readString();
                    break;
                case UzumToken.BYTES:
                    this.valBytes = source.createByteArray();
                    break;
                case UzumToken.OPEN:
                case UzumToken.CLOSE:
                    this.val = null;
                    this.valBytes = null;
                    break;
                default:
                    throw new UzumException("Invalid token in ParcelScanner = " + token);
            }
        }
        return hasValue;
    }

    @Override
    public int next() {
        if (this.peeked) {
            this.peeked = false;
        } else {
            if (!readNew()) {
                return UzumToken.EOF;
            }
        }
        return this.token;
    }

    @Override
    public int peek() {
        if (!this.peeked) {
            if (!readNew()) {
                return UzumToken.EOF;
            }
            this.peeked = true;
        }
        return this.token;
    }
}
