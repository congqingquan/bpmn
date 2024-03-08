package priv.cqq.activity.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * PM 审批任务完成事件监听器
 *
 * @author Qingquan.Cong
 */
@Slf4j
public class PMApproveTaskCompleteListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("================= PM 审批任务完成事件监听器 =================");
        System.out.println("委派任务主键：" + delegateTask.getId());
        System.out.println("委派任务节点主键：" + delegateTask.getTaskDefinitionKey());
        System.out.println("委派任务名称：" + delegateTask.getName());
        System.out.println("监听事件：" + delegateTask.getEventName());
    }
}