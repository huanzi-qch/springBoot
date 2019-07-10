package cn.huanzi.qch.springbootmybatis.mapper;

import cn.huanzi.qch.springbootmybatis.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "UserMapper")
public interface UserMapper {

    /**
     * 增
     */
//    @Insert(value = "insert into tb_user(username,password,created) value(#{username},#{password},#{created})")
//    @Options(useGeneratedKeys = true)
    int insert(User user);

    /**
     * 删
     */
//    @Delete("delete from tb_user where id = #{id}")
    int delete(User user);

    /**
     * 改
     */
//    @Update(value = "update tb_user set username = #{username} where id = #{id}")
    int update(User user);

    /**
     * 查
     */
//    @Select(value = "select * from tb_user where id = #{id}")
    User select(User user);

}
