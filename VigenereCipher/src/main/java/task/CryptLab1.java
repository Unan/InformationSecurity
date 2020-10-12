package task;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
 
 
public class CryptLab1 extends JPanel {
 
    public CryptLab1() {
 
        encryptButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                char[] key = keyArea.getText().toLowerCase().toCharArray();
                char[] text = textArea.getText().toLowerCase().toCharArray();
 
                if (key.length >= text.length){
                    outputText = encrypt(key, text);
                    newTextArea.setText(new String(outputText));
                } else {
                    JOptionPane.showMessageDialog(CryptLab1.this, "Длина ключа должна быть равна длине текста");
                }
                
            }
        });
        decryptButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                char[] key = keyArea.getText().toLowerCase().toCharArray();
                char[] text = textArea.getText().toLowerCase().toCharArray();
 
                if (key.length >= text.length){
                    outputText = decrypt(key, text);
                    newTextArea.setText(new String(outputText));
                } else {
                    JOptionPane.showMessageDialog(CryptLab1.this, "Длина ключа должна быть равна длине текста");
                }
 
            }
        });
        add(new JLabel("Ключ:"));
        add(keyArea);
        add(new JLabel("Исходный текст:"));
        add(textArea);
        add(new JLabel("Результат шифрования(расшифровки):"));
        add(newTextArea);
        add(encryptButton);
        add(decryptButton);
 
    }

    private static int offset = 1072;
    private static int letters = 33;
    //генератор таблицы Виженера
    private static char[][] genTable() {
        char[][] table = new char[letters][letters];
 
        for (int i = 0; i < letters; i++) {
            int off = i;
 
            for (int j = 0; j < letters; j++) {
 
                if(off == letters) {
                    off = 0;
                }
                table[i][j] = (char)(offset + off);
                off++;
            }
        }
        return table;
    }
    //метод шифрования
    private static  String encrypt(char[] key, char[] text) {
        char[] nText = new char[text.length];
        int k;
        int t;
        char[][] table = genTable();
 
        for (int i = 0; i < text.length; i++) {
            k = (int)key[i] - offset;
            t = (int)text[i] - offset;
            nText[i] = table[k][t];
            
        }
        return new String(nText);
    }
    //метод расшифровки
    private static  String decrypt(char[] key, char[] text) {
        char[] nText = new char[text.length];
        int k;
        int t;
        char[][] table = genTable();
 
        for (int i = 0; i < text.length; i++) {
            k = (int)key[i] - offset;
            t = (int)text[i] - offset;
            if (k > t) {
                nText[i] = table[letters + (t - k)][0];
            } else {
                nText[i] = table[t - k][0];
            }
            
 
        }
        return new String(nText);
    }
 
    private static void createAndShowGUI(){
 
        JFrame window = new JFrame("Шифровка");
        window.setSize(new Dimension(400, 210));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new CryptLab1());
    }
 
    public static void main(String args[]) {
        Runnable doCreateAndShowGUI = new Runnable() {
 
            public void run() {
                createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
    
    private String outputText;
    private JButton encryptButton = new JButton("En");
    private JButton decryptButton = new JButton("De");
    private JTextField keyArea = new JTextField("ABCDEFABCDEFABCDEFABCDEFABCD", 35);
    private JTextField textArea = new JTextField("csasxtitukswtgqugwyqvrkwaqjb",35);
    private JTextField newTextArea = new JTextField(35);
}