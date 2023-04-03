package top.easternday.graphviewer.erhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  // 声明当前类为一个 REST 控制器
@RequestMapping("/api")  // 声明当前类中的所有 HTTP 请求都以 /api 开头
@CrossOrigin  // 允许跨域访问
public class ERController {

    @Autowired  // 自动注入 ERService 实例
    private ERService service;

    // 处理 GET 请求，获取指定实体名称的实体关系信息
    @GetMapping("/{name}")
    public String getEntityRelationsByName(@PathVariable String name) {
        return service.getEntityRelationsByEntityName(name);
    }
}