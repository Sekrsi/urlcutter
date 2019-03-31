
package com.urlshortener.ox.Configs;

        import com.urlshortener.ox.Services.OwnUserDetailService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    OwnUserDetailService ownUserDetailService;

    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(ownUserDetailService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());


        return authProvider;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/**").hasRole("USER")
          //      .antMatchers("/user/**").hasRole("USER")
            //    .antMatchers("/user").hasRole("USER")
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
            //    .and()
            //    .csrf().disable()
        ;
    }

}
