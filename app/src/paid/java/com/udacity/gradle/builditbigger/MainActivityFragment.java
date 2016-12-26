package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import app.com.example.aks.jokeactivity.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnTaskCompleted{
    Button mJokeButton;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mJokeButton = (Button) root.findViewById(R.id.btn_joke);

        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        return root;
    }
    public void loadData() {
        CloudAsyncTask endpointsAsyncTask = new CloudAsyncTask(this);
        endpointsAsyncTask.execute();
    }
    @Override
    public void onTaskCompleted(String result) {
        Intent intent = new Intent(getActivity(), JokeActivity.class);

        intent.putExtra(JokeActivity.JOKE_KEY, result);
        startActivity(intent);
    }
}
