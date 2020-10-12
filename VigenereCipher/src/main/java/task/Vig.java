package task;

public class Vig {

    private int offset = 1072;
    private int letters = 33;

    public String encrypt(String key, String text) {
        return encrypt(key.toCharArray(), text.toCharArray());
    }

    public String decrypt(String key, String text) {
        return decrypt(key.toCharArray(), text.toCharArray());
    }

    public char[][] genTable() {
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

    public String encrypt(char[] key, char[] text) {
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

    public String decrypt(char[] key, char[] text) {
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
}
