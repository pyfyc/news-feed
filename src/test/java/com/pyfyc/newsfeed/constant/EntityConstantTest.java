package com.pyfyc.newsfeed.constant;

import com.pyfyc.newsfeed.entity.Category;
import com.pyfyc.newsfeed.entity.Feed;

import java.time.LocalDate;

public class EntityConstantTest {
    public static final Category CATEGORY;
    public static final Feed FEED;
    public static final Feed FEED_UPD;

    public static final Long ID1 = 1L;
    public static final Long ID2 = 2L;
    public static final LocalDate PUBLISH_DATE = LocalDate.now();

    static {
        CATEGORY = new Category();
        CATEGORY.setId(ID1);
        CATEGORY.setName("cat1");

        FEED = new Feed();
        FEED.setId(ID1);
        FEED.setName("news1");
        FEED.setDescription("news1 desc");
        FEED.setPublishDate(PUBLISH_DATE);
        FEED.setCategory(CATEGORY);

        FEED_UPD = new Feed();
        FEED_UPD.setId(ID2);
        FEED_UPD.setName("News2");
        FEED_UPD.setDescription("news2 desc");
        FEED_UPD.setPublishDate(PUBLISH_DATE);
        FEED_UPD.setCategory(CATEGORY);
    }
}
