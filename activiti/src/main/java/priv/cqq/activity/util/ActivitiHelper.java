package priv.cqq.activity.util;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import priv.cqq.activity.constans.BPMNFileEnums;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * Activiti helper
 *
 * @author Qingquan.Cong
 */
public class ActivitiHelper {
    
    private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
    
    // ======================================== Engines ========================================
    
    private static final ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    private static final ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
    
    // ======================================== Service ========================================
    
    private static final RepositoryService repositoryService = processEngine.getRepositoryService();
    
    private static final RuntimeService runtimeService = processEngine.getRuntimeService();
    
    private static final TaskService taskService = processEngine.getTaskService();
    
    private static final HistoryService historyService = processEngine.getHistoryService();
    
    private static final ManagementService managementService = processEngine.getManagementService();
    
    private static final DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
    
    public static ProcessEngine getProcessEngine() {
        return processEngine;
    }
    
    
    public static Deployment deploy(BPMNFileEnums bpmnFileEnum) {
        return repositoryService.createDeployment()
                .addClasspathResource(bpmnFileEnum.getFileClasspath())
                .addClasspathResource(bpmnFileEnum.getPicClasspath())
                .name(bpmnFileEnum.getName())
                .deploy();
    }

    public static void clear() {
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
        for (Deployment d : deploymentList) {
            // cascade: 同时删除正在运行的任务实例数据
            repositoryService.deleteDeployment(d.getId(), true);
        }
    }
    
    // =========================== Process diagram generate ===========================
    
    public static InputStream generateBytesByXMLFile(String bpmnFileClassPath) {
        try (InputStream bpmnStream = MethodHandles.lookup().getClass().getClassLoader().getResourceAsStream(bpmnFileClassPath)) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(bpmnStream);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);
            ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
            return generator.generateDiagram(bpmnModel, "宋体", "宋体", "宋体");
        } catch (Exception exception) {
            throw new RuntimeException("Generate svg file error");
        }
    }
    
    public static void generateByXMLFileForTest(BPMNFileEnums bpmnFileEnum) {
        try (InputStream bpmnStream = MethodHandles.lookup().getClass().getClassLoader().getResourceAsStream(bpmnFileEnum.getFileClasspath())) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(bpmnStream);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);
            
            // 生成 SVG 图像
            if (bpmnModel != null && bpmnModel.getLocationMap().size() > 0) {
                // 创建图像生成器
                ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
                // 生成流程图 已启动的task 高亮
                //                return generator.generateDiagram(model,
                //                        runtimeService.getActiveActivityIds(processInstanceId));
                InputStream inputstream = generator.generateDiagram(bpmnModel, "宋体", "宋体", "宋体");
                String imageName = "image-" + Instant.now().getEpochSecond() + ".svg";
                FileUtils.copyInputStreamToFile(inputstream, new File("processes/" + imageName));
            }
            
            xmlStreamReader.close();
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException("Generate svg file error");
        }
    }
    
    public static void generateByProcessInstanceIdForTest(ProcessInstance processInstance, boolean noteActiveNode) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        try (InputStream inputStream = noteActiveNode ?
                generator.generateDiagram(bpmnModel, runtimeService.getActiveActivityIds(processInstance.getProcessInstanceId()), Collections.emptyList(), "宋体", "宋体", "宋体")
                : generator.generateDiagram(bpmnModel, "宋体", "宋体", "宋体");) {
            String imageName = "image-" + Instant.now().getEpochSecond() + ".svg";
            FileUtils.copyInputStreamToFile(inputStream, new File("processes/" + imageName));
        } catch (Exception e) {
            throw new RuntimeException("Generate svg file error");
        }
    }
}