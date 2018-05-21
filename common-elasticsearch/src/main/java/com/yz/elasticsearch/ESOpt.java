package com.yz.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yz.common.core.utils.ClassUtil;
import com.yz.common.core.utils.SnowflakeID;
import com.yz.common.core.utils.StringUtils;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Map;

/**
 * @author yangzhao
 * @Description
 * @Date create by 23:04 18/5/7
 */
public class ESOpt {

    private static final Logger logger = LoggerFactory.getLogger(ESOpt.class);

    public static TransportClient client;

    /**
     * 创建索引
     * @param index
     * @return
     */
    public static boolean createIndex(String index) {
        if (!isIndexExist(index)) {
            logger.info("Index is not exits!");
        }
        try {
            CreateIndexResponse indexresponse = client.admin().indices().prepareCreate(index).execute().actionGet();
            logger.info("执行建立成功？" + indexresponse.isAcknowledged());
            return indexresponse.isAcknowledged();
        } catch (ResourceAlreadyExistsException existsException) {
            logger.error("index name : " + index + " already exists");
        }
        return false;
    }

    /**
     * 创建索引+映射
     * @param indexName
     * @param type
     * @param c         模型class
     * @return
     * @throws Exception
     */
    public static boolean createIndexAndMapping(String indexName, String type, Class c) throws IOException {
        Map<String, String> fieldType = ClassUtil.getClassFieldTypeMapping(c);
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(indexName);
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties");
        fieldType.forEach((k, v) -> {
            if (v.equals("int")) {
                v = "integer";
            }
            if (v.equals("double")) {
                v = "float";
            }
            if (v.equals("String")) {
                v = "text";
            }

            //设置之定义字段
            try {
                mapping.startObject(k).field("type", v.toLowerCase()).endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mapping.endObject().endObject();
        cib.addMapping(type, mapping);
        try {
            CreateIndexResponse createIndexResponse = cib.execute().actionGet();
            logger.info("----------添加映射成功----------");
            return createIndexResponse.isAcknowledged();
        } catch (ResourceAlreadyExistsException existsException) {
            logger.error("index name : " + indexName + " already exists");
        }
        return false;
    }

    /**
     * 删除索引
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index) {
        if (!isIndexExist(index)) {
            logger.info("Index is not exits!");
        }
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()) {
            logger.info("delete index " + index + "  successfully!");
        } else {
            logger.info("Fail to delete index " + index);
        }
        return dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
            logger.info("Index [" + index + "] is exist!");
        } else {
            logger.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }

    /**
     * 数据添加，正定ID
     * @param bean  要增加的数据
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     * @return
     */
    public static String addData(Object bean, String index, String type, String id) {
        JSONObject jsonObject = (JSONObject) JSON.parse(JSON.toJSONString(bean));
        IndexResponse response = client.prepareIndex(index, type, id).setSource(jsonObject).get();

        logger.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());

        return response.getId();
    }

    /**
     * 数据添加
     *
     * @param bean  要增加的数据
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @return
     */
    public static String addData(Object bean, String index, String type) {
        JSONObject jsonObject = (JSONObject) JSON.parse(JSON.toJSONString(bean));
        return addData(jsonObject, index, type, String.valueOf(SnowflakeID.getInstance().nextId()));
    }

    /**
     * 通过ID删除数据
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public static void deleteDataById(String index, String type, String id) {

        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();

        logger.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static void updateDataById(JSONObject jsonObject, String index, String type, String id) {

        UpdateRequest updateRequest = new UpdateRequest();

        updateRequest.index(index).type(type).id(id).doc(jsonObject);

        client.update(updateRequest);

    }

    /**
     * 通过ID获取数据
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public static Map<String, Object> searchDataById(String index, String type, String id, String fields) {
        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);

        if (!StringUtils.isEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }

        GetResponse getResponse = getRequestBuilder.execute().actionGet();

        return getResponse.getSource();
    }

}
