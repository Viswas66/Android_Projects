package com.example.viswas.homework4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Random;



public class MainActivity extends Activity {

    private static Handler t1Handler;
    private static  Handler t2Handler;
    //Initial turns
    private static int noOfTurns=1;
    //flag to check whether the game has ended or still going on
    private static int restartFlag=0;
    //Choice for random number generation
    public static int  maxChoice=9;
    private ImageButton tileToBeUpdated;
    //Image values for two players
    public static int[] buttonImage ={R.drawable.playerone_move,R.drawable.playertwo_move};
    private static Looper MyLooper1,MyLooper2;
    //Game winning Probabilities
    private static  List<Integer> game1 =new ArrayList<Integer>(Arrays.asList(1,2,3));
    private static List<Integer> game2 =new ArrayList<Integer>(Arrays.asList(4,5,6));
    private static List<Integer> game3 =new ArrayList<Integer>(Arrays.asList(7,8,9));
    private static List<Integer> game4 =new ArrayList<Integer>(Arrays.asList(1,4,7));
    private static List<Integer> game5 =new ArrayList<Integer>(Arrays.asList(2,5,8));
    private static List<Integer> game6 =new ArrayList<Integer>(Arrays.asList(3,6,9));
    private static List<Integer> game7 =new ArrayList<Integer>(Arrays.asList(1,5,9));
    private static List<Integer> game8 =new ArrayList<Integer>(Arrays.asList(3,5,7));

    //Choices to be made by player threads
    public static List<Integer> moveChoices = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));


   //Initiate list to store player moves
    List<Integer> playerOneMoves =new ArrayList<Integer>();
    List<Integer> playerTwoMoves =new ArrayList<Integer>();


   //Handler for UI Thread
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int whichPlayer = msg.arg1;
            //find out which player is it
            if (noOfTurns <= 9 ) {
                switch (whichPlayer) {
                    case 1:
                        //receive choice from player threads
                        Log.i("In UI Thread:", "Player Playing is :" + whichPlayer);
                        int playerChoice = msg.arg2;
                        Log.i("In UI Thread:", "Player choice is  :" + playerChoice);
                        //Remove this choice from existing choices
                        int playerPlayed = moveChoices.remove(playerChoice - 1);
                        Toast.makeText(MainActivity.this, "Player 1 played : " + playerPlayed, Toast.LENGTH_SHORT).show();
                        switch (playerPlayed) {
                            case 1:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile1);
                                break;
                            case 2:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile2);
                                break;
                            case 3:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile3);
                                break;
                            case 4:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile4);
                                break;
                            case 5:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile5);
                                break;
                            case 6:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile6);
                                break;
                            case 7:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile7);
                                break;
                            case 8:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile8);
                                break;
                            case 9:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile9);
                                break;

                        }
                        tileToBeUpdated.setImageResource(buttonImage[0]);
                        //Add to the list of player moves
                        playerOneMoves.add(playerPlayed);
                        //check if the game has ended
                        if (playerOneMoves.size() >= 3) {
                            if (playerOneMoves.containsAll(game1) || playerOneMoves.containsAll(game2) || playerOneMoves.containsAll(game3) ||
                                    playerOneMoves.containsAll(game4) || playerOneMoves.containsAll(game5) || playerOneMoves.containsAll(game6) ||
                                    playerOneMoves.containsAll(game7) || playerOneMoves.containsAll(game8)) {
                                Toast.makeText(MainActivity.this, "Player 1 won the game!!!", Toast.LENGTH_SHORT).show();
                                restartFlag=1;
                                break;
                            }
                        }
                        Log.i("In UI Thread:", "Player tile is  :" + playerPlayed);
                        //decrement the random number range
                        maxChoice -= 1;
                        noOfTurns++;
                        //Check if player is out of moves
                        if (noOfTurns == 10) {
                            Toast.makeText(MainActivity.this, "Game ended in Tie!!!", Toast.LENGTH_SHORT).show();
                            restartFlag=1;
                            break;
                        }

                        Log.i("Inside Main loop", "Player 2 to play");
                        Message msgToPlayer2 = Message.obtain();
                        msgToPlayer2.arg1 = 1;
                        //sleep thread for 10 seconds
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted!");
                        }
                        ;
                        t2Handler.sendMessage(msgToPlayer2);

                        break;
                    case 2:
                        Log.i("In UI Thread:", "Player Playing is :" + whichPlayer);
                        playerChoice = msg.arg2;
                        Log.i("In UI Thread:", "Player choice is  :" + playerChoice);
                        playerPlayed = moveChoices.remove(playerChoice - 1);
                        Log.i("In UI Thread:", "Player tile is  :" + playerPlayed);
                        Toast.makeText(MainActivity.this, "Player 2 played : " + playerPlayed, Toast.LENGTH_SHORT).show();
                        switch (playerPlayed) {
                            case 1:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile1);
                                break;
                            case 2:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile2);
                                break;
                            case 3:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile3);
                                break;
                            case 4:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile4);
                                break;
                            case 5:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile5);
                                break;
                            case 6:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile6);
                                break;
                            case 7:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile7);
                                break;
                            case 8:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile8);
                                break;
                            case 9:
                                tileToBeUpdated = (ImageButton) findViewById(R.id.tile9);
                                break;

                        }
                        tileToBeUpdated.setImageResource(buttonImage[1]);
                        //Add to the list of player moves
                        playerTwoMoves.add(playerPlayed);
                        //check if the game has ended
                        if (playerTwoMoves.size() >= 3) {
                            if (playerTwoMoves.containsAll(game1) || playerTwoMoves.containsAll(game2) || playerTwoMoves.containsAll(game3) ||
                                    playerTwoMoves.containsAll(game4) || playerTwoMoves.containsAll(game5) || playerTwoMoves.containsAll(game6) ||
                                    playerTwoMoves.containsAll(game7) || playerTwoMoves.containsAll(game8)) {
                                Toast.makeText(MainActivity.this, "Player 2 won the game!!!", Toast.LENGTH_SHORT).show();
                                restartFlag=1;
                                break;
                            }
                        }
                        //decrement the random number range
                        maxChoice -= 1;
                        noOfTurns++;
                        //Check if player is out of moves
                        if (noOfTurns == 10) {
                            Toast.makeText(MainActivity.this, "Game ended in Tie!!!", Toast.LENGTH_SHORT).show();
                            restartFlag=1;
                            break;
                        }

                        if (maxChoice >= 1) {

                            Log.i("Inside Main loop", "Player 1 to play");
                            Message msgToPlayer1 = Message.obtain();
                            msgToPlayer1.arg1 = 1;
                            //sleep thread for 10 seconds
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted!");
                            }
                            ;
                            t1Handler.sendMessage(msgToPlayer1);

                            break;


                        }

                }
            }

            else{
               Log.i("Main Thread","No turns");
            }
        }
    }	; // Handler is associated with UI Thread

    private Button mButton ;
    // Values to be used by handleMessage()
    public static final int PLAYER1_TURN= 1 ;
    public static final int PLAYER2_TURN = 2 ;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button) ;



        //Initialize and start Player threads
       final Thread t1 = new Thread(new ThreadOneRunnable()) ;
        final Thread t2 = new Thread(new ThreadTwoRunnable()) ;






        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //check if threads are already alive
                if(Thread.currentThread().isAlive()&&(t1.isAlive()||t2.isAlive())) {
                    //reset all the datastructures to play the game
                    resetDataStructures();
                    Toast.makeText(MainActivity.this, "New Game", Toast.LENGTH_SHORT).show();
                    if(restartFlag==1) {
                        restartFlag=0;
                        Message msgToFirst = Message.obtain();
                        msgToFirst.arg1 = 1;
                        t1Handler.sendMessage(msgToFirst);
                    }

                }
                else {
                //if not start the threads
                    t1.start();
                    t2.start();
                }

            }
        });


    }

    public void resetDataStructures(){

        ImageButton button1=(ImageButton) findViewById(R.id.tile1);
        ImageButton button2=(ImageButton) findViewById(R.id.tile2);
        ImageButton button3=(ImageButton) findViewById(R.id.tile3);
        ImageButton button4=(ImageButton) findViewById(R.id.tile4);
        ImageButton button5=(ImageButton) findViewById(R.id.tile5);
        ImageButton button6=(ImageButton) findViewById(R.id.tile6);
        ImageButton button7=(ImageButton) findViewById(R.id.tile7);
        ImageButton button8=(ImageButton) findViewById(R.id.tile8);
        ImageButton button9=(ImageButton) findViewById(R.id.tile9);
        button1.setImageResource(0);
        button2.setImageResource(0);
        button3.setImageResource(0);
        button4.setImageResource(0);
        button5.setImageResource(0);
        button6.setImageResource(0);
        button7.setImageResource(0);
        button8.setImageResource(0);
        button9.setImageResource(0);

        maxChoice=9;
        noOfTurns=1;
        moveChoices = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        playerOneMoves.clear();
        playerTwoMoves.clear();

    }


    public class ThreadOneRunnable implements Runnable  {

        private int choice;
        public void run() {

            MyLooper1.prepare();
            Message initialMessage =Message.obtain() ;
            Random rn = new Random();
            //get the choice for move to make
            Log.i("Player 1:","Initial MaxChoice:"+maxChoice);
            if(maxChoice>=1) {
                choice = rn.nextInt(maxChoice) + 1;
            }
            //send this choice to UI thread
            initialMessage.arg1=PLAYER1_TURN;
            initialMessage.arg2=choice;
            Log.i("Player 1:","Choice selected :"+choice);
            try { Thread.sleep(2000); }
            catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;
            mHandler.sendMessage(initialMessage) ;
            Log.i("Thread1 Intiated","Thread1 Intiated");

            t1Handler = new Handler() {

                public void handleMessage(Message msg) {
                    int what = msg.arg1 ;
                    switch(what){
                        case 1:
                      Log.i("Player 1","Selecting Choice");
                        Random rn = new Random();
                        //get the choice for move to make
                        if(maxChoice>=1) {
                            choice = rn.nextInt(maxChoice) + 1;
                        }
                        //send this choice to UI thread
                        Message msgToSend = Message.obtain() ;
                        msgToSend.arg1=PLAYER1_TURN;
                        msgToSend.arg2=choice;

                        Log.i("Player 1:","Choice selected :"+choice);
                            //sleep thread for 10 seconds
                            try { Thread.sleep(2000); }
                            catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;
                        mHandler.sendMessage(msgToSend) ;
                            break;
                    }
                }
            }	;// Handler is associated with Player1 Thread
            MyLooper1.loop();
        }
    }


    public class ThreadTwoRunnable implements Runnable  {

        private int choice;
        public void run() {

            MyLooper2.prepare();
            t2Handler = new Handler() {
                public void handleMessage(Message msg) {
                    int what = msg.arg1 ;
                    switch(what)
                    {
                        case 1 :
                            Log.i("Player 2","Selecting Choice");
                        Random rn = new Random();
                        //get the choice for move to make
                        choice = rn.nextInt(maxChoice)+1;
                        //send this choice to UI thread
                        Message msgToSend = Message.obtain() ;
                        msgToSend.arg1=PLAYER2_TURN;
                        msgToSend.arg2=choice;
                        Log.i("Player 2:","Choice selected :"+choice);
                            //sleep thread for 10 seconds
                            try { Thread.sleep(2000); }
                            catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;
                        mHandler.sendMessage(msgToSend) ;
                            break;

                    }
                }
            }	;// Handler is associated with Player2 Thread
        MyLooper2.loop();}
    }
}