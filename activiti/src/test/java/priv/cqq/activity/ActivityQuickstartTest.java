package priv.cqq.activity;

import cn.hutool.core.map.MapBuilder;
import org.activiti.bpmn.model.BpmnModel;
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
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import priv.cqq.activity.constans.BPMNFileEnums;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class ActivityQuickstartTest {

    // ============================================ Engine ============================================

    @Test
    public void createProcessEngine() {
        // Auto create table
        ProcessEngines.getDefaultProcessEngine();
    }

    // ============================================ Deploy: RepositoryService ============================================


    // RepositoryService repositoryService = ActivitiHelper.getProcessEngine().getRepositoryService();

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

    // RuntimeService runtimeService = ActivitiHelper.getProcessEngine().getRuntimeService();

    @Resource
    private RuntimeService runtimeService;

    @Test
    public void process() {
        // 1. 根据部署信息获取对应的流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("1")
                .singleResult();

        // 2. 启动流程实例
        // 2.1 根据流程定义主键
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
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

//        runtimeService.deleteProcessInstance(
//                processInstance.getProcessInstanceId(),
//                "Test" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    // ============================================ Task: TaskService ============================================

    // TaskService taskService = ActivitiHelper.getProcessEngine().getTaskService();

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

    // HistoryService historyService = ActivitiHelper.getProcessEngine().getHistoryService();

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

    // ============================================ ProcessDiagramGenerator ============================================

    /**
     * 生成流程图 SVG 图片
     *
     * <dependency>
     * <groupId>org.activiti</groupId>
     * <artifactId>activiti-image-generator</artifactId>
     * <version>${activiti-boot-starter.version}</version>
     * </dependency>
     */
    @Test
    public void generateSVG() throws IOException {
        BpmnModel bpmnModel = repositoryService.getBpmnModel("withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3");
        ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        //Inputstream inputstream generator.generateDiagram(bpmnModel,lastTask,Collections.emptyList(),
        InputStream inputstream = generator.generateDiagram(bpmnModel, "endEvent", "宋体", "宋体");
        String imageName = "image-" + Instant.now().getEpochSecond() + "svg";
        FileUtils.copyInputStreamToFile(inputstream, new File("processes/" + imageName));
    }

    @Test
    public void generateCurrentSVG() throws IOException {

        ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();

        // 获取运行中的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("fc6319b9-dadb-11ee-a49c-20c19b7a45c3")
                .singleResult();

        // 获取运行中的流程实例的历史节点列表
        List<HistoricActivityInstance> lastTasks =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .orderByHistoricActivityInstanceStartTime()
                        .desc()
                        .list();

        // 收集最后一个历史活动节点的主键
        List<String> lastTask = lastTasks.stream()
                .map(HistoricActivityInstance::getActivityId)
                .limit(1)
                .collect(Collectors.toList());

        //七个参数分别是：
        // BPMNModel
        // 高光节点
        // 高光顺序流
        // 活动字体名称
        // 标签字体名称
        // 批注字体名称
        // 生成默认关系图
        // 默认关系图映像文件名

        BpmnModel model = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        InputStream inputstream = generator.generateDiagram(
                model, lastTask, Collections.emptyList(), "宋体", "宋体", "宋体", true, "test");
        String imageName = "image-" + Instant.now().getEpochSecond() + ".svg";
        FileUtils.copyInputStreamToFile(inputstream, new File("processes/" + imageName));
    }
}