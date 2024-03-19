package priv.cqq.activity;

import cn.hutool.core.map.MapBuilder;
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
public class RollbackProcessTest {
    
    @Resource
    private RepositoryService repositoryService;
    
    @Resource
    private RuntimeService runtimeService;
    
    @Resource
    private TaskService taskService;
    
    @Resource
    private HistoryService historyService;
    
    @Test
    public void rollbackProcess() throws InterruptedException {
        ActivitiHelper.clear();
        
        BPMNFileEnums bpmnFileEnums = BPMNFileEnums.ROLLBACK_PROCESS;
        
        ActivitiHelper.generateSVGFile(bpmnFileEnums.getFileClasspath(), "processes", () -> "image-" + System.currentTimeMillis() + ".svg");
        
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
        
        boolean agree = false;
        
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
                
                ActivitiHelper.generateSVGFile(processInstance, true, "processes", () -> "image-" + System.currentTimeMillis() + ".svg");
                
                if ("request-approval".equals(task.getTaskDefinitionKey())) {
                    // 发现一个细节：局部变量会被同步到退回任务节点
                    taskService.complete(
                            task.getId(),
                            MapBuilder.<String, Object>create().put("approvalStatus", agree ? "AGREE" : "REJECT").map(),
                            // 必须设置为局部变量，测试来看：全局变量的值不会被 complete 时指定的同名变量覆盖
                            // false
                            true
                    );
                    agree = !agree;
                    continue;
                }
                taskService.complete(task.getId());
            }
        }
    }
}