package cn.huanzi.qch.springbootelasticsearch.itembyes.service;

import cn.huanzi.qch.springbootelasticsearch.common.pojo.PageInfo;
import cn.huanzi.qch.springbootelasticsearch.common.pojo.Result;
import cn.huanzi.qch.springbootelasticsearch.common.service.CommonService;
import cn.huanzi.qch.springbootelasticsearch.itembyes.pojo.ItemByEs;
import cn.huanzi.qch.springbootelasticsearch.itembyes.vo.ItemByEsVo;

public interface ItemByEsService  extends CommonService<ItemByEsVo,ItemByEs, String> {

    Result<PageInfo<ItemByEsVo>> searchByNative(ItemByEsVo itemByEsVo);
}
