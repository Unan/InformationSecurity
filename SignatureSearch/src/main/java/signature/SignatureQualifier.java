package signature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SignatureQualifier {

    public static byte[] getSignature(File file, int signatureLength, int offset) throws IOException {
        byte[] signature = new byte[signatureLength];
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        if (fileBytes.length + offset < signature.length + offset) {
            throw new IOException("File '" + file + "' is too short for signature with length '" + signatureLength + "' with offset '" + offset + "'");
        }
        for (int i = offset, j = 0; i < signatureLength + offset; i++, j++) {
            signature[j] = fileBytes[i];
        }
        return signature;
    }
}
