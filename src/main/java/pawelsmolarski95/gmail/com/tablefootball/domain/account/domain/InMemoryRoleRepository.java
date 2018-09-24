package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import java.util.List;
import java.util.Optional;

class InMemoryRoleRepository implements RoleRepository {
    @Override
    public Optional<Role> findByName(RoleName roleName) {
       return Optional.of(Role.builder().name(roleName).build());
    }

    @Override
    public Optional<List<Role>> findRolesByAccounts(Account account) {
        return Optional.empty(); //todo ps
    }
}
