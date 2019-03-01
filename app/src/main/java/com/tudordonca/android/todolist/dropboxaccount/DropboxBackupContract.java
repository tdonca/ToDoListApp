package com.tudordonca.android.todolist.dropboxaccount;

import java.util.ArrayList;

public interface DropboxBackupContract {

    interface View {

        void showAccountData(String name, String email, String type);

        void clearAccountData();

        void showDropboxAccountFailToast(String error);

        void deliverIntentResult(Boolean replaceLocal, ArrayList<String> tasks);

    }


    interface Presenter {

        void loadAccount(String accessToken);

        void syncData();
    }

}
