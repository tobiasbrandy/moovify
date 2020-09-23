package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@ComponentScan({ "ar.edu.itba.paw.webapp.auth", })
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Value("classpath:rememberMe.key")
    private Resource rememberMeKeyResource;

    @Autowired
    private UserDetailsService userDetails;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }

    // Spring Security Unresolved Issues
    // TODO: Make a redirect, change browser url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .sessionManagement()
                    //.invalidSessionUrl("/")

                .and().authorizeRequests()

                    // Home Controller
                        // "/"

                    // User Controller
                        // "/user/{userId:[\d]+}
                    .antMatchers("/login", "/user/create").anonymous()
                    .antMatchers("/user/profile").authenticated()
                    .antMatchers(
                            "/user/registrationConfirm",
                            "/user/resendConfirmation").hasRole("NOT_VALIDATED")
                    .antMatchers(
                            "/user/resetPassword",
                            "/user/updatePassword/token",
                            "/user/updatePassword").anonymous()

                    // Post Controller
                        // "/post/{postId}"
                    .antMatchers("/post/create").hasRole("USER")

                    // Movie Controller
                        // "/movies/{movieId}
                    .antMatchers("/movie/create", "/movie/register").hasRole("ADMIN")

                    // Comment Controller
                    .antMatchers("/comment/create").hasRole("USER")
                    .antMatchers("/comment/{commentId:[\\d]+}").hasRole("ADMIN")

                    // Search Controller
                        // "/search/posts"
                        // "/search/movies"
                        // "/search/users"

                    // Default
                    .antMatchers("/**").permitAll()

                .and().formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false)

                .and().rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                    .key(FileCopyUtils.copyToString(new InputStreamReader(rememberMeKeyResource.getInputStream())))

                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")

                .and().exceptionHandling()
                    .accessDeniedPage("/")

                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**", "/favicon.ico");
    }

}
