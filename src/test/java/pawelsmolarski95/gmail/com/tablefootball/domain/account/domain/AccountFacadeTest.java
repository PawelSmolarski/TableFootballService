package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.AccountDto;
import pawelsmolarski95.gmail.com.tablefootball.domain.account.dto.TokenDto;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.BadRequestException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.NotFoundException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.ResourceConflictException;

import java.util.Set;

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
    @DisplayName("Should add one and role should be user")
    void shouldAddOne() {
        AccountDto sampleAdminAccountDto = SampleAccounts.USER_NORMAL.getSampleAccountDto();
        accountFacade.add(sampleAdminAccountDto);
        assertEquals(sampleAdminAccountDto.getId(), accountFacade.findOne(sampleAdminAccountDto).getId());
        Set<Role> expectedRoles = sampleAdminAccountDto.getRoles();
        Set<Role> actualRoles = accountFacade.findOne(sampleAdminAccountDto).getRoles();
        String expectedRole = expectedRoles.stream().findFirst().orElseThrow(IllegalStateException::new).getName().name();
        String actualRole = actualRoles.stream().findFirst().orElseThrow(IllegalStateException::new).getName().name();
        assertEquals(expectedRole, actualRole);
        assertEquals(expectedRoles.size(), actualRoles.size());
    }

    @Test
    @DisplayName("Should throw NotFoundException because resource is not added")
    void shouldNotFoundAccount() {
        assertThrows(NotFoundException.class, () -> accountFacade.findOne(SampleAccounts.USER_NORMAL.getSampleAccountDto()));
    }

    @Test
    @DisplayName("Should throw ResourceConflictException because resource is already added")
    void shouldThrowConflictAccount() {
        AccountDto sampleAdminAccountDto = SampleAccounts.USER_NORMAL.getSampleAccountDto();
        accountFacade.add(sampleAdminAccountDto);
        assertThrows(ResourceConflictException.class, () -> accountFacade.add(sampleAdminAccountDto));
    }

    @Test
    @DisplayName("Should throw NotFoundException during trying to log in because resource is not added")
    void shouldNotFoundAccountLogIn() {
        assertThrows(NotFoundException.class, () -> accountFacade.login(SampleAccounts.USER_NORMAL.getSampleAccountDto()));
    }

    @Test
    @DisplayName("Should log in")
    void shouldLogIn() {
        AccountDto sampleAdminAccountDto = SampleAccounts.USER_NORMAL.getSampleAccountDto();
        accountFacade.add(sampleAdminAccountDto);
        TokenDto tokenDto = accountFacade.login(sampleAdminAccountDto);
        assertNotNull(tokenDto);
        assertEquals(tokenDto.getAccountDto().getId(), sampleAdminAccountDto.getId());
    }

    @Test
    @DisplayName("Should throw BadRequestException when we try to log in with invalid data")
    void shouldNotLogIn() {
        AccountDto sampleAdminAccountDto = SampleAccounts.USER_NORMAL.getSampleAccountDto();
        accountFacade.add(sampleAdminAccountDto);
        assertThrows(BadRequestException.class, () -> accountFacade.login(SampleAccounts.USER_INVALID_ADMIN.getSampleAccountDto()));
    }

    @Test
    @DisplayName("Should throw BadRequestException when we try to log in with null username")
    void shouldNotLogInWhenUsernameIsNull() {
        assertThrows(BadRequestException.class, () -> accountFacade.login(SampleAccounts.USER_NULL_NAME.getSampleAccountDto()));
    }

    @Test
    @DisplayName("Should throw BadRequestException when we try to log in with null username")
    void shouldNotLogInWhenPasswordIsNull() {
        assertThrows(BadRequestException.class, () -> accountFacade.login(SampleAccounts.USER_NULL_PASSWORD.getSampleAccountDto()));
    }
}
