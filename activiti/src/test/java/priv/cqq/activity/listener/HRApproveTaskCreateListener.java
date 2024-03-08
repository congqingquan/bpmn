package priv.cqq.activity.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * HR 审批任务创建事件监听器
 *
 * @author Qingquan.Cong
 */
@Slf4j
public class HRApproveTaskCreateListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("================= HR 审批任务创建事件监听器 =================");
        System.out.println("委派任务主键：" + delegateTask.getId());
        System.out.println("委派任务节点主键：" + delegateTask.getTaskDefinitionKey());
        System.out.println("委派任务名称：" + delegateTask.getName());
        System.out.println("监听事件：" + delegateTask.getEventName());
    }
}