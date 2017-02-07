package ooby.contents.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ooby.contents.dao.UserDao;
import ooby.contents.entity.User;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.headers().frameOptions().disable().and()
        	.csrf().disable()
            .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .antMatchers("/hello").hasAuthority("hello")
                .anyRequest().permitAll()
                .and()
            .formLogin()
            	.loginPage("/common/needlogin")
            	.loginProcessingUrl("/login")
            	.successForwardUrl("/common/loginsuccess")
            	.failureForwardUrl("/loginfailure")
                .permitAll()
                .and()
            .logout()
            	.logoutSuccessUrl("/common/logoutsuccess")
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.userDetailsService(userDetailService()).and();
//        	.jdbcAuthentication()
//        		.dataSource(dataSource)
//        		.usersByUsernameQuery("select loginname,password from user where loginname=?");
//            .inMemoryAuthentication()
//                .withUser("user").password("password").authorities("HELLO");
    }
    
    @Bean
    public UserDetailsService userDetailService(){
    	return new MyUserDetailSerivce();
    }
    
    private static class MyUserDetailSerivce implements UserDetailsService{
    	
    	@Autowired
    	private UserDao userDao;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user=userDao.findByLoginName(username);
			User result=new User();
			result.setId(user.getId());
			result.setLoginName(user.getLoginName());
			result.setPassword(user.getPassword());
			result.setNickname(user.getNickname());
			return result;
		}
    	
    }
}
