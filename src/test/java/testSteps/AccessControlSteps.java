package testSteps;


import api.RestRequest;
import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import io.restassured.response.Response;
import org.junit.Assert;

public class AccessControlSteps {
    Response response;

    @Дано("^Пользователь (\\d+) входит в комнату (\\d+)$")
    public void enterInRoom(Integer keyId, Integer roomId) {
        response = RestRequest.checkEntrance("ENTRANCE", keyId, roomId);
        System.out.println("Вход" + "\nНомер комнаты - " + roomId + "\nНомер ключа - " + keyId);
    }

    @Дано("^Пользователь (\\d+) выходит из комнаты (\\d+)$")
    public void exitOtRoom(Integer keyId, Integer roomId) {
        response = RestRequest.checkEntrance("EXIT", keyId, roomId);
        System.out.println("Выход" + "\nНомер комнаты - " + roomId + "\nНомер ключа - " + keyId);
    }

    @Когда("^Проверка входа пользователя в комнату$")
    public void checkEnterInRoom() {
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("You are welcome!", response.asString());
        System.out.println("Пользователь успешно вошел в комнату" + "\n");
    }

    @Тогда("^Проверка выхода пользователя из комнаты$")
    public void checkExitOutRoom() {
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Goodbye!", response.asString());
        System.out.println("Пользователь успешно покинул комнату" + "\n");
    }

    @Тогда("^Проверка входа пользователя в комнату при условии что keyId не делится на roomId$")
    public void checkEnterInRoomProvidedNotDivisible() {
        Assert.assertEquals(403, response.statusCode());
        Assert.assertEquals("You has no privileges to enter this room", response.asString());
        System.out.println("У пользователя нет прав для входа" + "\n");
    }

    @Дано("^Пользователь (\\d+) повторно входит в комнату (\\d+)$")
    public void reEnterInRoom(Integer keyId, Integer roomId) {
        response = RestRequest.checkEntrance("ENTRANCE", keyId, roomId);
        response = RestRequest.checkEntrance("ENTRANCE", keyId, roomId);
    }

    @Тогда("^Проверка повторного входа пользователя в комнату (\\d+)$")
    public void checkReEnterInRoom(Integer roomId) {
        Assert.assertEquals(500, response.statusCode());
        Assert.assertEquals("You can't enter into room #" + roomId + " without living room #" + roomId, response.asString());
        System.out.println("Пользователь не может войти в комнату" + "\n");
    }

    @Тогда("^Проверка входа пользователя в несуществующую комнату (\\d+)$")
    public void checkEnterNonExistentRoom(Integer roomId) {
        Assert.assertEquals(500, response.statusCode());
        Assert.assertEquals("There are no such room with id #" + roomId + " in repository", response.asString());
        System.out.println("Комнаты " + roomId + " не сущестует" + "\n");
    }

    @Тогда("^Проверка входа несуществующего пользователя (\\d+) в комнату$")
    public void checkDefunctUserEnterInRoom(Integer keyId) {
        Assert.assertEquals(500, response.statusCode());
        Assert.assertEquals("There are no such user with key #" + keyId + " in repository", response.asString());
        System.out.println("Пользователя " + keyId + " не сущестует" + "\n");
    }


    @Тогда("^Проверка выхода пользователя (\\d+) из комнаты (\\d+), если он находится в комнате (\\d+)$")
    public void checkOutFromAnotherRoom(Integer keyId, Integer roomIdExit, Integer roomId) {
        response = RestRequest.checkEntrance("EXIT", keyId, roomIdExit);
        Assert.assertEquals(500, response.statusCode());
        Assert.assertEquals("You can't enter into room #" + roomIdExit + " without living room #" + roomId, response.asString());
        System.out.println("Пользователь не может выйти из комнаты, в которую не заходил" + "\n");
    }

    @Когда("^Получить информацию по всем комнатам$")
    public void getInfoAllRooms() {
        response = RestRequest.infoRooms();
    }

    @Тогда("^Проверить статус код 200$")
    public void checkInfoAllRooms() {
        Assert.assertEquals(200, response.statusCode());
    }

    @Дано("^Отправить запрос с параметрами: конец отсчёта страницы пользователей (\\d+) и начало отсчёта страницы пользователей (\\d+)$")
    public void getInfoAllUsers(Integer end, Integer start) {
        response = RestRequest.infoUsers(end, start);
    }

    @И("^Вывести тело ответа$")
    public void outputResponseBody() {
        System.out.println(response.asString());
    }

    @Тогда("^Проверка получения ошибки$")
    public void checkReceiptErrors() {
        Assert.assertEquals(500, response.statusCode());
    }
}
