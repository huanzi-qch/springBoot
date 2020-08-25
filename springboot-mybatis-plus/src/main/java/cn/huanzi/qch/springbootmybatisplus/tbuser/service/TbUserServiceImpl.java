package cn.huanzi.qch.springbootmybatisplus.tbuser.service;

import cn.huanzi.qch.springbootmybatisplus.common.service.CommonServiceImpl;
import cn.huanzi.qch.springbootmybatisplus.tbuser.entity.TbUser;
import cn.huanzi.qch.springbootmybatisplus.tbuser.entity.TbUserVo;
import cn.huanzi.qch.springbootmybatisplus.tbuser.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author huanzi-qch
 * @since 2020-08-25
 */
@Service
public class TbUserServiceImpl  extends CommonServiceImpl<TbUserVo,TbUser>  implements TbUserService {

    @Autowired
    private TbUserMapper tbuserMapper;
}
