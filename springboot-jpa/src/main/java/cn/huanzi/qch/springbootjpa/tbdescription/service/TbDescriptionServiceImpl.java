package cn.huanzi.qch.springbootjpa.tbdescription.service;

import cn.huanzi.qch.springbootjpa.common.service.*;
import cn.huanzi.qch.springbootjpa.tbdescription.pojo.TbDescription;
import cn.huanzi.qch.springbootjpa.tbdescription.vo.TbDescriptionVo;
import cn.huanzi.qch.springbootjpa.tbdescription.repository.TbDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class TbDescriptionServiceImpl extends CommonServiceImpl<TbDescriptionVo, TbDescription, Integer> implements TbDescriptionService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private TbDescriptionRepository tbDescriptionRepository;
}
