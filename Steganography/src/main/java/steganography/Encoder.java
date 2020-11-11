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

public class Encoder {

    private static final String RUSSIAN_CHARACTERS = new String("аАВеЕКМНоОрРсСхХ".getBytes(), StandardCharsets.UTF_8);
    private static final String ENGLISH_CHARACTERS = new String("aABeEKMHoOpPcCxX".getBytes(), StandardCharsets.UTF_8);
    private static final String ENCODING = "Windows-1251";

    private static File container;
    private static File secret;
    private static File containerWithSecret;

    public static void main(String[] args) {

        if (args.length == 0) {
            args = new String[3];
            args[0] = "/Users/ustepanian/Documents/thd/project/InformationSecurity/Steganography/src/main/resources/encode/container.txt";
            args[1] = "/Users/ustepanian/Documents/thd/project/InformationSecurity/Steganography/src/main/resources/encode/secret.txt";
            args[2] = "/Users/ustepanian/Documents/thd/project/InformationSecurity/Steganography/src/main/resources/encode/containerWithSecret.txt";
        }
        if (!checkInput(args)) {
            return;
        }
        initFromArgs(args);

        BitSet bitsOfSecret;

        try (FileInputStream inputStream = new FileInputStream(secret)) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ENCODING)) {
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    bitsOfSecret = BitSet.valueOf(reader.lines().collect(Collectors.joining("\n")).getBytes(ENCODING));
                }
            }
        } catch (IOException e) {
            System.err.println("Не удаётся прочитать файл по пути =" + container);
            e.printStackTrace();
            return;
        }

        int bitIndexForContainer = 0;

        String containerTextWithSecret;

        try (FileInputStream inputStream = new FileInputStream(container)) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ENCODING)) {
                try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String containerText = bufferedReader.lines().collect(Collectors.joining("\n"));

                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < containerText.length(); i++) {
                        char original = containerText.charAt(i);
                        if (bitIndexForContainer < bitsOfSecret.size() && canUseInEncryption(original)) {
                            builder.append(encode(original, bitsOfSecret.get(bitIndexForContainer++)));
                        } else {
                            builder.append(original);
                        }
                    }

                    containerTextWithSecret = builder.toString();
                }
            }
        } catch (IOException e) {
            System.err.println("Не удаётся прочитать файл по пути = " + container);
            e.printStackTrace();
            return;
        }
        writeOutput(containerWithSecret, containerTextWithSecret, container);
    }

    private static void initFromArgs(String[] args) {
        container = new File(args[0]);
        secret = new File(args[1]);
        containerWithSecret = new File(args[2]);
    }

    private static void writeOutput(File outputFile, String result, File fileForInject) {
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, ENCODING)) {
                writer.write(result);
            }
        } catch (IOException e) {
            System.err.println("Не удаётся записать в файл по пути = " + fileForInject);
            e.printStackTrace();
        }
    }

    private static boolean checkInput(String[] args) {
        if (args.length < 3) {
            System.err.println("Обязательные аргументы: <container> <secret> <containerWithSecret>");
            return false;
        }

        File fileForInject = new File(args[0]);
        if (!fileForInject.exists()) {
            System.err.println("Не удаётся найти файл контейнер");
            return false;
        }

        File fileWithTextForInject = new File(args[1]);
        if (!fileWithTextForInject.exists()) {
            System.err.println("Не удаётся найти файл с секретом");
            return false;
        }

        File outputFile = new File(args[2]);
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

    private static char encode(char character, boolean bit) {
        if (!canUseInEncryption(character)) {
            throw new IllegalArgumentException("Не удаётся использовать символ '" + character + "' для шифрования");
        }
        return bit ? ENGLISH_CHARACTERS.charAt(RUSSIAN_CHARACTERS.indexOf(character)) : character;
    }
}
