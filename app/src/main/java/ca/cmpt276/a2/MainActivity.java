package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import ca.cmpt276.a2.model.Game;
import ca.cmpt276.a2.model.GameManager;
import ca.cmpt276.a2.model.PlayerScore;
import ca.cmpt276.a2.model.GameInfoCardModel;
import ca.cmpt276.a2.model.GameInfoRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    ArrayList<GameInfoCardModel> gameInfoCardModels = new ArrayList<>();
    GameInfoRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    GameManager gameManager = GameManager.getInstance();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbGamesPlayed);
        setSupportActionBar(toolbar);

        updateRecyclerViewAdapter();

        // Find the button
        FloatingActionButton createGame = findViewById(R.id.fabCreateGame);
        // Set button behaviour
        createGame.setOnClickListener(view -> {
            Intent intent = GameInfoActivity.makeIntent(MainActivity.this, "create");
            startActivity(intent);
            //startActivityForResult(intent, RESULT_OK);
            //updateRecyclerViewAdapter();
            Toast.makeText(MainActivity.this, "Added game. " + gameManager.getNumberOfGames() + " in total", Toast.LENGTH_SHORT).show();
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerViewAdapter(){
        // gameInfoCardModels.clear();
        setupGameInfoModels();
        setupRecyclerView();
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(){
        recyclerView = findViewById(R.id.rvGamesList);
        setupGameInfoModels();
        adapter = new GameInfoRecyclerViewAdapter(MainActivity.this, gameInfoCardModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void setupGameInfoModels() {

        gameInfoCardModels.clear();

        ArrayList<Game> gameList = gameManager.getGameList();
        for (int i = 0; i < gameList.size(); i++){
            gameInfoCardModels.add(new GameInfoCardModel(gameList.get(i)));
        }

        // Log.i("GameManagerSize","GAME MANAGER CURRENTLY HAS " + gameList.size() + " GAMES REGISTERED");
    }

    // below here is only for testing purposes
    private void populateArrayList(){
        for (int i = 0; i < 5; i++){
            int randomInt = random.nextInt(2) + 1;
            newGame(randomInt);
        }
    }

    private void newGame(int numberOfPlayers) {
        ArrayList<PlayerScore> playerList = createPlayers(numberOfPlayers);
        gameManager.createGame(playerList);
    }

    private ArrayList<PlayerScore> createPlayers(int numberOfPlayers) {
        ArrayList<PlayerScore> playerList = new ArrayList<>();


        for (int i = 0; i < numberOfPlayers; i++){
            int sumOfCards = random.nextInt(50 - 10) + 10;
            int numOfWagers = random.nextInt(6);
            int numOfCards = random.nextInt(16 - numOfWagers) + numOfWagers;
            //int sumOfCards = ThreadLocalRandom.current().nextInt(10, 50 + 1);
            //int numOfWagers = ThreadLocalRandom.current().nextInt(0, 5 + 1);
            //int numOfCards = ThreadLocalRandom.current().nextInt(numOfWagers, 15 + 1);

            PlayerScore playerScore = new PlayerScore(i, numOfCards, sumOfCards, numOfWagers);
            playerList.add(playerScore);

        }

        return playerList;
    }
}