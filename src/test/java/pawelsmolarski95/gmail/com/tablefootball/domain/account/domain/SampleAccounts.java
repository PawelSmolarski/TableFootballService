package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;

enum SampleAccounts {
    USER_ADMIN(createSampleAccountDto(1, "admin", "admin")),
    USER_GUEST(createSampleAccountDto(2, "guest", "guest"));

    private final AccountDto sampleAccountDto;

    SampleAccounts(AccountDto sampleAccountDto) {
        this.sampleAccountDto = sampleAccountDto;
    }

    private static AccountDto createSampleAccountDto(Integer id, String name, String password) {
        return new AccountDto(id, name, password);
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