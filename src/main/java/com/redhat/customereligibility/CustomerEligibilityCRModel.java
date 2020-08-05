package com.redhat.customereligibility;

import javax.persistence.EmbeddedId;
import java.util.Date;

public class CustomerEligibilityCRModel {

    private int id;
    @EmbeddedId
    private CustomerProductId customerProductId;
    private Date eligibilityDate;
    private String customerProductServiceTypeEligibility;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerProductId getCustomerProductId() {
        return customerProductId;
    }

    public void setCustomerProductId(CustomerProductId customerProductId) {
        this.customerProductId = customerProductId;
    }

    public Date getEligibilityDate() {
        return eligibilityDate;
    }

    public void setEligibilityDate(Date eligibilityDate) {
        this.eligibilityDate = eligibilityDate;
    }

    public String getCustomerProductServiceTypeEligibility() {
        return customerProductServiceTypeEligibility;
    }

    public void setCustomerProductServiceTypeEligibility(String customerProductServiceTypeEligibility) {
        this.customerProductServiceTypeEligibility = customerProductServiceTypeEligibility;
    }
}
