package seguridad;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Base64;

public class Cifrador {
    private static SecretKey key;
    
    static {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256);
            key = kg.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error inicializar AES", e);
        }
    }
    
    public static String cifrar(String t) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(c.doFinal(t.getBytes()));
        } catch (Exception e) { throw new RuntimeException(e); }
    }
    
    public static String descifrar(String t) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(t)));
        } catch (Exception e) { throw new RuntimeException(e); }
    }
    
    public static String hash(String t) {
        try {
            MessageDigest d = MessageDigest.getInstance("SHA-256");
            byte[] h = d.digest(t.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : h) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}