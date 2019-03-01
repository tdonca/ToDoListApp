package com.tudordonca.android.todolist.dropboxutils;

import android.util.Log;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DropboxUtils {

    private static final String LOG_TAG = DropboxUtils.class.getSimpleName();


    public static FullAccount getDropboxAccount(DbxClientV2 client){
        try{
            Log.i(LOG_TAG, "Retrieving dropbox account...");
            return client.users().getCurrentAccount();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Failed to get dropbox account!");
        }

        return null;
    }


    public static Boolean uploadDropboxFile(DbxClientV2 client, String filePath, String fileName){
        Boolean saved = false;
        File tasksFile = new File(filePath, fileName);
        try{
            InputStream inputStream = new FileInputStream(tasksFile);
            try{
                Log.d(LOG_TAG, "Uploading tasks file to dropbox...");
                client.files().uploadBuilder("/" + fileName).withMode(WriteMode.OVERWRITE).uploadAndFinish(inputStream);
                saved = true;
            }
            catch(Exception e){
                e.printStackTrace();
                Log.e(LOG_TAG, "Failed to upload tasks file to Dropbox!");
            }
            finally{
                inputStream.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Failed to create InputStream to read tasks file!");
        }

        return saved;
    }


    public static Boolean downloadDropboxFile(DbxClientV2 client, String filePath, String fileName){
        Boolean found = false;
        File tasksFile = new File(filePath, fileName);

        try{
            // check if file exists on dropbox
            client.files().getMetadata("/" + fileName.toLowerCase());
            // download file
            try{
                OutputStream outputStream = new FileOutputStream(tasksFile);
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
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e(LOG_TAG, "File does not exist on Dropbox!");
        }

        return found;
    }
}
