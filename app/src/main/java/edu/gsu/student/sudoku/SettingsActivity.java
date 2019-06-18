package edu.gsu.student.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private Globals    globals;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.init();
    }

    private void init() {
        this.globals    = (Globals) getApplicationContext();
        this.radioGroup = findViewById( R.id.difficulty );

        this.displayDifficulty();
        this.setGroupListener();
    }

    private void displayDifficulty() {

        switch( this.globals.getStringGameDifficulty() ) {
            case "Easy":   radioGroup.check( R.id.easy   ); break;
            case "Medium": radioGroup.check( R.id.medium ); break;
            case "Hard":   radioGroup.check( R.id.hard   ); break;
            case "Expert": radioGroup.check( R.id.expert ); break;
        }

    }

    private void setGroupListener() {
        this.radioGroup.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch( i ) {
                    case R.id.easy:   globals.setGameDifficulty(4); break;
                    case R.id.medium: globals.setGameDifficulty(3); break;
                    case R.id.hard:   globals.setGameDifficulty(2); break;
                    case R.id.expert: globals.setGameDifficulty(1); break;
                }

                Intent mainIntent = new Intent( SettingsActivity.this, MainActivity.class );
                startActivity( mainIntent );
            }
        } );
    }
}
