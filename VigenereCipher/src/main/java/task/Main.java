package task;

public class Main {
    public static void main(String[] args) {

        Vig vig = new Vig();
        System.out.println((int) ' ');

        String text = "альфа";
        String key = "диего";
        System.out.println("original: " + text);
        String encrypt = vig.encrypt(key, text);
        System.out.println("encrypted: " + encrypt);
        String decrypt = vig.decrypt(key, encrypt);
        System.out.println("decrypted: " + decrypt);

    }
}
