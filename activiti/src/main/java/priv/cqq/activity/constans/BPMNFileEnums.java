package priv.cqq.activity.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPMN file enum
 *
 * @author Qingquan.Cong
 */
@Getter
@AllArgsConstructor
public enum BPMNFileEnums {

    WITHDRAWAL(
            "提现审核流程",
            "withdrawal-approval-id",
            "processes/withdrawal-approval.bpmn20.xml",
            "processes/withdrawal-approval.withdrawal-approval-id.png"
    ),
    CANDIDATE_USER(
            "候选人流程",
            "candidate-user-id",
            "processes/candidate-user.bpmn20.xml",
            "processes/candidate-user.candidate-user-id.png"
    ),
    TASK_DELEGATE(
            "任务委托流程",
            "task-delegate-id",
            "processes/task-delegate.bpmn20.xml",
            "processes/task-delegate.task-delegate-id.svg"
    ),
    TASK_EXECUTION_LISTENER(
            "任务-执行流-监听流程",
            "task-execution-listener-id",
            "processes/task-execution-listener.bpmn20.xml",
            "processes/task-execution-listener.task-execution-listener-id.png"
    ),
    PROCESS_VARIABLE(
            "流程变量流程",
            "process-variable",
            "processes/process-variable.bpmn20.xml",
            "processes/process-variable.process-variable-id.svg"
    ),
    EXCLUSIVE_GATEWAY(
        "排他网关流程",
        "exclusive-gateway",
        "processes/exclusive-gateway.bpmn20.xml",
        "processes/exclusive-gateway.exclusive-gateway-id.svg"
    ),
    PARALLEL_GATEWAY(
            "并行网关流程",
            "parallel-gateway",
            "processes/parallel-gateway.bpmn20.xml",
            "processes/parallel-gateway.parallel-gateway-id.svg"
    );
    
    private final String name;

    private final String processDefinitionId;

    private final String fileClasspath;

    // Activiti required format: BPMN filename.process_id.png | jps | gif | svg
    private final String picClasspath;
}