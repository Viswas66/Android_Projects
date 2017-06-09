package com.example.viswas.homework3_a3;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Viswas on 10/21/2016.
 */
public class HotelPhotoFragment extends Fragment {

    private static final String TAG = "HotelPicFragment";
    private ImageView imageView = null;
    private int mCurrIdx = -1;
    private int mPicArrLen;

    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the Hotel Picture at position newIndex
    void showPictureAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mPicArrLen)
            return;
        mCurrIdx = newIndex;

        imageView.setImageResource(HotelViewerActivity.mPicArray[mCurrIdx]);
    }



    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        // Retain this Fragment across Activity reconfigurations
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        // Inflate the layout defined in hotel_picture_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.hotel_picture_fragment, container, false);
    }

    // Set up some information about the mQuoteView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        imageView = (ImageView) getActivity().findViewById(R.id.hotelImage);
        mPicArrLen = HotelViewerActivity.mPicArray.length;
    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
        super.onDestroyView();
    }
}
