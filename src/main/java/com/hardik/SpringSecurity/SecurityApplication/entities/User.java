package com.hardik.SpringSecurity.SecurityApplication.entities;

import com.hardik.SpringSecurity.SecurityApplication.entities.enums.Permission;
import com.hardik.SpringSecurity.SecurityApplication.entities.enums.Role;
import com.hardik.SpringSecurity.SecurityApplication.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @Override
    public Collection<? extends  GrantedAuthority> getAuthorities(){
      Set<SimpleGrantedAuthority> authorities=new HashSet<>();
      roles.forEach(role -> {
          Set<SimpleGrantedAuthority> permissions= PermissionMapping.getAuthorityForRole(role);
          authorities.addAll(permissions);
      });
      return authorities;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
