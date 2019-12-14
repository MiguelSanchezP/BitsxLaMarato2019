package com.miguelsanchezp.bitsxlamarato;

import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.util.IOUtils;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static android.content.ContentValues.TAG;

public class ServerConnection extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            JSch ssh = new JSch();
            Session session = ssh.getSession("miguelsanchezp", "elliot.ddns.net", 22);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setPassword("Hr5zfy23.--.");
            session.setConfig(config);
            session.connect();
            Log.d(TAG, "doInBackground: connection established");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            Log.d(TAG, "doInBackground: channel connected");
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd("/home/miguelsanchezp/Documents/folder/");
            Log.d(TAG, "doInBackground: " + channelSftp.ls(channelSftp.pwd()));
            InputStream is = channelSftp.get("./thisisthenewfile");
            try {
                File file  = new File (MainActivity.pathname + "/mydoc.txt");
                Log.d(TAG, "doInBackground: " + MainActivity.pathname);
                FileOutputStream os = new FileOutputStream (file);
                IOUtils.copyStream(is, os);
                os.write(is.toString().getBytes());
                Log.d(TAG, "doInBackground (inputStream): " + is.toString());
                Log.d(TAG, "doInBackground: Transfer made!! :)");
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: There was a filenotfoundexception");
            }catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: There was an IOException");
            }
            channelSftp.exit();
            channel.disconnect();
            session.disconnect();
            Log.d(TAG, "doInBackground: Connections closed");
        }catch (JSchException e) {
            Log.d(TAG, "doInBackground: There was a JSch Exception");
            e.printStackTrace();
        }catch (SftpException e){
            Log.d(TAG, "doInBackground: There was a SftpException");
            e.printStackTrace();
        }
        return null;
    }
}