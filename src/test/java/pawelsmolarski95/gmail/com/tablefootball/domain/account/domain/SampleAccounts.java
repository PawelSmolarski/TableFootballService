package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;

import java.util.HashSet;
import java.util.Set;

enum SampleAccounts {
    USER_NORMAL(createSampleAccountDto(1, "admin", "admin")),
    USER_GUEST(createSampleAccountDto(2, "guest", "guest")),
    USER_INVALID_ADMIN(createSampleAccountDto(3, "admin", "invalid")),
    USER_NULL_NAME(createSampleAccountDto(4, null, "test")),
    USER_NULL_PASSWORD(createSampleAccountDto(4, "test", null));

    private final AccountDto sampleAccountDto;

    SampleAccounts(AccountDto sampleAccountDto) {
        this.sampleAccountDto = sampleAccountDto;
    }

    private static AccountDto createSampleAccountDto(Integer id, String name, String password) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().name(RoleName.ROLE_USER).build());
        return new AccountDto(id, name, password, roles);
    }

    public AccountDto getSampleAccountDto() {
        return sampleAccountDto;
    }

    public String toJsonWithoutId() {
        return "{" + "\"name\": " + "\"" + getSampleAccountDto().getName() + "\"" + "," + "\"password\": " + "\"" + getSampleAccountDto().getPassword() + "\"" + "}";
    }

    public String toJsonWithId() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getSampleAccountDto());
    }
}