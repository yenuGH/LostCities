package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ca.cmpt276.a2.model.Game;
import ca.cmpt276.a2.model.GameManager;

import ca.cmpt276.a2.model.GameInfoCardModel;
import ca.cmpt276.a2.model.GameInfoRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCES_GAME_MANAGER_NAME = "Games Manager Shared Preferences";
    public static final String JSON_NAME = "GamesList";

    private ArrayList<GameInfoCardModel> gameInfoCardModels = new ArrayList<>();
    private GameInfoRecyclerViewAdapter adapter;

    GameManager gameManager = GameManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbGamesPlayed);
        setSupportActionBar(toolbar);

        TextView optionalFeaturesMessage = findViewById(R.id.optionalFeatureList);
        optionalFeaturesMessage.setText("1. Edit and delete game with confirmation. " +
                "\n2. Complex list view (multi element layout)." +
                "\n3. Confirm on cancel edit with dialog." +
                "\n4. Save data using SharedPreferences and Google Gson." +
                "\n5. Empty state message with multi-elements.");

        loadData();
        setupRecyclerView();
        updateRecyclerViewAdapter();

        // Find the button
        FloatingActionButton createGame = findViewById(R.id.fabCreateGame);
        // Set button behaviour
        createGame.setOnClickListener(view -> {
            Intent gameInfoIntent = GameInfoActivity.makeIntent(MainActivity.this);
            startActivity(gameInfoIntent);
        });
    }

    @Override
    public void onResume(){
        // When the activity is resumed (previous activity is popped off the stack) and is not reloaded,
        // Update the RecyclerView.
        updateRecyclerViewAdapter();
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        // This is when the back button (on the bottom of the screen) is pressed
        // Just save the data every time the main activity instance is exited
        saveData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerViewAdapter(){
        emptyStateHint();
        setupGameInfoModels();
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(){
        // https://www.youtube.com/watch?v=Mc0XT58A1Z4
        RecyclerView recyclerView = findViewById(R.id.rvGamesList);
        setupGameInfoModels();
        adapter = new GameInfoRecyclerViewAdapter(MainActivity.this, gameInfoCardModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void setupGameInfoModels() {
        // clear the card models the adapter uses to populate
        gameInfoCardModels.clear();
        // Then repopulate the card models arraylist
        ArrayList<Game> gameList = gameManager.getGameList();
        for (int i = 0; i < gameList.size(); i++){
            gameInfoCardModels.add(new GameInfoCardModel(gameList.get(i)));
        }

    }

    private void saveData(){
        // https://www.youtube.com/watch?v=jcliHGR3CHo
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_GAME_MANAGER_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // https://www.javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/com/google/gson/GsonBuilder.html
        // https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/time/LocalDateTime.html
        // https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/com/google/gson/TypeAdapter.html
        // https://www.javadoc.io/doc/com.google.code.gson/gson/2.6.2/com/google/gson/stream/JsonWriter.html
        // https://stackoverflow.com/questions/61432170/how-to-serialize-localdate-using-gson

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }

                }).setPrettyPrinting().create();

        String json = gson.toJson(gameManager.getGameList());
        editor.putString(JSON_NAME, json);
        editor.apply();
        // gameManager's gamelist is cleared because on relaunch of the activity
        // will repopulate and possibly create duplicates if the instance is not fully
        // closed but reopned from recent apps
        gameManager.getGameList().clear();
        finishAffinity();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_GAME_MANAGER_NAME, MODE_PRIVATE);

        // https://www.javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/com/google/gson/GsonBuilder.html
        // https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/time/LocalDateTime.html
        // https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/com/google/gson/TypeAdapter.html
        // https://www.javadoc.io/doc/com.google.code.gson/gson/2.6.2/com/google/gson/stream/JsonWriter.html
        // https://stackoverflow.com/questions/61432170/how-to-serialize-localdate-using-gson

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }
                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).setPrettyPrinting().create();

        String json = sharedPreferences.getString(JSON_NAME, null);
        if (json != null){
            Type type = new TypeToken<ArrayList<Game>>() {}.getType();
            ArrayList<Game> loadGameList = gson.fromJson(json, type);
            gameManager.loadGames(loadGameList);
        }
    }

    private void emptyStateHint(){
        ImageView peeposad = findViewById(R.id.peeposad);
        TextView peeposadMessage = findViewById(R.id.peeposad_message);
        ImageView peeposadHint = findViewById(R.id.peeposad_hint);
        TextView peeposadHintMessage = findViewById(R.id.peeposad_hint_message);

        peeposadMessage.setText("Look at peepo! He's sad :c\nHe hasn't played any games!\nPlay games to make peepo happy again!");

        if (gameManager.getGameList().size() == 0){
            peeposad.setVisibility(View.VISIBLE);
            peeposadMessage.setVisibility(View.VISIBLE);
            peeposadHint.setVisibility(View.VISIBLE);
            peeposadHintMessage.setVisibility(View.VISIBLE);
        }
        else {
            peeposad.setVisibility(View.INVISIBLE);
            peeposadMessage.setVisibility(View.INVISIBLE);
            peeposadHint.setVisibility(View.INVISIBLE);
            peeposadHintMessage.setVisibility(View.INVISIBLE);
        }
    }

}