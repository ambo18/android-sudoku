package edu.gsu.student.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

/**
 * Project 1
 * Sudoku Mobile App
 * Mobile App Development
 *
 * Authors
 * - Dino Cajic
 * - Ha Hwang
 * - Carlos Soares
 */
public class SettingsActivity extends AppCompatActivity {

    private Globals    globals;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.init();
    }

    /**
     * Initializes the necessary content for the Settings Screen
     */
    private void init() {
        this.globals    = (Globals) getApplicationContext();
        this.radioGroup = findViewById( R.id.difficulty );

        this.displayDifficulty();
        this.setGroupListener();
    }

    /**
     * Checks the difficulty button based on the current difficulty
     */
    private void displayDifficulty() {

        switch( this.globals.getStringGameDifficulty() ) {
            case "Easy":   radioGroup.check( R.id.easy   ); break;
            case "Medium": radioGroup.check( R.id.medium ); break;
            case "Hard":   radioGroup.check( R.id.hard   ); break;
            case "Expert": radioGroup.check( R.id.expert ); break;
        }

    }

    /**
     * Updates the game difficulty in the Globals class and redirects the user to the game
     */
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
