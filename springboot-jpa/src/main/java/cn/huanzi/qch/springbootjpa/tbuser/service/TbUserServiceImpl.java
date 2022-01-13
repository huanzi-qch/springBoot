package cn.huanzi.qch.springbootjpa.tbuser.service;

import cn.huanzi.qch.springbootjpa.common.pojo.Result;
import cn.huanzi.qch.springbootjpa.common.service.CommonServiceImpl;
import cn.huanzi.qch.springbootjpa.tbuser.pojo.Model;
import cn.huanzi.qch.springbootjpa.tbuser.pojo.TbUser;
import cn.huanzi.qch.springbootjpa.tbuser.repository.TbUserRepository;
import cn.huanzi.qch.springbootjpa.tbuser.vo.TbUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TbUserServiceImpl extends CommonServiceImpl<TbUserVo, TbUser, Integer> implements TbUserService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private TbUserRepository tbUserRepository;

    @Override
    public Result<TbUserVo> get(Integer id) {
        //自定义字段查询 方法调用
        List<Model> modelList = tbUserRepository.findByModel(id);
        modelList.forEach(System.out::println);

        List<Map<String, Object>> mapList = tbUserRepository.findByMap(id);
        mapList.forEach(System.out::println);
        return super.get(id);
    }
}
