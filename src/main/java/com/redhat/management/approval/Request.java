package com.redhat.management.approval;

import java.util.LinkedHashMap;
import java.util.Base64;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Request implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    private String requester;

    private java.lang.String description;

    private java.lang.String name;

    private LinkedHashMap<String, Object> content;

    private java.lang.String createdTime;

    private java.lang.String id;

    private java.lang.String tenantId;
    
    private LinkedHashMap<String, Object> context;
    
    public Request(LinkedHashMap<String, Object> maps) {
        this.requester = (String) maps.get("requester");
        this.description = (String) maps.get("description");
        this.id = maps.get("id").toString();
        this.tenantId = maps.get("tenant_id").toString();
        this.name = (String) maps.get("name");
        this.createdTime = (String) maps.get("created_at");
        this.content = (LinkedHashMap<String, Object>) maps.get("content");
        this.context = (LinkedHashMap<String, Object>) maps.get("context");
    }

    public LinkedHashMap<String, Object> getContent() {
        return this.content;
    }

    public void setContent(LinkedHashMap<String, Object> content) {
        this.content = content;
    }

    public LinkedHashMap<String, Object> getContext() {
        return this.context;
    }

    public void setContext(LinkedHashMap<String, Object> context) {
        this.context = context;
    }

    public java.lang.String getRequester() {
        return this.requester;
    }

    public void setRequester(java.lang.String requester) {
        this.requester = requester;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public Request() {
    }

    public String toString() {
        return "Request: " + "\n name: " + this.name
                + "\n description: " + this.description
                + "\n id: " + this.id
                + "\n tenant id: " + this.tenantId
                + "\n content: " + this.content
                + "\n context: " + this.context;
    }

    public java.lang.String getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(java.lang.String createdTime) {
        this.createdTime = createdTime;
    }

    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(java.lang.String tenantId) {
        this.tenantId = tenantId;
    }

    public Request(java.lang.String requester, java.lang.String description,
            java.lang.String name, java.lang.String createdTime,
            java.lang.String id, java.lang.String tenant_id) {
        this.requester = requester;
        this.description = description;
        this.name = name;
        this.createdTime = createdTime;
        this.id = id;
        this.tenantId = tenantId;
    }
    
    public LinkedHashMap<String, Object> getHeaders() {
        return (LinkedHashMap<String, Object>) this.context.get("headers");
    }
    
    public String getOriginalUrl() {
        return (String) this.context.get("original_url");
    }
    
    public String getEncodedIdentity() {
        return (String) getHeaders().get("x-rh-identity");
    }
    
    public String getIdentityEmail() {
        return getRHIdentity().getUser().getEmail();
    }
    
    public String getIdentityFullName() {
        return getRHIdentity().getUser().getFirst_name() + " " + getRHIdentity().getUser().getLast_name();
    }
    
    public static RHIdentity getRHIdentity(String context) {
        System.out.println("getIdentity: context = " + context);

        ObjectMapper mapper = new ObjectMapper();
        RHIdentity identity = new RHIdentity();

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(context);
            String jsonStr = new String(decodedBytes);
            System.out.println("Decoded identity: " + jsonStr);
            identity = mapper.readValue(jsonStr, RHIdentity.class);
            System.out.println("getIdentity: " + identity);
        } catch (IOException e) {
              // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return identity;
    }

    public RHIdentity getRHIdentity() {
        return getRHIdentity(getEncodedIdentity());
    }

    public String createEncodedIdentity(RHIdentity id) {
        ObjectMapper Obj = new ObjectMapper();
        String encoded = "";

        try {
            String jsonStr = Obj.writeValueAsString(id);
            byte[] bytes = jsonStr.getBytes("UTF-8");
            encoded = Base64.getEncoder().encodeToString(bytes);
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace(); 
        }

        return encoded;
    }  

    public String createSysadminIdentity() {
        RHIdentity rhid = getRHIdentity();

        rhid.getUser().setUsername("sysadmin");
        rhid.getUser().setEmail("sysadmin");
        rhid.getUser().setFirst_name("sysadmin");
        rhid.getUser().setLast_name("sysadmin");

        String id = "x-rh-identity=" + createEncodedIdentity(rhid);
        return id;
    }
}
