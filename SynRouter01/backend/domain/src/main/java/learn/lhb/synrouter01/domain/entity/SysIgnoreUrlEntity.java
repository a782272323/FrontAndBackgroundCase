package learn.lhb.synrouter01.domain.entity;

import java.io.Serializable;

/**
 * @Description  sys_ignore_url
 * @author Herbie Leung
 * @date 2020/7/16
 * @time 13:41
 */
public class SysIgnoreUrlEntity implements Serializable {

    private static final long serialVersionUID = -90000035L;
    /**
     * id
     */
    private String id;
    /**
     * 白名单请求路径
     */
    private String url;
    /**
     * 描述
     */
    private String description;

    @Override
    public String toString() {
        return "SysIgnoreUrlEntity{" +
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
