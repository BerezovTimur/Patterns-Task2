package ru.netology.domain;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@Data
public class GenerateUsers {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(RegistrationDto registrationDto) {
        given()
                .spec(requestSpec)
                .body(registrationDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationDto generateValidActiveUser() {
        Faker faker = new Faker(new Locale("en"));
        RegistrationDto valid = new RegistrationDto(
                faker.name().username().toLowerCase(),
                faker.internet().password(),
                "active");
        setUpAll(valid);
        return valid;
    }

    public static RegistrationDto generateValidBlockedUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username().toLowerCase();
        String password = faker.internet().password();
        setUpAll(new RegistrationDto(login,password,"blocked"));
        return new RegistrationDto(login,password,"blocked");
    }

    public static RegistrationDto generateUserInvalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        String status = "active";
        setUpAll(new RegistrationDto("vasya",password,status));
        return new RegistrationDto("вася",password,status);
    }

    public static RegistrationDto generateUserInvalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        setUpAll(new RegistrationDto(login,"password",status));
        return new RegistrationDto(login,"пасворд",status);
    }
}