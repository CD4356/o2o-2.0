package com.cd.o2o.web.frontend;

import com.cd.o2o.dto.PersonProductMapExecution;
import com.cd.o2o.entity.Person;
import com.cd.o2o.entity.PersonProductMap;
import com.cd.o2o.service.PersonProductMapService;
import com.cd.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class ConsumingRecordController {

    @Autowired
    private PersonProductMapService personProductMapService;

    //获取当前登陆用户的消费记录列表
    @RequestMapping("/list_person_product_map_by_customer")
    private Map<String,Object> listPersonProductMapByCustomer(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //获取页码，即第几页
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取页长，即一页显示多少条数据
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        //获取当前登陆用户信息
        Person person = (Person) request.getSession().getAttribute("person");
        //空值判断
        if((person !=null) && (person.getUserId() !=null) && (pageIndex >-1) && (pageSize >-1)){
            //组合查询条件
            PersonProductMap personProductMapCondition = new PersonProductMap();
            personProductMapCondition.setPerson(person);
            //根据组合的查询条件，获取用户消费记录列表
            PersonProductMapExecution personProductMapExecution =
                    personProductMapService.listPersonProductMap(personProductMapCondition, pageIndex,pageSize);
            map.put("personProductMapList",personProductMapExecution.getPersonProductMapList());
            map.put("count",personProductMapExecution.getCount());
            map.put("success",true);
        }else {
            map.put("errorMsg","用户未登陆！或 页码页长为空！");
            map.put("success",false);
        }
        return map;
    }

}
