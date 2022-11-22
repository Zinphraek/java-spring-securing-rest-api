package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepositoryJwtAuthenticationConverter authenticationConverter;

    public static void main(String[] args) {
        SpringApplication.run(ResolutionsApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .httpBasic(basic -> {
                })
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::opaqueToken)
                .cors(cors -> {
                });
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository users) {
        return new UserRepositoryUserDetailsService(users);
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .maxAge(0) // if using local verification
                        .allowedOrigins("http://localhost:4000")
                        .allowedMethods("HEAD")
                        .allowedHeaders("Authorization");
            }
        };
    }

    @Bean
    public OpaqueTokenIntrospector introspector(UserRepository userRepository, OAuth2ResourceServerProperties properties) {
        OpaqueTokenIntrospector introspect = new NimbusOpaqueTokenIntrospector(
                properties.getOpaquetoken().getIntrospectionUri(),
                properties.getOpaquetoken().getClientId(),
                properties.getOpaquetoken().getClientSecret());
        return new UserRepositoryOpaqueTokenIntrospector(introspect, userRepository);
    }

}
