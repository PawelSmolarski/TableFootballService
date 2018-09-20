package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.TokenDto;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.NotFoundException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.ResourceConflictException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountFacadeTest {
    private AccountFacade accountFacade;

    @BeforeEach
    void prepareFacade() {
        accountFacade = new AccountConfiguration().accountFacade();
    }

    @Test
    @DisplayName("Should add one")
    void shouldAddOne() {
        AccountDto sampleAdminAccountDto = SampleAccounts.USER_ADMIN.getSampleAccountDto();
        accountFacade.add(sampleAdminAccountDto);
        assertEquals(sampleAdminAccountDto.getId(), accountFacade.findOne(sampleAdminAccountDto).getId());
    }

    @Test
    @DisplayName("Should throw 404 because resource is not added")
    void shouldNotFoundAccount() {
        assertThrows(NotFoundException.class, () -> accountFacade.findOne(SampleAccounts.USER_ADMIN.getSampleAccountDto()));
    }

    @Test
    @DisplayName("Should throw 409 because resource is already added")
    void shouldThrowConflictAccount() {
        AccountDto sampleAdminAccountDto = SampleAccounts.USER_ADMIN.getSampleAccountDto();
        accountFacade.add(sampleAdminAccountDto);
        assertThrows(ResourceConflictException.class, () -> accountFacade.add(sampleAdminAccountDto));
    }

    @Test
    @DisplayName("Should throw 404 during trying to log in because resource is not added")
    void shouldNotFoundAccountLogIn() {
        assertThrows(NotFoundException.class, () -> accountFacade.login(SampleAccounts.USER_ADMIN.getSampleAccountDto()));
    }

    @Test
    @DisplayName("Should log in")
    void shouldLogIn() {
        AccountDto sampleAdminAccountDto = SampleAccounts.USER_ADMIN.getSampleAccountDto();
        accountFacade.add(sampleAdminAccountDto);
        TokenDto tokenDto = accountFacade.login(sampleAdminAccountDto);
        assertNotNull(tokenDto);
        assertEquals(tokenDto.getAccountDto().getId(), sampleAdminAccountDto.getId());
    }
}