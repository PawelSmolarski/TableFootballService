package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.NotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountDetails implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountDetails(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Account> user = accountRepository.findByName(username);

        AccountDto account = user.orElseThrow(NotFoundException::new).toDto();
        Set<Role> roles = account.getRoles();
        Set<SimpleGrantedAuthority> grantedAuthorities = roles.stream().map(it -> new SimpleGrantedAuthority(it.getName().name())).collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(account.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}