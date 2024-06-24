package fr.valentinthuillier.urgverif;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Password hashing and verification
 * @author Valentin THUILLIER
 * @version 1.0
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 * @see org.springframework.security.crypto.password.PasswordEncoder
 */
public class Password {
    
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private Password() {
        // Prevent instantiation
    }

    /**
     * Hash a password
     * @param password  (String)    -   The password to hash
     * @return        (String)    -   The hashed password
     */
    public static String hash(String password) {
        return encoder.encode(password);
    }

    /**
     * Verify a password
     * @param password  (String)    -   The password to verify
     * @param hash    (String)    -   The hash to compare
     * @return      (boolean)    -   True if the password is correct, false otherwise
     */
    public static boolean verify(String password, String hash) {
        return encoder.matches(password, hash);
    }

}
