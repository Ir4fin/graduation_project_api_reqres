package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class Specs {

    public static RequestSpecification baseRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);

    public static RequestSpecification registerRequestSpec = with()
            .basePath("/register");


    public static ResponseSpecification logsInResponse = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();
    public static ResponseSpecification successfulResponse = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification badResponse = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();


}
