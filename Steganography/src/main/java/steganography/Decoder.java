package steganography;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.stream.Collectors;

public class Decoder {

    private static final String RUSSIAN_CHARACTERS = new String("аАВеЕКМНоОрРсСхХ".getBytes(), StandardCharsets.UTF_8);
    private static final String ENGLISH_CHARACTERS = new String("aABeEKMHoOpPcCxX".getBytes(), StandardCharsets.UTF_8);
    private static final String ENCODING = "Windows-1251";

    private static File containerWithSecret;
    private static File decodedSecret;

    public static void main(String[] args) {

        if (args.length == 0) {
            args = new String[3];
            args[0] = "/Users/ustepanian/Documents/thd/project/InformationSecurity/Steganography/src/main/resources/encode/containerWithSecret.txt";
            args[1] = "/Users/ustepanian/Documents/thd/project/InformationSecurity/Steganography/src/main/resources/decode/decodedSecret.txt";
        }
        if (!checkInput(args)) {
            return;
        }
        initFromArgs(args);

        BitSet text = new BitSet();
        int indexOfBitsText = 0;

        try (FileInputStream inputStream = new FileInputStream(containerWithSecret)) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ENCODING)) {
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {

                    String readAll = reader.lines().collect(Collectors.joining("\n"));

                    for (int i = 0; i < readAll.length(); i++) {
                        char original = readAll.charAt(i);

                        if (canUseInDecryption(original)) {
                            text.set(indexOfBitsText++, decode(original));
                            if (indexOfBitsText - text.length() > 8) {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Не удаётся прочитать файл по пути = " + containerWithSecret);
            e.printStackTrace();
            return;
        }

        try (FileOutputStream outputStream = new FileOutputStream(decodedSecret)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, ENCODING)) {
                writer.write(new String(text.toByteArray(), ENCODING));

            }
        } catch (IOException e) {
            System.err.println("Не удаётся записать в файл по пути = " + decodedSecret);
            e.printStackTrace();
        }
    }

    private static void initFromArgs(String[] args) {
        containerWithSecret = new File(args[0]);
        decodedSecret = new File(args[1]);
    }

    private static boolean checkInput(String[] args) {
        if (args.length < 2) {
            System.err.println("Обязательные аргументы: <containerWithSecret> <decodedSecret>");
            return false;
        }

        File fileWithText = new File(args[0]);
        if (!fileWithText.exists()) {
            System.err.println("Не удаётся найти файл контейнер");
            return false;
        }

        File outputFile = new File(args[1]);
        try {
            if (!outputFile.createNewFile()) {
                System.out.println("Внимаение! файл контейнер с секретом будет перезаписан. Путь = " + outputFile);
            }
        } catch (IOException e) {
            System.err.println("Не удаётся создать файл по пути = " + outputFile);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean canUseInEncryption(char character) {
        return RUSSIAN_CHARACTERS.indexOf(character) > -1;
    }

    private static boolean canUseInDecryption(char character) {
        return canUseInEncryption(character) || ENGLISH_CHARACTERS.indexOf(character) > -1;
    }

    private static boolean decode(char character) {
        if (!canUseInDecryption(character)) {
            throw new IllegalArgumentException("Не удаётся использовать символ '" + character + "' для шифрования");
        }
        return ENGLISH_CHARACTERS.indexOf(character) > -1;
    }
}
