package ${groupPath}.biz.${artifactPath}.bo.base;


import lombok.Data;

import java.io.Serializable;

/**
 * 　　*  分页
 * 　　* @author yesk
 * 　　* @date 2021/9/16  15:02
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