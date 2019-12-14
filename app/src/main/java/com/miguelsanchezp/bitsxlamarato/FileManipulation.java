package com.miguelsanchezp.bitsxlamarato;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.REQUEST_LAST_ID;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.pathname;

class FileManipulation {
    static void deleteTheLine (String path) {
        File file = new File (path);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            String finalString = sb.toString();
            String[] lines = finalString.split("\n");
            OutputStream os = new FileOutputStream(file);
            for (int i = 0; i < lines.length -1; i++) {
                os.write(lines[i].getBytes());
                os.write("\n".getBytes());
                Log.d(TAG, "deleteTheLine: " + lines[i]);
            }
            os.close();
        }catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "deleteTheLine: There was an IOException");
        }
    }

    static void writeDown (String string, String path) {
        File file = new File(path);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(string.getBytes());
            os.write("\n".getBytes());
            Log.d(TAG, "writeDown: File found");
            os.close();
        }catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "writeDown: File not found");
        }
    }

    static int checkAndUpdateLastId (String path) {
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String string = br.readLine();
            OutputStream os = new FileOutputStream (file);
            os.write ((String.valueOf(Integer.valueOf(string)+1)).getBytes());
            os.close();
            new UploadToServer().execute(REQUEST_LAST_ID);
            return Integer.valueOf(string);
        }catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "checkAndUpdateLastId: IOException");
        }
        return 0;
    }
}
