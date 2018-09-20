package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.Encryptor;

@Configuration
class AccountConfiguration {

    AccountFacade accountFacade() {
        AccountCreator accountCreator = new AccountCreator();
        InMemoryAccountRepository inMemoryAccountRepository = new InMemoryAccountRepository(accountCreator);
        Encryptor accountEncryptor = accountEncryptor(passwordEncoder());
        return accountFacade(inMemoryAccountRepository, accountEncryptor, accountValidator(), accountCreator());
    }

    @Bean
    AccountFacade accountFacade(AccountRepository accountRepository, Encryptor accountEncryptor, AccountValidator accountValidator, AccountCreator accountCreator) {
        return new AccountFacade(accountCreator, accountRepository, accountEncryptor, accountValidator);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    Encryptor accountEncryptor(PasswordEncoder passwordEncoder) {
        return new AccountEncryptor(passwordEncoder);
    }

    @Bean
    AccountValidator accountValidator() {
        return new AccountValidator();
    }

    @Bean
    AccountCreator accountCreator() {
        return new AccountCreator();
    }
}
