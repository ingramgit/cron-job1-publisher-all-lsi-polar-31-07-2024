package com.ingramcontent.dataverse.utility;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.kie.api.runtime.process.ProcessContext;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.ingramcontent.dataverse.model.Producer;
import com.ingramcontent.dataverse.model.PipelineThreshold;
import com.ingramcontent.dataverse.model.DataFilter;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class ContextUtility implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    public ContextUtility() {
    }

	public static Object getProcessVariable(ProcessContext context, String variable) {
		WorkflowProcessInstance instance = (WorkflowProcessInstance) context.getProcessInstance();
		return instance.getVariable(variable);		
	}
	
	public static void setProcessVariable(ProcessContext context, String variable, Object obj) {
		WorkflowProcessInstance instance = (WorkflowProcessInstance) context.getProcessInstance();
		instance.setVariable(variable, obj);
	}	
	
	public static long getInstanceId(ProcessContext context) {
		return context.getProcessInstance().getId();
	}

    public static void initializeMetadata(ProcessContext context, JSONArray jsonArray)   {
		
		if (jsonArray == null || jsonArray.size() == 0) {
			return;
		}
		
	    Iterator<JSONObject> i = jsonArray.iterator();
 
	    // take each value from the json array separately
	    while (i.hasNext()) {
	        JSONObject jsonObject = i.next();
	        updateVariable(context, jsonObject);
	    }
	}
	
	public static void initializeCronMetadata(ProcessContext context, JSONArray jsonArray, int index) throws JsonProcessingException  {
		
		if (jsonArray == null || jsonArray.size() == 0) {
			return;
		}
		
		PipelineThreshold threshold = new PipelineThreshold();
        Producer producer = new Producer();
        producer.setCreatedDate(java.time.LocalDateTime.now().toString());
        DataFilter filter = new DataFilter();
        
	    Iterator<JSONObject> i = jsonArray.iterator();
 
	    // take each value from the json array separately
	    while (i.hasNext()) {
	        JSONObject jsonObject = i.next();
	        updateVariable(context, jsonObject, threshold, producer, filter, index);
	    }
	}
	
	private static void updateVariable(ProcessContext context, 
                                       JSONObject jsonObject)  {
    	
    	if(jsonObject == null) {
    		return;
    	}
    	
    	String key = (String)jsonObject.get(Constant.PARAMETER_NAME);
    	String value = (String)jsonObject.get(Constant.PARAMETER_VALUE);
    	
    	switch (key) {
			case Constant.PRODUCER_URL:
				setProcessVariable(context, Constant.PRODUCER_URL, value);
			break;
			case Constant.XFORMER_URL:
				setProcessVariable(context, Constant.XFORMER_URL, value);
			break;
			case Constant.CONSUMER_URL:
				setProcessVariable(context, Constant.CONSUMER_URL, value);
			break;
		}
    	
    }
	
    private static void updateVariable(ProcessContext context, 
                                       JSONObject jsonObject,
                                       PipelineThreshold threshold,
                                       Producer producer,
                                       DataFilter filter,
                                       int index)throws JsonProcessingException  {
    	
    	if(jsonObject == null) {
    		return;
    	}
    	
    	String key = (String)jsonObject.get(Constant.PARAMETER_NAME);
    	String value = (String)jsonObject.get(Constant.PARAMETER_VALUE);
    	
    	
    	switch (key) {
			case Constant.EXECUTE:
				setProcessVariable(context, Constant.EXECUTE, value);
			break;
			case Constant.END:
				setProcessVariable(context, Constant.END, value);
			break;
			case Constant.SIGNAL_NAME:
			    producer.setSignalName(value);
				setProcessVariable(context, Constant.PRODUCER_REQUEST, producer);
			break;
			case Constant.TASK_NAME:
			    producer.setTaskName(value);
				setProcessVariable(context, Constant.PRODUCER_REQUEST, producer);
			break;
			case Constant.REQUEST_TYPE:
			    producer.setRequestType(value);
				setProcessVariable(context, Constant.PRODUCER_REQUEST, producer);
			break;
			case Constant.THRESHOLD:
			    Map<String, Integer> thresholdMap = new ObjectMapper().readValue(value, HashMap.class);
			    threshold.setThreshold(thresholdMap);
				setProcessVariable(context, Constant.THRESHOLD, threshold);
			break;
			default:
			  if (key != null && key.equalsIgnoreCase(Constant.PIPELINE_CODE_+index)) {
			      producer.setPipelineCode(value);
				  setProcessVariable(context, Constant.PRODUCER_REQUEST, producer);
			  }
			  
			  if (key != null && key.equalsIgnoreCase(Constant.DATAFILTER_+index)) {
			      Map<String, String> input = new ObjectMapper().readValue(value, HashMap.class);
			      filter.setInput(input);
				  setProcessVariable(context, Constant.DATA_FILTER, filter);
			  }
			  
			  if (key != null && key.equalsIgnoreCase(Constant.CONTAINER_ID_+index)) {
			     producer.setContainerId(value);
				 setProcessVariable(context, Constant.PRODUCER_REQUEST, producer);
			  }
		}
    	
    }
    
   
}