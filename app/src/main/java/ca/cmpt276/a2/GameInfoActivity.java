package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class GameInfoActivity extends AppCompatActivity {

    static String activityTitle;

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

        GameInfoActivity.this.setTitle(activityTitle);
    }
}