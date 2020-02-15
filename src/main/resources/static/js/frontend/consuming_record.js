$(function () {
    getList();
    //获取当前用户消费记录列表
    function getList() {
        //获取当前用户消费记录列表的URL
        var url = "/o2o/frontend/list_person_product_map_by_customer?pageIndex=1&pageSize=999";
        //向服务端发送请求，获取用户消费记录信息
        $.getJSON(url, function (data) {
            //判断服务端是否成功处理请求
            if(data.success){
                var personProductMapList = data.personProductMapList;
                var tempHtml = '';
                personProductMapList.map(function (value, index) {
                    tempHtml += '<div class="card">' +
                                '<div class="card-header" data-shop-id="'+ value.shop.shopId +'">' + value.shop.shopName + '</div>' +
                                '<div class="card-product" data-product-id="'+ value.product.productId +'">' +
                                    '<div class="card-content">' +
                                        '<div class="list-block media-list">' +
                                            '<ul>' +
                                                '<li class="item-content">' +
                                                    '<div class="item-media">' +
                                                        '<img src="'+ getContextPath() + value.product.imgAddress +'" width="44">' +
                                                    '</div>' +
                                                    '<div class="item-inner">' +
                                                        '<div class="item-title-row">' +
                                                            '<div class="item-title">' + value.product.productName + '</div>' +
                                                        '<div' +
                                                        '<div class="item-subtitle">' + '价格: ' + value.product.normalPrice + '</div>' +
                                                    '</div>' +
                                                '</li>' +
                                            '</ul>' +
                                        '</div>' +
                                    '</div>' +
                                    '<div class="card-footer">' +
                                        '<span>' + value.createTime + '</span>' +
                                        '<span>' + '积分: ' + value.point + '</span>' +
                                    '</div>' +
                                '</div>'+
                            '</div>'
                });
                $('.person-product-map-list').html(tempHtml);
            }
        });
    }

    //点击消费记录卡片进入商品详情页
    $('.person-product-map-list').on('click','.card-header',function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o/frontend/shop_detail?shopId=' + shopId;
    });

    //点击消费记录卡片进入店铺详情页
    $('.person-product-map-list').on('click',".card-product",function (e) {
        var productId = e.currentTarget.dataset.productId;
        window.location.href = '/o2o/frontend/product_detail?productId=' + productId;
    });

});