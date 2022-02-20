package rick.einvioice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 載具發票表頭查詢 responose
 */
public class EinvTitleResponse extends EinvResponse implements Serializable {

    private static final long serialVersionUID = -2678616031596088937L;
    @JsonProperty("onlyWinningInv")
    private String onlyWinningInv;
    @JsonProperty("details")
    private List<EinvTitleBean> details;


    public String getOnlyWinningInv() {
        return onlyWinningInv;
    }

    public void setOnlyWinningInv(String onlyWinningInv) {
        this.onlyWinningInv = onlyWinningInv;
    }

    public List<EinvTitleBean> getDetails() {
        return details;
    }

    public void setDetails(List<EinvTitleBean> details) {
        this.details = details;
    }

}
