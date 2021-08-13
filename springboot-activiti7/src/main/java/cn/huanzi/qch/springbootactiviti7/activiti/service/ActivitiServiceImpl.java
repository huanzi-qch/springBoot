package cn.huanzi.qch.springbootactiviti7.activiti.service;

import cn.huanzi.qch.springbootactiviti7.activiti.pojo.ActivitiVo;
import cn.huanzi.qch.springbootactiviti7.config.CustomProcessDiagramGenerator;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ActivitiServiceImpl implements ActivitiService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    @Override
    public void getFlowImgByInstanceId(String processInstanceId, OutputStream outputStream) {
        try {
            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService
                    .createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            // 获取流程中已经执行的节点，按照执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstances = historyService
                    .createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceEndTime()
                    .asc().list();
            // 高亮已经执行流程节点ID集合
            List<String> highLightedActivitiIds = new ArrayList<>();
            int size = historicActivityInstances.size();
            for (int i = 0; i < size; i++) {
                HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(i);
                String id = historicActivityInstance.getActivityId();
                if (size - i == 1) {
                    // 当前节点
                    id = id + "#";
                }
                highLightedActivitiIds.add(id);
            }

            // 使用自定义的程序图片生成器
            ProcessDiagramGenerator processDiagramGenerator = new CustomProcessDiagramGenerator();

            BpmnModel bpmnModel = repositoryService
                    .getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            // 高亮流程已发生流转的线id集合
            List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);

            // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
            InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel,
                    highLightedActivitiIds, highLightedFlowIds,"宋体","宋体","宋体");

            // 输出图片内容
            Integer byteSize = 1024;
            byte[] b = new byte[byteSize];
            int len;
            while ((len = imageStream.read(b, 0, byteSize)) != -1) {
                outputStream.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askForLeave(ActivitiVo activitiVo) {
        //查询已部署的流程
        DeploymentQuery query = repositoryService.createDeploymentQuery();
        List<Deployment> list = query.deploymentKey("流程关键字").list();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list1 = processDefinitionQuery.deploymentId(list.get(0).getId()).list();

        //指定流程执行人
        Map<String,Object> variables = new HashMap<>();
        variables.put("pm",activitiVo.getPm());//项目经理
        variables.put("bm",activitiVo.getBm());//部门经理
        variables.put("count",activitiVo.getCount());//请假天数

        //设置流程启动者
        Authentication.setAuthenticatedUserId(activitiVo.getUsername());

        //通过流程部署id启动
        runtimeService.startProcessInstanceById(list1.get(0).getId(),activitiVo.getBusinessKey(),variables);
    }

    @Override
    public ArrayList<Map> queryUserTaskByUserName(ActivitiVo activitiVo) {
        ArrayList<Map> list = new ArrayList<>();;
        taskService.createTaskQuery().taskAssignee(activitiVo.getUsername()).list().forEach((task)->{
            HashMap<String, Object> map = new HashMap<>();
            map.put("task",task);
            //根据任务ID获取当前流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            map.put("processInstance",historicProcessInstance);
            list.add(map);
        });
        return list;
    }

    @Override
    public ArrayList<Map> getHistoricProcessInstance(ActivitiVo activitiVo) {
        ArrayList<Map> list = new ArrayList<>();
        historyService.createHistoricProcessInstanceQuery().startedBy(activitiVo.getUsername()).list().forEach((historicProcessInstance)->{
            HashMap<String, Object> map = new HashMap<>();
            map.put("processInstance",historicProcessInstance);
            //根据流程实例ID获取当前任务
            Task task = taskService.createTaskQuery().processInstanceId(historicProcessInstance.getId()).active().singleResult();
            map.put("task",task);
            list.add(map);
        });
        return list;
    }

    @Override
    public void completeUserTaskById(ActivitiVo activitiVo) {
        Task task = taskService.createTaskQuery().taskId(activitiVo.getTaskId()).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        //设置用户
        Authentication.setAuthenticatedUserId(activitiVo.getUsername());

        //认领任务
        taskService.claim(activitiVo.getTaskId(),activitiVo.getUsername());

        //给当前任务添加批注信息
        taskService.addComment(activitiVo.getTaskId(), processInstanceId, activitiVo.getMessage());

        Map<String, Object> variables = new HashMap<>();
        //true/同意、false/拒绝
        variables.put("auditFlag",activitiVo.isAuditFlag());
        taskService.complete(activitiVo.getTaskId(), variables);
    }

    /**
     * 获取已经流转的线
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess()
                    .getFlowElement(historicActivityInstance.getActivityId(), true);
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess()
                    .getFlowElement(currentActivityInstance.getActivityId(), true);
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转：
             * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType())
                    || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess()
                            .getFlowElement(sequenceFlow.getTargetRef(), true);
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(targetFlowNode.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>(16);
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // 遍历匹配的集合，取得开始时间最早的一个
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }

                    highLightedFlowIds.add(highLightedFlowId);
                }

            }

        }
        return highLightedFlowIds;
    }

}
