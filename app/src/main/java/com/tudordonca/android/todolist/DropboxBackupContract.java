package com.tudordonca.android.todolist;

import java.util.ArrayList;

public interface DropboxBackupContract {

    interface View {

        public void showAccountData(String name, String email, String type);

        public void showExistingTasks(ArrayList<String> tasks);

    }


    interface Presenter {

        void start();

        void initAndLoadData(String accessToken);

    }

}
