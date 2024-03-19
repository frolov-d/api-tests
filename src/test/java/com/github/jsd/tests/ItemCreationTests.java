package com.github.jsd.tests;

import com.github.jsd.api.endpoints.ItemEndPoints;
import com.github.jsd.api.models.CreateItemRequestDto;
import com.github.jsd.api.models.ItemErrorFieldResponseDto;
import com.github.jsd.api.models.ItemInfoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.github.jsd.helpers.ItemFactory.getBase64FileString;
import static io.qameta.allure.Allure.step;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Create item")
public class ItemCreationTests extends TestBase {
    CreateItemRequestDto newItem = itemFactory.getRandomItem();

    @Test
    @DisplayName("Creation an item in the shop with all fields set")
    public void createItemWithAllFields() {
        ItemInfoResponseDto createItemResponse = itemApi.createItem(newItem)
                .body(matchesJsonSchemaInClasspath("schemas/itemInfoSchema.json"))
                .body("method", equalTo(ItemEndPoints.CREATE))
                .body("status",equalTo("ok"))
                .extract().as(ItemInfoResponseDto.class);

        step("Checking that created item has an id", () ->
                assertThat(Integer.parseInt(createItemResponse.getResult().getId())).isGreaterThan(0));

        step("Verifying that the created item contains all the fields sen in the request", () ->
                assertAll(
                        () -> assertEquals(newItem.getName(), createItemResponse.getResult().getName()),
                        () -> assertEquals(newItem.getSection(), createItemResponse.getResult().getSection()),
                        () -> assertEquals(newItem.getDescription(), createItemResponse.getResult().getDescription()),
                        () -> assertEquals(newItem.getColor(), createItemResponse.getResult().getColor()),
                        () -> assertEquals(newItem.getSize(), createItemResponse.getResult().getSize()),
                        () -> assertEquals(newItem.getPrice(), createItemResponse.getResult().getPrice()),
                        () -> assertEquals(newItem.getParams(), createItemResponse.getResult().getParams()),
                        () -> assertEquals(newItem.getPhoto(), createItemResponse.getResult().getPhoto())
                ));

        cleanup(Integer.parseInt(createItemResponse.getResult().getId()));
    }

    @Test
    @DisplayName("Creation an item in the shop with required fields only")
    public void createItemWithRequiredFields() {
        newItem.setPhoto(null);
        newItem.setParams(null);
        newItem.setColor(null);
        newItem.setSize(null);
        newItem.setPrice(null);

        ItemInfoResponseDto createItemResponse = itemApi.createItem(newItem).extract().as(ItemInfoResponseDto.class);

        step("Checking that the created item has an id", () ->
                assertThat(Integer.parseInt(createItemResponse.getResult().getId())).isGreaterThan(0));

        step("Verifying that the created item contains all the fields set in the request", () ->
                assertAll(
                        () -> assertEquals(newItem.getName(), createItemResponse.getResult().getName()),
                        () -> assertEquals(newItem.getSection(), createItemResponse.getResult().getSection()),
                        () -> assertEquals(newItem.getDescription(), createItemResponse.getResult().getDescription())
                ));

        cleanup(Integer.parseInt(createItemResponse.getResult().getId()));
    }

    @CsvSource(value = {
            "name | Название товара не заполнено!",
            "section | Категория не найдена!",
            "description | Описание товара не заполнено!"
    }, delimiterString = "|")
    @ParameterizedTest(name = "Unsuccessful item creation: missing required parameter {0}")
    public void createItemWithoutRequiredParam(String parameter, String expectedMessage) {
        switch (parameter) {
            case "name":
                newItem.setName("");
                break;
            case "section":
                newItem.setSection("");
                break;
            case "description":
                newItem.setDescription("");
                break;
        }

        ItemErrorFieldResponseDto createItemWithError = itemApi.createItem(newItem)
                .body(matchesJsonSchemaInClasspath("schemas/itemErrorSchema.json"))
                .body("method", equalTo(ItemEndPoints.CREATE + "/"))
                .body("status", equalTo("error"))
                .extract().as(ItemErrorFieldResponseDto.class);

        step("Verifying field_error", () ->
                assertThat(createItemWithError.getErrorField()).isEqualTo(parameter));
        step("Verifying error", () ->
                assertThat(createItemWithError.getError()).isIn(parameter + "_not_filled", parameter + "_not_found"));
        step("Verifying message", () ->
                assertThat(createItemWithError.getMessage()).isEqualTo(expectedMessage));
    }

    @ValueSource(strings = {"big_cat.jpg", "big_cats.jpg"})
    @ParameterizedTest(name = "Creation an item with a photo with width more than 500px")
    public void createItemWithTooBigPhoto(String photoName) {
        newItem.setPhoto(getBase64FileString(photoName));

        ItemErrorFieldResponseDto createItemWithBadPhoto = itemApi.createItem(newItem).extract().as(ItemErrorFieldResponseDto.class);

        step("Verifying field_error", () ->
                assertThat(createItemWithBadPhoto.getErrorField()).isEqualTo("photo"));
        step("Verifying error", () ->
                assertThat(createItemWithBadPhoto.getError()).isIn("photo_not_correct"));
        step("Verifying message", () ->
                assertThat(createItemWithBadPhoto.getMessage()).isEqualTo("Ширина должна быть не более 500px"));
    }
}
