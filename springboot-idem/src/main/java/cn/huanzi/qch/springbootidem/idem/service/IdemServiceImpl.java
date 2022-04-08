package cn.huanzi.qch.springbootidem.idem.service;

import cn.huanzi.qch.springbootidem.idem.entity.Idem;
import cn.huanzi.qch.springbootidem.idem.mapper.IdemMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class IdemServiceImpl implements IdemService{

    private final IdemMapper idemMapper;

    public IdemServiceImpl(IdemMapper idemMapper) {
        this.idemMapper = idemMapper;
    }

    /**
     * 插入操作，使用唯一主键实现幂等性
     */
    @Override
    public Object insert(Idem idem) {
        String msg = "操作成功！";
        try{
            idemMapper.insert(idem);
        }catch (DuplicateKeyException e){
            msg = "操作失败，id："+idem.getId()+"，已经存在...";
        }
        return msg;
    }

    /**
     * 删除操作，使用唯一主键实现幂等性
     * PS：使用非主键条件除外
     */
    @Override
    public Object delete(Idem idem) {
        String msg = "操作成功！";
        int deleteById = idemMapper.deleteById(idem.getId());
        if(deleteById == 0){
            msg = "操作失败，id："+idem.getId()+"，已经被删除...";
        }
        return msg;
    }

    /**
     * 更新操作，使用乐观锁实现幂等性
     */
    @Override
    public Object update(Idem idem) {
        String msg = "操作成功！";

        // UPDATE table SET [... 业务字段=? ...], version = version+1 WHERE (id = ? AND version = ?)
        UpdateWrapper<Idem> updateWrapper = new UpdateWrapper<>();

        //where条件
        updateWrapper.eq("id",idem.getId());
        updateWrapper.eq("version",idem.getVersion());

        //version版本号要单独设置
        updateWrapper.setSql("version = version+1");
        idem.setVersion(null);

        int update = idemMapper.update(idem, updateWrapper);
        if(update == 0){
            msg = "操作失败，id："+idem.getId()+"，已经被更新...";
        }

        return msg;
    }

    /**
     * 查询操作，天生幂等性
     */
    @Override
    public Object select(Idem idem) {
        QueryWrapper<Idem> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(idem);
        return idemMapper.selectList(queryWrapper);
    }
}
