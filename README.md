Hangman Game in Java

This project is a simple implementation of the classic Hangman word-guessing game in Java. It includes a leaderboard system that tracks player streaks, allowing players to compete for the highest score over multiple game sessions. Players can choose from various themes or have a random theme selected for them, adding variety to the game. The game also features a visual Hangman figure that updates with each incorrect guess.
Key Features:

    Leaderboard System: Player names and streaks (consecutive wins) are saved in a text file (leaderboard.txt), and the leaderboard is displayed at the end of the game session. The leaderboard is sorted in descending order based on the longest winning streaks.

    Multiple Themes: Players can choose from several predefined themes including:
        Fruits: Apple, Banana, Grape, Orange, Kiwi, Peach.
        Animals: Elephant, Giraffe, Tiger, Dog, Cat, Kangaroo.
        Countries: Russia, Canada, Brazil, Japan, France, India.
        Vegetables: Carrot, Potato, Onion, Tomato, Lettuce, Cucumber.
        Miscellaneous: Moon, Sun, Computer, Java, Music, Game.

    Players can either choose a theme manually or let the game pick one randomly for them.

    Streak Tracking: The player’s streak is tracked during the game session. If a player guesses all letters in a word correctly, their streak is incremented. If they fail to guess the word within 6 incorrect attempts, their streak is reset to 0.

    Hangman Visuals: As players make incorrect guesses, a visual Hangman figure is displayed, showing the player’s remaining attempts.

How the Game Works:

    Player Setup: The player is prompted to enter their name at the start of the game. The game then checks the leaderboard to see if the player has played before and loads their current streak. If it's their first time, a new player is added.
    Game Loop:
        Theme Selection: Players can choose to either manually select a theme or have one randomly assigned.
        Guessing Process: Players are presented with a word and must guess letters. For each incorrect guess, a part of the Hangman figure is drawn.
        Game Over: The game ends when the player either guesses the word correctly or runs out of lives (6 incorrect guesses). A win increases their streak, while a loss resets it.
    Leaderboard: After the game, the leaderboard is saved to leaderboard.txt and displayed to the player, showing all players sorted by their longest streak.

File Structure:

    Main.java: Contains the main logic for the Hangman game, including player input, theme selection, game progress, and leaderboard handling.
    leaderboard.txt: A text file that stores player names and their streaks, used for keeping track of scores across sessions.

Game Flow:

    The player enters their name.
    They choose a theme or let the game pick one randomly.
    The game proceeds with letter guesses, and feedback is provided after each guess.
    The streak is updated based on the outcome of the game (win/loss).
    At the end of the session, the leaderboard is displayed, and player streaks are saved to the leaderboard file.
