package com.yz.elasticsearch;

import com.yz.common.core.utils.ClassUtil;
import com.yz.common.core.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangzhao
 * @Description ES查询 链式调用
 * @Date create by 21:53 18/5/9
 */
public class ESSearch<T> {

    private static final Logger logger = LoggerFactory.getLogger(ESSearch.class);

    private TransportClient transportClient;

    private String indexName;

    private String[]types;

    private Integer page = 0;

    private Integer pageSize = 0;

    private Integer size = 0;
    /**
     *
     */
    private T bean;
    /**
     * 显示字段
     */
    private String[]showField;
    /**
     * 排序字段 命令
     */
    private String sortField;
    //精确查询
    private boolean matchPhrase;

    public ESSearch(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    public ESSearch setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }

    public ESSearch setType(String[] types) {
        this.types = types;
        return this;
    }

    public ESSearch setPage(Integer page) {
        this.page = page;
        return this;
    }

    public ESSearch setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public ESSearch setSize(Integer size) {
        this.size = size;
        return this;
    }

    public ESSearch setBean(T bean) {
        this.bean = bean;
        return this;
    }

    public ESSearch setShowField(String[] showField) {
        this.showField = showField;
        return this;
    }

    public ESSearch setSortField(String sortField) {
        this.sortField = sortField;
        return this;
    }

    public ESSearch setMatchPhrase(boolean matchPhrase) {
        this.matchPhrase = matchPhrase;
        return this;
    }

    public SearchRequestBuilder getSearchRequestBuilder(String[] indices, String... types) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(indices).setTypes(types);
        return searchRequestBuilder;
    }

    public List<Map<String, Object>> execute() throws Exception {
        if (StringUtils.isEmpty(indexName)&& ArrayUtils.isEmpty(types)){
            throw new Exception("indexName or types is null");
        }
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(this.indexName);
        searchRequestBuilder.setTypes(this.types);
        if (page>0&&pageSize>0){
            searchRequestBuilder.setFrom(this.page).setSize(this.pageSize);
        }

        if (!ArrayUtils.isEmpty(showField)){
            searchRequestBuilder.setFetchSource(showField,null);
        }
        if (size>0){
            searchRequestBuilder.setSize(size);
        }
        searchRequestBuilder.setFetchSource(true);
        //按字段排序
        if (!StringUtils.isEmpty(sortField)){
            String[] split = sortField.split(" ");
            if (split[1].equals("asc")){
                searchRequestBuilder.addSort(split[0], SortOrder.ASC);
            }
            if (split[1].equals("desc")){
                searchRequestBuilder.addSort(split[0], SortOrder.DESC);
            }

        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (bean!=null){
            Map<String, Object> fieldNameAndVauleMapping = ClassUtil.getNotNullFieldNameAndVauleMapping(this.bean);
            if (!fieldNameAndVauleMapping.isEmpty()){
                fieldNameAndVauleMapping.forEach((k,v)->{
                    if (matchPhrase==true){
                        boolQuery.must(QueryBuilders.matchPhraseQuery(k,v));
                    }else{
                        boolQuery.must(QueryBuilders.matchQuery(k,v));
                    }
                });
            }
        }
        searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchRequestBuilder.setQuery(boolQuery);
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        logger.info("\n{}", searchRequestBuilder);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;
        logger.debug("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);
        return searchResponse(searchResponse,null);
    }

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private List<Map<String, Object>> searchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
        StringBuffer stringBuffer = new StringBuffer();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());

            if (!StringUtils.isEmpty(highlightField)) {

                System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }

        return sourceList;
    }

    /**
     * 获取对象列表
     * @param aClass
     * @param <A>
     * @return
     * @throws Exception
     */
    public <A>List<A> executeForObjList(Class<A> aClass) throws Exception {
        List<Map<String, Object>> mapList = execute();
        List<A> tList = new ArrayList<>();
        for (Map map:mapList){
            A t = aClass.newInstance();
            BeanUtils.populate(t,map);
            tList.add(t);
        }
        return tList;
    }

}
