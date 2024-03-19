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
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

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
    
    public static InputStream getInputStreamOfBPMFile(String bpmnFileClassPath) {
        try (InputStream bpmnStream = MethodHandles.lookup().lookupClass().getClassLoader().getResourceAsStream(bpmnFileClassPath)) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(bpmnStream);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);
            xmlStreamReader.close();
            
            ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
            return generator.generateDiagram(bpmnModel, "宋体", "宋体", "宋体");
        } catch (Exception exception) {
            throw new RuntimeException("Get input stream of bpm file error");
        }
    }
    
    public static void generateSVGFile(String bpmnFileClassPath, String savePath, Supplier<String> fileNameSupplier) {
        try (InputStream bpmnStream = getInputStreamOfBPMFile(bpmnFileClassPath)) {
            FileUtils.copyInputStreamToFile(bpmnStream, new File(String.join("/", savePath, fileNameSupplier.get())));
        } catch (IOException ioException) {
            throw new RuntimeException("Generate svg file error");
        }
    }
    
    public static void generateSVGFile(ProcessInstance processInstance, boolean noteActiveNode,
                                       String savePath, Supplier<String> fileNameSupplier) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        try (InputStream inputStream = noteActiveNode ?
                generator.generateDiagram(bpmnModel, runtimeService.getActiveActivityIds(processInstance.getProcessInstanceId()), Collections.emptyList(), "宋体", "宋体", "宋体")
                : generator.generateDiagram(bpmnModel, "宋体", "宋体", "宋体")) {
            FileUtils.copyInputStreamToFile(inputStream, new File(String.join("/", savePath, fileNameSupplier.get())));
        } catch (Exception e) {
            throw new RuntimeException("Generate svg file error");
        }
    }
}