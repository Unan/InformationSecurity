package signature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static signature.SignatureQualifier.getSignature;
import static signature.SignatureSearch.signatureMatchFiles;

public class SignatureApplication {

    private static final String FILES_DIRECTORY = "/Users/ustepanian/Documents/thd/project/InformationSecurity/SignatureSearch/src/main/resources/files";
    private static final String FILE_WITH_SIGNATURE = "/Users/ustepanian/Documents/thd/project/InformationSecurity/SignatureSearch/src/main/resources/signature/signature.txt";
    private static final int SIGNATURE_LENGTH = 16;
    private static final int SIGNATURE_OFFSET = 22;

    public static void main(String[] args) throws IOException {
        byte[] signature = getSignature(new File(FILE_WITH_SIGNATURE), SIGNATURE_LENGTH, SIGNATURE_OFFSET);
        List<File> files = signatureMatchFiles(new File(FILES_DIRECTORY), signature);

        if (files.size() > 0) {
            System.out.println("Signature '" + new String(signature) + "' matched in files:");
            printFiles(files);
        } else {
            System.out.println("No match found for signature '" + Arrays.toString(signature) + "'");
        }
    }

    private static void printFiles(List<File> files) {
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
