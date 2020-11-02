package signature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class SignatureSearch {

    public static List<File> signatureMatchFiles(File fileOrDirectory, byte[] signature) throws IOException {
        List<File> matchedFiles = new ArrayList<>();

        for (File file : Objects.requireNonNull(fileOrDirectory.listFiles())) {
            if (file.isDirectory()) {
                matchedFiles.addAll(signatureMatchFiles(file, signature));
            } else {
                if (signatureMatch(file, signature)) {
                    matchedFiles.add(file);
                }
            }
        }
        return matchedFiles;
    }

    private static boolean signatureMatch(File file, byte[] signature) throws IOException {
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        for (int i = 0; i <= fileBytes.length - signature.length; i++) {
            if (fileBytes[i] == signature[0]) {
                if (checkSubArray(fileBytes, signature, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkSubArray(byte[] originalArray, byte[] subArray, int offset) {
        for (int j = offset + 1; j < offset + subArray.length; j++) {
            if (originalArray[j] != subArray[j - offset]) {
                return false;
            }
        }
        return true;
    }
}
