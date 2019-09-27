package cn.huanzi.qch.springbootelasticsearch.itembyes.service;

import cn.huanzi.qch.springbootelasticsearch.common.pojo.PageInfo;
import cn.huanzi.qch.springbootelasticsearch.common.pojo.Result;
import cn.huanzi.qch.springbootelasticsearch.common.service.CommonServiceImpl;
import cn.huanzi.qch.springbootelasticsearch.itembyes.pojo.ItemByEs;
import cn.huanzi.qch.springbootelasticsearch.itembyes.repository.ItemByEsRepository;
import cn.huanzi.qch.springbootelasticsearch.itembyes.vo.ItemByEsVo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class ItemByEsServiceImpl extends CommonServiceImpl<ItemByEsVo,ItemByEs, String> implements ItemByEsService  {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate; //原生查询API

    @Autowired
    private TransportClient client; //原生查询API

    @Autowired
    private ItemByEsRepository itemByEsRepository;

    @Override
    public Result<PageInfo<ItemByEsVo>> searchByNative(ItemByEsVo itemByEsVo) {
        // 统计查询时间,这里开始
        Instant start = Instant.now();

        // 构造查询条件
        QueryBuilder matchQuery = QueryBuilders.matchQuery("title", itemByEsVo.getTitle())
                .analyzer("standard") //分词器
                .operator(Operator.OR);//or查询 Operator.OR、and查询Operator.AND

        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("title") //需要高亮的域（字段）
                .preTags("<span style=\"color:red;\">") //前缀
                .postTags("</span>"); //后缀

        // 设置查询字段
        SearchResponse response = client.prepareSearch("book")
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                // 设置一次返回的文档数量
                .setSize(10)
                .get();

        // 返回搜索结果
        SearchHits hits = response.getHits();

        // 统计搜索结束时间
        Instant end = Instant.now();

        ArrayList novel = new ArrayList();
        for (int i = 0; i < hits.getTotalHits(); i++) {
            // 得到SearchHit对象
            SearchHit hit = hits.getAt(i);
            // 遍历结果,使用HashMap存放
            LinkedHashMap map = new LinkedHashMap();
            map.put("Source As String", hit.getSourceAsString());
            // 返回String格式的文档结果
            System.out.println("Source As String:" + hit.getSourceAsString());
            map.put("Source As Map", hit.getSourceAsMap());
            // 返回Map格式的文档结果
            System.out.println("Source As Map:" + hit.getSourceAsMap());
            // 返回文档所在的索引
            map.put("Index", hit.getIndex());
            System.out.println("Index:" + hit.getIndex());
            // 返回文档所在的类型
            map.put("Type", hit.getType());
            System.out.println("Type:" + hit.getType());
            // 返回文档所在的ID编号
            map.put("Id", hit.getId());
            System.out.println("Id:" + hit.getId());
            // 返回指定字段的内容,例如这里返回完整的title的内容
            map.put("Title", hit.getSourceAsMap().get("title"));
            System.out.println("title: " + hit.getSourceAsMap().get("title"));
            // 返回文档的评分
            map.put("Scope", hit.getScore());
            System.out.println("Scope:" + hit.getScore());
            // 返回文档的高亮字段
            Text[] text = hit.getHighlightFields().get("title").getFragments();
            StringBuilder hight = new StringBuilder();
            if (text != null) {
                for (Text str : text) {
                    hight.append(str);
                    System.out.println(str.toString());
                }
            }
            map.put("Highlight", hight.toString());
            novel.add(map);
        }

        System.out.println(novel);
        System.out.println("共查出"+hits.getTotalHits()+"条记录！");
        System.out.println("共耗时"+ Duration.between(start, end).toMillis()+"ms");
        return null;
    }
}
