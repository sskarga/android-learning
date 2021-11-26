package com.sskarga.downloadwebcontent.streamsdata;

import android.graphics.BitmapFactory;

import java.io.InputStream;

public class StreamDataBitmap implements IStreamData {

    @Override
    public Object getData(InputStream inStream) {
        return BitmapFactory.decodeStream(inStream);
    }
}
