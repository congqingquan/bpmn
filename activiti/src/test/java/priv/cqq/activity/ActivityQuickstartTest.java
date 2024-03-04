package priv.cqq.activity;

import cn.hutool.json.JSONUtil;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.DeploymentQueryProperty;
import org.activiti.engine.impl.ProcessInstanceQueryProperty;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import priv.cqq.activity.constans.BPMNFileEnums;
import priv.cqq.activity.util.ActivitiHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class ActivityQuickstartTest {

    // ============================================ Engine ============================================

    @Test
    public void createProcessEngine() {
        // Auto create table
        ProcessEngines.getDefaultProcessEngine();
    }

    // ============================================ Deploy ============================================

    //     SELECT
    //     *
    //     FROM
    //     act_re_deployment rd
    //     LEFT JOIN act_ge_bytearray gb ON gb.DEPLOYMENT_ID_ = rd.ID_
    @Test
    public void deployment() {
        Deployment deployment = ActivitiHelper.deploy(BPMNFileEnums.WITHDRAWAL);
        String deploymentStr = JSONUtil.toJsonStr(deployment);
        System.out.println(deploymentStr);
    }

    @Test
    public void deleteDeployment() {
        Deployment deployment = ActivitiHelper.deploy(BPMNFileEnums.WITHDRAWAL);
        ActivitiHelper.getProcessEngine().getRepositoryService().deleteDeployment(deployment.getId());
    }

    @Test
    public void queryDeploy() {
        List<Deployment> deploymentList = ActivitiHelper.getProcessEngine().getRepositoryService()
                .createDeploymentQuery()
                .deploymentName(BPMNFileEnums.WITHDRAWAL.getName())
                .orderBy(DeploymentQueryProperty.DEPLOYMENT_ID).desc()
                .list();
        String deploymentListStr = JSONUtil.toJsonStr(deploymentList);
        System.out.println(deploymentListStr);
    }

    // ============================================ Processes ============================================

    @Test
    public ProcessInstance startProcess() {
        // 注意：processInstance.getProcessDefinitionId() != BPMNFileEnums.WITHDRAWAL.getProcessDefinitionId()
        //      processInstance.getProcessDefinitionId() 会在尾部追加唯一值
        ProcessInstance processInstance =
                ActivitiHelper.getProcessEngine().getRuntimeService().startProcessInstanceByKey(BPMNFileEnums.WITHDRAWAL.getProcessDefinitionId());
        String processInstanceStr = JSONUtil.toJsonStr(processInstance);
        System.out.println(processInstanceStr);
        return processInstance;
    }

    @Test
    public void deleteProcess() {
        ProcessInstance processInstance =
                ActivitiHelper.getProcessEngine().getRuntimeService().startProcessInstanceByKey(BPMNFileEnums.WITHDRAWAL.getProcessDefinitionId());

        // same below two
        System.out.println("流程主键：" + processInstance.getId());
        System.out.println("流程主键：" + processInstance.getProcessInstanceId());
        System.out.println("流程定义主键：" + processInstance.getProcessDefinitionId());

        ActivitiHelper.getProcessEngine().getRuntimeService().deleteProcessInstance(
                processInstance.getProcessInstanceId(),
                "Test" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
        );
    }

    @Test
    public void queryProcess() {
        ProcessInstance processInstance =
                ActivitiHelper.getProcessEngine().getRuntimeService().startProcessInstanceByKey(BPMNFileEnums.WITHDRAWAL.getProcessDefinitionId());

        List<ProcessInstance> processInstanceList = ActivitiHelper.getProcessEngine().getRuntimeService()
                .createProcessInstanceQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId())
                .orderBy(ProcessInstanceQueryProperty.PROCESS_DEFINITION_ID).desc()
                .list();

        String processInstanceListStr = JSONUtil.toJsonStr(processInstanceList);
        System.out.println(processInstanceListStr);

        ActivitiHelper.getProcessEngine().getRuntimeService().deleteProcessInstance(
                processInstance.getProcessInstanceId(),
                "Test" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
        );
    }
}