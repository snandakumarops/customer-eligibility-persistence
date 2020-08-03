package com.redhat.customereligibility;

import com.redhat.bian.servicedomain.models.CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord;
import com.redhat.bian.servicedomain.models.CRCustomerEligibilityAssessmentUpdateOutputModel;
import org.apache.camel.Exchange;

import java.util.Date;

public class CRCustomerEligibilityCRBean {

    public CustomerEligibilityCRModel generateCRBean(Exchange exchange) {

        String customerReference = (String) exchange.getProperty("customerReference");
        CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord outputModel = (CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord) exchange.getProperty("outputModel");
        Date crDate = (Date) exchange.getProperty("exchangeDate");



        CustomerEligibilityCRModel customerEligibilityCRModel = new CustomerEligibilityCRModel();
        customerEligibilityCRModel.setCustomerReference(customerReference);
        customerEligibilityCRModel.setCustomerProductServiceTypeEligibility(outputModel.getCustomerProductServiceTypeUsage());
        customerEligibilityCRModel.setProductServiceType(outputModel.getCustomerProductServiceTypeUsage());
        customerEligibilityCRModel.setEligibilityDate(crDate);
        return customerEligibilityCRModel;
    }
}
