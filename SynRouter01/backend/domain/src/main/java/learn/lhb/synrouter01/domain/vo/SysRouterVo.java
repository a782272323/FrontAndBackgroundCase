package learn.lhb.synrouter01.domain.vo;

import learn.lhb.synrouter01.domain.entity.SysRoleEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description 路由表
 * 与 MetaVo 一起构建路由表返回给前端
 * @author Herbie Leung
 * @date 2020/7/17
 * @time 10:59
 */
public class SysRouterVo implements Serializable {


    private static final long serialVersionUID = -90000077L;
    /**
     * id
     */
    private Long id;
    /**
     * vue路由中对应的path
     */
    private String path;
    /**
     * 面包屑重定向的路径，若设置为noreirect则不可以重定向
     */
    private String  redirect;
    /**
     * vue组件component中的路由路径
     */
    private String component;
    /**
     * 侧边栏的菜单名字
     */
    private String name;
    /**
     * 如果设置为真，项目将不会显示在侧栏(默认为假),如401，login等页面，或者如一些编辑页面/edit/1
     */
    private String hidden;
    /**
     * 为真时，显示子路由，为假时，隐藏子路由.
     */
    private String alwaysShow;
    /**
     * 侧边栏元数据
     */
    private MetaVo meta;
    /**
     * 父级路由ID
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
    /**
     * 对于的角色
     */
    private List<SysRoleEntity> roles;
    /**
     * 递归查询子路由
     */
    private List<SysRouterVo> children;

    @Override
    public String toString() {
        return "SysRouterVo{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", redirect='" + redirect + '\'' +
                ", component='" + component + '\'' +
                ", name='" + name + '\'' +
                ", hidden='" + hidden + '\'' +
                ", alwaysShow='" + alwaysShow + '\'' +
                ", meta=" + meta +
                ", parentId=" + parentId +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", roles=" + roles +
                ", children=" + children +
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

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(String alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public MetaVo getMeta() {
        return meta;
    }

    public void setMeta(MetaVo meta) {
        this.meta = meta;
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

    public List<SysRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleEntity> roles) {
        this.roles = roles;
    }

    public List<SysRouterVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysRouterVo> children) {
        this.children = children;
    }
}
