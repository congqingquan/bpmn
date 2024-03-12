package priv.cqq.activity;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import priv.cqq.activity.constans.BPMNFileEnums;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diagram generator
 *
 * @author Qingquan.Cong
 */
@SpringBootTest
public class ProcessDiagramGeneratorTest {

    @Resource
    private HistoryService historyService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private TaskService taskService;
    
    private final XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();

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
    public void generateSVG() {
        BPMNFileEnums bpmnFileEnum = BPMNFileEnums.PARALLEL_GATEWAY;
        
        // 加载 BPMN 文件
        try (InputStream bpmnStream = getClass().getClassLoader().getResourceAsStream(bpmnFileEnum.getFileClasspath())) {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(bpmnStream);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);
            
            // 生成 SVG 图像
            if (bpmnModel != null && bpmnModel.getLocationMap().size() > 0) {
                // 创建图像生成器
                ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
                // 生成流程图 已启动的task 高亮
                //                return generator.generateDiagram(model,
                //                        runtimeService.getActiveActivityIds(processInstanceId));
                InputStream inputstream = generator.generateDiagram(bpmnModel, "宋体", "宋体", "宋体");
                String imageName = "image-" + Instant.now().getEpochSecond() + ".svg";
                FileUtils.copyInputStreamToFile(inputstream, new File("processes/" + imageName));
            }
            
            xmlStreamReader.close();
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException("Generate svg file error");
        }
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