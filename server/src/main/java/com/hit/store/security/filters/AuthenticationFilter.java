package com.hit.store.security.filters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.hit.store.services.MyUserDetailsService;
import com.hit.store.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// font: https://github.com/pairlearning/expense-tracker-api/blob/master/src/main/java/com/pairlearning/expensetracker/filters/AuthFilter.java
@Component
public class AuthenticationFilter extends GenericFilterBean {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtUtils jwtUtils;

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        
        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if(authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                final String username = jwtUtils.extractUsername(token);
                if(username != null) {
	    			UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
	                try {
	                    if(jwtUtils.validateToken(token, userDetails)) {
	                    	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
	    							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
	    					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);	
	                    }
	                }catch (Exception e) {
	                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
	                    return;
	                }
                }
            } else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
