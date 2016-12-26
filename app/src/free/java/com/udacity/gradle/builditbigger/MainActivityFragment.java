package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import app.com.example.aks.jokeactivity.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnTaskCompleted{
    Button mJokeButton;
    boolean mAdsOnScreen;
    String mResult;
    ProgressBar mProgressBar;
    InterstitialAd mInterstitialAd;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        mJokeButton = (Button) root.findViewById(R.id.btn_joke);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                mAdsOnScreen = false;
                displayActivity();
            }
        });
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the interstitial ad
                if (mInterstitialAd.isLoaded()) {
                    mAdsOnScreen = true;
                    mInterstitialAd.show();
                } else {
                    mAdsOnScreen = false;
                }
                loadData();
                displayActivity();
            }
        });

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        requestNewInterstitial();
        return root;
    }
    public void loadData() {
        mResult = null;
        CloudAsyncTask endpointsAsyncTask = new CloudAsyncTask(this);
        endpointsAsyncTask.execute();
    }
    // Request new interstitial
    private void requestNewInterstitial() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    public void displayActivity() {
        // No ads currently displayed
        if (!mAdsOnScreen){
            // Data is ready
            if (mResult != null) {
                Intent intent = new Intent(getActivity(), JokeActivity.class);
                intent.putExtra(JokeActivity.JOKE_KEY, mResult);
                mProgressBar.setVisibility(View.GONE);
                startActivity(intent);
                // AsyncTask is not finish
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onTaskCompleted(String result) {
       mResult=result;
        displayActivity();
         }
}
