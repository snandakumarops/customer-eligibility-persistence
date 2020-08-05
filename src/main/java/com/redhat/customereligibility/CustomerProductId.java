package com.redhat.customereligibility;

public class CustomerProductId {

    private String customerReference;
    private String productServiceType;

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
}
