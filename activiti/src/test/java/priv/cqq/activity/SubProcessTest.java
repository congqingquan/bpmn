package priv.cqq.activity;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import priv.cqq.activity.constans.BPMNFileEnums;
import priv.cqq.activity.util.ActivitiHelper;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class SubProcessTest {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;
    // ACT_HI_ACTINST / ACT_HI_TASKINST 等表中构建子流程的数据，但与外部流程仍属于同一个流程实例
    @Test
    public void subProcess() {
        ActivitiHelper.clear();

        BPMNFileEnums bpmnFileEnums = BPMNFileEnums.SUB_PROCESS;

        ActivitiHelper.generateByXMLFileForTest(bpmnFileEnums);

        Deployment deployment = repositoryService
                .createDeployment()
                .addClasspathResource(bpmnFileEnums.getFileClasspath())
                .addClasspathResource(bpmnFileEnums.getPicClasspath())
                .name(bpmnFileEnums.getName())
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .latestVersion()
                .singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务处理人: " + task.getAssignee());
                System.out.println("任务所属人: " + task.getOwner());
                System.out.println("任务委派状态: " + task.getDelegationState());

                ActivitiHelper.generateByProcessInstanceIdForTest(processInstance, true);

                taskService.complete(task.getId());
            }
        }
    }
}