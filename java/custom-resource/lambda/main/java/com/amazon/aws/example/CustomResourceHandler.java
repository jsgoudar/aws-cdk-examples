package com.amazon.aws.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CloudFormationCustomResourceEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomResourceHandler implements RequestHandler<CloudFormationCustomResourceEvent, Map> {

  private static final Logger LOG = LoggerFactory.getLogger(CustomResourceHandler.class);

  public Map handleRequest(CloudFormationCustomResourceEvent event, Context context)
  {
    System.out.println("Event details "+ event);

    try {
      switch (event.getRequestType()) {
        case "Create":
          return create(event, context);
        case "Update":
          return update(event, context);
        case "Delete":
          return delete(event, context);
        default:
          LOG.warn("Unexpected request type \"" + event.getRequestType() + "\" for event " + event);
          return null;
      }
    } catch (RuntimeException e) {
      LOG.error("Unable to process request", e);
      throw e;
    }
  }

  //@Override
  private Map create(CloudFormationCustomResourceEvent createEvent, Context context) {

    String physicalResourceId = "sample-resource-id-" + UUID.randomUUID(); //Create a unique ID for your resource
    LOG.info(" EVENT = " + createEvent.toString());
    Map<String, Object> properties = createEvent.getResourceProperties();
    System.out.println(" proeprties -----"+ properties );

    String message = (String) properties.get("Message");
    System.out.println("---MESSAGE " + message);


    Map<String,String> attributes = new HashMap<>();
    attributes.put("Response", message);
    Map<String,Object> data = new HashMap<>();
    data.put("Data", attributes);

    System.out.println("Arritbutes------- " + attributes1);

    return attributes1;
  }


  private Map update(CloudFormationCustomResourceEvent updateEvent, Context context) {
    String physicalResourceId = updateEvent.getPhysicalResourceId();
    Map<String,Object> data = new HashMap<>();
    data.put("Data", "Resource updated" + physicalResourceId);
    return data;

  }

  private Map delete(CloudFormationCustomResourceEvent updateEvent, Context context) {
    String physicalResourceId = updateEvent.getPhysicalResourceId();
    Map<String,Object> data = new HashMap<>();
    data.put("Data", "Resource deleted" + physicalResourceId);
    return data;

  }
  }
}
