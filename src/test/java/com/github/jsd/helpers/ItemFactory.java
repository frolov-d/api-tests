package com.github.jsd.helpers;

import com.github.javafaker.Faker;
import com.github.jsd.api.models.CreateItemRequestDto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ItemFactory {
    static final Faker faker = new Faker();
    private static final String PHOTO_DIRECTORY = "src/test/resources/photos/";
    private static final String[] CATEGORIES = new String[]{"Категория 1", "＼(〇_ｏ)／", "Test"};
    private static final String[] COLORS = new String[]{"RED", "ORANGE", "BLUE", "GREEN"};
    private static final String[] SIZES = new String[]{"42-44", "┐(￣∀￣)┌", "1-2г", "20"};

    private static String getRandomPhoto() {
        List<String> photos = Arrays.asList("cat_1.jpg", "cat_2.jpg", "doggo_1.jpg", "doggo_2.jpg");
        return faker.options().nextElement(photos);
    }

    public static String getBase64FileString(String photoName) {
        Path photoPath = Paths.get(PHOTO_DIRECTORY + photoName);
        try {
            byte[] photoBytes = Files.readAllBytes(photoPath);
            String base64String = Base64.getEncoder().encodeToString(photoBytes);
            return "data:image/jpeg;base64," + base64String;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CreateItemRequestDto getRandomItem() {
        return CreateItemRequestDto.builder()
                .name(faker.commerce().productName())
                .section(faker.options().nextElement(CATEGORIES))
                .description(faker.hitchhikersGuideToTheGalaxy().quote())
                .color(faker.options().nextElement(COLORS))
                .size(faker.options().nextElement(SIZES))
                .price(Double.parseDouble(faker.commerce().price(10.00, 1000.00)))
                .params(faker.hitchhikersGuideToTheGalaxy().specie())
                .photo(getBase64FileString(getRandomPhoto()))
                .build();
    }
}
