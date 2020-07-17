package br.com.abinbev.service;

import br.com.abinbev.repository.UserRepository;
import br.com.abinbev.repository.entity.UserEntity;
import br.com.abinbev.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntityFound = userRepository.findByUsername(username);
		if (userEntityFound == null) {
			throw new UsernameNotFoundException(Constants.MESSAGE_USER_NOT_FOUND);
		}
		return new UserRepositoryUserDetails(userEntityFound);
	}

	private final static class UserRepositoryUserDetails extends UserEntity implements UserDetails {

		private static final long serialVersionUID = 1L;

		private UserRepositoryUserDetails(UserEntity user) {
			super(user);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return getRoleEntityList();
		}

		@Override
		public String getUsername() {
			return this.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public String getPassword() {
			return  super.getPassword();
		}

	}

}
