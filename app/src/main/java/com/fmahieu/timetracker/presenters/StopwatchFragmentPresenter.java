package com.fmahieu.timetracker.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;


import com.fmahieu.timetracker.logic.InitialSetupLogic;
import com.fmahieu.timetracker.views.StopwatchFragment;

public class StopwatchFragmentPresenter {

    private StopwatchFragment stopwatchFragment;

    public StopwatchFragmentPresenter(StopwatchFragment fragment){
        this.stopwatchFragment = fragment;
    }

    @SuppressLint("StaticFieldLeak")
    public void load(){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                new InitialSetupLogic().loadData();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                stopwatchFragment.loadViews();
            }
        }.execute();
    }
}
