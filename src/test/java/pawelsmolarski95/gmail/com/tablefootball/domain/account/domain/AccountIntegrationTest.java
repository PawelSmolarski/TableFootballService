package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.AccountController;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.GlobalControllerExceptionHandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
class AccountIntegrationTest {
    private MockMvc mockMvc;

    //        @Autowired
//        AccountController accountController;
//    @InjectMocks //todo ps moze to
//    todo ps problem bo uzywa bazy produkcyjnej co jest logiczne
    private AccountController accountController = new AccountController(new AccountConfiguration().accountFacade());
    private GlobalControllerExceptionHandler globalControllerExceptionHandler = new GlobalControllerExceptionHandler();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(globalControllerExceptionHandler)
                .build();
    }

    @Test
    @DisplayName("Should throw 400 Bad Request when we try to add account and request body is null")
    void shouldThrowBadRequest() throws Exception {
        mockMvc.perform(post("/account").content("") // todo ps spring sam rzuca 400
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should throw 409 Conflict when we try to add user that names already exists")
    void shouldThrowConflict() throws Exception {
        mockMvc.perform(post("/account")
                .content(SampleAccounts.USER_ADMIN.toJsonWithId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(post("/account")
                .content(SampleAccounts.USER_ADMIN.toJsonWithId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Should return 200 when we try to add account")
    void shouldAddAccount() throws Exception {
        mockMvc.perform(post("/account")
                .content(SampleAccounts.USER_ADMIN.toJsonWithoutId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should throw 404 when we try to login to not existing account")
    void shouldThrowNotFoundWithNotExistingAccount() throws Exception {
        mockMvc.perform(post("/accounts/login").content(SampleAccounts.USER_ADMIN.toJsonWithoutId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 200 when we try to login account with appropriate data")
    void shouldLogInAccount() throws Exception {
        mockMvc.perform(post("/account")
                .content(SampleAccounts.USER_ADMIN.toJsonWithoutId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/accounts/login")
                .content(SampleAccounts.USER_ADMIN.toJsonWithoutId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // todo ps sprawdzanie jsona
    }
}