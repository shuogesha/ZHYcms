 /*-- NTV
====================================================== --*/

 var ntv = ntv || {};

 /*--Log 页面调试输出
====================================================== */
 ntv.log = function(){};
 
 ntv.log.debug = false;
 ntv.log.console = function(str){
 	if(ntv.log.debug && this.filter(str))
 	{
 		 
	 	var html = '<div style="display: block; background-color: #336699;'
	 			 + ' opacity: 0.7; position: absolute; top: 20px; left: 20px;'
	 			 + ' text-align: left; font-size: 18px; color:#fff;"'
	 			 + ' id="console"><!-- #$#: --></div>';
	 	
	 	var html = '<div class="console" id="console"><!-- #$#: --></div>';
	 	if($("#console") == null)
	 		window.document.body.innerHTML += html;
	 	else
	 		$("#console").innerHTML += "<p>#$#: " + str + "</p>";
 	}
 };
 ntv.log.filter = function(str){
 	var print = true;
 	
	if(str.indexOf("$PAGE") > -1)
 		print = false;
 	else if(str.indexOf("ntv.key") > -1)
 		print = false;
 	else if(str.indexOf("ntv.navigation") > -1)
 		print = false;
 	else if(str.indexOf("ntv.page") > -1)
 		print = false;
 	else if(str.indexOf("ntv.stb") > -1)
 		print = false;
 	else if(str.indexOf("ntv.effect") > -1)
 		print = true;
 	
 	return print;
 };

 /*--Profile
====================================================== */
 ntv.profile = function(){};

 ntv.profile.platform = "PC or STB";
 ntv.profile.browser = "NGB-H or iPanel or SHDV"; // 国标NGB-H or iPanel or SHDV

 ntv.profile.info = function(){
 	var info = "browser info"
 			 + ", platform: " + navigator.platform
 			 + ", appName: " + navigator.appName
 			 + ", appVersion: " + navigator.appVersion;

 	var platform = "当前网页运行平台检测结果为(PC or STB): "
 			 + this.platform;

 	var browser = "当前网页运行浏览器检测结果为(NGB-H or iPanel or SHDV): "
 	            + this.browser;

 	ntv.log.console(info);
 	ntv.log.console(platform);
 	ntv.log.console(browser);
 };

 ntv.profile.init = function(){
 	this.platform = this._get_platform();
 	this.browser = this._get_browser();
 };

 ntv.profile._get_platform = function(){
 	var platform = navigator.platform;

 	if(platform.indexOf("Win32") == -1
 		 && platform.indexOf("Linux x86") == -1)
 		platform = "STB";
 	else
 		platform = "PC";

 	return platform;
 };

 ntv.profile._get_browser = function(){ 
 	var browser = "PCBrowser";

 	// 茁壮中间件
 	if(typeof(iPanel) != "undefined")
 		browser = "iPanel";
 	// 全景中间件
 	else if(typeof(jShow) != "undefined")
 		browser = "SHDV";
 	// NGB-H国标(iPanel也有MediaPlayer对象,但NGB-H绝对没有iPanel对象)
	else if(typeof(MediaPlayer) != "undefined")
 		browser = "NGB-H";

 	return browser;
 };

/*--NTV对象初始化
====================================================== */
(function(){
	ntv.profile.init();
	ntv.profile.info();
})();