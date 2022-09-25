package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

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

    }

    private ArrayList<PlayerScore> createPlayers(int numberOfPlayers) {
        ArrayList<PlayerScore> playerList = new ArrayList<>();

        ArrayList<String> inputList = new ArrayList<>(
                Arrays.asList("Number of cards: ", "Sum of cards: ", "Number of wagers: "));

        ArrayList<Integer> playerInput = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            int playerNumber = i + 1;
            System.out.print("\nPlayer " + playerNumber + ":");

            int inputListCounter = 0;
            while (inputListCounter <= inputList.size() - 1) {
                System.out.print("\n" + inputList.get(inputListCounter));
                int input = scanner.nextInt();
                if (input == 0 && inputList.get(inputListCounter).equals("Number of cards: ")) {
                    playerInput = new ArrayList<>(Arrays.asList(0, 0, 0));
                    break;
                } else if (input < 0) {
                    System.out.println("Invalid input. Please enter a value that is 0 or greater.");
                } else {
                    playerInput.add(input);
                    inputListCounter++;
                }
            }

            // if somehow negative values are passed into a new PlayerScore object
            // it will throw an IllegalArgumentException and will create a new object
            // the new object will just have a total score of 0
            try {
                playerList
                        .add(new PlayerScore(playerNumber, playerInput.get(0), playerInput.get(1), playerInput.get(2)));
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                playerList.add(new PlayerScore(0, 0, 0, 0));
            }
            playerInput.clear();
        }

        return playerList;
    }
}