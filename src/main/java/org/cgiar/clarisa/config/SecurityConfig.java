/*****************************************************************
 * This file is part of CGIAR Level Agricultural Results
 * Interoperable System Architecture (CLARISA).
 * CLARISA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CLARISA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CLARISA. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.clarisa.config;

import org.cgiar.clarisa.exception.UserNotFoundException;
import org.cgiar.clarisa.filters.JwtFilter;
import org.cgiar.clarisa.manager.UserManager;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private UserManager userManager;

  private AppConfig appConfig;

  private final JwtFilter jwtTokenFilter;

  @Inject
  public SecurityConfig(JwtFilter jwtTokenFilter, UserManager userManager, AppConfig appConfig) {
    this.jwtTokenFilter = jwtTokenFilter;
    this.userManager = userManager;
    this.appConfig = appConfig;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(
        username -> this.userManager.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username)))
      .passwordEncoder(this.passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http = http.cors().and().csrf().disable();

    http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
      response.sendError(HttpStatus.UNAUTHORIZED.value());
    }).and();

    http.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated();

    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:4200");
    config.addAllowedOrigin("http://127.0.0.1:4200");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(appConfig.getBcryptRounds());
  }

}