package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import ca.cmpt276.a2.model.Game;
import ca.cmpt276.a2.model.GameManager;
import ca.cmpt276.a2.model.PlayerScore;

public class GameInfoActivity extends AppCompatActivity {

    private final int PLAYER1_NUMBER = 0;
    private final int PLAYER2_NUMBER = 1;

    private String activityTitle;
    private GameManager gameManager;
    private static int gameIndex;
    private static boolean editGameActivity = false;

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

    // When an intent is created with only a context, it is used for creating a new game.
    public static Intent makeIntent(Context context) {
        editGameActivity = false;
        return new Intent(context, GameInfoActivity.class);
    }
    // When an intent is created with a context and integer of a card position,
    // It is used for editing a game.
    public static Intent makeIntent(Context context, int position){
        gameIndex = position;
        editGameActivity = true;
        // Toast.makeText(context, "Editing Game #" + position, Toast.LENGTH_SHORT).show();
        return new Intent(context, GameInfoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        // Set the fields depending on what kind of activity it is
        if (editGameActivity == false){
            activityTitle = "Create Game";
        }
        if (editGameActivity == true){
            activityTitle = "Edit Game";
            gameManager = GameManager.getInstance();
        }

        Toolbar toolbarGameInfo = findViewById(R.id.tbGameInfo);
        setSupportActionBar(toolbarGameInfo);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(activityTitle);


        // One great thing about the EditText is that it will not allow negative integers to be inputted!
        // So we don't have to check for any non-positive integers c:

        // Get the realtime score calculation text
        winnersRealTimeUpdate = findViewById(R.id.tvWinnersRealTimeUpdate);
        // Get the date!
        // The date is important!
        TextView currentDate = findViewById(R.id.tvCurrentDate);
        LocalDateTime datePlayed = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d @ H:mma");
        String formattedDatePlayed = datePlayed.format(dateTimeFormatter);
        currentDate.setText(formattedDatePlayed);

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

        Button saveGameInfo = findViewById(R.id.btnSaveGameInfo);
        Button deleteGameInfo = findViewById(R.id.btnDeleteGameInfo);

        // Set up the save button for creating a new game
        if (editGameActivity == false){
            deleteGameInfo.setVisibility(View.INVISIBLE);

            saveGameInfo.setOnClickListener(view -> {
                saveGame();
                setResult(Activity.RESULT_OK);
                finish();
            });
        }
        if (editGameActivity == true){
            gameManager = GameManager.getInstance();
            deleteGameInfo.setVisibility(View.VISIBLE);
            saveGameInfo.setText("EDIT");
            setEditInfo();

            saveGameInfo.setOnClickListener(view -> {
                editGame();
                setResult(Activity.RESULT_OK);
                finish();
            });

            deleteGameInfo.setOnClickListener(view -> {
                deleteGame();
                setResult(Activity.RESULT_OK);
                finish();
            });
        }


    }

    private final TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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
        gameManager = GameManager.getInstance();

        PlayerScore player1 = new PlayerScore(PLAYER1_NUMBER, p1Cards, p1Sum, p1Wagers);
        PlayerScore player2 = new PlayerScore(PLAYER2_NUMBER, p2Cards, p2Sum, p2Wagers);

        ArrayList<PlayerScore> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        gameManager.createGame(playerList);
    }

    private void editGame() {
        Game editGame = gameManager.getSpecificGame(gameIndex);
        ArrayList<PlayerScore> playerScores = editGame.getPlayerList();

        updateIntegersFromInput();

        playerScores.get(PLAYER1_NUMBER).editScore(p1Cards, p1Sum, p1Wagers);
        playerScores.get(PLAYER2_NUMBER).editScore(p2Cards, p2Sum, p2Wagers);
    }
    private void setEditInfo(){
        // Set the EditText fields to the existing values when editing
        Game editGame = gameManager.getSpecificGame(gameIndex);

        PlayerScore player1 = editGame.getPlayerList().get(PLAYER1_NUMBER);
        PlayerScore player2 = editGame.getPlayerList().get(PLAYER2_NUMBER);

        player1NumberOfCards.setText(String.valueOf(player1.getNumberOfCards()));
        player1SumOfCards.setText(String.valueOf(player1.getSumOfPointCards()));
        player1NumberOfWagers.setText(String.valueOf(player1.getNumberOfWagers()));
        player1Score.setText(String.valueOf(player1.getScore()));

        player2NumberOfCards.setText(String.valueOf(player2.getNumberOfCards()));
        player2SumOfCards.setText(String.valueOf(player2.getSumOfPointCards()));
        player2NumberOfWagers.setText(String.valueOf(player2.getNumberOfWagers()));
        player2Score.setText(String.valueOf(player2.getScore()));
    }

    private void deleteGame(){
        gameManager.deleteSpecificGame(gameIndex);
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
        return player1NumberOfCards.getText().toString().trim().isEmpty() ||
                player1SumOfCards.getText().toString().trim().isEmpty() ||
                player1NumberOfWagers.getText().toString().trim().isEmpty() ||
                player2NumberOfCards.getText().toString().trim().isEmpty() ||
                player2SumOfCards.getText().toString().trim().isEmpty() ||
                player2NumberOfWagers.getText().toString().trim().isEmpty();
    }



}