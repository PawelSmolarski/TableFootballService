package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Account {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String password;

    @ManyToMany(cascade =  CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    AccountDto toDto() {
        return AccountDto.builder()
                .id(id)
                .name(name)
                .password(password)
                .roles(roles)
                .build();
    }
}