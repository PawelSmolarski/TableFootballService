package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;

class AccountValidator {
    boolean validateAccount(AccountDto accountDto) {
        return accountDto.getName() != null && accountDto.getPassword() != null;
    }
}
