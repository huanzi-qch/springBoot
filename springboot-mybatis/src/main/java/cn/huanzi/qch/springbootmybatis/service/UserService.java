package cn.huanzi.qch.springbootmybatis.service;


import cn.huanzi.qch.springbootmybatis.pojo.Result;
import cn.huanzi.qch.springbootmybatis.pojo.User;

public interface UserService {

    /**
     * 增
     */
    Result insert(User user);

    /**
     * 删
     */
    Result delete(User user);

    /**
     * 改
     */
    Result update(User user);

    /**
     * 查
     */
    Result select(User user);
}
