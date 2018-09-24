package pawelsmolarski95.gmail.com.tablefootball.domain.account;


import pawelsmolarski95.gmail.com.tablefootball.domain.account.domain.Role;

import java.util.Set;

public interface AccountService {
    String login(String username, String password, Set<Role> roleList);
}
