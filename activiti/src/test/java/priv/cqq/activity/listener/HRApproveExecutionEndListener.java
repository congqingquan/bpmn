package priv.cqq.activity.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * HR 审批执行流-结束事件-监听器
 *
 * @author Qingquan.Cong
 */
@Slf4j
public class HRApproveExecutionEndListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        log.info("================= HR 审批执行流-结束事件-监听器 =================");
        System.out.println("执行流主键：" + execution.getId());
        System.out.println("执行流名称：" + execution.getEventName());
        System.out.println("当前ACT_ID：" + execution.getCurrentActivityId());
        System.out.println("流程实例主键：" + execution.getProcessInstanceId());
    }
}