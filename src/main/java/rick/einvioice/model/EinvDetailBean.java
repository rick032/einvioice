package rick.einvioice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "EINVDETIAL", indexes = {
        @Index(name = "einv_detial_index1", columnList = "title_id")
})
public class EinvDetailBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("rowNum")
    private Integer rowNum;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private EinvTitleBean invNum;

    @JsonProperty("description")
    private String description;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("unitPrice")
    private BigDecimal unitPrice;
    @JsonProperty("amount")
    private BigDecimal amount;

    protected EinvDetailBean() {
    }

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

    public EinvTitleBean getInvNum() {
        return invNum;
    }

    public void setInvNum(EinvTitleBean invNum) {
        this.invNum = invNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
