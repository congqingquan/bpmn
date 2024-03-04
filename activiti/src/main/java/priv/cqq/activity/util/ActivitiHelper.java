package priv.cqq.activity.util;

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
import priv.cqq.activity.constans.BPMNFileEnums;

/**
 * Activiti helper
 *
 * @author Qingquan.Cong
 */
public class ActivitiHelper {

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
}