package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

class InMemoryAccountRepository implements AccountRepository {
    private final ConcurrentHashMap<Integer, Account> map = new ConcurrentHashMap<>();
    private final AccountCreator accountCreator;

    InMemoryAccountRepository(AccountCreator accountCreator) {
        this.accountCreator = accountCreator;
    }

    @Override
    @NonNull
    public Account save(@NonNull Account account) {
        requireNonNull(account);
        AccountDto accountDto = account.toDto();

        if (accountDto.getId() == null)
            accountDto = accountDto.toBuilder().id(createNewId()).build();

        map.put(accountDto.getId(), account);
        return account;
    }

    private int createNewId() {
        List<Integer> keys = Collections.list(map.keys());
        if (keys.isEmpty())
            return 1;
        else
            return keys.get(keys.size()) + 1;
    }

    @Override
    @Nullable
    public Optional<Account> findById(@NonNull Integer id) {
        requireNonNull(id);
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Account> findByName(String name) {
        return filterBy(a -> a.getName().equals(name));
    }

    private Optional<Account> filterBy(Predicate<? super AccountDto> predicate) {
        return Collections.
                list(map.elements())
                .stream().map(Account::toDto)
                .filter(predicate)
                .findFirst()
                .map(accountCreator::from);
    }
}
