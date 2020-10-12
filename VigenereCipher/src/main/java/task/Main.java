package task;

public class Main {
    public static void main(String[] args) {

//        System.out.println((int)'а');

        Vigenere vigenere = new Vigenere();

        String text = "альфа";
        String key = "браво";
        System.out.println("text: " + text);
        System.out.println("key: " + key);
        String encrypt = vigenere.encrypt(key, text);
        System.out.println("encrypted: " + encrypt);
        String decrypt = vigenere.decrypt(key, encrypt);
        System.out.println("decrypted: " + decrypt);

    }
}
