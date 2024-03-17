package com.github.jsd.tests;

import com.github.jsd.api.ItemApi;
import com.github.jsd.api.models.ItemInfoResponseDto;
import com.github.jsd.api.models.SingleItemRequestDto;
import com.github.jsd.helpers.ItemFactory;
import io.qameta.allure.Step;

public class TestBase {
    ItemApi itemApi = new ItemApi();
    ItemFactory itemFactory = new ItemFactory();

    @Step("Preparing new item")
    public ItemInfoResponseDto createNewItem() {
        return itemApi.createItem(itemFactory.getRandomItem()).extract().as(ItemInfoResponseDto.class);
    }

    @Step("Performing cleanup")
    public void cleanup(Integer itemId) {
        itemApi.deleteItem(SingleItemRequestDto.builder()
                .id(itemId)
                .build());
    }
}
