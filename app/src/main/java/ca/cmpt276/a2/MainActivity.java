package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import ca.cmpt276.a2.model.Game;
import ca.cmpt276.a2.model.GameManager;
import ca.cmpt276.a2.model.PlayerScore;
import ca.cmpt276.a2.ui.GameInfoCardModel;
import ca.cmpt276.a2.ui.GameInfoRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    ArrayList<GameInfoCardModel> gameInfoCardModels = new ArrayList<>();
    GameManager gameManager = GameManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateArrayList();

        // Find the button
        FloatingActionButton createGame = findViewById(R.id.fabCreateGame);
        // Set button behaviour
        createGame.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "YAY! Good work!", Toast.LENGTH_SHORT).show();
        });

        RecyclerView recyclerView = findViewById(R.id.rvGamesList);
        setupGameInfoModels();
        GameInfoRecyclerViewAdapter adapter = new GameInfoRecyclerViewAdapter(MainActivity.this, gameInfoCardModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    private void setupGameInfoModels() {
        ArrayList<Game> gameList = gameManager.getGameList();
        for (int i = 0; i < gameList.size(); i++){
            gameInfoCardModels.add(new GameInfoCardModel(gameList.get(i)));
        }
    }

    // below here is only for testing purposes
    private void populateArrayList(){
        for (int i = 0; i < 10; i++){
            newGame(ThreadLocalRandom.current().nextInt(1, 4));
        }
    }

    private void newGame(int numberOfPlayers) {
        ArrayList<PlayerScore> playerList = createPlayers(numberOfPlayers);
        gameManager.createGame(playerList);
    }

    private ArrayList<PlayerScore> createPlayers(int numberOfPlayers) {
        ArrayList<PlayerScore> playerList = new ArrayList<>();


        for (int i = 0; i < numberOfPlayers; i++){
            int playerNumber = i;
            int sumOfCards = ThreadLocalRandom.current().nextInt(10, 50 + 1);
            int numOfWagers = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            int numOfCards = ThreadLocalRandom.current().nextInt(numOfWagers, 15 + 1);

            PlayerScore playerScore = new PlayerScore(playerNumber, numOfCards, sumOfCards, numOfWagers);
            playerList.add(playerScore);

        }

        return playerList;
    }
}