package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.AccountAuthenticationService;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.AccountService;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.AuthenticationConfiguration;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.Encryptor;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.JwtTokenProvider;

@Configuration
class AccountConfiguration {
    AccountFacade accountFacade() {
        AccountCreator accountCreator = new AccountCreator();
        InMemoryAccountRepository inMemoryAccountRepository = new InMemoryAccountRepository(accountCreator);
        InMemoryRoleRepository inMemoryRoleRepository = new InMemoryRoleRepository();
        Encryptor accountEncryptor = accountEncryptor(passwordEncoder());
        return accountFacade(inMemoryAccountRepository, accountEncryptor, accountValidator(), accountCreator(), (username, password, roleList) -> "TEST_TOKEN", inMemoryRoleRepository);
    }

    @Bean
    AccountFacade accountFacade(AccountRepository accountRepository, Encryptor accountEncryptor, AccountValidator accountValidator, AccountCreator accountCreator, AccountService accountService, RoleRepository roleRepository) {
        return new AccountFacade(accountCreator, accountRepository, accountEncryptor, accountValidator, accountService, roleRepository);
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

    @Bean
    AccountService accountService() {
        try {
            AuthenticationConfiguration authenticationConfiguration = authenticationConfiguration();
            AuthenticationManager authenticationManager = authenticationConfiguration.authenticationManagerBean();
            return new AccountAuthenticationService(jwtTokenProvider(), authenticationManager);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Bean
    JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    @Bean
    AuthenticationConfiguration authenticationConfiguration() {
        return new AuthenticationConfiguration(jwtTokenProvider());
    }
}