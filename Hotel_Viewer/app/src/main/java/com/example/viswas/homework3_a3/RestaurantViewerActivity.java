package com.example.viswas.homework3_a3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Viswas on 10/21/2016.
 */
public class RestaurantViewerActivity extends AppCompatActivity
        implements RestaurantTitleFragment.ListSelectionListener
{
    public static String[] mRestaurantArray;
    public static int[] mRPicArray={R.drawable.girl_and_goat,R.drawable.the_rosebud,R.drawable.greek_islands,R.drawable.purple_pig,R.drawable.tanta_chicago,R.drawable.blackbird_chicago};
    private final RestaurantPhotoFragment mRestaurantPictureFragment = new RestaurantPhotoFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout mCTitleFrameLayout, mCPicFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "RestaurantViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");

        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles
        mRestaurantArray = getResources().getStringArray(R.array.ChicagoRestaurantsNames);


        setContentView(R.layout.restaurant_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Get references to the TitleFragment and to the RestaurantPictureFragment
        mCTitleFrameLayout = (FrameLayout) findViewById(R.id.rtitle_fragment_container);
        mCPicFrameLayout = (FrameLayout) findViewById(R.id.rpic_fragment_container);

        RestaurantTitleFragment ctf= new RestaurantTitleFragment();
        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();
        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        // Add the TitleFragment to the layout
        fragmentTransaction.replace(R.id.rtitle_fragment_container, ctf);
        // Commit the FragmentTransaction
        fragmentTransaction.commit();
        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {
        // Determine whether the RestaurantPicFragment has been added
        if (!mRestaurantPictureFragment.isAdded()) {
            // Make the RestaurantTitlesFragment occupy the entire layout
            mCTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mCPicFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // Make the TitleLayout take 1/3 of the layout's width
                mCTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
                // Make the PictureLayout take 2/3's of the layout's width
                mCPicFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }else{
                mCTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));
                mCPicFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }
        }
    }

    // Called when the user selects an item in the RestaurantTitlesFragment
    @Override
    public void onListSelection(int index) {
        // If the Fragment has not been added, add it now
        if (!mRestaurantPictureFragment.isAdded()) {
            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            // Add the Fragment to the layout
            fragmentTransaction.add(R.id.rpic_fragment_container, mRestaurantPictureFragment);
            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);
            // Commit the FragmentTransaction
            fragmentTransaction.commit();
            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }
        //if (mRestaurantPictureFragment.getShownIndex() != index) {
            // Tell the Picture Fragment to show the picture at position index
            mRestaurantPictureFragment.showPictureAtIndex(index);
        //}
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //start HotelViewerActivity
        if (id == R.id.action_hotels) {
            Intent i=new Intent(RestaurantViewerActivity.this,HotelViewerActivity.class);
            startActivity(i);
        }
        //start RestaurantViewerActivity
        if (id == R.id.action_restaurants) {
            Intent i=new Intent(RestaurantViewerActivity.this,RestaurantViewerActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, getClass().getSimpleName() + ":onConfigurationChanged()");
        super.onConfigurationChanged(newConfig);
        setLayout();
    }
}
