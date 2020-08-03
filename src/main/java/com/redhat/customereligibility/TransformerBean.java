package com.redhat.customereligibility;

import com.google.gson.Gson;
import com.redhat.bian.servicedomain.models.*;
import org.apache.camel.Exchange;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class TransformerBean {
    private  KieContainer kieContainer;



    public String validateTxn(String custId, String product) {
        String resultJson=  "NO_DATA";
        try {
            KieServices kieServices = KieServices.Factory.get();
            kieContainer = kieServices.newKieClasspathContainer();
            DMNRuntime dmnRuntime = RuleSessionFactory.createDMNRuntime();
            String namespace = "https://kiegroup.org/dmn/_03A4B62B-BA02-43B4-B776-34B0D7DA117C";
            String modelName = "ProductEligibilityDMN";
            DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);
            DMNContext dmnContext = dmnRuntime.newContext();
            //Customer Data Lookup, Mock data setup for test
            dmnContext.set("KYC Check",true);
            dmnContext.set("Member Since",2018);
            dmnContext.set("Last Transaction Date",LocalDate.now());
            dmnContext.set("Credit Rating", 650);
            dmnContext.set("Residency","RESIDENT");
            dmnContext.set("Customer Status","PLATINUM");
            dmnContext.set("Customer Age",34);
            dmnContext.set("Delinquency History",1);
            dmnContext.set("Product",product);
            DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
            DMNDecisionResult resultOffer = dmnResult.getDecisionResultByName("Product Eligibility");
            boolean resultOfferPayload = (boolean)resultOffer.getResult();
            DMNDecisionResult dueDiligence = dmnResult.getDecisionResultByName("Due Diligence");
            boolean dueDiligencePayload = (boolean)dueDiligence.getResult();
            DMNDecisionResult creditRatingCheck = dmnResult.getDecisionResultByName("Credit Rating Check");
            boolean creditRatingCheckPayload = (boolean)creditRatingCheck.getResult();
            DMNDecisionResult riskCheck = dmnResult.getDecisionResultByName("Risk Checks");
            BigDecimal bigDecimal = (BigDecimal) riskCheck.getResult();
            boolean riskCheckPayload =bigDecimal.compareTo(BigDecimal.valueOf(3)) < 0;




            String resultString = "{\n" +
                    "  \"Due Diligence\":"+dueDiligencePayload+",\n" +
                    "  \"Credit Rating Check\" : "+creditRatingCheckPayload+",\n" +
                    "  \"Risk Check\" : "+riskCheckPayload+",\n" +
                    "  \"Product Eligibility\":"+resultOfferPayload+"\t\n" +
                    "}";

            return resultString;



        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String evaluateEligibility(Exchange exchange){

        String evalString = "";
        System.out.println(exchange.getIn().getBody());
        BianResponse bianResponse = new BianResponse();
        BianRequest bianRequest = new Gson().fromJson(exchange.getIn().getBody().toString(),BianRequest.class);
        Map dataMap = (Map) bianRequest.getData();
        CRCustomerProductDeploymentInstanceRecord crRecord = new Gson().fromJson(dataMap.get("customerEligibilityAssessmentInstanceRecord").toString(),CRCustomerProductDeploymentInstanceRecord.class);
        //Return Customer Eligibility Assessment
        CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord output = new CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord();

        //Call to Rule execution
        String result = validateTxn(crRecord.getCustomerReference(),crRecord.getProductType());
        if(result != null) {
            evalString = result;
        } else {
            evalString = "Not Eligible";
        }



        output.setCustomerProductServiceTypeEligibility(evalString);
        CRCustomerEligibilityAssessmentEvaluateOutputModel crCustomerEligibilityAssessmentEvaluateOutputModel = new CRCustomerEligibilityAssessmentEvaluateOutputModel();
        crCustomerEligibilityAssessmentEvaluateOutputModel.setCustomerEligibilityAssessmentEvaluateActionReference("CEAIR780662");
        crCustomerEligibilityAssessmentEvaluateOutputModel.setCustomerEligibilityAssessmentEvaluateActionRecord(output);
        crCustomerEligibilityAssessmentEvaluateOutputModel.setCustomerEligibilityAssessmentInstanceReference("CEAEAR781025");
        crCustomerEligibilityAssessmentEvaluateOutputModel.setDate(new Date().toString());
        bianResponse.setData(crCustomerEligibilityAssessmentEvaluateOutputModel);
        return new Gson().toJson(bianResponse);
    }

    public String updateProductUsage(Exchange exchange) {
        BianResponse bianResponse = new BianResponse();
        BianRequest bianRequest = new Gson().fromJson(exchange.getIn().getBody().toString(), BianRequest.class);
        Map dataMap = (Map) bianRequest.getData();
        CRCustomerEligibilityAssessmentUpdateInputModelCustomerEligibilityAssessmentInstanceRecord crRecord = new Gson().fromJson(dataMap.get("customerEligibilityAssessmentUpdateActionTaskRecord").toString(), CRCustomerEligibilityAssessmentUpdateInputModelCustomerEligibilityAssessmentInstanceRecord.class);
        //Return Customer Eligibility Assessment
        CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord output = new CRCustomerEligibilityAssessmentEvaluateOutputModelCustomerEligibilityAssessmentInstanceRecord();
        output.setCustomerProductServiceTypeEligibility("Eligible: Customer is in Good Standing");
        CRCustomerEligibilityAssessmentUpdateOutputModel crCustomerEligibilityAssessmentEvaluateOutputModel = new CRCustomerEligibilityAssessmentUpdateOutputModel();
        crCustomerEligibilityAssessmentEvaluateOutputModel.setCustomerEligibilityAssessmentUpdateActionTaskReference("CEAIR780662");
        crCustomerEligibilityAssessmentEvaluateOutputModel.setCustomerEligibilityAssessmentUpdateActionTaskRecord(output);
        crCustomerEligibilityAssessmentEvaluateOutputModel.setUpdateResponseRecord("Successfully added product " + crRecord.getProductServiceType());
        crCustomerEligibilityAssessmentEvaluateOutputModel.setDate(new Date().toString());
        System.out.println("Update Product Usage: " + new Gson().toJson(crCustomerEligibilityAssessmentEvaluateOutputModel));
        bianResponse.setData(crCustomerEligibilityAssessmentEvaluateOutputModel);

        exchange.setProperty("customerReference",crRecord.getCustomerReference());
        exchange.setProperty("exchangeDate",new Date());
        exchange.setProperty("outputModel",output);
        return new Gson().toJson(bianResponse);
    }
    

}
