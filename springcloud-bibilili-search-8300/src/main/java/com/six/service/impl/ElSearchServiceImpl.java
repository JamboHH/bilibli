package com.six.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.six.common.BaseRes;
import com.six.pojo.Video;
import com.six.service.ElSearchService;
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
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElSearchServiceImpl implements ElSearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public BaseRes selectKey(Map map) {
        BaseRes baseRes = new BaseRes();
        String key = map.get("key").toString();
        Integer page = (Integer) map.get("page");
        Integer size = (Integer) map.get("limit");
        String dateSort = null;
        String volumeSort = null;
        if (!StringUtils.isEmpty(map.get("dateSort"))) {
            dateSort = map.get("dateSort").toString();
        }
        if (!StringUtils.isEmpty(map.get("volumeSort"))) {
            volumeSort = map.get("volumeSort").toString();
        }
        SearchRequest searchRequest = new SearchRequest("video");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (StringUtils.isEmpty(key)) {
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        } else {
            searchSourceBuilder.query(QueryBuilders.multiMatchQuery(key, "vName", "vAuthor"));
        }
        //设置高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("vName");
        highlightBuilder.field("vAuthor");
        highlightBuilder.preTags("<font style='color:red'>");
        highlightBuilder.postTags("</font>");
        searchSourceBuilder.highlighter(highlightBuilder);
        if (!StringUtils.isEmpty(dateSort) && dateSort.equals("asc")) {
            searchSourceBuilder.sort("vCreateDate", SortOrder.ASC);
        }
        if (!StringUtils.isEmpty(dateSort) && dateSort.equals("desc")) {
            searchSourceBuilder.sort("vCreateDate", SortOrder.DESC);
        }
        if (!StringUtils.isEmpty(volumeSort) && volumeSort.equals("asc")) {
            searchSourceBuilder.sort("vVolume", SortOrder.ASC);
        }
        if (!StringUtils.isEmpty(volumeSort) && volumeSort.equals("desc")) {
            searchSourceBuilder.sort("vVolume", SortOrder.DESC);
        }
        //分页
        searchSourceBuilder.from((page - 1) * size);
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        //执行请求
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest);
            //解析结果
            SearchHits hits = search.getHits();
            baseRes.setTotal(hits.getTotalHits());
            SearchHit[] hits1 = hits.getHits();
            List list = new ArrayList<>();
            for (SearchHit hi : hits1) {
                Map<String, HighlightField> highlightFields = hi.getHighlightFields();
                String vName = null;
                String vAuthor = null;
                if (highlightFields != null) {
                    HighlightField vNames = highlightFields.get("vName");
                    HighlightField vAuthors = highlightFields.get("vAuthor");
                    if (vNames != null) {
                        Text[] fragments = vNames.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        if (fragments != null) {
                            for (Text te : fragments) {
                                vName = stringBuffer.append(te).toString();
                            }
                        }
                    }
                    if (vAuthors != null) {
                        Text[] fragments = vAuthors.getFragments();
                        StringBuffer stringBuffer = new StringBuffer();
                        if (fragments != null) {
                            for (Text te : fragments) {
                                vAuthor = stringBuffer.append(te).toString();
                            }
                        }
                    }
                }
                Map<String, Object> sourceAsMap = hi.getSourceAsMap();
                if (vName != null) {
                    sourceAsMap.put("vName", vName);
                }
                if (vAuthor != null) {
                    sourceAsMap.put("vAuthor", vAuthor);
                }
                Video video = JSONObject.parseObject(JSONObject.toJSON(sourceAsMap).toString(), Video.class);
                list.add(video);
            }
            baseRes.setCode(200);
            baseRes.setData(list);
            baseRes.setMsg("搜索成功");
            return baseRes;
        } catch (IOException e) {
            baseRes.setMsg("搜索失败");
            baseRes.setCode(201);
            return baseRes;
        }
    }
}
