package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;


import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
interface RoleRepository extends Repository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);

    Optional<List<Role>> findRolesByAccounts(Account account);
}
