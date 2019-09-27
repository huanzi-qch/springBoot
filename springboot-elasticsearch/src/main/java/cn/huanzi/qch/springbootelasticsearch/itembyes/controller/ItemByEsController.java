package cn.huanzi.qch.springbootelasticsearch.itembyes.controller;

import cn.huanzi.qch.springbootelasticsearch.common.controller.CommonController;
import cn.huanzi.qch.springbootelasticsearch.common.pojo.PageInfo;
import cn.huanzi.qch.springbootelasticsearch.common.pojo.Result;
import cn.huanzi.qch.springbootelasticsearch.itembyes.pojo.ItemByEs;
import cn.huanzi.qch.springbootelasticsearch.itembyes.service.ItemByEsService;
import cn.huanzi.qch.springbootelasticsearch.itembyes.vo.ItemByEsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ES测试控制器
 */
@RestController
@RequestMapping("/itemByEs/")
public class ItemByEsController extends CommonController<ItemByEsVo, ItemByEs, String> {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemByEsService itemByEsService;

    /**
     * 原生API搜索并且高亮显示
     */
    @GetMapping("searchByNative")
    public Result<PageInfo<ItemByEsVo>> searchByNative(ItemByEsVo itemByEsVo) {
        return itemByEsService.searchByNative(itemByEsVo);
    }

    /**
     * 创建索引，会根据Item类的@Document注解信息来创建
     */
    @GetMapping("createIndex")
    public void createIndex() {
        elasticsearchTemplate.createIndex(ItemByEs.class);
        elasticsearchTemplate.putMapping(ItemByEs.class);
    }

    /**
     * 更新映射，会根据Item类
     */
    @GetMapping("putMapping")
    public void putMapping() {
        elasticsearchTemplate.putMapping(ItemByEs.class);
    }

    /**
     * 删除索引，会根据Item类的@Document注解信息来删除
     */
    @GetMapping("deleteIndex")
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex(ItemByEs.class);
    }

    /**
     * 批量创建测试数据
     */
    @GetMapping("batchSave")
    public void batchSave() {
        for (int i = 0; i < 100; i++) {
            ItemByEsVo itemByEsVo = new ItemByEsVo();
            itemByEsVo.setTitle("华为手机" + i);
            itemByEsVo.setBrand("华为");
            if(i > 50){
                itemByEsVo.setTitle("小米手机" + i);
                itemByEsVo.setBrand("小米");
            }
            itemByEsVo.setCategory("手机");
            itemByEsVo.setPrice(3499.00 + i);
            itemByEsVo.setImages("http://image.baidu.com/13123.jpg");
            itemByEsService.save(itemByEsVo);
        }
    }

    /**
     * 删除所有
     */
    @GetMapping("deleteAll")
    public void deleteAll() {
        list(new ItemByEsVo()).getData().forEach((itemByEsVo)->{
            delete(itemByEsVo.getId());
        });
    }
}
