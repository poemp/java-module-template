package org.poem.vo;

import java.util.List;

public class DataSetVO {

    private String tableName;

    private List<String> metas;

    private List<List<String>> datas;

    private Long total;

    public List<List<String>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<String>> datas) {
        this.datas = datas;
    }

    public List<String> getMetas() {
        return metas;
    }

    public void setMetas(List<String> metas) {
        this.metas = metas;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public DataSetVO() {
    }

    public DataSetVO(List<String> metas, List<List<String>> datas) {
        this.metas = metas;
        this.datas = datas;
    }

    public DataSetVO(String tableName, List<List<String>> datas, List<String> metas) {
        this.tableName=tableName;
        this.datas = datas;
        this.metas = metas;
    }

}
