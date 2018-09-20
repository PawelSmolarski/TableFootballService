package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.lang.NonNull;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.TokenDto;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.Encryptor;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.BadRequestException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.NotFoundException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.ResourceConflictException;

import javax.transaction.Transactional;
import java.util.Optional;

import static pawelsmolarski95.gmail.com.tablefootball.infrastructure.utils.Utils.requireNonNullBadRequest;

@Transactional
public class AccountFacade {
    private final AccountCreator accountCreator;
    private final AccountRepository accountRepository;
    private final Encryptor accountEncryptor;
    private final AccountValidator accountValidator; // todo ps

    public AccountFacade(AccountCreator accountCreator, AccountRepository accountRepository, Encryptor accountEncryptor, AccountValidator accountValidator) {
        this.accountCreator = accountCreator;
        this.accountRepository = accountRepository;
        this.accountEncryptor = accountEncryptor;
        this.accountValidator = accountValidator;
    }

    public AccountDto add(@NonNull AccountDto accountDto) {
        requireNonNullBadRequest(accountDto);
        accountDto = modifyWithEncodedPassword(accountDto);
        Account account = accountCreator.from(accountDto);
        validateIfExistsAccountByName(accountDto);
        account = accountRepository.save(account);

        return account.toDto();
    }

    public AccountDto findOne(@NonNull final AccountDto accountDto) {
        requireNonNullBadRequest(accountDto);
        Optional<Account> account = accountRepository.findById(accountDto.getId());

        return account.orElseThrow(NotFoundException::new).toDto();
    }

    private AccountDto findOneByName(@NonNull final AccountDto accountDto) {
        requireNonNullBadRequest(accountDto);
        Optional<Account> account = accountRepository.findByName(accountDto.getName());

        return account.orElseThrow(() -> new NotFoundException("User not found")).toDto();
    }

    private void validateIfExistsAccountByName(@NonNull final AccountDto accountDto) {
        requireNonNullBadRequest(accountDto);
        Optional<Account> account = accountRepository.findByName(accountDto.getName());

        if (account.isPresent())
            throw new ResourceConflictException("Username already exists");
    }

    public TokenDto login(AccountDto accountDto) {
        requireNonNullBadRequest(accountDto);
        if (accountEncryptor.matches(accountDto.getPassword(), findOneByName(accountDto).getPassword()))
            return TokenDto.builder().token("EXAMPLE TOKEN").accountDto(accountDto).build();
        else
            throw new BadRequestException("Username or password is invalid");
    }

    private AccountDto modifyWithEncodedPassword(AccountDto accountDto) {
        String encodedPassword = accountEncryptor.encrypt(accountDto.getPassword());
        accountDto = accountDto.toBuilder().password(encodedPassword).build();
        return accountDto;
    }
}
