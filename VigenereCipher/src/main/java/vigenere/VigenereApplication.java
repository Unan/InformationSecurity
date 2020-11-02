package vigenere;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VigenereApplication extends JPanel {

    private JButton encrypt = new JButton("Encrypt");
    private JButton decrypt = new JButton("Decrypt");
    private JTextField keyArea = new JTextField("ключ", 40);
    private JTextField textArea = new JTextField("привет мир!", 40);
    private JTextField newTextArea = new JTextField(40);

    private VigenereCipher vigenereCipher = new VigenereCipher();

    public VigenereApplication() {

        encrypt.addActionListener(e -> {
            char[] key = keyArea.getText().toLowerCase().toCharArray();
            char[] text = textArea.getText().toLowerCase().toCharArray();

            if (key.length < text.length) {
                key = prolongKey(key, text);
                printAction("encrypt", key, text);
            }
            newTextArea.setText(vigenereCipher.encrypt(key, text));
        });

        decrypt.addActionListener(e -> {
            char[] key = keyArea.getText().toLowerCase().toCharArray();
            char[] text = textArea.getText().toLowerCase().toCharArray();

            if (key.length < text.length) {
                key = prolongKey(key, text);
                printAction("decrypt", key, text);
            }
            newTextArea.setText(vigenereCipher.decrypt(key, text));

        });
        add(new JLabel("Ключ:"));
        add(keyArea);
        add(new JLabel("Исходный текст:"));
        add(textArea);
        add(new JLabel("Результат шифрования(расшифровки):"));
        add(newTextArea);
        add(encrypt);
        add(decrypt);
    }

    private static void printAction(String action, char[] key, char[] text) {
        System.out.println(action + "\nkey: " + new String(key) + " - text: " + new String(text));
    }

    private static char[] prolongKey(char[] key, char[] text) {
        StringBuilder resultKey = new StringBuilder();
        for (int i = 0, j = 0; i < text.length; i++) {
            resultKey.append(key[j]);
            j = ++j % key.length;
        }
        return resultKey.toString().toCharArray();
    }

    private static void createAndShowGUI() {
        JFrame window = new JFrame("Шифр Виженера");
        window.setSize(new Dimension(500, 220));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new VigenereApplication());
    }

    public static void main(String[] args) {
        Runnable doCreateAndShowGUI = VigenereApplication::createAndShowGUI;
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
} 