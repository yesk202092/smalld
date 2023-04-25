package com.smalld.common.page;

import lombok.Data;

/**
 * @author yesk
 */
@Data
public class PageBO {

    private Integer current;

    private Integer size;

    private Integer offset;

    private Integer total;

    public void setSize(Integer size) {
        this.size = size > 10 ? 10 : size;
    }

    public Integer getOffset() {
        return (current - 1) * size;
    }

    public PageBO() {
        this.current = 1;
        this.size = 10;
    }
}
