package com.cd.o2o.web.shopadmin;

import com.cd.o2o.dto.EchartSeries;
import com.cd.o2o.dto.PersonProductMapExecution;
import com.cd.o2o.entity.PersonProductMap;
import com.cd.o2o.entity.Product;
import com.cd.o2o.entity.ProductSellDaily;
import com.cd.o2o.entity.Shop;
import com.cd.o2o.service.PersonProductMapService;
import com.cd.o2o.service.ProductSellDailyService;
import com.cd.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/shop_admin")
public class PersonProductMapController {

    @Autowired
    private PersonProductMapService personProductMapService;
    @Autowired
    private ProductSellDailyService productSellDailyService;

    //店铺中商品的销售情况
    @RequestMapping("/list_person_product_map_by_shop")
    @ResponseBody
    public Map<String,Object> listPersonProductMapByShop(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //获取前端提交过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取前端提交过来的页宽
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        //从session中获取当前店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //判空操作
        if((currentShop!=null) && (currentShop.getShopId()!=null) && (pageIndex >-1) && (pageSize >-1)){
            //组合查询条件
            PersonProductMap personProductMapCondition = new PersonProductMap();
            personProductMapCondition.setShop(currentShop);
            //获取商品名
            String productName = HttpServletRequestUtil.getString(request,"productName");
            if(productName != null){
                //根据商品名进行模糊查询
                Product product = new Product();
                product.setProductName(productName);
                personProductMapCondition.setProduct(product);
            }
            //根据拼接的查询条件，获取该店铺商品的销售情况
            PersonProductMapExecution personProductMapExecution = personProductMapService.listPersonProductMap(personProductMapCondition, pageIndex, pageSize);
            map.put("personProductMapList", personProductMapExecution.getPersonProductMapList());
            map.put("count", personProductMapExecution.getCount());
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errorMsg","empty shopId or pageIndex or pageSize ");
        }
        return map;
    }

    //店铺中商品的七天销售情况
    @RequestMapping(value = "/list_product_sell_daily",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listProductSellDaily(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //获取当前店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //控制判断
        if((currentShop != null) && (currentShop.getShopId() != null)){
            //组合查询条件
            ProductSellDaily productSellDailyCondition = new ProductSellDaily();
            productSellDailyCondition.setShop(currentShop);
            //获取calendar日历对象
            Calendar calendar = Calendar.getInstance();
            // 获取昨天的日期
            calendar.add(Calendar.DATE, -1);
            Date endTime = calendar.getTime();
            // 获取七天前的日期
            calendar.add(Calendar.DATE, -6);
            Date beginTime = calendar.getTime();
            //根据传入的条件获取该店铺的销售情况
            List<ProductSellDaily> productSellDailyList = productSellDailyService.listProductSellDaily(productSellDailyCondition,
                    beginTime,endTime);

            //指定日期格式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //商品名列表，使用HashSet去重复，保证唯一性
            HashSet<String> legendData = new HashSet<>();
            // x轴数据
            HashSet<String> yearArr = new HashSet<>();
            // 定义series
            List<EchartSeries> series = new ArrayList<>();
            // 日销量列表
            List<Integer> totalList = new ArrayList<>();
            // 当前商品名，默认为空
            String currentProductName = "";
            for (int i=0;i<productSellDailyList.size();i++){
                ProductSellDaily productSellDaily = productSellDailyList.get(i);
                //将商品名去重后，添加到legendData集合中
                legendData.add(productSellDaily.getProduct().getProductName());
                //将商品创建时间格式化后，添加到yearArr集合中
                yearArr.add(dateFormat.format(productSellDaily.getCreateTime()));

                if(!currentProductName.equals(productSellDaily.getProduct().getProductName())
                        && !currentProductName.isEmpty()){
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0,totalList.size()));
                    series.add(echartSeries);
                    //重置totalList
                    totalList = new ArrayList<>();
                    //变换currentProduct为当前product
                    currentProductName = productSellDaily.getProduct().getProductName();
                    //继续添加新的值
                    totalList.add(productSellDaily.getTotal());
                }else {
                    //如果还是当前product，则继续添加新值
                    totalList.add(productSellDaily.getTotal());
                    //变换currentProduct为当前product
                    currentProductName = productSellDaily.getProduct().getProductName();
                }
                //队列之末，需要将最后一个商品销售信息也添加上
                if(i == productSellDailyList.size() - 1){
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0,totalList.size()));
                    series.add(echartSeries);
                }
            }
            //图例
            map.put("legendData",legendData);
            //内容数组系列
            map.put("series",series);
            //横轴坐标
            map.put("yearArr",yearArr);
            //成功标识
            map.put("success",true);
        }else {
            map.put("errorMsg","shopId is empty!");
            map.put("success",false);
        }
        return map;
    }

}
