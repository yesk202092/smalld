package com.smalld.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageVO <T> implements Serializable {

    private Long current;
    private Long size;
    private Long total;
    private List<T> records;


}
