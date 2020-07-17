package learn.lhb.synrouter01.domain.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户实体,实现UserDetails，UserDetails
 * 主要实现UserDetails的几个方法，如获取用户名，密码，获取用户冻结状态等
 *
 * @author 梁鸿斌
 * @date 2020/3/25.
 * @time 09:59
 */
public class JwtUser implements UserDetails {
    private String username;
    private String password;
//    private List<GrantedAuthority> authorities;
    private List<SimpleGrantedAuthority> authorities;


    public JwtUser() {
    }

    public JwtUser(String username, String password, String... roles) {
        this.username = username;
        this.password = password;
        this.authorities = Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
//    public JwtUser(String username, String password, List<GrantedAuthority> authorities) {
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setAuthorities(List<GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }
    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}