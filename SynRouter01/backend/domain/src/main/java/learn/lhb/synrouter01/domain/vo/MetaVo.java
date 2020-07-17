package learn.lhb.synrouter01.domain.vo;

import java.io.Serializable;

/**
 * @Description  元数据表
 * 与 SysRouterVo 一起返回路由表给前端
 * @author Herbie Leung
 * @date 2020/7/17
 * @time 11:03
 */
public class MetaVo implements Serializable {

    private static final long serialVersionUID = -90000123L;
    /**
     * 侧边栏标题
     */
    private String title;
    /**
     * 侧边栏菜单样式图标
     */
    private String icon;

    @Override
    public String toString() {
        return "MetaVo{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                '}';
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
}
