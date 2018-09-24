package pawelsmolarski95.gmail.com.tablefootball.domain.account;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.domain.Role;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.JwtTokenProvider;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.BadRequestException;

import java.util.Set;

@Service
public class AccountAuthenticationService implements AccountService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AccountAuthenticationService(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public String login(String username, String password, Set<Role> roleList) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, roleList);
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }
    }
}
