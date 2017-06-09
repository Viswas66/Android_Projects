package com.example.viswas.homework3_a3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragmentReceiver extends BroadcastReceiver {
    public FragmentReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Intent i;
        if(intent.getAction().equals("com.app.SendHotel")){
            i = new Intent(context.getApplicationContext(), HotelViewerActivity.class);
        }else{
            i = new Intent(context.getApplicationContext(), RestaurantViewerActivity.class);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
