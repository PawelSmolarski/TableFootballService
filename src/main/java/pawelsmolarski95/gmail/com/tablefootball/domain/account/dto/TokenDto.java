package pawelsmolarski95.gmail.com.tablefootball.domain.account.dto;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class TokenDto {
    private final String token;

    private final AccountDto accountDto;
}
