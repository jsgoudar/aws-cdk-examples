import com.amazon.aws.example.CustomResourceHandler;
import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.CloudFormationCustomResourceEvent;
import org.junit.Test;
import software.amazon.awssdk.annotations.SdkTestInternalApi;
import software.amazon.lambda.powertools.cloudformation.AbstractCustomResourceHandler;
import software.amazon.lambda.powertools.cloudformation.Response;

import java.util.Map;
import java.util.UUID;

public class CustomResourceHandlerTest {

  class TestContext implements Context{
    public TestContext() {}
    public String getAwsRequestId(){
      return new String("495b12a8-xmpl-4eca-8168-160484189f99");
    }
    public String getLogGroupName(){
      return new String("/aws/lambda/my-function");
    }

    @Override
    public String getLogStreamName() {
      return null;
    }

    @Override
    public String getFunctionName() {
      return null;
    }

    @Override
    public String getFunctionVersion() {
      return null;
    }

    @Override
    public String getInvokedFunctionArn() {
      return null;
    }

    @Override
    public CognitoIdentity getIdentity() {
      return null;
    }

    @Override
    public ClientContext getClientContext() {
      return null;
    }

    @Override
    public int getRemainingTimeInMillis() {
      return 0;
    }

    @Override
    public int getMemoryLimitInMB() {
      return 0;
    }

    public LambdaLogger getLogger(){
      return null;
    }

  }
  @Test
  public void test() {
    CustomResourceHandler handler = new CustomResourceHandler();

    CloudFormationCustomResourceEvent event = new CloudFormationCustomResourceEvent();
    event.setResourceProperties(Map.of("Message", "Hello World"));
    event.setRequestType("Create");
    event.setStackId("stack-id");
    event.setLogicalResourceId("logical-resource-id");
    event.setPhysicalResourceId("physical-resource-id");
    event.setResourceType("resource-type");
    event.setServiceToken("arn:aws:lambda");
    event.setResponseUrl("https://cloudformation-custom-resource-response.com");

    event.setResourceProperties(Map.of("Message", "Hello World"));


    Response response = handler.handleRequest(event, new TestContext());
    System.out.println("RESPONSE = " + response.toString());
  }

}
