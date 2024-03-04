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
    );

    private final String name;

    private final String processDefinitionId;

    private final String fileClasspath;

    // Activiti required format: BPMN filename.process_id.png | jps | gif | svg
    private final String picClasspath;
}