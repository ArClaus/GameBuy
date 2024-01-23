package solarev.demo.dto;

import solarev.demo.entity.User;
import lombok.Data;

@Data
public class RegisterFormDTO {

    private String username;
    private String password;

    public User toEntity() {
        var user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        user.setAuthority("USER");
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        return user;
    }

}
