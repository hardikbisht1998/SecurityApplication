package com.hardik.SpringSecurity.SecurityApplication.utils;

import com.hardik.SpringSecurity.SecurityApplication.entities.enums.Permission;
import com.hardik.SpringSecurity.SecurityApplication.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hardik.SpringSecurity.SecurityApplication.entities.enums.Role.*;

public class PermissionMapping {
    public static final Map<Role, Set<Permission>> map=Map.of(
            USER,Set.of(Permission.USER_VIEW,Permission.POST_VIEW),
            CREATOR,Set.of(Permission.POST_CREATE,Permission.USER_UPDATE,Permission.POST_UPDATE),
            ADMIN,Set.of(Permission.POST_CREATE,Permission.USER_UPDATE,Permission.USER_DELETE,Permission.USER_CREATE,Permission.POST_DELETE)
    );


    public static Set<SimpleGrantedAuthority> getAuthorityForRole(Role role){
        return map.get(role).stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());

    }
}