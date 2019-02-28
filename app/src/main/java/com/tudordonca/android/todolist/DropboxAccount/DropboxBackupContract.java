package com.tudordonca.android.todolist.DropboxAccount;

import java.util.ArrayList;

public interface DropboxBackupContract {

    interface View {

        public void showAccountData(String name, String email, String type);

        void deliverIntentResult(Boolean replaceLocal, ArrayList<String> tasks);

    }


    interface Presenter {

        void loadAccount(String accessToken);

        void syncData();
    }

}
