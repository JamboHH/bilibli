package com.six.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.six.pojo.resp.ResultResp;
import com.six.pojo.vo.RoomMessage;
import com.six.service.SearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhouJinDong
 * @Date 2020/12/29
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RestHighLevelClient client;

    @Value("${es.index}")
    private String index;

    @Override
    public ResultResp searchKey(String key, Integer page, Integer size) {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (StringUtils.isEmpty(key)){
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        }else {
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(key,"roomtitle","username").field("roomtitle",10));
        }

        searchSourceBuilder.size(size);
        searchSourceBuilder.from((page-1)*size);
        //排序
//        if (!StringUtils.isEmpty(price) && price.equals("desc")){
//            searchSourceBuilder.sort("gprice", SortOrder.DESC);
//        }
//        if (!StringUtils.isEmpty(price) && price.equals("asc")){
//            searchSourceBuilder.sort("gprice",SortOrder.ASC);
//        }
//        if (!StringUtils.isEmpty(appraisal) && appraisal.equals("desc")){
//            searchSourceBuilder.sort("gappraisal", SortOrder.DESC);
//        }
//        if (!StringUtils.isEmpty(appraisal) && appraisal.equals("asc")){
//            searchSourceBuilder.sort("gappraisal",SortOrder.ASC);
//        }

        //高亮展示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("roomtitle");
        highlightBuilder.field("username");
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");

        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);
        ResultResp resultResp = new ResultResp();
        try {
            SearchResponse search = client.search(searchRequest);
            SearchHits hits = search.getHits();
            long totalHits = hits.getTotalHits();
            resultResp.setTotal(totalHits);

            SearchHit[] hits1 = hits.getHits();
            List list = new ArrayList();
            for (SearchHit hit : hits1) {
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                String shopname = null;
                String shopdesc = null;

                if (highlightFields != null){
                    HighlightField shopname1 = highlightFields.get("roomtitle");
                    HighlightField shopdesc1 = highlightFields.get("username");

                    if (shopname1 != null){
                        Text[] fragments = shopname1.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        if (fragments != null){
                            for (Text fragment : fragments) {
                                shopname = stringBuffer.append(fragment).toString();
                            }
                        }
                    }
                    if (shopdesc1 != null){
                        Text[] fragments = shopdesc1.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        if (fragments != null){
                            for (Text fragment : fragments) {
                                shopdesc = stringBuffer.append(fragment).toString();
                            }
                        }
                    }
                }
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                if (shopname != null){
                    sourceAsMap.put("roomtitle",shopname);
                }
                if (shopdesc != null){
                    sourceAsMap.put("username", shopdesc);
                }
                Object o = JSONObject.toJSON(sourceAsMap);
                RoomMessage roomMessage = JSONObject.parseObject(o.toString(), RoomMessage.class);
                list.add(roomMessage);
            }
            resultResp.setCode(200);
            resultResp.setData(list);
            resultResp.setMessage("搜索成功！！");
            return resultResp;
        } catch (IOException e) {
            e.printStackTrace();
            resultResp.setCode(201);
            resultResp.setMessage("搜索失败！！");
            return resultResp;
        }

    }
}
