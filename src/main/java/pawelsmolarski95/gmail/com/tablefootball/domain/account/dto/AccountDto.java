package pawelsmolarski95.gmail.com.tablefootball.domain.account.dto;

import lombok.*;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.domain.Role;

import java.util.Set;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class AccountDto {
    private Integer id;

    private String name;

    private String password;

    private Set<Role> roles;
}
