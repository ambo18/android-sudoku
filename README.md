# android-sudoku
An Android Sudoku game for Programmers going from 0 to 8 instead of 1 to 9. 

The game is small twist on the regular Sudoku consisting of numbers 0 to 8 instead of 1 to 9. There are 9 rows and 9 columns consisting of 81 total squares. Once a new game is generated, the puzzle is displayed. Once the user finishes entering the numbers, he or she will click on Submit Puzzle to validate it. If the puzzle was not solved correctly, the user will receive a message stating that the puzzle is not solved, otherwise he or she will receive a message stating that the puzzle was solved successfully.

##Algorithm
- Populate first row with numbers 0 to 8.
- Suffle first row.
- Shift each subsequent row by 3 spaces to the right.

![alt text](http://dinocajic.xyz/screenshots/sudoku.PNG)

The main menu will allow the user to start a new game, select the difficulty level, and access the Help screen. The user can restart the game at any time by clicking on the new game menu option. The user can also change the difficulty by going to the Settings screen and choosing the difficulty level: Easy, Medium, Hard or Expert. To find out how to play the game, the user can click on the Help menu option and read about the game.
