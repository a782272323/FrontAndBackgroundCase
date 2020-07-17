package learn.lhb.synrouter01.domain.vo;

import java.io.Serializable;

/**
 * @Description  vue 需要的 登录 info 接口参数
 * @author Herbie Leung
 * @date 2020/6/19
 * @time 01:19
 */
public class LoginInfoVo implements Serializable {


    private static final long serialVersionUID = -90000027L;

    private String name;
    private String avatar;
    private String roles;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
