package com.redhat.management.approval;

import java.io.IOException;

import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class InputParser implements java.io.Serializable {

  static final long serialVersionUID = 1L;

  private final static String DATE_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private final static String DATE_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static Request parseRequest(java.util.LinkedHashMap<String, Object> requestMaps) {
      return new Request(requestMaps);
  }
    
 /*
  group 1: {name=g_abc, description=desc, uuid=1234, users=[{username=abc, email=abc@123.com, first_name=abc, last_name=def}, {username=bcd,
 email=bcd@123.com, first_name=bcd, last_name=efg}]}
 */

  public static java.util.ArrayList<Group> parseGroups(java.util.ArrayList<java.util.LinkedHashMap<String, Object>> rawGroups) {
      java.util.ArrayList<Group> groups = new java.util.ArrayList<Group>();
      for (java.util.LinkedHashMap<String, Object> rawGroup : rawGroups) {
          java.util.ArrayList<java.util.LinkedHashMap<String, String>> rawApprovers = (java.util.ArrayList<java.util.LinkedHashMap<String, String>>) rawGroup.get("users");
          java.util.ArrayList<Approver> approvers = new java.util.ArrayList<Approver>();

          for (java.util.LinkedHashMap<String, String> rawApprover : rawApprovers) {
              Approver approver = new Approver(rawApprover.get("username"), rawApprover.get("email"), rawApprover.get("first_name"), rawApprover.get("last_name"));
              approvers.add(approver);
          }

          Group group = new Group((String)rawGroup.get("name"), approvers, (String)rawGroup.get("description"),  (String)rawGroup.get("uuid"));
          groups.add(group);
      }

      return groups;
  }
    
  public static java.util.ArrayList<Stage> parseStages(java.util.ArrayList<java.util.LinkedHashMap<String, Object>> rawStages) {

      java.util.ArrayList<Stage> stages = new java.util.ArrayList<Stage>();
      for (java.util.LinkedHashMap<String, Object> rawStage : rawStages) {
          Stage stage = new Stage(rawStage.get("id").toString(), (String)rawStage.get("random_access_key"), 
                          (String)rawStage.get("created_at"), (String)rawStage.get("group_ref"));
          stages.add(stage);
      }

      return stages;
  }
    
  public static String getCreated(String pattern, String timeStr) throws Exception {
      DateFormat df = new SimpleDateFormat(pattern);
      return df.format(getDate(timeStr));
  }

  public static Date getDate(String timeStr) throws Exception {
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_1);
      sdf.setLenient(false);
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date requestDate = null;

      try {
          requestDate = sdf.parse(timeStr); 
      }
      catch (ParseException e) {
          sdf = new SimpleDateFormat(DATE_FORMAT_2);
          sdf.setLenient(false);
          sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
          requestDate = sdf.parse(timeStr); 
      }
          
      return requestDate;
  }
}
