package com.redhat.management.approval;

import java.io.*;
import java.util.HashMap;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.lang3.text.StrSubstitutor;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class ApproveEmailBody implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private com.redhat.management.approval.Request request;
	private com.redhat.management.approval.Approver approver;
	private String templateFile = "EmailTemplate.html";

	private com.redhat.management.approval.Group group;

	private java.util.ArrayList<com.redhat.management.approval.Stage> stages;

	public java.lang.String getEmailTemplate() {
	    URL url = ApproveEmailBody.class.getResource(templateFile);

 		java.lang.String content = "";
		try {
			content = getUrlContent(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public String getEmailBody() {
		String template = getEmailTemplate();
		HashMap<String, String> values = getRequestParameters();

		StrSubstitutor sub = new StrSubstitutor(values);
		return sub.replace(template);
	}
	
	public static String getUrlContent(URL url) throws Exception {
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder content = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            content.append(inputLine);

        in.close();

        return content.toString();
	    
	}

	public HashMap<String, String> getRequestParameters() {
	    Stage currentStage = EmailDispatcher.getCurrentStage(group, stages);
		HashMap<String, Object> request_content = request.getContent();
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("approver_name",
				approver.getFirstName() + " " + approver.getLastName());
		values.put("requester_name", (String) request.getIdentityFullName());
	    values.put("orderer_email", request.getIdentityEmail());
		values.put("product_name", (String) request_content.get("product"));
		values.put("portfolio_name", (String) request_content.get("portfolio"));
		values.put("platform_name", (String) request_content.get("platform"));
		values.put("order_id", (String) request_content.get("order_id"));
		
		values.put("order_link", request.getOriginalUrl());
		// TODO: get valid links here
		String webUrl = System.getenv("APPROVAL_WEB_URL");
		String approveLink = webUrl + currentStage.getRandomAccessKey() + "?approver=" + approver.getUserName();
		values.put("approve_link", approveLink);
		
		try {
			String date = InputParser.getCreated("dd MMM yyyy", request.getCreatedTime());
			String time = InputParser.getCreated("HH:mm:ss", request.getCreatedTime());
			values.put("order_date", date);
			values.put("order_time", time);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HashMap<String, String> params = (HashMap<String, String>) request_content
				.get("params");

		System.out.println("request content params: " + params);
		values.put("params", getParamsTable(params));
		values.put("approval_id", request.getId());

		return values;
	}

	public String getParamsTable(HashMap<String, String> params) {
		StringBuilder paramsTable = new StringBuilder(
				"<table><tbody><tr><td><strong>Key</strong></td><td><strong>Value<strong></td></tr>\n");
		
		for(HashMap.Entry<String, String> entry: params.entrySet()) {
			String param = "<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>\n";
			paramsTable.append(param);

		};
		paramsTable.append("</tbody></table>");
		return paramsTable.toString();
	}
	
	public Stage getCurrentStage() {
	    for (Stage stage : stages) {
	        if (stage.getGroupRef().equals(group.getUuid()))
	            return stage;
	    }
	    return null; //TODO Exception handler
	}

	public com.redhat.management.approval.Request getRequest() {
		return this.request;
	}

	public void setRequest(com.redhat.management.approval.Request request) {
		this.request = request;
	}

	public com.redhat.management.approval.Approver getApprover() {
		return this.approver;
	}

	public void setApprover(com.redhat.management.approval.Approver approver) {
		this.approver = approver;
	}

	public com.redhat.management.approval.Group getGroup() {
		return this.group;
	}

	public void setGroup(com.redhat.management.approval.Group group) {
		this.group = group;
	}

	public java.util.ArrayList<com.redhat.management.approval.Stage> getStages() {
		return this.stages;
	}

	public void setStages(
			java.util.ArrayList<com.redhat.management.approval.Stage> stages) {
		this.stages = stages;
	}

	public ApproveEmailBody(com.redhat.management.approval.Request request,
			com.redhat.management.approval.Approver approver,
			com.redhat.management.approval.Group group,
			java.util.ArrayList<com.redhat.management.approval.Stage> stages) {
		this.request = request;
		this.approver = approver;
		this.group = group;
		this.stages = stages;
	}

}
