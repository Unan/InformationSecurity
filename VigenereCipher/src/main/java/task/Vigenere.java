package task;

public class Vigenere {

    private int offset = 1072;
    private int letters = 32;
    private char[][] table = genTable();

    {
        for (char[] chars : genTable()) {
//            System.out.println(chars);
        }
    }

    public String encrypt(String key, String text) {
        return encrypt(key.toCharArray(), text.toCharArray());
    }

    public String decrypt(String key, String text) {
        return decrypt(key.toCharArray(), text.toCharArray());
    }

    public String encrypt(char[] key, char[] text) {
        char[] encrypted = new char[text.length];
        char[][] table = this.table;

        for (int i = 0; i < text.length; i++) {
            int k = (int)key[i] - offset;
            int t = (int)text[i] - offset;
//            System.out.println("k = " + k + " : " + "t = " + t);
            encrypted[i] = table[k][t];
        }
        return new String(encrypted);
    }

    public String decrypt(char[] key, char[] text) {
        char[] decrypted = new char[text.length];
        char[][] table = this.table;

        for (int i = 0; i < text.length; i++) {
            int k = (int)key[i] - offset;
            int t = (int)text[i] - offset;
//            System.out.println("k = " + k + " : " + "t = " + t);
            if (k > t) {
                decrypted[i] = table[(t - k) + letters][0];
            } else {
                decrypted[i] = table[t - k][0];
            }
        }
        return new String(decrypted);
    }

    private char[][] genTable() {
        char[][] table = new char[letters][letters];

        for (int i = 0; i < letters; i++) {
            int shift = i;

            for (int j = 0; j < letters; j++) {

                if(shift == letters) {
                    shift = 0;
                }
                table[i][j] = (char)(this.offset + shift);
                shift++;
            }
        }
        return table;
    }
}
