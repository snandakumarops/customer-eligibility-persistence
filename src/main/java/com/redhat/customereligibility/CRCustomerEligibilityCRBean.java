package com.redhat.customereligibility;

import com.redhat.bian.servicedomain.models.CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord;
import com.redhat.bian.servicedomain.models.CRCustomerEligibilityAssessmentUpdateOutputModel;
import org.apache.camel.Exchange;

import java.util.Date;

public class CRCustomerEligibilityCRBean {

    public CustomerEligibilityCRModel setCustomerEligibilityCRModel(Exchange exchange) {

        String customerReference = (String) exchange.getProperty("customerReference");
        Date crDate = (Date) exchange.getProperty("exchangeDate");
        String productType = (String) exchange.getProperty("productUsage");
        String eligibility = (String) exchange.getProperty("eligibility");
        String crNo = (String) exchange.getProperty("crNumber");



        CustomerEligibilityCRModel customerEligibilityCRModel = new CustomerEligibilityCRModel();
        customerEligibilityCRModel.setCustomerReference(customerReference);
        customerEligibilityCRModel.setCustomerProductServiceTypeEligibility(eligibility);
        customerEligibilityCRModel.setProductServiceType(productType);
        customerEligibilityCRModel.setEligibilityDate(crDate);
        customerEligibilityCRModel.setCrNumber(crNo);
        System.out.println(customerEligibilityCRModel.getCustomerProductServiceTypeEligibility()+customerEligibilityCRModel.getCustomerReference());
        return customerEligibilityCRModel;
    }

    public CustomerEligibilityCRModel getCustomerEligibilityCRModel(Exchange exchange) {
        return null;
    }
}
