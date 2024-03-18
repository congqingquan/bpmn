package priv.cqq.activity;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskTest {


    // 不一一测试了，用到的时候现查

    // UserTask: 最常用的基本任务节点
    // ServiceTask: UserTask 需要 调用 taskService.complete(task.getId()) 来完成任务，而ServiceTask是不需要用户操作而自动执行的，它会自动调用Spring环境下的某个Bean的某个方法
    // ScriptTask: 可执行 Groovy 脚本的任务节点
    // ReceiveTask: 任务节点需要手动触发 trigger API 才能进入下一个节点
    // ManualTask: 任务节点会被自动执行，且不会记录在 ACT_HI_TASKINST 表中。可以将某个节点完成后需要自动执行的代码封装到这里执行
    // MailTask: 根据项目的邮箱配置 & 邮件任务节点的发送方、接收方、标题、正文等配置实现邮件的发送
    // CallActivitiTask: 调用另一个流程
}