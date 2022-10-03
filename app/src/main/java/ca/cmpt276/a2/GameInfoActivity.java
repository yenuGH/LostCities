package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class GameInfoActivity extends AppCompatActivity {

    static String activityTitle;

    // Player 1
    private EditText player1NumberOfCards;
    private EditText player1SumOfCards;
    private EditText player1NumberOfWagers;
    private TextView player1Score;

    private int p1Cards;
    private int p1Sum;
    private int p1Wagers;
    private int p1Score;

    // Player 2
    private EditText player2NumberOfCards;
    private EditText player2SumOfCards;
    private EditText player2NumberOfWagers;
    private TextView player2Score;

    private int p2Cards;
    private int p2Sum;
    private int p2Wagers;
    private int p2Score;

    // Realtime score calculation text
    private TextView winnersRealTimeUpdate;

    public static Intent makeIntent(Context context, String name) {

        if (name.equals("create")){
            activityTitle = "Create Game";
        }
        if (name.equals("edit")){
            activityTitle = "Edit Game";
        }

        return new Intent(context, GameInfoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        // GameInfoActivity.this.setTitle(activityTitle);
        Objects.requireNonNull(getSupportActionBar()).setTitle(activityTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // One great thing about the EditText is that it will not allow negative integers to be inputted!
        // So we don't have to check for any non-positive integers c:

        // Get the realtime score calculation text
        winnersRealTimeUpdate = findViewById(R.id.tvWinnersRealTimeUpdate);

        // Get player 1 input
        player1NumberOfCards = findViewById(R.id.etPlayer1NumberOfCards);
        player1SumOfCards = findViewById(R.id.etPlayer1SumOfCards);
        player1NumberOfWagers = findViewById(R.id.etPlayer1NumberOfWagers);


        // Get player 2 input
        player2NumberOfCards = findViewById(R.id.etPlayer2NumberOfCards);
        player2SumOfCards = findViewById(R.id.etPlayer2SumOfCards);
        player2NumberOfWagers = findViewById(R.id.etPlayer2NumberOfWagers);

        // Set the score views
        player1Score = findViewById(R.id.tvPlayer1Score);
        player2Score = findViewById(R.id.tvPlayer2Score);

        // Set the edittext listeners
        player1NumberOfCards.addTextChangedListener(inputTextWatcher);
        player1SumOfCards.addTextChangedListener(inputTextWatcher);
        player1NumberOfWagers.addTextChangedListener(inputTextWatcher);

        player2NumberOfCards.addTextChangedListener(inputTextWatcher);
        player2SumOfCards.addTextChangedListener(inputTextWatcher);
        player2NumberOfWagers.addTextChangedListener(inputTextWatcher);


    }

    private final TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            winnersRealTimeUpdate.setText("Awaiting input...");
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (checkIfEmpty()){
                resetWinnersRealTimeUpdate();
            }
            if (!checkIfEmpty()) {
                setWinnersRealTimeUpdate();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void saveGame(){
        TextView winnersRealTimeUpdate = findViewById(R.id.tvWinnersRealTimeUpdate);
        winnersRealTimeUpdate.setText("Winners : ");
    }

    private void resetWinnersRealTimeUpdate() {
        winnersRealTimeUpdate.setText("Calculating score...");
        player1Score.setText("--");
        player2Score.setText("--");
    }

    private void setWinnersRealTimeUpdate (){
        if (!checkIfEmpty()){

            updateIntegersFromInput();

            player1Score.setText(String.valueOf(p1Score));
            player2Score.setText(String.valueOf(p2Score));

            String winningPlayers;
            if (p1Score > p2Score){
                winningPlayers = "Player 1";
            }
            else if (p2Score > p1Score){
                winningPlayers = "Player 2";
            }
            else {
                winningPlayers = "Player 1 and Player 2 (TIE)";
            }
            winnersRealTimeUpdate.setText("Winning players: " + winningPlayers);
        }
    }

    private void updateIntegersFromInput(){
        p1Cards = Integer.parseInt(player1NumberOfCards.getText().toString());
        p1Sum = Integer.parseInt(player1SumOfCards.getText().toString());
        p1Wagers = Integer.parseInt(player1NumberOfWagers.getText().toString());

        p2Cards = Integer.parseInt(player2NumberOfCards.getText().toString());
        p2Sum = Integer.parseInt(player2SumOfCards.getText().toString());
        p2Wagers = Integer.parseInt(player2NumberOfWagers.getText().toString());

        p1Score = calculateScore(p1Cards, p1Sum, p1Wagers);
        p2Score = calculateScore(p2Cards, p2Sum, p2Wagers);
    }

    private int calculateScore(int numberOfCards, int sumOfPointCards, int numberOfWagers) {

        // if there are 0 cards, we will just return a score of 0
        if (numberOfCards == 0) {
            return 0;
        }

        // add up the number of points on their point cards, then subtract 20
        int score = sumOfPointCards - 20;
        // then multiply the score by the multiplier of the amount of wager cards
        score = score * (numberOfWagers + 1);
        // and if the player has 8 cards in total (including wagers) or more
        // then we will add 20 onto the final score
        if (numberOfCards >= 8) {
            score += 20;
        }

        return score;
    }

    private boolean checkIfEmpty(){
        if (    player1NumberOfCards.getText().toString().trim().isEmpty() ||
                player1SumOfCards.getText().toString().trim().isEmpty() ||
                player1NumberOfWagers.getText().toString().trim().isEmpty() ||
                player2NumberOfCards.getText().toString().trim().isEmpty() ||
                player2SumOfCards.getText().toString().trim().isEmpty() ||
                player2NumberOfWagers.getText().toString().trim().isEmpty()){
            return true;
        }
        return false;
    }



}