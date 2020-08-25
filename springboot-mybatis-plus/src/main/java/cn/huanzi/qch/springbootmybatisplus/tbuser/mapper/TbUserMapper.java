package cn.huanzi.qch.springbootmybatisplus.tbuser.mapper;

import cn.huanzi.qch.springbootmybatisplus.common.mapper.CommonMapper;
import cn.huanzi.qch.springbootmybatisplus.tbuser.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author huanzi-qch
 * @since 2020-08-25
 */
@Mapper
public interface TbUserMapper extends CommonMapper<TbUser> {

}

