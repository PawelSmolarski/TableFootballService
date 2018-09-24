package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.AccountService;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.TokenDto;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.authentication.Encryptor;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.BadRequestException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.NotFoundException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.ResourceConflictException;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static pawelsmolarski95.gmail.com.tablefootball.infrastructure.utils.Utils.requireNonNullBadRequest;

@Transactional
public class AccountFacade {
    private final AccountCreator accountCreator;
    private final AccountRepository accountRepository;
    private final Encryptor accountEncryptor;
    private final AccountValidator accountValidator;
    private final RoleRepository roleRepository;
    private final AccountService accountService;

    public AccountFacade(AccountCreator accountCreator, AccountRepository accountRepository, Encryptor accountEncryptor, AccountValidator accountValidator, AccountService accountService, RoleRepository roleRepository) {
        this.accountCreator = accountCreator;
        this.accountRepository = accountRepository;
        this.accountEncryptor = accountEncryptor;
        this.accountValidator = accountValidator;
        this.accountService = accountService;
        this.roleRepository = roleRepository;
    }

    public AccountDto add(@NonNull AccountDto accountDto) {
        validateAccount(accountDto);
        accountDto = modifyWithEncodedPassword(accountDto);
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new InvalidStateException("User Role not set"));
        Account account = accountCreator.from(accountDto, Collections.singleton(userRole));
        validateIfExistsAccountByName(accountDto);
        account = accountRepository.save(account);

        return account.toDto();
    }

    public AccountDto findOne(@NonNull final AccountDto accountDto) {
        validateAccount(accountDto);
        Optional<Account> account = accountRepository.findById(accountDto.getId());

        return account.orElseThrow(NotFoundException::new).toDto();
    }

    private AccountDto findOneByName(@NonNull final AccountDto accountDto) {
        validateAccount(accountDto);
        Optional<Account> account = accountRepository.findByName(accountDto.getName());

        return account.orElseThrow(() -> new NotFoundException("User not found")).toDto();
    }

    private void validateIfExistsAccountByName(@NonNull final AccountDto accountDto) {
        validateAccount(accountDto);
        Optional<Account> account = accountRepository.findByName(accountDto.getName());

        if (account.isPresent())
            throw new ResourceConflictException("Username already exists");
    }

    public TokenDto login(AccountDto accountDto) {
        validateAccount(accountDto);
        AccountDto foundAccount = findOneByName(accountDto);
        if (accountEncryptor.matches(accountDto.getPassword(), foundAccount.getPassword())) {
            Account account = accountCreator.from(foundAccount);
            Set<Role> roles = new HashSet<>(roleRepository.findRolesByAccounts(account).orElseThrow(NotFoundException::new));
            return TokenDto.builder()
                    .token(accountService.login(accountDto.getName(), accountDto.getPassword(), roles))
                    .accountDto(accountDto).build();
        } else {
            throw new BadRequestException("Username or password is invalid");
        }
    }

    private AccountDto modifyWithEncodedPassword(AccountDto accountDto) {
        String encodedPassword = accountEncryptor.encrypt(accountDto.getPassword());
        accountDto = accountDto.toBuilder().password(encodedPassword).build();
        return accountDto;
    }

    private void validateAccount(AccountDto accountDto) {
        requireNonNullBadRequest(accountDto);
        if (!accountValidator.validateAccount(accountDto))
            throw new BadRequestException("Username or password is invalid");
    }
}
