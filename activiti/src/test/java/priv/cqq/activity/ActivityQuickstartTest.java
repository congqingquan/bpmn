package priv.cqq.activity;

import cn.hutool.core.map.MapBuilder;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import priv.cqq.activity.constans.BPMNFileEnums;
import priv.cqq.activity.util.ActivitiHelper;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activiti quickstart
 *
 * @author Qingquan.Cong
 */
@SpringBootTest
public class ActivityQuickstartTest {
    
    // ============================================ Engine ============================================
    
    @Test
    public void createProcessEngine() {
        // Auto create table
        ProcessEngines.getDefaultProcessEngine();
    }
    
    // ============================================ Deploy: RepositoryService ============================================
    
    @Resource
    private RepositoryService repositoryService;
    
    @Test
    public void deployment() {
        // 1. 流程部署 (注意：同时会创建部署信息、流程定义信息数据)
        Deployment deployment = repositoryService
                .createDeployment()
                // 1) 流数据加载
                // .addInputStream()
                
                // 2) 压缩包加载
                // .addZipInputStream()
                
                // 3) classpath 加载
                .addClasspathResource(BPMNFileEnums.WITHDRAWAL.getFileClasspath())
                .addClasspathResource(BPMNFileEnums.WITHDRAWAL.getPicClasspath())
                .name(BPMNFileEnums.WITHDRAWAL.getName())
                .deploy();
        System.out.println(deployment);
        
        // 2.
        // 2.1 查询部署信息
        Deployment queriedDeployment = repositoryService
                .createDeploymentQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("部署主键：" + queriedDeployment.getId());
        System.out.println("部署名称：" + queriedDeployment.getName());
        System.out.println("部署KEY：" + queriedDeployment.getKey());
        
        // 2.2 查询与部署信息对应的流程定义信息
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("流程定义主键：" + processDefinition.getId());
        System.out.println("流程定义名称：" + processDefinition.getName());
        System.out.println("流程定义KEY：" + processDefinition.getKey() + "\t" + "枚举 Key: " + BPMNFileEnums.WITHDRAWAL.getProcessDefinitionId());
        
        // 3. 删除部署信息、流程定义信息
        // List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
        // for (Deployment d : deploymentList) {
        // cascade: 同时删除正在运行的任务实例数据
        // repositoryService.deleteDeployment(d.getId(), true);
        // }
    }
    
    @Test
    public void clear() {
        // 1. 删除部署信息、流程定义信息
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
        for (Deployment d : deploymentList) {
            // cascade: 同时删除正在运行的任务实例数据
            repositoryService.deleteDeployment(d.getId(), true);
        }
    }
    
    // ============================================ Processes: RuntimeService ============================================
    
    @Resource
    private RuntimeService runtimeService;
    
    @Test
    public void process() {
        // clear();
        
        // 1. 根据部署信息获取对应的流程定义信息
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
        
        // 2. 启动流程实例
        Map<String, Object> variables = MapBuilder.<String, Object>create()
                .put("employee", "employee-CQQ")
                .put("manager", "manager-CQQ")
                .put("financialAccounting", "financial-accounting-CQQ")
                .map();
        
        // 2.1 根据流程定义主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
        // 2.2 根据流程定义Key
        // ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinition.getKey());
        
        System.out.println("流程实例主键：" + processInstance.getId());
        System.out.println("流程实例主键：" + processInstance.getProcessInstanceId());
        System.out.println("流程实例名称：" + processInstance.getName());
        System.out.println("流程实例描述：" + processInstance.getDescription());
        
        // 3. 查询流程实例
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId())
                .list();
        
        for (ProcessInstance instance : processInstanceList) {
            System.out.println("流程实例主键：" + instance.getId());
            System.out.println("流程实例主键：" + instance.getProcessInstanceId());
            System.out.println("流程实例名称：" + instance.getName());
            System.out.println("流程实例描述：" + instance.getDescription());
        }
        
        // 4. 删除流程实例 (Activiti 会进行级联的对流程以及相关的表进行逻辑删除，如：ACT_HI_PROCINST/ACT_HI_ACTINST/ACT_HI_TASKINST/ACT_RU_EXECUTION...，如果涉及变量，还会删除变量表)
        runtimeService.deleteProcessInstance(
                processInstance.getProcessInstanceId(),
                "Cancel process: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }
    
    
    // 创建流程实例
    public ProcessInstance createProcessInstance(BPMNFileEnums bpmnFileEnums) {
        return createProcessInstance(bpmnFileEnums, new HashMap<>());
    }
    
    public ProcessInstance createProcessInstance(BPMNFileEnums bpmnFileEnums, Map<String, Object> variables) {
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
        
        return runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
    }
    
    // ============================================ Task: TaskService ============================================
    
    @Resource
    private TaskService taskService;
    
    @Test
    public void task() throws IOException {
        // act_ru_task
        // 1. 查询任务
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId("2501")
                .list();
        for (Task task : taskList) {
            System.out.println("流程实例主键: " + task.getProcessInstanceId());
            System.out.println("任务主键: " + task.getId());
            System.out.println("任务名称: " + task.getName());
        }
        // 2. 完成任务
        Task lastTask = taskList.get(taskList.size() - 1);
        taskService.complete(lastTask.getId());
    }
    
    @Test
    public void setAssignee() {
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
        
        // 1. 在流程创建的时候就初始化所有被托人 (会新增操作表: act_hi_detail / act_hi_varinst / act_ru_variable), 每个结点都会
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(
                processDefinition.getId(),
                MapBuilder.<String, Object>create()
                        .put("employee", "employee-CQQ")
                        .put("manager", "manager-CQQ")
                        // .put("financialAccounting", "financial-accounting-CQQ")
                        .map()
        );
        
        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务被托人: " + task.getAssignee());
                // 2. 否则在完成前一个任务节点的时候，设置下一个任务节点的被托人
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
            
            // clear();
        }
    }
    
    @Test
    public void queryAssignee() {
        ProcessDefinition latestProcessDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(BPMNFileEnums.WITHDRAWAL.getProcessDefinitionId())
                .latestVersion()
                .singleResult();
        
        List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionId(latestProcessDefinition.getId())
                .list();
        
        String processInstanceId = historicProcessInstanceList.get(0).getId();
        
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskAssignee("manager")
                .list();
        
        for (Task task : taskList) {
            System.out.println("流程实例主键: " + task.getProcessInstanceId());
            System.out.println("任务主键: " + task.getId());
            System.out.println("任务名称: " + task.getName());
            System.out.println("任务被托人: " + task.getAssignee());
        }
    }
    
    @Test
    public void candidateUser() {
        clear();
        
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
            System.out.println("任务被托人: " + task.getAssignee());
            
            // 设置候选人为 c2
            // 1. 候选人节点不能设置默认的被托人，否则将某个候选人设置为被托人时会提示：Task '77516' is already claimed by someone else.
            // 2. 设置完候选人节点后，该节点可再次被查询出进行执行
            if ("candidate-user-node-id".equals(task.getTaskDefinitionKey()) && StringUtils.isBlank(task.getAssignee())) {
                // 3. 如果设置的被托人不为 c2，也不会报错
                // taskService.claim(task.getId(), "c3");
                taskService.claim(task.getId(), "c2");
                // 4. 任务节点分配完被托人为候选人后，在根据候选人查询任务列表，就查不出来了
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
                System.out.println("任务被托人: " + task.getAssignee());
                taskService.complete(task.getId());
            }
        }
    }
    
    @Test
    public void comment() {
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
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(
                processDefinition.getId(),
                MapBuilder.<String, Object>create()
                        .put("employee", "employee-CQQ")
                        .put("manager", "manager-CQQ")
                        .put("financialAccounting", "financial-accounting-CQQ")
                        .map()
        );
        
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
        
        for (Task task : taskList) {
            taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), "MyCommentType", "测试评论");
        }
        
        // 默认查询 TYPE_ = 'comment' 的评论：select * from ACT_HI_COMMENT where TASK_ID_ = ? and TYPE_ = 'comment' order by TIME_ desc
        for (Task task : taskList) {
            List<Comment> taskComments = taskService.getTaskComments(task.getId());
            for (Comment taskComment : taskComments) {
                System.out.println("评论所属任务主键：" + taskComment.getTaskId());
                System.out.println("评论主键：" + taskComment.getId());
                System.out.println("评论类型：" + taskComment.getType());
                System.out.println("评论内容：" + taskComment.getFullMessage());
                System.out.println("评论用户主键：" + taskComment.getUserId());
            }
        }
        
        // 查询自定义类型评论
        for (Task task : taskList) {
            List<Comment> taskComments = taskService.getTaskComments(task.getId(), "MyCommentType");
            for (Comment taskComment : taskComments) {
                System.out.println("评论所属任务主键：" + taskComment.getTaskId());
                System.out.println("评论主键：" + taskComment.getId());
                System.out.println("评论类型：" + taskComment.getType());
                System.out.println("评论内容：" + taskComment.getFullMessage());
                System.out.println("评论用户主键：" + taskComment.getUserId());
            }
        }
    }
    
    // ============================================ History: HistoryService ============================================
    
    @Resource
    private HistoryService historyService;
    
    @Test
    public void history() {
        // 0. 查询最新的流程定义
        ProcessDefinition latestProcessDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(BPMNFileEnums.WITHDRAWAL.getProcessDefinitionId())
                .latestVersion()
                .singleResult();
        
        // 1. 查询最新的流程定义的历史流程实例
        List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionId(latestProcessDefinition.getId())
                .list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstanceList) {
            System.out.println("历史流程实例主键：" + historicProcessInstance.getId());
            System.out.println("历史流程实例名称：" + historicProcessInstance.getName());
            System.out.println("历史流程实例所属定义主键：" + historicProcessInstance.getProcessDefinitionId());
            System.out.println("历史流程实例所属定义Key：" + historicProcessInstance.getProcessDefinitionKey());
        }
        
        // 2. 查询最新的流程定义的 首个历史流程实例的 历史活动节点
        String processInstanceId = historicProcessInstanceList.get(0).getId();
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            System.out.println("历史活动节点主键：" + historicActivityInstance.getId());
            System.out.println("历史活动节点活动主键(bpmn xml 中 task 标签上的 id 属性值)：" + historicActivityInstance.getId());
            System.out.println("历史活动节点活动名称：" + historicActivityInstance.getActivityName());
            System.out.println("历史活动节点活动类型：" + historicActivityInstance.getActivityType());
            System.out.println("历史活动节点活动受托人：" + historicActivityInstance.getAssignee());
        }
        
        // 3. 查询历史流程实例的历史任务
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {
            System.out.println("历史任务主键：" + historicTaskInstance.getId());
            System.out.println("历史任务名称：" + historicTaskInstance.getName());
            System.out.println("历史任务受托人：" + historicTaskInstance.getAssignee());
            System.out.println("历史任务开始时间：" + historicTaskInstance.getStartTime());
            System.out.println("历史任务结束时间：" + historicTaskInstance.getEndTime());
        }
    }
    
    // ============================================ ManagementService ============================================
    
    //    @Resource
    //    private ManagementService managementService;
    
    // ============================================ DynamicBpmnService ============================================
    
    //    @Resource
    //    private DynamicBpmnService dynamicBpmnService;
    
    // ============================================ Listener ============================================
    
    /**
     * 可监听的任务状态：
     * 1. create: 创建任务
     * 2. assignment: 分配被托人
     * 3. complete: 完成任务
     * 4. delete: 删除任务
     * <p>
     * 可监听的执行流状态：
     * 1. start: 任务节点前
     * 2. end: 任务节点后
     * 3. take: 节点间的连线
     * <p>
     * 执行流监听与任务监听间的执行顺序：
     * 1. 开始节点：start -> end
     * 2. 连线：take
     * 3. 任务节点ExecutionListener： start
     * 4. 任务节点TaskListener：create -> assignment -> complete
     * 5. 任务节点ExecutionListener： end
     * 6. 连线：take
     * 7. 结束节点：start -> end
     */
    @Test
    public void taskExecutionListener() {
        clear();
        
        ProcessInstance processInstance = createProcessInstance(BPMNFileEnums.TASK_EXECUTION_LISTENER, new HashMap<>());
        
        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务被托人: " + task.getAssignee());
                taskService.complete(task.getId());
            }
        }
    }
    
    // ============================================ Variables ============================================
    
    @Test
    public void vars() {
        clear();
        
        ProcessInstance processInstance = createProcessInstance(BPMNFileEnums.PROCESS_VARIABLE);
        
        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务被托人: " + task.getAssignee());
                
                ActivitiHelper.generateByProcessInstanceIdForTest(processInstance, true);
                
                if ("set-global-var-task-id".equals(task.getTaskDefinitionKey())) {
                    // localScope: 默认为 false，即只在当前 task 范围内生效
                    taskService.complete(task.getId(), MapBuilder.<String, Object>create().put("myVar", 5).map(), false);
                    continue;
                }
                taskService.complete(task.getId());
            }
        }
    }
    
    // ============================================ Gateway ============================================
    
    /**
        排他网关的逻辑等价于：
     
        if() {}
        else if() {}
        else throw new Exception("No outgoing sequence flow of the exclusive gateway 'sid-9F9EB010-CBB0-449B-ABC6-96663081191C' could be selected for continuing the process")
     
        特别说明：在不使用排他网关，直接在 line 上设置 condition expression，若也没有找到匹配的 line 也会抛出上面的异常。所以可以理解为：默认就是排他网关。
     */
    @Test
    public void exclusiveGateway() {
        clear();
        
        ProcessInstance processInstance = createProcessInstance(BPMNFileEnums.EXCLUSIVE_GATEWAY);
        
        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务被托人: " + task.getAssignee());
                
                ActivitiHelper.generateByProcessInstanceIdForTest(processInstance, true);
                
                if ("submit-leave-approve-task-id".equals(task.getTaskDefinitionKey())) {
                    // No outgoing sequence flow of the exclusive gateway 'sid-9F9EB010-CBB0-449B-ABC6-96663081191C' could be selected for continuing the process
                    // taskService.complete(task.getId(), MapBuilder.<String, Object>create().put("day", 5).map(), false);
                    
                    taskService.complete(task.getId(), MapBuilder.<String, Object>create().put("day", 10).map(), false);
                    continue;
                }
                taskService.complete(task.getId());
            }
        }
    }
    
    /**
        并行网关的逻辑等价于：
        
        if (true) {}
        if (true) {}
        ...
     
        即多链路都会无条件执行，即使设置了 condition expression，也不会进行判断。
     
        并行网关在数据上的体现：经过并行网关后，第一节点继续在当前执行实例上执行，剩余的其他链路会独立创建各自的执行实例执行到最后。也就是说后继的任务节点会被各个执行实例都执行一次。
        可以关注一下表中的数据：
         ACT_HI_ACTINST 表记录了所有实例经历的详细的任务节点（包含startEvent、parallelGateway、endEvent 这样的非用户任务节点）
         ACT_HI_TASKINST 表记录了所有实例经历的用户任务节点（如 userTask 这样的任务节点。不包含网关、起始事件、结束事件这样的系统功能性节点）
        注意表中的 execution_id，并关联 ACT_RU_EXECUTION 表查看各个任务节点与执行实例的关系，观察各个并行链路的执行实例是否是不同的。
     */
    @Test
    public void parallelGateway() {
        clear();
        
        ProcessInstance processInstance = createProcessInstance(BPMNFileEnums.PARALLEL_GATEWAY);
        
        for (List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
             !CollectionUtils.isEmpty(taskList);
             taskList = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list()
        ) {
            // 执行完并行网关后，会发现 taskList 的 size 为 2，即多个链路的任务都会被查询出来
            for (Task task : taskList) {
                System.out.println("流程实例主键: " + task.getProcessInstanceId());
                System.out.println("任务主键: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务被托人: " + task.getAssignee());
                
                ActivitiHelper.generateByProcessInstanceIdForTest(processInstance, true);
                
                if ("submit-leave-approve-task-id".equals(task.getTaskDefinitionKey())) {
                    // 测试 line 上的 condition expression 是否会生效
                    taskService.complete(task.getId(), MapBuilder.<String, Object>create().put("day", 5).map(), false);
                    continue;
                }
                taskService.complete(task.getId());
            }
        }
    }
    
    // TODO 1. 不在多个 line 上配置 condition，也不用排他网关，是否效果与排他网关相同。（注意下 ACT_HI_ACTINST 表中是否也存储了网关节点数据）
    // TODO 如果推测正确，则说明网关的意义只是标明下如何看待后面 line 上的 condition expression，activiti 是否要进行使用。
    // TODO 比如并行网关，即使配置了 condition 也会被直接忽略。(3. 排他网关，如果没有配置 condition 又会怎样？)
    // TODO 反之如果没有配置网关，则必须读取 line 上的 condition expression 进行执行。
    
    // TODO 2. 排他网关（以及不使用排他网关）是否会执行多个 condition 为 true 的链路。
    
    /**
        包含网关：包含网关是排他、并行网关的结合。具体是什么网关，取决于是否在 line 上配置了 condition expression。配置了则为排他网关，否则为并行网关。
     */
    @Test
    public void inclusiveGateway() {}
}