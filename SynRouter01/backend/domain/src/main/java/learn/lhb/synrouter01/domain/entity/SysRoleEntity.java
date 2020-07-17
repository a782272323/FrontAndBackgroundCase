package learn.lhb.synrouter01.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  角色表 sys_role 实体类
 * @author Herbie Leung
 * @date 2020/7/15
 * @time 16:19
 */
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = -90000009L;
    /**
     * id
     */
    private Long id;
    /**
     * 角色英文名
     */
    private String name;
    /**
     * 角色中文名
     */
    private String nameZh;
    /**
     * 状态
     * 0: 禁用
     * 1: 启用
     */
    private String enabled;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;

    @Override
    public String toString() {
        return "SysRoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameZh='" + nameZh + '\'' +
                ", enabled='" + enabled + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
