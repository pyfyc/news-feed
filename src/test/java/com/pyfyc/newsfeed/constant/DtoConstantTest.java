package com.pyfyc.newsfeed.constant;

import com.pyfyc.newsfeed.dto.*;

import static com.pyfyc.newsfeed.constant.EntityConstantTest.ID1;
import static com.pyfyc.newsfeed.constant.EntityConstantTest.PUBLISH_DATE;

public class DtoConstantTest {
    public static final CreateCategoryDto CREATE_CATEGORY_DTO;
    public static final CreateCategoryDto CREATE_CATEGORY_DTO_INVALID_NAME;
    public static final CreateFeedDto CREATE_FEED_DTO;
    public static final CreateFeedDto CREATE_FEED_DTO_INVALID_NAME;
    public static final UpdateFeedDto UPDATE_FEED_DTO;
    public static final CategoryDto CATEGORY_DTO;
    public static final FeedDto FEED_DTO;

    static {
        CREATE_CATEGORY_DTO = new CreateCategoryDto();
        CREATE_CATEGORY_DTO.setName("cat1");

        CREATE_CATEGORY_DTO_INVALID_NAME = new CreateCategoryDto();
        CREATE_CATEGORY_DTO_INVALID_NAME.setName("ca"); // Invalid name: size must be between 3 and 100

        CATEGORY_DTO = new CategoryDto();
        CATEGORY_DTO.setId(ID1);
        CATEGORY_DTO.setName("cat1");

        FEED_DTO = new FeedDto();
        FEED_DTO.setName("news1");
        FEED_DTO.setDescription("news1 desc");
        FEED_DTO.setPublishDate(PUBLISH_DATE);
        FEED_DTO.setCategory(CATEGORY_DTO);

        CREATE_FEED_DTO = new CreateFeedDto();
        CREATE_FEED_DTO.setName("news1");
        CREATE_FEED_DTO.setDescription("news1 desc");
        CREATE_FEED_DTO.setPublishDate(PUBLISH_DATE);
        CREATE_FEED_DTO.setCategory(CATEGORY_DTO);

        UPDATE_FEED_DTO = new UpdateFeedDto();
        UPDATE_FEED_DTO.setName("news1 upd");
        UPDATE_FEED_DTO.setDescription("news1 update");

        CREATE_FEED_DTO_INVALID_NAME = new CreateFeedDto();
        CREATE_FEED_DTO_INVALID_NAME.setName("ne"); // Invalid name: size must be between 3 and 100
        CREATE_FEED_DTO_INVALID_NAME.setDescription("news1 desc");
    }
}
