package org.softtech.internship.backend.login.util;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
    public static PublicKey loadPublicKeyFromResource(String resourceName) {
        try {
            InputStream inputStream = KeyUtils.class.getResourceAsStream(resourceName);
            assert inputStream != null;
            byte[] keyBytes = inputStream.readAllBytes();
            inputStream.close();

            String publicKeyPEM = new String(keyBytes)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);

            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            System.err.println("Invalid public key");
            return null;
        }
    }

    public static PrivateKey loadPrivateKeyFromResource(String resourceName) {
        try (InputStream inputStream = KeyUtils.class.getResourceAsStream(resourceName)) {
            byte[] keyBytes = inputStream.readAllBytes();
            inputStream.close();

            String privateKeyPEM = new String(keyBytes)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);

            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            // Handle exception
            return null;
        }
    }

}