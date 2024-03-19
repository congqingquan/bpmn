package priv.cqq.activity;

import cn.hutool.core.map.MapBuilder;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import priv.cqq.activity.constans.BPMNFileEnums;
import priv.cqq.activity.util.ActivitiHelper;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class BusinessTest {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    // ============================================ Business ============================================

    // 1. 任务处理人(任务处理人): 设置任务的处理人(处理人)，并完成任务(taskService.complete)。
    // 处理人在act_ru_identitylink / act_hi_identitylink 任务人员表中的 type 为 participant
    @Test
    public void assignee() {
        ActivitiHelper.clear();

        Deployment deployment = repositoryService
                .createDeployment()
                .addClasspathResource(BPMNFileEnums.WITHDRAWAL.getFileClasspath())
                .addClasspathResource(BPMNFileEnums.WITHDRAWAL.getPicClasspath())
                .name(BPMNFileEnums.WITHDRAWAL.getName())
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .latestVersion()
                .singleResult();

        // Must set the property named employee on first node otherwise will throw the exception:  Unknown property used in expression: ${employee}

        // 1. 在流程创建的时候就初始化所有处理人 (会新增操作表: act_hi_detail / act_hi_varinst / act_ru_variable), 每个结点都会
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(
                processDefinition.getId(),
                MapBuilder.<String, Object>create()
                        .put("employee", "employee-CQQ")
                        .put("manager", "manager-CQQ")
                        // .put("financialAccounting", "financial-accounting-CQQ")
                        .map()
        );

        // 2. 执行后续的所有节点 (到达管理员审批节点时设置财务审批人)
        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务处理人: " + task.getAssignee());
                // 2. 否则在完成前一个任务节点的时候，设置下一个任务节点的处理人
                if ("withdrawal-manager-approve-task-id".equals(task.getTaskDefinitionKey())) {
                    taskService.complete(
                            task.getId(),
                            MapBuilder.<String, Object>create()
                                    .put("financialAccounting", "financial-accounting-CQQ")
                                    .map()
                    );
                    continue;
                }
                taskService.complete(task.getId());
            }
        }

        // 3. 查询处理人为 `manager-CQQ` 的任务节点
        List<HistoricTaskInstance> queryTaskList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .taskAssignee("manager-CQQ")
                .list();

        for (HistoricTaskInstance task : queryTaskList) {
            System.out.println("流程实例主键: " + task.getProcessInstanceId());
            System.out.println("任务主键: " + task.getId());
            System.out.println("任务名称: " + task.getName());
            System.out.println("任务处理人: " + task.getAssignee());
        }
    }

    // 2.1 任务候选人: 从单、多个委托人中选取一个作为任务的处理人(处理人)拾取任务，并完成任务(taskService.complete)
    // 候选人在act_ru_identitylink / act_hi_identitylink 任务人员表中的 type 为 candidate
    @Test
    public void candidateUser() {
        ActivitiHelper.clear();

        Deployment deployment = repositoryService
                .createDeployment()
                .addClasspathResource(BPMNFileEnums.CANDIDATE_USER.getFileClasspath())
                .addClasspathResource(BPMNFileEnums.CANDIDATE_USER.getPicClasspath())
                .name(BPMNFileEnums.CANDIDATE_USER.getName())
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .latestVersion()
                .singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(
                processDefinition.getId(),
                MapBuilder.<String, Object>create()
                        .put("myAssignee", "CQQ")
                        .map()
        );

        // 1. 完成第一个非候选人任务节点
        Task firsetTask = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        taskService.complete(firsetTask.getId());

        // 2. 查询候选人包含 c2 的任务节点
        List<Task> c2TaskList = taskService.createTaskQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .taskCandidateUser("c2")
                .list();

        for (Task task : c2TaskList) {
            System.out.println("流程实例主键: " + task.getProcessInstanceId());
            System.out.println("任务主键: " + task.getId());
            System.out.println("任务名称: " + task.getName());
            System.out.println("任务处理人: " + task.getAssignee());

            // 设置候选人为 c2
            // 1. 候选人节点不能设置默认的处理人，否则将某个候选人设置为处理人时会提示：Task '77516' is already claimed by someone else.
            // 2. 设置完候选人节点后，该节点可再次被查询出进行执行
            if ("candidate-user-node-id".equals(task.getTaskDefinitionKey()) && StringUtils.isBlank(task.getAssignee())) {
                // 3. 如果设置的处理人不为 c2，也不会报错
                // taskService.claim(task.getId(), "c3");
                taskService.claim(task.getId(), "c2");
                // 4. 任务节点分配完处理人为候选人后，在根据候选人查询任务列表，就查不出来了
                System.out.println("候选人为 c2 的任务节点：" +
                        taskService.createTaskQuery()
                                .processInstanceId(processInstance.getProcessInstanceId())
                                .taskCandidateUser("c2")
                                .list()
                                .size()
                );
            }
        }

        // 3. 完成后继的所有任务节点
        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务处理人: " + task.getAssignee());
                taskService.complete(task.getId());
            }
        }
    }

    // 2.2 候选人组：与候选人的区别仅仅是语义不同，由个体转为了组。设置处理人时，逻辑上从组成员中获取一个人员拾取任务，并完成任务。

    // 3. 任务委托人：将任务委托给其他人，受托人解决任务后(taskService.resolve)，在由委托人完成任务(taskService.complete)
    // 委托人在act_ru_identitylink / act_hi_identitylink 任务人员表中的 type 为 participant
    @Test
    public void delegateTask() {
        ActivitiHelper.clear();

        Deployment deployment = repositoryService
                .createDeployment()
                .addClasspathResource(BPMNFileEnums.TASK_DELEGATE.getFileClasspath())
                .addClasspathResource(BPMNFileEnums.TASK_DELEGATE.getPicClasspath())
                .name(BPMNFileEnums.TASK_DELEGATE.getName())
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

                if ("task-delegate-node-id".equals(task.getTaskDefinitionKey())) {
                    // 1. 将任务委托给他人处理:
                    // ACT_HI_ACTINST: 修改 ASSIGNEE_ 字段为受托人
                    // ACT_HI_TASKINST: 修改 ASSIGNEE_ 字段为受托人
                    // ACT_RU_TASK: 修改 OWNER_ 字段为委托人，ASSIGNEE_ 字段为受托人，DELEGATION_ 字段为 PENDING
                    if (null == task.getDelegationState()) {
                        taskService.delegateTask(task.getId(), "receiver-qqc");
                        continue;
                    }

                    // 2. 委托人解决任务：
                    // ACT_HI_ACTINST: 改回 ASSIGNEE_ 字段为委托人
                    // ACT_HI_TASKINST: 改回 ASSIGNEE_ 字段为委托人
                    // ACT_RU_TASK: 改回 ASSIGNEE_ 字段为受托人，DELEGATION_ 字段为 RESOLVED

                    // 受托人不能直接完成任务，需要交给所属人完成。
                    // 受托人直接完成任务会抛出：org.activiti.engine.ActivitiException: A delegated task cannot be completed, but should be resolved instead.
                    else if (DelegationState.PENDING == task.getDelegationState()) {
                        taskService.resolveTask(task.getId());
                        continue;
                    }
                }

                taskService.complete(task.getId());
            }
        }
    }
}