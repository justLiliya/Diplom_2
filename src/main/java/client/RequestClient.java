package client;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

//Класс для описания апи-спецификации - убираем дублирование из кода клиентов
public class RequestClient {

    public RequestSpecification getBaseSpec(){

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .build();

    }
}
