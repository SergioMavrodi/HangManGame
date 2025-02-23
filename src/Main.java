import java.io.*;
import java.util.*;

public class Main {

    static class Player implements Comparable<Player> {
        String name;
        int streak;

        Player(String name) {
            this.name = name;
            this.streak = 0;
        }

        void increaseStreak() {
            streak++;
        }

        void resetStreak() {
            streak = 0;
        }

        @Override
        public int compareTo(Player other) {
            return Integer.compare(other.streak, this.streak); // Sort descending by streak
        }

        @Override
        public String toString() {
            return name + " - Streak: " + streak;
        }
    }

    private static final String FILE_NAME = "leaderboard.txt";
    private static List<Player> leaderboard = new ArrayList<>();

    public static void main(String[] args) {
        // Load leaderboard from file
        loadLeaderboard();

        // Dictionaries for different themes
        Map<String, String[]> themes = new HashMap<>();
        themes.put("Fruits", new String[]{"apple", "banana", "grape", "orange", "kiwi", "peach"});
        themes.put("Animals", new String[]{"elephant", "giraffe", "tiger", "dog", "cat", "kangaroo"});
        themes.put("Countries", new String[]{"russia", "canada", "brazil", "japan", "france", "india"});
        themes.put("Vegetables", new String[]{"carrot", "potato", "onion", "tomato", "lettuce", "cucumber"});
        themes.put("Miscellaneous", new String[]{"moon", "sun", "computer", "java", "music", "game"});

        // Scanner for player input
        Scanner scanner = new Scanner(System.in);

        // Input player name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        Player player = getPlayer(playerName);

        // Game loop
        while (true) {
            // Theme selection
            String randomTheme = getRandomTheme(themes);
            System.out.println("\nWould you like to choose the theme manually or randomly? (enter 'manual' or 'random')");
            String choice = scanner.nextLine().toLowerCase();

            String selectedTheme;
            if (choice.equals("manual")) {
                System.out.println("Available themes:");
                themes.keySet().forEach(System.out::println);
                System.out.print("Choose a theme: ");
                selectedTheme = scanner.nextLine();
                while (!themes.containsKey(selectedTheme)) {
                    System.out.println("Theme not found. Try again.");
                    selectedTheme = scanner.nextLine();
                }
            } else {
                selectedTheme = randomTheme;
                System.out.println("Randomly selected theme: " + selectedTheme);
            }

            String word = getRandomWord(themes.get(selectedTheme));
            playGame(player, selectedTheme, word);

            // Print player status
            System.out.println(player);

            // Ask to continue playing
            System.out.print("\nWould you like to play again? (y/n): ");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("n")) {
                System.out.println("Thank you for playing, " + player.name + "!");
                break;
            }
        }

        // Save leaderboard to file
        saveLeaderboard();

        // Show leaderboard
        showLeaderboard();
    }

    // Get a random theme
    private static String getRandomTheme(Map<String, String[]> themes) {
        Random rand = new Random();
        List<String> themeKeys = new ArrayList<>(themes.keySet());
        return themeKeys.get(rand.nextInt(themeKeys.size()));
    }

    // Get a random word from the selected theme
    private static String getRandomWord(String[] themeWords) {
        Random rand = new Random();
        return themeWords[rand.nextInt(themeWords.length)];
    }

    // Main game process
    private static void playGame(Player player, String theme, String word) {
        Set<Character> guessedLetters = new HashSet<>();
        int lives = 6;

        char[] wordArray = word.toCharArray();
        char[] displayWord = new char[wordArray.length];
        Arrays.fill(displayWord, '_');

        System.out.println("Theme: " + theme);
        printHangman(lives);

        boolean isWordGuessed = false;

        while (lives > 0 && !isWordGuessed) {
            System.out.println("\nCurrent word: " + new String(displayWord));
            System.out.println("Attempts left: " + lives);
            System.out.print("Enter a letter: ");
            char guess = new Scanner(System.in).next().toLowerCase().charAt(0);

            if (guessedLetters.contains(guess)) {
                System.out.println("You already guessed this letter!");
                continue;
            }

            guessedLetters.add(guess);

            boolean found = false;
            for (int i = 0; i < wordArray.length; i++) {
                if (wordArray[i] == guess) {
                    displayWord[i] = guess;
                    found = true;
                }
            }

            if (!found) {
                lives--;
                System.out.println("Incorrect! This letter is not in the word.");
                printHangman(lives);
            } else {
                System.out.println("Correct! The letter is in the word.");
            }

            // Clear the screen after each guess
            clearScreen();

            isWordGuessed = isWordGuessed(displayWord);
        }

        if (lives > 0) {
            System.out.println("\nCongratulations! You guessed the word: " + word);
            player.increaseStreak(); // Increase streak after a win
        } else {
            System.out.println("\nYou lost! The correct word was: " + word);
            player.resetStreak(); // Reset streak after a loss
        }
    }

    // Check if the word is guessed
    private static boolean isWordGuessed(char[] displayWord) {
        for (char c : displayWord) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    // Method to display the hangman and the person
    private static void printHangman(int lives) {
        switch (lives) {
            case 6:
                System.out.println("-------");
                System.out.println("|     |");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("----|----");
                System.out.println("         O");
                break;
            case 5:
                System.out.println("-------");
                System.out.println("|     |");
                System.out.println("|     O");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("----|----");
                System.out.println("         ");
                break;
            case 4:
                System.out.println("-------");
                System.out.println("|     |");
                System.out.println("|     O");
                System.out.println("|     |");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("----|----");
                System.out.println("         ");
                break;
            case 3:
                System.out.println("-------");
                System.out.println("|     |");
                System.out.println("|     O");
                System.out.println("|    /|");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("----|----");
                System.out.println("         ");
                break;
            case 2:
                System.out.println("-------");
                System.out.println("|     |");
                System.out.println("|     O");
                System.out.println("|    /|\\");
                System.out.println("|      ");
                System.out.println("|      ");
                System.out.println("----|----");
                System.out.println("         ");
                break;
            case 1:
                System.out.println("-------");
                System.out.println("|     |");
                System.out.println("|     O");
                System.out.println("|    /|\\");
                System.out.println("|    / ");
                System.out.println("|      ");
                System.out.println("----|----");
                System.out.println("         ");
                break;
            case 0:
                System.out.println("-------");
                System.out.println("|     |");
                System.out.println("|     O");
                System.out.println("|    /|\\");
                System.out.println("|    / \\");
                System.out.println("|      ");
                System.out.println("----|----");
                System.out.println("         ");
                break;
        }
    }

    // Clear screen for both Windows and Unix-like systems (Linux, macOS)
    private static void clearScreen() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                // For Linux and macOS
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get player from leaderboard or create new one
    private static Player getPlayer(String name) {
        // Check if player already exists
        for (Player p : leaderboard) {
            if (p.name.equals(name)) {
                return p; // Return existing player
            }
        }
        // If player does not exist, create and add to leaderboard
        Player newPlayer = new Player(name);
        leaderboard.add(newPlayer);
        return newPlayer;
    }

    // Load leaderboard from file
    private static void loadLeaderboard() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                // If file does not exist, create it
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating leaderboard file.");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - Streak: ");
                String name = parts[0];
                int streak = Integer.parseInt(parts[1]);
                Player player = new Player(name);
                player.streak = streak;
                leaderboard.add(player);
            }
        } catch (IOException e) {
            System.out.println("Error loading leaderboard.");
        }
    }

    // Save leaderboard to file
    private static void saveLeaderboard() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Sort leaderboard before saving
            Collections.sort(leaderboard);

            for (Player player : leaderboard) {
                writer.write(player.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show leaderboard
    private static void showLeaderboard() {
        System.out.println("\nLeaderboard:");
        leaderboard.sort(null); // Sort the players by streak in descending order
        for (Player player : leaderboard) {
            System.out.println(player);
        }
    }
}
