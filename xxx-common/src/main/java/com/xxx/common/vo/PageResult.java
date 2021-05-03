package com.xxx.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> {

    //  总条数
    private Long total;

    //  总页数
    private Long totalPage;

    //  当前页结果
    private List<T> items;

    public PageResult(Long total, Long totalPage) {
        this.total = total;
        this.totalPage = totalPage;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
