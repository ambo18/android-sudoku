package edu.gsu.student.sudoku;

import android.app.Application;

public class Globals extends Application {

    // The game difficulty. The higher the value, the harder the game.
    private double[] difficulty = {0.25, 0.50, 0.75, 0.90};

    // The game's current difficulty level. It's set to medium initially
    private double gameDifficulty = difficulty[1];

    /**
     * Sets the game difficulty
     *
     * @param difficultyLevel - 4: easy, 3: medium, 2: hard, 1: expert
     */
    public void setGameDifficulty( int difficultyLevel ) {

        switch( difficultyLevel ) {
            case 1: this.gameDifficulty = this.difficulty[0]; break;
            case 2: this.gameDifficulty = this.difficulty[1]; break;
            case 3: this.gameDifficulty = this.difficulty[2]; break;
            case 4: this.gameDifficulty = this.difficulty[3]; break;
        }
    }

    /**
     * @return - game difficulty
     */
    public double getGameDifficulty() {
        return this.gameDifficulty;
    }

    /**
     * @return - game difficulty string
     */
    public String getStringGameDifficulty() {

        if ( this.gameDifficulty == this.difficulty[3] ) {
            return "Easy";

        } else if ( this.gameDifficulty == this.difficulty[2] ) {
            return "Medium";

        } else if ( this.gameDifficulty == this.difficulty[1] ) {
            return "Hard";

        } else {
            return "Expert";
        }

    }
}
