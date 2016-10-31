/*-- NTV
====================================================== --*/

var ntv = ntv || {};


/*--Page 页面处理类
====================================================== */
ntv.page = function(){};

ntv.page.init = function(){
	window.onload = ntv.page._onload;
	window.onunload = ntv.page._onunload;
	window.onbeforeunload = ntv.page._onunload;
};

/*--Page Event
====================================================== */
ntv.page._onload = function(){}; // 具体使用时重写,页面建议用匿名函数
ntv.page._onunload = function(){
	ntv.page.onbeforeunload_page();
	ntv.page.onbeforeunload_stb();
};
ntv.page.onbeforeunload_page = function(){}; // 页面使用时重写
ntv.page.onbeforeunload_stb = function(){}; // 被stb.js重写

/*--Tools
====================================================== */
ntv.page.openurl = function(url){
	window.location.href = url;
};

ntv.page.refresh = function(){
	window.location.href = window.location.href;
};

ntv.page.url = function(){};
ntv.page.url.host = function(){
	return "http://" + window.location.host;
	ntv.log.console("ntv.page.url.host: " + window.location.host);
};


/*--page对象初始化
====================================================== */
(function(){
	ntv.page.init();
})();