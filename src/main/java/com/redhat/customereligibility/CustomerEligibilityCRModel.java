package com.redhat.customereligibility;

import java.util.Date;

public class CustomerEligibilityCRModel {

    private int id;

    private String customerReference;
    private String productServiceType;
    private Date eligibilityDate;
    private String customerProductServiceTypeEligibility;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getProductServiceType() {
        return productServiceType;
    }

    public void setProductServiceType(String productServiceType) {
        this.productServiceType = productServiceType;
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
