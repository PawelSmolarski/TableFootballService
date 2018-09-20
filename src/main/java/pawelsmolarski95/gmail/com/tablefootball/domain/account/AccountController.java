package pawelsmolarski95.gmail.com.tablefootball.domain.account;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.domain.AccountFacade;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.TokenDto;

@RestController
public class AccountController {
    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping(path = "/account", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    AccountDto add(@RequestBody AccountDto accountDto) {
        return accountFacade.add(accountDto);
    }

    @PostMapping(path = "/accounts/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    TokenDto login(@RequestBody AccountDto accountDto) {
        return accountFacade.login(accountDto);
    }

}