package cn.huanzi.qch.springbootactiviti7;

import org.activiti.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class  SpringbootActiviti7ApplicationTests {

    @Autowired
    private RepositoryService repositoryService;


    /**
     * 流程部署
     */
    @Test
    public void test() {
        repositoryService.createDeployment()
                .addClasspathResource("bpm/askForLeaveBpm.bpmn")
                .name("请假流程")
                .key("ASK_FOR_LEAVE_ACT")
                .deploy();

        System.out.println("流程部署成功！");
    }
}
