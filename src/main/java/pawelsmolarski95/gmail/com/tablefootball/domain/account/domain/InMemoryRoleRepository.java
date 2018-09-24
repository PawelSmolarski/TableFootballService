package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import java.util.*;

class InMemoryRoleRepository implements RoleRepository {
    @Override
    public Optional<Role> findByName(RoleName roleName) {
       return Optional.of(Role.builder().name(roleName).build());
    }

    @Override
    public Optional<List<Role>> findRolesByAccounts(Account account) {
        return Optional.of(new ArrayList<>(account.toDto().getRoles()));
    }
}