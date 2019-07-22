package cn.huanzi.qch.springbootsecurity.sysuserauthority.repository;

import cn.huanzi.qch.springbootsecurity.common.repository.CommonRepository;
import cn.huanzi.qch.springbootsecurity.sysuserauthority.pojo.SysUserAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserAuthorityRepository extends CommonRepository<SysUserAuthority, String> {
    List<SysUserAuthority> findByUserId(String userId);
}
