package cn.huanzi.qch.springbootsecurity.sysauthority.repository;

import cn.huanzi.qch.springbootsecurity.common.repository.*;
import cn.huanzi.qch.springbootsecurity.sysauthority.pojo.SysAuthority;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAuthorityRepository extends CommonRepository<SysAuthority, String> {
}
