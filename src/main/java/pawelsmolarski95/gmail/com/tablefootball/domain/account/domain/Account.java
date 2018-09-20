package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;

import javax.persistence.*;

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

    AccountDto toDto() {
        return AccountDto.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();
    }
}