package rick.einvioice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class EinvResponse {
    @JsonProperty("v")
    String v;
    @JsonProperty("code")
    String code;
    @JsonProperty("msg")
    String msg;

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
