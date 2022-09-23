package ca.cmpt276.a2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyFirstApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button
        Button button = findViewById(R.id.buttonClickMe);
        // Set button behaviour
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(TAG, "You clicked it!");
                Toast.makeText(MainActivity.this, "YAY! Good work!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}