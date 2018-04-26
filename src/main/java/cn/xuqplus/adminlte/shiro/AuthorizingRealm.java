package cn.xuqplus.adminlte.shiro;

import cn.xuqplus.adminlte.domain.User;
import cn.xuqplus.adminlte.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.AllPermission;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AuthorizingRealm extends org.apache.shiro.realm.AuthorizingRealm {
    @Autowired
    UserRepository userRepository;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        User user = userRepository.getByName(token.getPrincipal().toString());
        return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        /**
         * 角色
         */
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        //authorizationInfo.setRoles(roles);
        /**
         * 对象权限
         */
        Set<Permission> permissions = new HashSet<>();
        permissions.add(new AllPermission());
        //authorizationInfo.setObjectPermissions(permissions);

        /**
         * 字符权限
         */
        Set<String> strings = new HashSet<>();
        strings.add("admin:read");
        strings.add("admin:create");
        authorizationInfo.setStringPermissions(strings);
        return authorizationInfo;
    }
}
