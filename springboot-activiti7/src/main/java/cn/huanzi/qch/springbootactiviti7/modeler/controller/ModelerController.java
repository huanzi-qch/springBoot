package cn.huanzi.qch.springbootactiviti7.modeler.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 流程引擎管理
 */
@RestController
@RequestMapping("/modeler/")
public class ModelerController {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("index")
    public ModelAndView index(){
        RepositoryService repositoryService = processEngine.getRepositoryService();

        List<Model> models = repositoryService.createModelQuery().list();
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();

        ModelAndView modelAndView = new ModelAndView("modeler");
        modelAndView.addObject("modelList",models);
        modelAndView.addObject("deploymentList",deploymentList);
        return modelAndView;
    }

    /**
     * 新建模型
     */
    @GetMapping("newModel")
    public void newModel(HttpServletResponse response) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Model model = repositoryService.newModel();
        //设置一些默认信息
        String name = "流程名称";
        String key = "流程关键字";
        String description = "流程描述";
        String revision = "1.0";

        model.setName(name);
        model.setKey(key);
        ObjectNode metaInfo = objectMapper.createObjectNode();
        metaInfo.put("name",name);
        metaInfo.put("description",description);
        metaInfo.put("revision",revision);
        model.setMetaInfo(metaInfo.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes(StandardCharsets.UTF_8));

        response.setStatus(302);
        response.addHeader("Location","URL");
        response.sendRedirect("/modeler.html?modelId="+id);
    }

    /**
     * 编辑模型
     */
    @GetMapping("editor/{id}")
    public void editor(@PathVariable("id")String id,HttpServletResponse response) throws IOException {
        response.setStatus(302);
        response.addHeader("Location","URL");
        response.sendRedirect("/modeler.html?modelId="+id);
    }

    /**
     * 删除模型
     */
    @GetMapping("delete/{id}")
    public String deleteModel(@PathVariable("id")String id){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteModel(id);
        return "操作成功！";
    }

    /**
     * 删除部署
     */
    @GetMapping("delete/deployment/{id}")
    public String deleteDeployment(@PathVariable("id")String id){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment(id);
        return "操作成功！";
    }

    /**
     * 发布模型为流程定义
     */
    @GetMapping("deployment/{id}")
    public String deploy(@PathVariable("id")String id) throws Exception {

        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
            return "数据模型不符要求，请至少设计一条主线流程。";
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .key(modelData.getKey())
                .addString(processName, new String(bpmnBytes, StandardCharsets.UTF_8))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return "操作成功！";
    }

}