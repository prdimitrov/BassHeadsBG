package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.details.BassHeadsUserDetails;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * This class implements UserDetailsService to provide user details for authentication.
 * It interacts with the UserRepository to fetch user data from the database.
 */
public class BassHeadsDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BassHeadsDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method loads a user's details by their username.
     *
     * @param username the username of the user to load
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(BassHeadsDetailsService::mapUser)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with username " + username + " not found!"));
    }

    /**
     * The method is used to map a UserEntity to a UserDetails object.
     *
     * @param userEntity the user entity to map
     * @return UserDetails object, that contains user information
     */
    private static UserDetails mapUser(UserEntity userEntity) {
        return new BassHeadsUserDetails(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(UserRole::getRole).map(BassHeadsDetailsService::map).toList(),
                userEntity.isEnabled()
        );
    }

    private static GrantedAuthority map(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}