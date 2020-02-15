$(function() {
    var productName = '';
    getList();
    //拼接商品销售列表进行显示
    function getList(){
        //获取商品销售列表信息的URL
        var url = "/o2o/shop_admin/list_person_product_map_by_shop?pageIndex=1&pageSize=9999&productName=" + productName;
        //访问服务端，获取商品销售列表信息
        $.getJSON(url,function (data) { //data是访问服务器后，返回的json数据
            //判断服务器返回的数据是否真确
            if(data.success){
                //获取服务端放回的销售记录列表信息
                var personProductMapList = data.personProductMapList;
                var tempHtml = '';
                //遍历服务器返回的商品销售列表，拼接对应的表格
                personProductMapList.map(function (item, index) {
                    //注意这里是+=不是= 若使用等于，则只会列出一条信息而已
                    tempHtml += '<div class="row row-product-buy-check table-content">'
                            + '<div  class="col-25">' + item.product.productName + '</div>'
                            + '<div  class="col-25">' + item.createTime + '</div>'
                            + '<div  class="col-25">' + item.person.name + '</div>'
                            + '<div  class="col-25">' + item.point + '</div>'
                         + '</div>'
                });
                $('.product-buy-check-wrap').html(tempHtml);
            }
        });
    }

    //根据搜索框输入的商品名进行模糊查询
    $('search').on('change',function (e) {
        //获取搜索框输入的商品名
        productName = e.target.value();
        //清空商品销售列表信息
        $('.product-buy-check-wrap').empty();
        //调用getList()函数刷新商品销售列表
        getList();
    });

    getProductSellDailyList();
    //获取店铺七天销量
    function getProductSellDailyList() {
        //获取指定店铺七天销量URL
        var listProductSellDailyUrl = '/o2o/shop_admin/list_product_sell_daily';
        //访问服务端，获取相应数据
        $.getJSON(listProductSellDailyUrl, function(data) {
            //如果success为true
            if(data.success){
                /* 生成动态Echarts信息部分 */
                //图例
                var legendArr = data.legendData;
                //横轴坐标
                var yearArr = data.yearArr;
                //图标内容数组系列
                var seriesArr = data.series;

                //生成Echarts静态信息部分
                generateStaticEchartPart(legendArr,yearArr,seriesArr);
            }
        });
    }

    //生成Echarts静态信息部分
    function generateStaticEchartPart(legendArr,yearArr,seriesArr) {

        //基于准备好的DOM，通过echarts.init方法初始化一个echarts实例，并通过setOption方法生成柱状图
        var chart = echarts.init(document.getElementById('main'));

        //指定图表的配置项和数据（option的每个属性都是一类组件）
        var option = {
            //背景颜色
            // backgroundColor: 'rgba(0, 0, 0)',
            //图表标题
            title: {
                text: '商品日销量',
                // subtext: '数据来自o2o平台'
            },
            //数据区域缩放
            dataZoom: [
                {
                    id: 'dataZoomX',
                    type: 'slider',
                    xAxisIndex: [0],
                    filterMode: 'filter'
                },
                {
                    id: 'dataZoomY',
                    type: 'slider',
                    yAxisIndex: [0],
                    filterMode: 'empty'
                }
            ],
            //工具栏组件
            toolbox: {
                //是否显示该工具栏组件
                show: true,
                //工具栏的布局朝向，可选horizontal、vertical
                orient: 'vertical',
                //工具栏组件离容器右侧的距离，默认单位px
                right: '3%',
                //工具栏组件离容器上侧的距离，默认单位px
                top: 'center',
                //是否在鼠标hover的时候显示每个icon工具的标题
                showTitle: true,
                feature: {
                    //数据视图工具，可以展现当前图表所用的数据，编辑后可以动态更新
                    dataView: {
                        //是否显示该icon工具
                        show: true,
                        //是否只读
                        readOnly: false,
                        //数据视图浮层背景色
                        backgroundColor: '#ccc',
                    },
                    //动态数据类型切换，
                    magicType: {
                        //是否显示该icon工具
                        show: true,
                        //切换的类型
                        type: ['line', 'bar', 'stack', 'tiled']
                    },
                    //刷新图表
                    restore: {
                        //是否显示该icon工具
                        show: true
                    },
                    //将图表保存为图片
                    saveAsImage: {
                        //是否允许保存为图片
                        show: true
                    }
                }
            },
            //图例颜色
            color: ['#199126', '#006099', '#886099'],
            //消息提示框，鼠标悬停显示提示信息
            tooltip: {
                trigger: 'axis',
                //坐标轴指示器，鼠标悬停触发
                axisPointer: {
                    //默认为直线，可选line（线形）或shadow（矩形）或cross（十字形）
                    type: 'cross'
                },
                backgroundColor: 'rgba(255, 0, 0, 0.3)',
                // borderWidth: 1,
                // borderColor: '#ccc',
                padding: 10,
                textStyle: {
                    color: '#000',
                    fontFamily: '楷体',
                    fontWeight: 'bold',
                },
            },
            //图例，每个图标最多仅有一个图列
            legend: {
                //图例内容数组，数组的每一个元素代表一个系列的name
                // data:["菊花茶","普洱茶","下火茶"],
                data: legendArr,
                right: '10%'
            },
            //直角坐标系x轴
            xAxis: {
                type: 'category',
                data: yearArr
            },
            //直角坐标系y轴数组
            yAxis: {
                type:'value',
                name:'销量',
                axisLabel: {
                    formatter: '{value} 份'
                },
                data: {

                }
            },
            //直角坐标系内绘图网格
            gird: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            //图表内容数组系列（一个元素代表一个系列）
            series: seriesArr,
        };
        // 使用刚指定的配置项和数据显示图表
        chart.setOption(option);
    }

});