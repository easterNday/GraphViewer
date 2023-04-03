package top.easternday.graphviewer.erhandler;  // 定义当前类所在的包名

import org.springframework.beans.factory.annotation.Autowired;  // 导入 Spring Framework 提供的 Autowired 注解
import org.springframework.stereotype.Service;  // 导入 Spring Framework 提供的 Service 注解

import java.util.*;  // 导入 java.util 包中的相关类
import com.alibaba.fastjson2.JSON;  // 导入 fastjson 提供的 JSON 类

@Service  // 声明当前类为一个服务类
public class ERService {

    @Autowired  // 自动注入 ERRepo 接口的实例
    private ERRepo repo;

    // 通过给定实体名称查询实体关系信息，并返回 JSON 格式字符串
    public String getEntityRelationsByEntityName(String name) {
        // 从 ERRepo 实例中查询指定实体名称的实体关系列表
        List<Object[]> list = repo.findEntityRelationsByEntityName(name);

        // 创建最终的查询结果映射表，并将节点信息集合和关系信息集合添加到其中
        Map<String, Object> finalResult = new HashMap<>();

        // 设置返回 relation-graph 数据中的根节点
        finalResult.put("rootId", name);

        // 存储节点信息的集合
        Set<Map<String, Object>> nodesList = new HashSet<>();

        // 存储关系信息的集合
        Set<Map<String, Object>> relationsList = new HashSet<>();

        // 添加查询节点
        Map<String, Object> nodeMap = new HashMap<>();
        nodeMap.put("id", name);
        nodeMap.put("text", name);
        nodesList.add(nodeMap);

        // 节点 ID 初始值为 1
        int nodeId = 1;

        // 遍历实体关系列表
        for (Object[] objs : list) {
            // 获取实体关系的类型和终止节点
            String type = (String) objs[1];
            String to = (String) objs[2];
            Boolean hasChild = (Boolean) objs[3];

            // 右边的节点信息
            Map<String, Object> node = new HashMap<>();
            node.put("id", to);
            node.put("text", to);
            if(hasChild){
                node.put("color","#131313");
            }
            nodesList.add(node);

            // 创建关系信息的映射表，并填充属性值
            Map<String, Object> relationMap = new HashMap<>();
            relationMap.put("from", name);
            relationMap.put("to", to);
            relationMap.put("text", type);

            // 将关系信息添加到关系信息集合中
            relationsList.add(relationMap);
        }

        finalResult.put("nodes", nodesList);
        finalResult.put("lines", relationsList);

        // 将查询结果映射表转为 JSON 格式字符串并返回
        return JSON.toJSONString(finalResult);
    }
}