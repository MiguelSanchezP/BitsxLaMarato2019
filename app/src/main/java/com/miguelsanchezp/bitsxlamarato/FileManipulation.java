package com.miguelsanchezp.bitsxlamarato;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_APPEND;
import static com.miguelsanchezp.bitsxlamarato.MainActivity.pathname;

class FileManipulation {
    final static String USERNAME_FIELD = "username";
    final static String NAME_FIELD = "name";
    final static String SURNAME_FIELD = "surname";
    final static String BIRTHDATE_FIELD = "birthdate";
    final static String DIAGNOSED_FIELD = "diagnosed";
    final static String MEDICALCONDITION_FIELD = "medicalcondition";
    final static String BIOGRAPHY_FIELD = "biography";
    final static String PHONE_FIELD  = "phone";
    final static String MAIL_FIELD = "mail";
    final static String WEB_FIELD = "web";
    final static String DISEASEDATE_FIELD = "diseasedate";
    final static String CONSENT_FIELD = "consent";
    final static String LATITUDE_FIELD = "latitude";
    final static String LONGITUDE_FIELD = "longitude";

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

    static String readFile (String path) {
        File file = new File (path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
//            Log.d(TAG, "readFile: " + br.readLine());
            return br.readLine();
        }catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "readFile: there was an ioexception error");
        }
        Log.d(TAG, "readFile: return a null value");
        return "NOPE";
    }

    static void createEmptyConf () {
        File file = new File (pathname + "conf.txt");
        try {
            OutputStream os = new FileOutputStream(file);
            os.write("username=".getBytes());
            os.write("\n".getBytes());
            os.write("name=".getBytes());
            os.write("\n".getBytes());
            os.write("surname=".getBytes());
            os.write("\n".getBytes());
            os.write("birthdate=".getBytes());
            os.write("\n".getBytes());
            os.write("diagnosed=".getBytes());
            os.write("\n".getBytes());
            os.write("medicalcondition=".getBytes());
            os.write("\n".getBytes());
            os.write("biography=".getBytes());
            os.write("\n".getBytes());
            os.write("phone=".getBytes());
            os.write("\n".getBytes());
            os.write("mail=".getBytes());
            os.write("\n".getBytes());
            os.write("web=".getBytes());
            os.write("\n".getBytes());
            os.write("diseasedate=".getBytes());
            os.write("\n".getBytes());
            os.write("consent=".getBytes());
            os.write("\n".getBytes());
            os.write("latitude=".getBytes());
            os.write("\n".getBytes());
            os.write("longitude=".getBytes());
            os.write("\n".getBytes());
        }catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "createEmptyConf: error accessing file");
        }
    }

    static void modifyConf (String field, String param) {
        File file = new File(pathname + "conf.txt");
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            String config = sb.toString();
            Log.d(TAG, "modifyConf: string " + config);
            String[] fields = config.split("\n");
            for (String s : fields) {
                Log.d(TAG, "modifyConf: the string " + s);
            }
            for (int i = 0; i < fields.length; i++) {
                String[] individual = fields[i].split("=");
                if (individual[0].equals(field)) {
                    fields[i] = individual[0] + "=" + param;
                }
            }
            try {
                OutputStream os = new FileOutputStream(file);
                for (String s : fields) {
                    Log.d(TAG, "modifyConf: " + s);
                    os.write(s.getBytes());
                    os.write("\n".getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getConfField (String field) {
        File file = new File (pathname + "conf.txt");
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line=br.readLine())!=null) {
                sb.append(line);
                sb.append("\n");
            }
            String finalString = sb.toString();
            String[] fields = finalString.split("\n");
            for (String s : fields) {
                String [] individual = s.split("=");
                if (individual[0].equals(field)) {
                    return individual[1];
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void removeRepetition (String path) {
        File file = new File(path);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            String finalString = sb.toString();
            String [] coordinates = finalString.split("\n");
            ArrayList<String> ids = new ArrayList<>();
            ArrayList<String> longitudes = new ArrayList<>();
            ArrayList<String> latitudes = new ArrayList<>();
            for (String s : coordinates) {
                String [] parts = s.split("%");
                Log.d(TAG, "removeRepetition: got here :)");
                if (ids.contains(parts[0])) {
                    Log.d(TAG, "removeRepetition: got here? :)");
                    int index = ids.indexOf(parts[0]);
                    longitudes.remove(index);
                    latitudes.remove(index);
                    ids.remove(index);
                }
                ids.add(parts[0]);
                latitudes.add(parts[1]);
                longitudes.add(parts[2]);
            }
            Log.d(TAG, "removeRepetition: " + ids.size());
            try {
                File file2 = new File (pathname + "formattedPositions.txt");
                OutputStream os = new FileOutputStream(file2);
                for (int i = 0; i<ids.size(); i++) {
                    if (ids.get(i) != null) {
                        os.write(ids.get(i).getBytes());
                        os.write("%".getBytes());
                        os.write(latitudes.get(i).getBytes());
                        os.write("%".getBytes());
                        os.write(longitudes.get(i).getBytes());
                        os.write("\n".getBytes());
                    }
                }
            }catch (IOException e) {
                Log.d(TAG, "removeRepetition: exception");
                e.printStackTrace();
            }
        }catch (IOException e) {
            Log.d(TAG, "removeRepetition: exception");
            e.printStackTrace();
        }
    }
}
