package com.dennisjonsson.tm.client;

import java.util.ArrayList;

/**
 * Created by dennis on 2016-03-15.
 */
public class TagsDTO {

    public final int limit, offset;
    public String startDate;
    public String endDate;

    public final ArrayList<String> tags;

    public TagsDTO(int limit, int offset, ArrayList<String> tags) {
        this.limit = limit;
        this.offset = offset;
        this.tags = tags;
    }
}
