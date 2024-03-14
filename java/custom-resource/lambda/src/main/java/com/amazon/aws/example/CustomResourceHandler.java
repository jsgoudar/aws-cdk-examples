package com.amazon.aws.example;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.CloudFormationCustomResourceEvent;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import software.amazon.lambda.powertools.cloudformation.AbstractCustomResourceHandler;
import software.amazon.lambda.powertools.cloudformation.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomResourceHandler extends AbstractCustomResourceHandler {

  @Override
  protected Response create(CloudFormationCustomResourceEvent createEvent, Context context) {

    String physicalResourceId = "sampleZZ-resource-id-" + UUID.randomUUID(); //Create a unique ID for your resource
    System.out.println(" EVENT = " + createEvent.toString());
    Map<String, Object> properties = createEvent.getResourceProperties();
    System.out.println(" proeprties -----"+ properties );

    String message = (String) properties.get("Message");
    System.out.println("---MESSAGE " + message);

    Map<String,String> attributes = Map.of("Response", "Hello World----" +message);
  //  Map<String,Object> attributes1 = Map.of("Data",attributes);
    //attributes1.put("Data", attributes);

    Response response = Response.builder().value(attributes)
      .status(Response.Status.SUCCESS)
      .physicalResourceId(physicalResourceId)
      .build();


    System.out.println("Arritbutes " + attributes);
    if(response!=null){
      System.out.println("RESPONSE IS NOT NULL " + response.toString());
    }else {
      System.out.println("RESPONSE IS NULL ******** ");
    }

    return response;
  }

  @Override
  protected Response update(CloudFormationCustomResourceEvent updateEvent, Context context) {
    String physicalResourceId = updateEvent.getPhysicalResourceId();
    return Response.success(physicalResourceId);//Get the PhysicalResourceId from CloudFormation
//    UpdateResult updateResult = doUpdates(physicalResourceId);
//    if(updateResult.isSuccessful()){ //check if the update operations were successful
//      return Response.success(physicalResourceId);
//    }else{
//      return Response.failed(physicalResourceId);
//    }
  }

  @Override
  protected Response delete(CloudFormationCustomResourceEvent deleteEvent, Context context) {
    String physicalResourceId = deleteEvent.getPhysicalResourceId();
    return Response.success(physicalResourceId);//Get the PhysicalResourceId from CloudFormation
//    DeleteResult deleteResult = doDeletes(physicalResourceId);
//    if(deleteResult.isSuccessful()){ //check if the delete operations were successful
//      return Response.success(physicalResourceId);
//    }else{
//      return Response.failed(physicalResourceId);
//    }
  }
}
