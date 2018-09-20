package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository //todo ps co z tym
interface AccountRepository extends Repository<Account, Integer> {
    Account save(Account account);

    Optional<Account> findById(Integer id);

    Optional<Account> findByName(String name);
}
