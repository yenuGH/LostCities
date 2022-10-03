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
    // Player 2
    private EditText player2NumberOfCards;
    private EditText player2SumOfCards;
    private EditText player2NumberOfWagers;
    // Realtime score calculation text
    TextView winnersRealTimeUpdate;

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

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!checkAllEmpty()){
                winnersRealTimeUpdate.setText("Awaiting input...");
            }
            else {
                winnersRealTimeUpdate.setText("Calculating scores...");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //calculateScores();
        }
    };

    private void calculateScores(){
        TextView winnersRealTimeUpdate = findViewById(R.id.tvWinnersRealTimeUpdate);
        winnersRealTimeUpdate.setText("Winners : ");
    }

    private boolean checkAllEmpty(){
        if (    player1NumberOfCards.getText().toString().isEmpty() &&
                player1SumOfCards.getText().toString().isEmpty() &&
                player1NumberOfWagers.getText().toString().isEmpty() &&
                player2NumberOfCards.getText().toString().isEmpty() &&
                player2SumOfCards.getText().toString().isEmpty() &&
                player2NumberOfWagers.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }



}