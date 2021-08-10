package utils;

import java.util.Base64;

public class Base64EncryptionUtil {

    public static String getBase64EncryptedString(String inputStr) {
        String encodedString = Base64.getEncoder().encodeToString(inputStr.getBytes());
        return encodedString;
    }

    public static String getBase64DecryptedString(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

    public static void main(String args[]) {
        Base64EncryptionUtil base64EncryptionUtil = new Base64EncryptionUtil();
        String inputString = "c8daede3-40ec-41fe-ab4d-1754b6ffa2f3:bmKxYydcZDDcwQJEGgeE4HAxPda5u55U3ni1vlrA9xg=";
        String encryptedString = base64EncryptionUtil.getBase64EncryptedString(inputString);
        System.out.println(encryptedString);
    }
}
