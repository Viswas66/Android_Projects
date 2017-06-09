package com.example.viswas.ClientPlayer;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Viswas on 11/28/2016.
 */
public class getTransactions  extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a new Adapter containing a list of colors
        // Set the adapter on this ListActivity's built-in ListView

        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
                myList));
    }

    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {

        // Display a Toast message indicting the selected item
        Toast.makeText(getApplicationContext(),
                ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
    }
}
