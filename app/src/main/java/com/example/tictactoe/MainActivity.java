package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView plrOneScr,plrTwoScr,plrStatus;
    private Button [] buttons = new Button[9];
    private Button resetGame;

    private int playerOneScroreCount,playerTwoScroreCount,rountCount;
    boolean activePlayer;

    // p1 => 0
    // p2 => 1
    // empty => 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    //possible pattern
    int [][] winningPosition ={
            {0,1,2},{3,4,5},{6,7,8}, // rows
            {0,3,6},{1,4,7},{2,5,8},// columns
            {0,4,8},{2,4,6}// cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TicTacToe);
        setContentView(R.layout.activity_main);

        plrOneScr = (TextView) findViewById(R.id.playerOneScore);
        plrTwoScr = (TextView) findViewById(R.id.playerTwoScore);
        plrStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i=0;i<buttons.length;i++){
            String buttonID ="btn_"+i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i]=(Button)findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScroreCount =0;
        playerTwoScroreCount =0;
        activePlayer =true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID =v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer =Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }
        else{
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;

        }
        rountCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScroreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player One Won!",Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                playerTwoScroreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player Two Won!",Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount == 9){
            playAgain();
            Toast.makeText(this,"No Winner!",Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer;
        }
        if(playerOneScroreCount > playerTwoScroreCount){
            plrStatus.setText("Player One is Winning!");
        }else if(playerTwoScroreCount > playerOneScroreCount){
            plrStatus.setText("Player Two is Winning!");
        }else{
            plrStatus.setText("");
        }

        //reset Function
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScroreCount =0;
                playerTwoScroreCount =0;
                plrOneScr.setText("");
                plrStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPositions : winningPosition){
            if(gameState[winningPositions[0]] == gameState[winningPositions[1]] && gameState[winningPositions[1]] == gameState[winningPositions[2]] && gameState[winningPositions[0]] != 2){
               winnerResult = true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        plrOneScr.setText(Integer.toString(playerOneScroreCount));
        plrTwoScr.setText(Integer.toString(playerTwoScroreCount));

    }
    //Play again function
    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for(int i = 0;i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}