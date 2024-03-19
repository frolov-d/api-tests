package com.github.jsd.tests;

import com.github.jsd.api.endpoints.ItemEndPoints;
import com.github.jsd.api.models.DeleteItemResponseDto;
import com.github.jsd.api.models.ItemErrorFieldResponseDto;
import com.github.jsd.api.models.ItemInfoResponseDto;
import com.github.jsd.api.models.SingleItemRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Delete item")
public class ItemDeletionTests extends TestBase {

    @Test
    @DisplayName("Deletion an item in the shop")
    public void deleteItem() {
        ItemInfoResponseDto createItemResponse = createNewItem();

        SingleItemRequestDto requestBody = SingleItemRequestDto.builder()
                .id(Integer.parseInt(createItemResponse.getResult().getId())).build();

        DeleteItemResponseDto deleteItemResponse = itemApi.deleteItem(requestBody)
                .body(matchesJsonSchemaInClasspath("schemas/deleteItemSchema.json"))
                .body("method", equalTo(ItemEndPoints.DELETE))
                .body("status", equalTo("ok"))
                .extract().as(DeleteItemResponseDto.class);

        step("Verifying that the response contains an id of a deleted item", () ->
                assertThat(deleteItemResponse.getResult()).contains("ID " + createItemResponse.getResult().getId()));

        ItemErrorFieldResponseDto getDeletedItem = itemApi.getSingleItem(requestBody).extract().as(ItemErrorFieldResponseDto.class);
        step("Verifying response that deleted item was not found", () ->
                assertThat(getDeletedItem.getError()).isEqualTo("item_with_id_not_found"));
    }

    static Stream<Arguments> badIdsAndErrors() {
        return Stream.of(
                Arguments.of(0, List.of("id_not_filled", "Поле ID товара  не заполнено")),
                Arguments.of(123456789, List.of("item_with_id_not_found", "Товар с ID 123456789 не найден!"))
        );
    }

    @MethodSource("badIdsAndErrors")
    @ParameterizedTest(name = "Deletion an item with a bad id={0}")
    public void deleteItemWithBadId(Integer itemId, List<String> errors) {
        SingleItemRequestDto requestBody = SingleItemRequestDto.builder()
                .id(itemId).build();

        ItemErrorFieldResponseDto deleteItemResponse = itemApi.deleteItem(requestBody)
                .body(matchesJsonSchemaInClasspath("schemas/itemErrorSchema.json"))
                .body("method", equalTo(ItemEndPoints.DELETE + "/"))
                .body("status", equalTo("error"))
                .extract().as(ItemErrorFieldResponseDto.class);

        step("Verifying field_error=id", () ->
                assertThat(deleteItemResponse.getErrorField()).isEqualTo("id"));
        step("Verifying error text: " + errors.get(0), () ->
                assertThat(deleteItemResponse.getError()).isEqualTo(errors.get(0)));
        step("Verifying message text: " + errors.get(1), () ->
                assertThat(deleteItemResponse.getMessage()).isEqualTo(errors.get(1)));
    }
}
