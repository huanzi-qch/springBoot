package cn.huanzi.qch.springboottimer;

import cn.huanzi.qch.springboottimer.task.TbTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * TbTask动态定时任务Repository
 */
@Repository
public interface TbTaskRepository extends JpaRepository<TbTask,String>, JpaSpecificationExecutor<TbTask> {
}
