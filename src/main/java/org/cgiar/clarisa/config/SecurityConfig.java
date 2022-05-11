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
import org.cgiar.clarisa.filters.ExceptionFilter;
import org.cgiar.clarisa.filters.JwtFilter;
import org.cgiar.clarisa.filters.MultiReadRequestFilter;
import org.cgiar.clarisa.filters.RequestFilter;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.utils.GeneralUtils;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

  private UserManager userManager;

  private AppConfig appConfig;

  private final JwtFilter jwtTokenFilter;

  private final RequestFilter requestFilter;

  private final MultiReadRequestFilter multiReadRequestFilter;

  private final ExceptionFilter exceptionFilter;

  @Inject
  public SecurityConfig(JwtFilter jwtTokenFilter, UserManager userManager, AppConfig appConfig,
    RequestFilter requestFilter, MultiReadRequestFilter multiReadRequestFilter, ExceptionFilter exceptionFilter) {
    this.jwtTokenFilter = jwtTokenFilter;
    this.userManager = userManager;
    this.appConfig = appConfig;
    this.requestFilter = requestFilter;
    this.multiReadRequestFilter = multiReadRequestFilter;
    this.exceptionFilter = exceptionFilter;
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

    http.addFilterBefore(multiReadRequestFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(jwtTokenFilter, MultiReadRequestFilter.class);
    http.addFilterBefore(requestFilter, JwtFilter.class);
    http.addFilterBefore(exceptionFilter, RequestFilter.class);
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);

    String[] allowedOrigins = StringUtils.split(this.appConfig.getAllowedUrlsCrossOrigins(), ',');
    if (GeneralUtils.isNotEmpty(allowedOrigins)) {
      for (String allowed : allowedOrigins) {
        if (StringUtils.isNotBlank(allowed)) {
          config.addAllowedOrigin(allowed);
        }
      }

      if (config.getAllowedOrigins() == null || config.getAllowedOrigins().isEmpty()) {
        LOG.debug("There are no CORS specified");
      }
    } else {
      LOG.debug("There are no CORS specified");
    }

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