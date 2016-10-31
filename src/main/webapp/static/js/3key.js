/*-- NTV
====================================================== --*/

 var ntv = ntv || {};

 /*--Key 页面键值控制
====================================================== */
 ntv.key = function(){};

 ntv.key.init = function(){
 	this.listener_keyevent();
 	this.listener_systemevent();
	this.keycode_set();
 };

 ntv.key.listener_keyevent = function(){
	if(ntv.profile.platform == "STB")
 	{
 		document.onkeydown = this.keypress;

 		if(ntv.profile.browser == "iPanel")
 		{
 			document.onkeypress = this.keypress;
			document.onirkeypress = this.keypress;
 		}

 	}else if(ntv.profile.platform == "PC")
 	{
 		document.addEventListener("keydown",this.keypress,false);
 	}
 };

 ntv.key.listener_systemevent = function(){
 		
 		if(ntv.profile.browser == "NGB-H"
 			 || ntv.profile.browser == "iPanel")
			document.onsystemevent = this.systemevent;
 		else if(ntv.profile.browser == "SHDV")
 		{
 			jShow.signal.connect(ntv.key.systemevent);
 		}
 };

 ntv.key.keypress = function(event){
 	var key_event = event ? event : window.Event;
	var key_code = key_event.which ? key_event.which : key_event.keyCode;

	ntv.log.console("ntv.key.keypress, keycode: " + key_code);

	ntv.navigation.move(key_code, event); // 用来处理 上/下/左/右/确定/刷新/返回 等常规键值
	ntv.key.keypress_handle(key_code); // 用来自定义处理键值的函数
 };

 /*
  * 用于手动控制返回页面路径的键值处理函数, 由于各浏览器厂商对返回事件处理不一致.
  * 说明: 通过禁止系统处理返回事件来达到手动控制返回操作. 代码经测试, 必须写在此处.
  * 使用：通过调用如下代码来手动控制返回操作
  *       ntv.stb.key.enable_manual_control_back_event();
  *       ntv.stb.key.move_back_url = "返回页面地址"; 
  */
 ntv.key.keypress_for_manual_control_back_event = function(event){
 	var key_event = event ? event : window.Event;
	var key_code = key_event.which ? key_event.which : key_event.keyCode;

	ntv.log.console("ntv.key.keypress, keycode: " + key_code);

	// 禁用系统返回键动作,以下代码必须放置在此
	var browser = ntv.profile.browser;
	if(browser == "NGB-H" && key_code == ntv.key.keycode_stb_ngb_h.KEY_BACK)
	{
		event.preventDefault();
		ntv.navigation.move_back();
	}
	else if(browser == "iPanel" && key_code == ntv.key.keycode_stb_ipanel.KEY_BACK)
	{
		ntv.navigation.move_back();
		return 0;
	}
	else if(browser == "SHDV" && key_code == ntv.key.keycode_stb_shdv.KEY_BACK)
	{
		ntv.navigation.move_back();
	}
	else if(browser == "PCBrowser" && key_code == ntv.key.keycode_pc.KEY_BACK)
	{
		event.preventDefault();
		ntv.navigation.move_back();
	}else
	{
		ntv.navigation.move(key_code, event); // 用来处理 上/下/左/右/确定/刷新/返回 等常规键值
		ntv.key.keypress_handle(key_code); // 用来自定义处理键值的函数
	}
	
 };

 // 具体使用时重写此函数
 ntv.key.keypress_handle = function(event_code){
 	ntv.log.console("ntv.key.keypress_handle(), event_code: " + event_code);
 };

 ntv.key.systemevent = function(event){
 	// SHDV回传对象需重新构造
 	if(ntv.profile.browser == "SHDV")
 	{
 		// 重构透传参数
		var shdv_system_event = {
			type: event.type,
			which: event.msg,
			modifiers: event.info
		};
		event = shdv_system_event;
 	}

 	var system_event = event ? event : window.Event;
	var event_code = system_event.which ? system_event.which : system_event.keyCode;

	ntv.log.console("ntv.key.systemevent(), event_code: " + event_code);
	ntv.key.systemevent_handle(event_code);
 };

 // 具体使用时重写此函数
 ntv.key.systemevent_handle = function(event_code){
 	ntv.log.console("ntv.key.systemevent_handle(), event_code: " + event_code);
 };

 ntv.key.keycode_pc = {
	KEY_OK : 13,
	KEY_UP : 38,
	KEY_DOWN : 40,
    KEY_LEFT : 37,
    KEY_RIGHT : 39,
    KEY_REFRESH : 116,
    KEY_BACK : 32,
    
    KEY_RED: 192 // ~键(tab上方)
 };

 ntv.key.keycode_stb_ipanel = {
	KEY_OK : 13,
	KEY_UP : 1,
	KEY_DOWN : 2,
    KEY_LEFT : 3,
    KEY_RIGHT : 4,
    KEY_REFRESH : 338,
    KEY_BACK : 340,

    KEY_RED: 832
 };

 ntv.key.keycode_stb_shdv = {
	KEY_OK : 13,
	KEY_UP : 38,
	KEY_DOWN : 40,
    KEY_LEFT : 37,
    KEY_RIGHT : 39,
    KEY_REFRESH : 338,
    KEY_BACK : 70,

    KEY_RED: 66
 };

 ntv.key.keycode_stb_ngb_h= {
	KEY_OK : 4097,
	KEY_UP : 38,
	KEY_DOWN : 40,
    KEY_LEFT : 37,
    KEY_RIGHT : 39,
    KEY_REFRESH : 4226,
    KEY_BACK : 4096,

    KEY_RED: 2305
 }; 

 ntv.key.keycode = ntv.key.keycode_pc;

 ntv.key.keycode_set = function(){
	if(ntv.profile.browser == "iPanel")
		this.keycode = this.keycode_stb_ipanel;
	else if(ntv.profile.browser == "SHDV")
		this.keycode = this.keycode_stb_shdv;
	else if(ntv.profile.browser == "NGB-H")
		this.keycode = this.keycode_stb_ngb_h;
 };


/*--Key对象初始化
====================================================== */
(function(){
	ntv.key.init();
})();