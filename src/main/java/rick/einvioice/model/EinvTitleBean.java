package rick.einvioice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "EINVTITLE",indexes = {
        @Index(name = "einv_title_index1", columnList = "invNum")
})
public class EinvTitleBean {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @JsonProperty("rowNum")
    private Integer rowNum;
    @JsonProperty("invNum")
    @Column(unique = true)
    private String invNum;

    @JsonIgnore
    @OneToMany(mappedBy="invNum", cascade = CascadeType.ALL)
    private List<EinvDetailBean> invDetails;

    @JsonProperty("invDate")
    @Transient
    private InvDate invDate;

    @JsonIgnore
    private Date inv_Date;
    @JsonProperty("sellerName")
    private String sellerName;
    @JsonProperty("invStatus")
    private String invStatus;
    @JsonProperty("invPeriod")
    private String invPeriod;
    @JsonProperty("cardType")
    private String cardType;
    @JsonProperty("cardNo")
    private String cardNo;
    @JsonProperty("invDonatable")
    private Boolean invDonatable;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("donateMark")
    private int donateMark;
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

    protected EinvTitleBean(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getInvNum() {
        return invNum;
    }

    public void setInvNum(String invNum) {
        this.invNum = invNum;
    }

    public InvDate getInvDate() {
        return invDate;
    }

    public void setInvDate(InvDate invDate) {
        this.invDate = invDate;
        setInv_Date(new Date(invDate.getTime()));
    }

    public Date getInv_Date() {
        return inv_Date;
    }

    public void setInv_Date(Date inv_Date) {
        this.inv_Date = inv_Date;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Boolean getInvDonatable() {
        return invDonatable;
    }

    public void setInvDonatable(Boolean invDonatable) {
        this.invDonatable = invDonatable;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getDonateMark() {
        return donateMark;
    }

    public void setDonateMark(int donateMark) {
        this.donateMark = donateMark;
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

    public List<EinvDetailBean> getInvDetails() {
        return invDetails;
    }

    public void setInvDetails(List<EinvDetailBean> invDetails) {
        if(invDetails !=null){
            invDetails.forEach(x->x.setInvNum(this));
        }
        this.invDetails = invDetails;
    }
}
