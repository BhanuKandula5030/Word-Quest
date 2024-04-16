import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class WordQuestGameModel {
    private List<String> words;
    private String targetWord;
    private int maxTries;
    private int tries;

    public WordQuestGameModel() {
        words = readWordsFromFile("Dictionary");
        targetWord = getRandomWord(words);
        maxTries = 6;
        tries = 0;
    }

    private List<String> readWordsFromFile(String filename) {
        List<String> wordList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordList.add(line.toUpperCase()); // Store words in uppercase for consistent comparison
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    private String getRandomWord(List<String> wordList) {
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }

    public String getTargetWord() {
        return targetWord;
    }

    public int getMaxTries() {
        return maxTries;
    }

    public int getTries() {
        return tries;
    }

    public void incrementTries() {
        tries++;
    }

    public boolean isWordInDictionary(String word) {
        return words.contains(word.toUpperCase()); 
    }
}