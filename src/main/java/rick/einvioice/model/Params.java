package rick.einvioice.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PARAMS")
public class Params {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String p_k;
    private String p_v;
    private String p_d;
    private Timestamp createTime;
    protected Params() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getP_k() {
        return p_k;
    }

    public void setP_k(String p_k) {
        this.p_k = p_k;
    }

    public String getP_v() {
        return p_v;
    }

    public void setP_v(String p_v) {
        this.p_v = p_v;
    }

    public String getP_d() {
        return p_d;
    }

    public void setP_d(String p_d) {
        this.p_d = p_d;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
