package cn.xuqplus.adminlte.shiro;

import cn.xuqplus.adminlte.domain.user.User;
import cn.xuqplus.adminlte.repository.user.UserRepository;
import cn.xuqplus.adminlte.util.MessageDigestUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.AllPermission;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class AuthorizingRealm extends org.apache.shiro.realm.AuthorizingRealm {
    @Autowired
    UserRepository userRepository;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String nameOrEmail = token.getPrincipal().toString();
        User user = nameOrEmail.contains("@") ? userRepository.getByEmail(nameOrEmail) : userRepository.getByName(nameOrEmail);
        String[] passwordAtSalt0AtSalt1 = user.getPassword().split("@");
        String password = passwordAtSalt0AtSalt1[0];
        String salt1 = passwordAtSalt0AtSalt1[2];
        try {
            String passwordIn = MessageDigestUtil.md5(String.valueOf(((UsernamePasswordToken) token).getPassword()) + salt1);
            if (passwordIn.equals(password)) {
                return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
