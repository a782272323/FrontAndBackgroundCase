package learn.lhb.synrouter01.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  路由表 sys_router 实体类
 * @author Herbie Leung
 * @date 2020/7/15
 * @time 16:29
 */
public class SysRouterEntity implements Serializable {


    private static final long serialVersionUID = -90007627L;
    /**
     * id
     */
    private Long id;
    /**
     * vue路由中对应的path
     */
    private String path;
    /**
     *
     */
    private String  redirect;
    /**
     *
     */
    private String component;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String icon;
    /**
     *
     */
    private Long parentId;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date created;

    @Override
    public String toString() {
        return "SysRouterEntity{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", redirect='" + redirect + '\'' +
                ", component='" + component + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", parentId=" + parentId +
                ", description='" + description + '\'' +
                ", created=" + created +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
