
function changeVerifyCode(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}


/**
 * 获取浏览器url中的参数（这里用了正则表达式匹配，如果看不懂可以看一下这篇博文https://blog.csdn.net/weixin_42950079/article/details/102763183）
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}


/**
 * 获取项目的ContextPath（上下文路径）以便修正图片路由，让其正常显示
 */
function getContextPath(){
    return "/o2o"
}