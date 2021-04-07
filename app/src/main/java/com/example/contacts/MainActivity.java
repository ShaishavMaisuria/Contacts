package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements UsersFragment.UsersFragmentListener,
        StatesFragment.StatesFragmentListener, SortFragment.SortFragmentListener {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new UsersFragment(), "UsersFragment")
                .commit();

    }

    @Override
    public void showFilter() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new StatesFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SortFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void statePicked(String state) {
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("UsersFragment");

        if(fragment != null){
            fragment.setState(state);
        }

        getSupportFragmentManager().popBackStack();
        ///??????
        //the UsersFragment is on the back stack ...
        //I would like to send it the state so that it can filter based on state..

    }

    @Override
    public void sortSelected(String sortBy, String sortDirection) {
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("UsersFragment");

        if(fragment != null){
            fragment.setSort(sortBy, sortDirection);
        }
        getSupportFragmentManager().popBackStack();
    }
}