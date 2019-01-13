package com.tudordonca.android.todolist;

public interface DropboxBackupContract {

    interface View {



    }


    interface Presenter {

        void start();

        void initAndLoadData(String accessToken);

    }

}
