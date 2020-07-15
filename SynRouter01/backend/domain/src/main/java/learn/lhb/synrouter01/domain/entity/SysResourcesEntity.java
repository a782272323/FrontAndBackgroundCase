package learn.lhb.synrouter01.domain.entity;

import java.io.Serializable;

/**
 * @Description  资源表 sys_resources 实体类
 * @author Herbie Leung
 * @date 2020/7/15
 * @time 16:28
 */
public class SysResourcesEntity implements Serializable {
    private static final long serialVersionUID = -93537864L;
    /**
     * id
     */
    private String id;
    /**
     * 接口请求的地址
     */
    private String url;
    /**
     * 描述
     */
    private String description;

    @Override
    public String toString() {
        return "SysResourcesEntity{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
