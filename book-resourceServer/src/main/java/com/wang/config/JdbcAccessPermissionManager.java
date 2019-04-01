package com.wang.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.access.AccessPermissionManager;
import org.springframework.security.oauth2.provider.access.Permission;
import org.springframework.security.oauth2.provider.access.Role;
import org.springframework.security.oauth2.provider.access.UserCore;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangxiangyun on 2018/9/13.
 */
public class JdbcAccessPermissionManager implements AccessPermissionManager {

    private final JdbcTemplate jdbcTemplate;
    private ClientDetailsService clientDetailsService;

    public JdbcAccessPermissionManager(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public List<Role> getUserRole(UserCore user) {
        String sql = "SELECT * FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id =?) ";
        List<Role> roleList = jdbcTemplate.query(sql, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Role role = new Role();
                role.setId(id);
                role.setName(name);
                return role;
            }
        }, user.getUserId());
        return roleList;
    }

    @Override
    public List<Permission> getUserAllPermisson(UserCore user) {
        String sql = "  SELECT * FROM permission WHERE id IN " +
                "( SELECT permisson_id FROM role_permisson WHERE  role_id IN" +
                "( SELECT role_id FROM user_role WHERE user_id =?))";
        List<Permission> permissionList = jdbcTemplate.query(sql, new RowMapper<Permission>() {
            @Override
            public Permission mapRow(ResultSet resultSet, int i) throws SQLException {
                String id = resultSet.getString("id");
                String pid = resultSet.getString("pid");
                String name = resultSet.getString("name");
                String path = resultSet.getString("url");
                String method = resultSet.getString("method");
                Permission permission = new Permission();
                permission.setId(id);
                permission.setPid(pid);
                permission.setName(name);
                permission.setPath(path);
                permission.setMethod(method);
                return permission;
            }
        }, user.getUserId());
        return permissionList;
    }

    @Override
    public boolean checkPermission(Authentication httpAuth, Authentication auth2) {
        Object object = httpAuth.getCredentials();
        //客户端登陆
        if (auth2 instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) auth2;
            if (auth2Authentication.getUserAuthentication() == null) {
                Boolean matchFlag = checkClientPermisson((HttpServletRequest) object, auth2Authentication);
                return matchFlag;
            } else {
                return checkUserPermission(auth2, object);
            }
        }

        return false;
    }

    private boolean checkUserPermission(Authentication auth2, Object object) {
        if (object instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) object;
            final String URI = request.getRequestURI();
            final String METHOD = request.getMethod();
            final String ALL_METHOD = "ALL";
            List<Permission> permissionList = this.getUserAllPermisson((UserCore) auth2.getPrincipal());

            if (!permissionList.isEmpty()) {
                PathMatcher matcher = new AntPathMatcher();
                for (Permission permission : permissionList) {
                    String patternURL = permission.getPath();
                    String grandMethod = permission.getMethod();
                    boolean matchFlag = matcher.match(patternURL, URI);
                    if (matchFlag == true && grandMethod.equals(ALL_METHOD)) {
                        return true;
                    }
                    if (matchFlag == true && grandMethod.equals(METHOD)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Boolean checkClientPermisson(HttpServletRequest object, OAuth2Authentication auth2Authentication) {
        ClientDetails client;
        try {
            client = clientDetailsService.loadClientByClientId(auth2Authentication.getOAuth2Request().getClientId());
        } catch (ClientRegistrationException e) {
            throw new OAuth2AccessDeniedException("Invalid token contains invalid client id");
        }

        HttpServletRequest request = object;
        final String URI = request.getRequestURI();
        PathMatcher matcher = new AntPathMatcher();
        for (GrantedAuthority grantedAuthority : client.getAuthorities()) {
            String patternURL = grantedAuthority.getAuthority();
            return matcher.match(patternURL, URI);
        }
        return false;
    }
}
