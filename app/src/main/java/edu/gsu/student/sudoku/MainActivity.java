package edu.gsu.student.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // App Name at the top
    TextView appName;

    // Stores the numbers from each text field
    EditText[][] sudokuNumbers = new EditText[9][9];

    // Stores the generated solved Sudoku puzzle
    int[][] solvedSudokuPuzzle;

    // Stores the generated unsolved Sudoku puzzle
    int[][] unsolvedSudokuPuzzle;

    // Submit Puzzle Button
    Button submitPuzzleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
    }

    /**
     * Creates the menu from res/menu/options
     *
     * @param menu - menu bar
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.options, menu );

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Allows for the user to switch to the Help screen
     *
     * @param item - menu item
     * @return - boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if ( item.getItemId() == R.id.help ) {
            Intent helpIntent = new Intent( MainActivity.this, HelpActivity.class );
            startActivity( helpIntent );
        }

        if ( item.getItemId() == R.id.settings ) {
            Intent settingsIntent = new Intent( MainActivity.this, SettingsActivity.class );
            startActivity( settingsIntent );
        }

        if ( item.getItemId() == R.id.new_game ) {
            Intent mainIntent = new Intent( MainActivity.this, MainActivity.class );
            startActivity( mainIntent );
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the necessary items on start up
     */
    private void init() {
        // Create an array to store all of the sudoku numbers
        EditText[][] sudokuNumbers = {

                // First row
                {findViewById( R.id.editText00 ), findViewById( R.id.editText01 ),
                 findViewById( R.id.editText02 ), findViewById( R.id.editText03 ),
                 findViewById( R.id.editText04 ), findViewById( R.id.editText05 ),
                 findViewById( R.id.editText06 ), findViewById( R.id.editText07 ),
                 findViewById( R.id.editText08 )},

                // Second row
                {findViewById( R.id.editText10 ), findViewById( R.id.editText11 ),
                 findViewById( R.id.editText12 ), findViewById( R.id.editText13 ),
                 findViewById( R.id.editText14 ), findViewById( R.id.editText15 ),
                 findViewById( R.id.editText16 ), findViewById( R.id.editText17 ),
                 findViewById( R.id.editText18 )},

                // Third row
                {findViewById( R.id.editText20 ), findViewById( R.id.editText21 ),
                 findViewById( R.id.editText22 ), findViewById( R.id.editText23 ),
                 findViewById( R.id.editText24 ), findViewById( R.id.editText25 ),
                 findViewById( R.id.editText26 ), findViewById( R.id.editText27 ),
                 findViewById( R.id.editText28 )},

                // Fourth row
                {findViewById( R.id.editText30 ), findViewById( R.id.editText31 ),
                 findViewById( R.id.editText32 ), findViewById( R.id.editText33 ),
                 findViewById( R.id.editText34 ), findViewById( R.id.editText35 ),
                 findViewById( R.id.editText36 ), findViewById( R.id.editText37 ),
                 findViewById( R.id.editText38 )},

                // Fifth row
                {findViewById( R.id.editText40 ), findViewById( R.id.editText41 ),
                 findViewById( R.id.editText42 ), findViewById( R.id.editText43 ),
                 findViewById( R.id.editText44 ), findViewById( R.id.editText45 ),
                 findViewById( R.id.editText46 ), findViewById( R.id.editText47 ),
                 findViewById( R.id.editText48 )},

                // Sixth row
                {findViewById( R.id.editText50 ), findViewById( R.id.editText51 ),
                 findViewById( R.id.editText52 ), findViewById( R.id.editText53 ),
                 findViewById( R.id.editText54 ), findViewById( R.id.editText55 ),
                 findViewById( R.id.editText56 ), findViewById( R.id.editText57 ),
                 findViewById( R.id.editText58 )},

                // Seventh row
                {findViewById( R.id.editText60 ), findViewById( R.id.editText61 ),
                 findViewById( R.id.editText62 ), findViewById( R.id.editText63 ),
                 findViewById( R.id.editText64 ), findViewById( R.id.editText65 ),
                 findViewById( R.id.editText66 ), findViewById( R.id.editText67 ),
                 findViewById( R.id.editText68 )},

                // Eight row
                {findViewById( R.id.editText70 ), findViewById( R.id.editText71 ),
                 findViewById( R.id.editText72 ), findViewById( R.id.editText73 ),
                 findViewById( R.id.editText74 ), findViewById( R.id.editText75 ),
                 findViewById( R.id.editText76 ), findViewById( R.id.editText77 ),
                 findViewById( R.id.editText78 )},

                // Ninth row
                {findViewById( R.id.editText80 ), findViewById( R.id.editText81 ),
                 findViewById( R.id.editText82 ), findViewById( R.id.editText83 ),
                 findViewById( R.id.editText84 ), findViewById( R.id.editText85 ),
                 findViewById( R.id.editText86 ), findViewById( R.id.editText87 ),
                 findViewById( R.id.editText88 )}
        };

        this.sudokuNumbers      = sudokuNumbers;
        this.submitPuzzleButton = findViewById( R.id.submitPuzzleButton );
        this.appName            = findViewById( R.id.app_name );

        this.addOnClickEditTextListeners( this.sudokuNumbers );

        this.solvedSudokuPuzzle   = this.generateSudokuPuzzle();

        Globals globals = (Globals) getApplicationContext();

        this.unsolvedSudokuPuzzle = this.generateUnsolvedPuzzle( this.solvedSudokuPuzzle, globals.getGameDifficulty() );

        // For testing if we needed to see the entire solved array
        // System.out.println( Arrays.deepToString( solvedSudokuPuzzle ) );
        // System.out.println( Arrays.deepToString( unsolvedSudokuPuzzle ) );

        this.populatePuzzle();
        this.addOnClickButtonListener();
    }

    /**
     * Allows the keyboard to be closed when the user clicks enter on it
     *
     * @param sudokuNumbers - the EditText fields
     */
    private void addOnClickEditTextListeners( EditText[][] sudokuNumbers ) {
        for ( EditText[] nums : sudokuNumbers ) {

            for ( EditText num : nums ) {

                num.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeKeyboard();
                    }
                } );

            }
        }
    }

    /**
     * Closes the keyboard after completion. The user can press return to minimize the keyboard.
     */
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Creates the solved Sudoku puzzle
     *
     * @return solved sudoku puzzle
     */
    private int[][] generateSudokuPuzzle() {

        int[][] solved = new int[9][9];

        // Populate the first row of the array
        for ( int i = 0; i < 9; i++ ) {
            solved[0][i] = i;
        }

        // Shuffle the first row
        this.shuffleArray( solved[0] );

        // Populate the remainder of the array
        // k = (k == 9)  ? 0 : k; ... and the two other assignment statements below that
        // handle the array elements being pushed out from the front and to the back.
        int k;

        for ( int i = 1; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {

                if ( i % 3 == 0 ) {
                    k = j + 1;
                } else {
                    k = j + 3;
                }

                k = (k == 9)  ? 0 : k;
                k = (k == 10) ? 1 : k;
                k = (k == 11) ? 2 : k;

                solved[i][j] = solved[i - 1][k];
            }
        }

        return solved;
    }

    /**
     * After the first row is created, it's shuffled with the shuffleArray() method. It just
     * reorders the 9 elements in the first row.
     *
     * @param array - the first row of the solved array
     */
    private void shuffleArray( int[] array ) {

        int index, temp;
        Random random = new Random();

        for (int i = array.length - 1; i > 0; i--) {

            index        = random.nextInt(i + 1);
            temp         = array[index];
            array[index] = array[i];
            array[i]     = temp;
        }
    }

    /**
     * Takes the solved array and deletes certain numbers from them based on the difficulty
     * level of the game. The lower the difficulty number, the more difficult the game.
     *
     * @param solved - the solved array
     * @param difficulty - the game difficulty
     *
     * @return unsolved array
     */
    private int[][] generateUnsolvedPuzzle( int[][] solved, double difficulty ) {
        int[][] unsolved = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if ( Math.random() > difficulty) {
                    unsolved[i][j] = 0;

                } else {
                    unsolved[i][j] = solved[i][j];
                }

            }
        }

        return unsolved;
    }

    /**
     * Populates the puzzle with the generated unsolved sudoku puzzle array.
     * The user will see the missing digits
     */
    private void populatePuzzle() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {

                if ( this.unsolvedSudokuPuzzle[i][j] == 0 ) {
                    this.sudokuNumbers[i][j].setText( "" );

                } else {
                    this.sudokuNumbers[i][j].setText(
                            getString( R.string.puzzle_numbers, this.unsolvedSudokuPuzzle[i][j] + "" )
                    );
                }

            }
        }
    }

    /**
     * Checks to see if the Sudoku puzzle is correct once it has been submitted by clicking on the
     * Submit Puzzle Button. If the puzzle has been solved, it will say solved, otherwise it says
     * Keep Trying.
     */
    private void addOnClickButtonListener() {
        this.submitPuzzleButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if ( checkIfSolved( sudokuNumbers, solvedSudokuPuzzle ) ) {

                    appName.setText(
                            getString( R.string.solved )
                    );

                } else {
                    appName.setText(
                            getString( R.string.keep_trying )
                    );
                }
            }

        } );
    }

    /**
     * Checks to see if the game is solved.
     *
     * @param sudokuNumbers - the numbers entered by the user
     * @param solvedSudokuNumbers - the solved array
     *
     * @return true if solved, false otherwise
     */
    private boolean checkIfSolved( EditText[][] sudokuNumbers, int[][] solvedSudokuNumbers ) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if ( !sudokuNumbers[i][j].getText().toString().equals( solvedSudokuNumbers[i][j] + "" ) ) {
                    return false;
                }
            }
        }

        return true;
    }
}
