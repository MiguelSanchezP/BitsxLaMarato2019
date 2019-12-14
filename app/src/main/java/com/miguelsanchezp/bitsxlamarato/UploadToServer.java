package com.miguelsanchezp.bitsxlamarato;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static android.content.ContentValues.TAG;

public class UploadToServer extends AsyncTask<Integer, Integer, Void> {

    @Override
    protected Void doInBackground(Integer... integers) {
        String filename = null;
        String filenameServer = null;
        if (integers[0].equals(0)) {
            filename = "positionPersonal.txt";
            filenameServer = "positions.txt";
        }
        if (filename != null) {
            try {
                JSch ssh = new JSch();
                Session session = ssh.getSession("miguelsanchezp", "elliot.ddns.net", 22);
                session.setPassword("Hr5zfy23.--.");
                Properties config = new Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                Log.d(TAG, "doInBackground: connection established");
                Channel channel = session.openChannel("sftp");
                channel.connect();
                ChannelSftp sftp = (ChannelSftp) channel;
                sftp.cd("/home/miguelsanchezp/BitsxLaMaratoServer/");
                File file = new File(MainActivity.pathname + filename);
                sftp.put(new FileInputStream(file), filenameServer, ChannelSftp.APPEND);
                Log.d(TAG, "doInBackground: File added successfully");
                sftp.exit();
                channel.disconnect();
                session.disconnect();
            } catch (JSchException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: There was a JSch exception");
            } catch (SftpException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: There was a SFTPException");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}