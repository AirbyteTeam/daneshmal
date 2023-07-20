package com.airbyte.daneshmal.security.permission;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public enum Role {
    MANAGER(Sets.newHashSet(Permission.COMPANY_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> grantedAuthorities = this.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new SimpleGrantedAuthority("Role: " + this.name()));
        return grantedAuthorities;
    }
}
