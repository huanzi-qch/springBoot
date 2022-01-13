package cn.huanzi.qch.springbootjpa.tbuser.repository;

import cn.huanzi.qch.springbootjpa.common.repository.CommonRepository;
import cn.huanzi.qch.springbootjpa.tbuser.pojo.Model;
import cn.huanzi.qch.springbootjpa.tbuser.pojo.TbUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TbUserRepository extends CommonRepository<TbUser, Integer> {

    //自定义字段查询 HQL
    @Query("select new cn.huanzi.qch.springbootjpa.tbuser.pojo.Model(u.username,u.descriptionId,d.description) from TbUser u join TbDescription d on u.descriptionId = d.id where u.id = :id ")
    List<Model> findByModel(@Param("id")int id);

    //自定义字段查询 原生SQL
    @Query(value = "select u.username,u.description_id,d.description from tb_User u join tb_Description d on u.description_id = d.id where u.id = :id",nativeQuery = true)
    List<Map<String, Object>> findByMap(@Param("id")int id);
}
