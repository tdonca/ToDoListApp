package com.tudordonca.android.todolist.DropboxAccount;

import java.util.ArrayList;

public interface DropboxBackupContract {

    interface View {

        public void showAccountData(String name, String email, String type);

        public void showExistingTasks(ArrayList<String> tasks);

        void deliverIntentResult(Boolean replace, ArrayList<String> tasks);

    }


    interface Presenter {

        void resume(String accessToken);

        void initAndLoadData(String accessToken);

        void syncData();
    }

}
