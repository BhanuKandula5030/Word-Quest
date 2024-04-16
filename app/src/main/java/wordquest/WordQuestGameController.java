import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

class WordQuestGameController implements ActionListener {
    private WordQuestGameModel model;
    private WordQuestGameView view;

    public WordQuestGameController(WordQuestGameModel model, WordQuestGameView view) {
        this.model = model;
        this.view = view;
        this.view.addSubmitListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputWord = view.getInputWord();

        if (inputWord.length() != 5) {
            JOptionPane.showMessageDialog(view, "Please enter a five-letter word.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isValidWord(inputWord)) {
            JOptionPane.showMessageDialog(view, "The entered word does not exist in the dictionary.", "Invalid Word", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int[] colorOfLetters = playWordle(inputWord);
        updateView(model.getTries(), colorOfLetters, inputWord);

        if (Arrays.equals(colorOfLetters, new int[]{2, 2, 2, 2, 2})) {
            view.displayWinMessage();
            view.disableInput();
        } else {
            model.incrementTries();
            view.clearInputField();

            if (model.getTries() >= model.getMaxTries()) {
                view.disableInput();
                JOptionPane.showMessageDialog(view, "Sorry, you've run out of tries. The word was: " + model.getTargetWord(), "Game Over", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    protected int[] playWordle(String inputWord) {
        String targetWord = model.getTargetWord().toUpperCase(); // Convert to uppercase
        int[] colorOfLetters = new int[5];

        for (int i = 0; i < 5; i++) {
            char inputChar = inputWord.charAt(i);
            char targetChar = targetWord.charAt(i);

            if (inputChar == targetChar) {
                colorOfLetters[i] = 2; // Green
            } else if (targetWord.contains(String.valueOf(inputChar))) {
                colorOfLetters[i] = 1; // Red
            } else {
                colorOfLetters[i] = 0; // No match
            }
        }

        return colorOfLetters;
    }

    private void updateView(int row, int[] colorOfLetters, String inputWord) {
        view.updateResult(row, colorOfLetters, inputWord);
    }

    protected boolean isValidWord(String word) {
        return model.isWordInDictionary(word);
    }
}