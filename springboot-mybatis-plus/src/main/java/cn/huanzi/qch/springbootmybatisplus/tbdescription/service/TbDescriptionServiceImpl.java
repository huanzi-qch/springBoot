package cn.huanzi.qch.springbootmybatisplus.tbdescription.service;

import cn.huanzi.qch.springbootmybatisplus.common.service.CommonServiceImpl;
import cn.huanzi.qch.springbootmybatisplus.tbdescription.entity.TbDescription;
import cn.huanzi.qch.springbootmybatisplus.tbdescription.entity.TbDescriptionVo;
import cn.huanzi.qch.springbootmybatisplus.tbdescription.mapper.TbDescriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户描述表 服务实现类
 * </p>
 *
 * @author huanzi-qch
 * @since 2020-08-25
 */
@Service
public class TbDescriptionServiceImpl  extends CommonServiceImpl<TbDescriptionVo,TbDescription>  implements TbDescriptionService {

    @Autowired
    private TbDescriptionMapper tbdescriptionMapper;
}
