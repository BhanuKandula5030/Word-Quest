import javax.swing.JFrame;
import java.awt.Color;

public class WordQuestGame {
    public static WordQuestGameModel model;
    public static WordQuestGameView view;
    public static WordQuestGameController controller;

    public static void main(String[] args) {
        model = new WordQuestGameModel();
        view = new WordQuestGameView();
        controller = new WordQuestGameController(model, view);

        JFrame frame = new JFrame("WordQuest Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setBackground(Color.BLACK);
        frame.add(view);

        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
