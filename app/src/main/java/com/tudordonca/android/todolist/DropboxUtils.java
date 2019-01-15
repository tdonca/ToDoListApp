package com.tudordonca.android.todolist;

import android.util.Log;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DropboxUtils {

    private static final String LOG_TAG = DropboxUtils.class.getSimpleName();



    static Boolean saveDropboxFile(DbxClientV2 client, String filePath, String fileName){


        Boolean saved = false;
        File tasksFile = new File(filePath, fileName);
        try{
            InputStream inputStream = new FileInputStream(tasksFile);
            try{
                client.files().uploadBuilder("/" + fileName).uploadAndFinish(inputStream);
                saved = true;
            }
            catch(Exception e){
                e.printStackTrace();
                Log.e(LOG_TAG, "Failed to upload file to Dropbox!");
            }
            finally{
                inputStream.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Failed to create InputStream to read file!");
        }


        return saved;
    }


    static Boolean getDropboxFile(DbxClientV2 client, String filePath, String fileName){


        // create local file and OutputSteam
        Boolean found = false;
        File tasksFile = new File(filePath, fileName);
        try{
            OutputStream outputStream = new FileOutputStream(tasksFile);
            // try to download file to disk
            try{
                client.files().download("/" + fileName.toLowerCase()).download(outputStream);
                found = true;
                Log.d(LOG_TAG, "Successfully downloaded file from Dropbox.");
            }
            catch(Exception e){
                e.printStackTrace();
                Log.e(LOG_TAG, "Failed to download file from Dropbox!");
            }
            finally{
                outputStream.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Failed to create OutputStream to save file!");
        }

        // return result
        return found;
    }
}
