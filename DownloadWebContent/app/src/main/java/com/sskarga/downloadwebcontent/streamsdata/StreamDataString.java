package com.sskarga.downloadwebcontent.streamsdata;

import com.sskarga.downloadwebcontent.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamDataString implements IStreamData {

    @Override
    public Object getData(InputStream inStream) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}

