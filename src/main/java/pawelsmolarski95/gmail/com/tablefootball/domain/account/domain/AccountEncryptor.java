package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.Encryptor;

import static java.util.Objects.requireNonNull;

class AccountEncryptor implements Encryptor {
    private final PasswordEncoder passwordEncoder;

    AccountEncryptor(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encrypt(@NonNull String toEncrypt) {
        requireNonNull(toEncrypt);
        return passwordEncoder.encode(toEncrypt);
    }

    @Override
    public boolean matches(@NonNull String rawPassword, @NonNull String encodedPassword) {
        requireNonNull(rawPassword);
        requireNonNull(encodedPassword);

        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
