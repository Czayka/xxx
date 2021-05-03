package com.xxx.search.pojo;

public class SearchRequest {
    private String key; // 搜索条件

    private Integer page;   //  当前页

    /**
     * 1->歌名
     * 2->专辑名
     * 3->歌手
     * 其他->根据all字段
     */
    private Integer type;   //  过滤方式

    private static final int DEFAULT_TYPE = 0;

    private static final int DEFAULT_PAGE = 1;

    private static final int DEFAULT_SIZE = 10;

    public Integer getType() {
        if (type == null){
            return DEFAULT_TYPE;
        }
        return Math.max(type,DEFAULT_TYPE);
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null){
            return DEFAULT_PAGE;
        }
        return Math.max(page,DEFAULT_PAGE);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getSize(){
        return DEFAULT_SIZE;
    }
}
