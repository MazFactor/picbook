package com.lyl.web.base.web;

import java.util.Collections;
import java.util.List;

public class QueryResultDto<T> {
    public final int page;
    public final long total;
    public final List<T> rows;

    public QueryResultDto() {
        this.page = 1;
        this.total = 0L;
        this.rows = Collections.emptyList();
    }

    public QueryResultDto(long totalRecords, List<T> data, int page) {
        this.total = totalRecords;
        this.rows = data;
        this.page = page;
    }
}
