package com.github.jsd.api;

import com.github.jsd.api.endpoints.ItemEndPoints;
import com.github.jsd.api.models.CreateItemRequestDto;
import com.github.jsd.api.models.SingleItemRequestDto;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static com.github.jsd.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class ItemApi extends BaseApi {

    @Step("Sending request to create new item")
    public ValidatableResponse createItem(CreateItemRequestDto item) {
        return with()
                .filter(withCustomTemplates())
                .given().spec(defaultRequestSpec)
                .body(item)
                .when()
                .post(ItemEndPoints.CREATE)
                .then()
                .log().all();
    }

    @Step("Sending request to delete item")
    public ValidatableResponse deleteItem(SingleItemRequestDto itemId) {
        return with()
                .filter(withCustomTemplates())
                .given().spec(defaultRequestSpec)
                .body(itemId)
                .when()
                .post(ItemEndPoints.DELETE)
                .then()
                .log().all();
    }

    @Step("Sending get item request")
    public ValidatableResponse getSingleItem(SingleItemRequestDto itemId) {
        return with()
                .filter(withCustomTemplates())
                .given().spec(defaultRequestSpec)
                .body(itemId)
                .when()
                .post(ItemEndPoints.GET_INFO)
                .then()
                .log().all();
    }
}