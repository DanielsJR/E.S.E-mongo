package nx.ESE.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import nx.ESE.dtos.TokenOutputDto;


@Service
public class RestService {
    
    @Autowired
    private Environment environment;

    @Value("${server.servlet.contextPath}")
    private String contextPath;

    @Value("${nx.admin.username}")
    private String adminUsername;

    @Value("${nx.admin.password}")
    private String adminPassword;
    
    @Value("${nx.databaseSeeder.ymlFileNameTest}")
    private String testFile;

    private TokenOutputDto tokenDto;

    private int port() {
        return Integer.parseInt(environment.getProperty("local.server.port"));
    }

    public <T> RestBuilder<T> restBuilder(RestBuilder<T> restBuilder) {
        restBuilder.port(this.port());
        restBuilder.path(contextPath);
        if (tokenDto != null) {
            restBuilder.basicAuth(tokenDto.getToken());
        }
        return restBuilder;
    }

    public RestBuilder<Object> restBuilder() {
        RestBuilder<Object> restBuilder = new RestBuilder<>(this.port());
        restBuilder.path(contextPath);
        if (tokenDto != null) {
            restBuilder.basicAuth(tokenDto.getToken());
        }
        return restBuilder;
    }

    public RestService loginAdmin() {
        this.tokenDto = new RestBuilder<TokenOutputDto>(this.port()).path(contextPath).path(TokenResource.TOKENS)
                .basicAuth(this.adminUsername, this.adminPassword).clazz(TokenOutputDto.class).post().build();
        return this;
    }

    public RestService loginManager() {
        this.tokenDto = new RestBuilder<TokenOutputDto>(this.port()).path(contextPath).path(TokenResource.TOKENS).basicAuth("666666001", "p001")
                .clazz(TokenOutputDto.class).post().build();
        return this;
    }

    public RestService loginTeacher() {
        this.tokenDto = new RestBuilder<TokenOutputDto>(this.port()).path(contextPath).path(TokenResource.TOKENS).basicAuth("666666005", "p005")
                .clazz(TokenOutputDto.class).post().build();
        return this;
    }

    public RestService loginStudent() {
        this.tokenDto = new RestBuilder<TokenOutputDto>(this.port()).path(contextPath).path(TokenResource.TOKENS).basicAuth("666666002", "p002")
                .clazz(TokenOutputDto.class).post().build();
        return this;
    }

    public RestService logout() {
        this.tokenDto = null;
        return this;
    }
    /*
    public void reLoadTestDB() {
        this.loginAdmin().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).delete().build();
        this.loginAdmin().restBuilder().path(AdminResource.ADMINS).path(AdminResource.DB).body(testFile).post().build();        
    }*/

    public TokenOutputDto getTokenDto() {
        return tokenDto;
    }

    public void setTokenDto(TokenOutputDto tokenDto) {
        this.tokenDto = tokenDto;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

}
