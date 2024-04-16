import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordQuestGameView extends JPanel {
    private JTextField inputField;
    private JButton submitButton;
    private JPanel resultPanel;
    private JPanel[][] resultBlocks;

    public WordQuestGameView() {
        setLayout(new BorderLayout());

        resultPanel = new JPanel(new GridBagLayout());
        resultBlocks = new JPanel[6][5];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        for (int i = 0; i < 6; i++) {
            JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            gbc.gridy = i;

            for (int j = 0; j < 5; j++) {
                JPanel blockPanel = new JPanel(new BorderLayout());
                blockPanel.setPreferredSize(new Dimension(50, 50));
                blockPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel letterLabel = new JLabel("", SwingConstants.CENTER);
                blockPanel.add(letterLabel, BorderLayout.CENTER);
                inputPanel.add(blockPanel);
                resultBlocks[i][j] = blockPanel;
            }

            resultPanel.add(inputPanel, gbc);
        }

        JPanel inputPanel = new JPanel(new BorderLayout());

        JPanel keyboardPanel = new JPanel(new GridLayout(3, 10));

        // Adding buttons for each letter of the alphabet
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (char letter : alphabet) {
            JButton button = new JButton(Character.toString(letter));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inputField.setText(inputField.getText() + letter);
                }
            });
            keyboardPanel.add(button);
        }
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.doClick();
            }
        });

        JButton backButton = new JButton("Backspace");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                if (!text.isEmpty()) {
                    inputField.setText(text.substring(0, text.length() - 1));
                }
            }
        });

        inputPanel.add(keyboardPanel, BorderLayout.CENTER);
        inputPanel.add(enterButton, BorderLayout.EAST);
        inputPanel.add(backButton, BorderLayout.WEST);

        inputField = new JTextField(5);
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        inputPanel.add(inputField, BorderLayout.NORTH);
        inputPanel.add(submitButton, BorderLayout.SOUTH);

        add(resultPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    public void updateResult(int row, int[] colorOfLetters, String inputWord) {
        for (int i = 0; i < 5; i++) {
            JPanel block = resultBlocks[row][i];
            JLabel letterLabel = (JLabel) block.getComponent(0);

            if (colorOfLetters[i] == 1) {
                block.setBackground(Color.RED);
            } else if (colorOfLetters[i] == 2) {
                block.setBackground(Color.GREEN);
            } else {
                block.setBackground(Color.GRAY);
            }

            letterLabel.setText(Character.toString(inputWord.charAt(i)));
        }

        revalidate();
        repaint();
    }

    public String getInputWord() 
    {
        return inputField.getText().toUpperCase();
    }

    public void clearInputField() {
        inputField.setText("");
    }

    public void disableInput() {
        inputField.setEnabled(false);
        submitButton.setEnabled(false);
    }

    public void displayWinMessage() {
        JOptionPane.showMessageDialog(this, "Congratulations! You found the word!", "You Win!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayLoseMessage() {
        JOptionPane.showMessageDialog(this, "Sorry, you've run out of tries. The word was: " + WordQuestGame.model.getTargetWord(), "Game Over", JOptionPane.WARNING_MESSAGE);
    }

    public void addSubmitListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }
}
