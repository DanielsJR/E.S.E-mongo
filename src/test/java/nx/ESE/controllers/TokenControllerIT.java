package nx.ESE.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import nx.ESE.documents.Role;
import nx.ESE.dtos.UserDto;
import nx.ESE.services.HttpMatcher;
import nx.ESE.services.RestBuilder;
import nx.ESE.services.RestService;

@ApiTestConfig
public class TokenControllerIT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    private UserDto getUserByUsername(String username) {
        UserDto userDto = restService.restBuilder(new RestBuilder<UserDto>()).clazz(UserDto.class)
                .path(UserController.USERS).path(UserController.PATH_USERNAME).expand(username)
                .bearerAuth(restService.getAuthToken().getToken()).get().log().build();
        return userDto;

    }

    @Test
    public void testLoginAdmin() {
        restService.loginAdmin();
        assertEquals(Role.ADMIN, this.getUserByUsername(restService.getAdminUsername()).getRoles()[0]);
    }

    @Test
    public void testLoginAdminUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.loginUser(restService.getAdminUsername(), "incorrectPass");

    }

    @Test
    public void testLoginUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        restService.loginUser("incorrectUser", "incorrectPass");
    }

    @Test
    public void testLoginNoUserAndPass() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        restService.restBuilder().path(AuthenticationController.TOKEN).path(AuthenticationController.GENERATE_TOKEN)
                .post().build();
    }


}