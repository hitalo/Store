package com.hit.store.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hit.store.models.people.Role;
import com.hit.store.models.people.User;

public class SecurityUser implements UserDetails {
    
	private static final long serialVersionUID = 1L;

	String ROLE_PREFIX = "ROLE_";

    
    private User user;
    

    public SecurityUser(User user){
        this.user = user;
    }

    
	public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<Role> roles = user.getRoles();
    	List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    	for(Role role : roles) authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()));
    	return authorities;
    }

	public String getPassword() {
		return this.user.getPassword();
	}

	public String getUsername() {
		return this.user.getLogin();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}