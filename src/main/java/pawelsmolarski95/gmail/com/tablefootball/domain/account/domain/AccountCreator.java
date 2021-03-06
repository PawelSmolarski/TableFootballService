package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.lang.NonNull;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;

import java.util.Objects;
import java.util.Set;

class AccountCreator {
    @NonNull
    Account from(@NonNull final AccountDto accountDto) {
        Objects.requireNonNull(accountDto);

        return Account.builder()
                .id(accountDto.getId())
                .name(accountDto.getName())
                .password(accountDto.getPassword())
                .build();
    }

    @NonNull
    Account from(@NonNull final AccountDto accountDto, Set<Role> roles) {
        Objects.requireNonNull(accountDto);

        return Account.builder()
                .id(accountDto.getId())
                .name(accountDto.getName())
                .password(accountDto.getPassword())
                .roles(roles)
                .build();
    }
}
