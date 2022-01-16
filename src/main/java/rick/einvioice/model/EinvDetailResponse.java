package rick.einvioice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 載具發票表頭查詢 responose
 */
public class EinvDetailResponse extends EinvResponse implements Serializable {

    private static final long serialVersionUID = -2678616031596088937L;
    @JsonProperty("invNum")
    private String invNum;
    @JsonProperty("invDate")
    private String invDate;
    @JsonProperty("sellerName")
    private String sellerName;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("invStatus")
    private String invStatus;
    @JsonProperty("invPeriod")
    private String invPeriod;
    @JsonProperty("sellerBan")
    private String sellerBan;
    @JsonProperty("sellerAddress")
    private String sellerAddress;
    @JsonProperty("invoiceTime")
    private String invoiceTime;
    @JsonProperty("buyerBan")
    private String buyerBan;
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("details")
    private List<EinvDetailBean> details;

    public String getInvNum() {
        return invNum;
    }

    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getInvPeriod() {
        return invPeriod;
    }

    public void setInvPeriod(String invPeriod) {
        this.invPeriod = invPeriod;
    }

    public String getSellerBan() {
        return sellerBan;
    }

    public void setSellerBan(String sellerBan) {
        this.sellerBan = sellerBan;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getBuyerBan() {
        return buyerBan;
    }

    public void setBuyerBan(String buyerBan) {
        this.buyerBan = buyerBan;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<EinvDetailBean> getDetails() {
        return details;
    }

    public void setDetails(List<EinvDetailBean> details) {
        this.details = details;
    }
}
