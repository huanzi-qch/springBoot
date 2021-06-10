package cn.huanzi.qhc.springbootcache.tbuser.service;

import cn.huanzi.qhc.springbootcache.tbuser.pojo.TbUser;
import cn.huanzi.qhc.springbootcache.tbuser.repository.TbUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@CacheConfig(cacheNames={"myCache"})
public class TbUserServiceImpl implements TbUserService{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TbUserRepository tbUserRepository;

    //@Cacheable缓存数据：key为userList，value为返回值List<TbUser>
    @Cacheable(key = "'userList'")
    @Override
    public List<TbUser> list(TbUser entityVo) {
        System.out.println("获取list用户列表缓存数据,"+new Date());
        return tbUserRepository.findAll(Example.of(entityVo));
    }

    //@Cacheable缓存数据：key为参数id，value为返回值TbUser
    @Cacheable(key = "#id")
    @Override
    public TbUser get(Integer id) {
        System.out.println("获取数据缓存，key:"+id);
        Optional<TbUser> optionalE = tbUserRepository.findById(id);
        if (!optionalE.isPresent()) {
            throw new RuntimeException("ID不存在！");
        }
        return optionalE.get();
    }

    //@CachePut缓存新增的或更新的数据到缓存，其中缓存的名称为people，数据的key是person的id
    @CachePut(key = "#entityVo.id")
    // @CacheEvict从缓存中删除key为参数userList的数据
    @CacheEvict(key = "'userList'")
    @Override
    public TbUser save(TbUser entityVo) {
        System.out.println("新增/更新缓存，key:"+entityVo.getId());
        //entityVo传啥存啥，会全部更新
        return tbUserRepository.save(entityVo);
    }

    //清空所有缓存
    @CacheEvict(allEntries=true)
    @Override
    public Integer delete(Integer id) {
        System.out.println("清空所有缓存");
        tbUserRepository.deleteById(id);
        return id;
    }
}
