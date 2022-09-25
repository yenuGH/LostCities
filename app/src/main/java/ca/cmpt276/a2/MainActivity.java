package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyFirstApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button
        FloatingActionButton createGame = findViewById(R.id.fabCreateGame);
        // Set button behaviour
        createGame.setOnClickListener(view -> {
            Log.i(TAG, "You clicked it!");
            Toast.makeText(MainActivity.this, "YAY! Good work!", Toast.LENGTH_SHORT).show();
        });
    }
}