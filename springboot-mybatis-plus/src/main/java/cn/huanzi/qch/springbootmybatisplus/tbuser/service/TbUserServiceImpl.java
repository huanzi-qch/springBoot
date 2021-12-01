package cn.huanzi.qch.springbootmybatisplus.tbuser.service;

import cn.huanzi.qch.springbootmybatisplus.common.entity.Result;
import cn.huanzi.qch.springbootmybatisplus.common.service.CommonServiceImpl;
import cn.huanzi.qch.springbootmybatisplus.tbuser.entity.TbUser;
import cn.huanzi.qch.springbootmybatisplus.tbuser.entity.TbUserVo;
import cn.huanzi.qch.springbootmybatisplus.tbuser.mapper.TbUserMapper;
import cn.huanzi.qch.springbootmybatisplus.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public Result<TbUserVo> get(String id) {
        Map<String,Object> select = tbuserMapper.executeNativeSqlOfOne("select u.id,u.username,d.description from tb_user u join tb_description d on u.description_id = d.id where u.id = '" + id + "'");
        List<Map<String,Object>> select1 = tbuserMapper.executeNativeSqlOfList("select * from tb_user");

        TbUser tbUser = tbuserMapper.executeNativeSqlFindOne("select * from tb_user where id = '"+id+"'");
        return Result.build(CopyUtil.copy(tbUser,TbUserVo.class));
    }
}
