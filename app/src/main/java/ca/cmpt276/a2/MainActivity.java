package ca.cmpt276.a2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tbGamesPlayed);
        setSupportActionBar(toolbar);

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
        super.onResume();
        updateRecyclerViewAdapter();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerViewAdapter(){
        emptyStateHint();
        setupGameInfoModels();
        adapter.notifyDataSetChanged();
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

    private void setupRecyclerView(){
        // https://www.youtube.com/watch?v=Mc0XT58A1Z4
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

    }

}