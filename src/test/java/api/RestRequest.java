package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestRequest {
    private final static String url = "http://localhost:8080";

    public static Response checkEntrance(String entrance, Integer keyId, Integer roomId) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get(url + "/check?entrance=" + entrance + "&keyId=" + keyId + "&roomId=" + roomId)
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public static Response infoRooms() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get(url + "/info/rooms")
                .then()
                .log()
                .body()
                .extract()
                .response();
    }

    public static Response infoUsers(Integer end, Integer start) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get(url + "/info/users?end=" + end + "&start=" + start)
                .then()
                .log()
                .body()
                .extract()
                .response();

    }

}
