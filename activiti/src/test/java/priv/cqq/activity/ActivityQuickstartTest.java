package priv.cqq.activity;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import priv.cqq.activity.constans.BPMNFileEnums;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    // ============================================ Processes: RuntimeService ============================================

    // RuntimeService runtimeService = ActivitiHelper.getProcessEngine().getRuntimeService();

    @Resource
    private RuntimeService runtimeService;

    @Test
    public void process() {
        // 1. 根据部署信息获取对应的流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("8f6216eb-dada-11ee-a27d-20c19b7a45c3")
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

        // TODO
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId())
                .list();
        for (ProcessInstance instance : processInstanceList) {
            System.out.println(instance);
        }

        runtimeService.deleteProcessInstance(
                processInstance.getProcessInstanceId(),
                "Test" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    // ============================================ Task: TaskService ============================================

    // TaskService taskService = ActivitiHelper.getProcessEngine().getTaskService();

    @Resource
    private TaskService taskService;

    // ============================================ History: HistoryService ============================================

    // HistoryService historyService = ActivitiHelper.getProcessEngine().getHistoryService();

    @Resource
    private HistoryService historyService;

    // ============================================ ProcessDiagramGenerator ============================================

    /**
     生成流程图 SVG 图片

     <dependency>
     <groupId>org.activiti</groupId>
     <artifactId>activiti-image-generator</artifactId>
     <version>${activiti-boot-starter.version}</version>
     </dependency>
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