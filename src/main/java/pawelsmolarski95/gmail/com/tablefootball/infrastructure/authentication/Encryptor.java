package pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication;

public interface Encryptor {
    String encrypt(String toEncrypt);

    boolean matches(String rawPassword, String encodedPassword);
}
