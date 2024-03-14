package software.amazon.awscdk.examples;

import java.nio.file.*;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.CloudFormationCustomResourceEvent;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.services.lambda.Code;
import software.constructs.Construct;
import software.amazon.awscdk.CustomResource;
import software.amazon.awscdk.Duration;
import java.util.UUID;
import software.amazon.awscdk.customresources.*;

import software.amazon.awscdk.services.logs.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.InlineCode;
import software.amazon.awscdk.services.lambda.SingletonFunction;
import software.constructs.IConstruct;

public class MyCustomResource extends Construct {
  public String response = "";
  public MyCustomResource(final Construct scope, final String id, final Map<String, ? extends Object> props) {
    super(scope, id);

    try {

      Map<String,String> envvars = new HashMap<>();
      envvars.put("DEBUG_LOGGING_ENABLED", "true");
      envvars.put("LOG_LEVEL", "DEBUG");
      envvars.put("POWERTOOLS_LOG_LEVEL", "DEBUG");
      envvars.put("POWERTOOLS_SERVICE_NAME", "example");
      final SingletonFunction onEvent = SingletonFunction.Builder.create(this, "Singleton")
        .code(Code.fromAsset("./functionx.jar"))
        .handler("com.amazon.aws.example.CustomResourceHandler::handleRequest")
        .runtime(Runtime.JAVA_17)
        .uuid(UUID.randomUUID().toString())
        .timeout(Duration.minutes(1))
        .environment(envvars)
        .build();

//      final SingletonFunction onEvent = SingletonFunction.Builder.create(this, "Singleton")
//        .code(InlineCode.fromAsset("lambda"))
//        .handler("custom-resource-handler.on_event")
//        .runtime(Runtime.PYTHON_3_8)
//        .uuid(UUID.randomUUID().toString())
//        .timeout(Duration.minutes(1))
//        .build();

      final Provider myProvider = Provider.Builder.create(this, "MyProvider")
        .onEventHandler(onEvent)
        .logRetention(RetentionDays.ONE_DAY)
        .build();
      CfnOutput.Builder.create(this, "ProviderToken")
        .description("Provider Token is ")
        .value((myProvider.getServiceToken()))
        .build();

      final CustomResource resource = CustomResource.Builder.create(this, "Resource1")
        .serviceToken(myProvider.getServiceToken())
        .properties(props)
        .build();

      CfnOutput.Builder.create(this, "custom Resource")
        .description("custom resource properties is ")
        .value((props.toString()))
        .build();

      response = "RESPONSE IS EXCEPTION*****";
//      ListIterator<IConstruct> resString = resource.getNode().getChildren().listIterator();
//      while(resString.hasNext()){
//        CfnOutput.Builder.create(this, "response")
//          .description("response is ")
//          .value(response)
//          .build();
//        System.out.println(resString.next().toString());
//
//
//      }
//      CfnOutput.Builder.create(this, "resString")
//        .description("resString is ")
//        .value(resString.toString())
//        .build();
      if(resource.getAtt("Response")!=null){
        response = resource.getAtt("Response").toString();
        if(response.isEmpty()){
          response = "RESPONSE IS EMPTY";
        }
      }else{
        response = "RESPONSE IS NULL";
      }
      CfnOutput.Builder.create(this, "response1")
        .description("response is ")
        .value(response)
        .build();

    } catch (Exception e) {
      CfnOutput.Builder.create(this, "exception")
        .description("exception  is ")
        .value(e.getMessage())
        .build();

      e.printStackTrace();
    }


  }

}
