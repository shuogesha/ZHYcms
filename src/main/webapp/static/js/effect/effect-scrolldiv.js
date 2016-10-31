/*-- NTV Effect
 * 滚动div. 用于配合<div />标签来展示超出<div />可视高度的内容, 含滚动条及键盘方向键操控。
 *
 * 特别注意事项: 
 *  1.在调用插件初始函数时必须要使用setTimeout延时去调用函数, 不然插件会无效.
 *  	1.1调用示例:
 *      setTimeout(function(){
 *  			ntv.effect.scroll.init("#scroll-line", "#scroll-bar", "#scroll-bar-focus", "#scroll-content");	
 *  		}, 500);
 *  	1.2原因说明:延时调用函数是因为一般内容div元素中的内容HTML是通过js动态填充进去的, 
 *  	             在填充HTML过程中由于浏览器还在渲染HTML，造成此时去获取
 *  	             div的scrollHeight属性值为undefined。PC浏览器上不会出现此问题,
 *  	             是因为渲染速度快或是浏览器内核的渲染与js脚本执行的先后问题.
 *		             
 *  2.此插件需要HTML及CSS代码配合.
 * HTML代码示例:
 * <div class="content" id="scroll-content" >
 * <!-- 超长内容 -->
 * </div>
 *
 * <div class="scroll" id="scroll-line">
 *  <div class="scroll-bar" id="scroll-bar">
 *    <img id="scroll-bar-focus" src="images/detail/scroll-bar.png" alt="12">
 *  </div>
 * </div>
 *
 * CSS代码示例:
 * 特别注意事项:
 *    1. 为什么scroll的css属性display:block; z-index:-1;而不是用display:none; 是为了兼容机顶盒,如果不这样写会获取不到line的scrollHeight.
 *     原因是机顶盒在渲染时不会渲染隐藏元素HTML,当设置元素display:block机顶盒浏览器才渲染此元素. 而当我们在js中在设置display:block就立即去获取元素的scrollHeight
 *     是获取不到的, 因为浏览器还未渲染完成. 使用z-index:-1;是一种折中的方法. PC浏览器上不会出现此问题.
 * CSS代码示例:
 * .content{width: 590px; height: 400px; overflow: hidden;}
 *
 * .scroll{position: absolute; top:140px; left: 1150px; width: 13px; height: 410px; background-image: url("../images/detail/scroll-bg.png"); background-repeat: no-repeat; background-position: 2px 5px;  display: block; z-index:-1;}
 * .scroll .scroll-bar{position: absolute; top:5px; left: 0px; height: 74px; width: 13px; display: none;}
 * .scroll .scroll-bar img{width: 13px;}
 *
====================================================== --*/

/*--依赖 ntv.js 之后引用
====================================================== */
var ntv = ntv || {};
ntv.effect = ntv.effect || function(){};

ntv.effect.scroll = function(){};
ntv.effect.scroll.option = {line: "", bar: "", bar_focus: "", content: ""};
ntv.effect.scroll.init = function(line, bar, bar_focus, content){
	

	if(line && bar && bar_focus && content)
	{
		this.option.line = $(line);
		this.option.bar = $(bar);
		this.option.bar_focus = $(bar_focus);
		this.option.content = $(content);

		this.content.init(this.option.content);

		// 内容的实际高度大于可视高度显示滚动条
		if(this.content.attr.height > this.content.attr.view_height)
		{
			this.show();
			this.slider.init(this.option.line, this.option.bar,
							 this.option.bar_focus, this.content.attr.part);

			this.content.scroll_out = ntv.effect.scroll.disabled;
			this.listen();
		}else
			this.hide();
	}else
		ntv.log.console("ntv.effect.scroll.init(), param is error!");

};

// 使用ntv.navigation.move_done_callback函数来监听焦点是否移动到滚动条
ntv.effect.scroll.listen = function(){
	ntv.navigation.move_done_callback = ntv.effect.scroll.is_focus;
};

ntv.effect.scroll.is_focus = function(coord){
	if(ntv.effect.scroll.option.bar_focus.alt.indexOf(coord) != -1)
		 ntv.effect.scroll.enabled();
};

// 当焦点在滚动条上时, 重写焦点移动的事件
ntv.effect.scroll.enabled = function(){
	ntv.navigation.move_up = ntv.effect.scroll.scroll_top;
	ntv.navigation.move_down = ntv.effect.scroll.scroll_down;
	ntv.navigation.move_right = function(){ntv.effect.scroll.disabled(2)};
	ntv.navigation.move_left = function(){ntv.effect.scroll.disabled(4)};
};

// 当焦点离开滚动条时
ntv.effect.scroll.disabled = function(num){

	// 获取下一动作的焦点元素坐标，并判断此坐标是否存在
	var next_coord = ntv.navigation.get_next_coord(num);
	if(next_coord && ntv.navigation.getElmByCoord(next_coord))
	{
		// 还原系统焦点移动的事件
		ntv.navigation.move_up = function(){this.move_control(1)};
		ntv.navigation.move_down = function(){this.move_control(3)};
		ntv.navigation.move_left = function(){this.move_control(4)};
		ntv.navigation.move_right = function(){this.move_control(2)};

		// 手动模拟触发一次移动
		ntv.navigation.move_control(num);
	}
};


/* 内容容器和滚动条容器的移动控制 */
ntv.effect.scroll.scroll_top = function(){
	// 回调函数内部的this作用域为调用方

	ntv.effect.scroll.content.scroll_top();
	ntv.effect.scroll.slider.scroll_top();
};

ntv.effect.scroll.scroll_down = function(){
	// 回调函数内部的this作用域为调用方

	ntv.effect.scroll.content.scroll_down();
	ntv.effect.scroll.slider.scroll_down();
};

ntv.effect.scroll.show = function(){
	this.option.line.style.zIndex = "9";
	this.option.bar.style.display = "block";
	
	if(this.option.bar_focus.alt == " ")
	{
		if(this.option.bar_focus.name)
		{
			this.option.bar_focus.alt = this.option.bar_focus.name;
			this.option.bar_focus.name = " ";
		}
		else
			ntv.log.console("ntv.effect.scroll.show(), coord of bar is null!");
	}
		
};


ntv.effect.scroll.hide = function(){
	this.option.line.style.zIndex = "-1";
	this.option.bar.style.display = "none";

	if(this.option.bar_focus.alt && this.option.bar_focus.alt != " ")
	{
		this.option.bar_focus.name = this.option.bar_focus.alt;
		this.option.bar_focus.alt = " "; // 不可获取焦点, 是" "而非"".设置""不会实际修改属性值
	}
};



// 控制内容. 原理说明: 移动内容元素的可视区域, 利用 内容元素.scrollTop = 移动值;
ntv.effect.scroll.content = function(){};
ntv.effect.scroll.content.attr = {content: "", height: 0, view_height: 0, part: 0, curr_part: 1};
ntv.effect.scroll.content.init = function(content){
	this.attr.content = content;
	
	this.attr.height = this.attr.content.scrollHeight; // 内容元素的实际高度
	this.attr.view_height = this.attr.content.clientHeight; // 内容元素的可视区域高度
	this.attr.part = Math.ceil(this.attr.height / this.attr.view_height);

	ntv.log.console("ntv.effect.scroll.content.init(),"
					+ " height: " + this.attr.height
					+ " view_height: " + this.attr.view_height
					+ " part: " + this.attr.part);
};

ntv.effect.scroll.content.scroll_out = function(type){}; // 使用时重写
ntv.effect.scroll.content.scroll_top = function(){
	if(this.attr.curr_part > 1)
	{
		this.attr.content.scrollTop -= this.attr.view_height;
		this.attr.curr_part --;
	}else
		this.scroll_out(1);

	ntv.log.console("ntv.effect.scroll.content.scroll_top(),"
					+ " curr_part: " + this.attr.curr_part);
};

ntv.effect.scroll.content.scroll_down = function(){
	if(this.attr.curr_part < this.attr.part)
	{
		this.attr.content.scrollTop += this.attr.view_height;
		this.attr.curr_part ++;
	}else
		this.scroll_out(3);

	ntv.log.console("ntv.effect.scroll.content.scroll_down(),"
					+ " curr_part: " + this.attr.curr_part);
};



// 控制滑块. 原理说明: 移动滚动条bar的高度, 利用 bar元素.style.top = "移动值" + "px";
ntv.effect.scroll.slider = function(){};
ntv.effect.scroll.slider.attr = {line: "", bar: "", bar_focus: "", line_height: 0, step: 0, part: 0, curr_part: 0};
ntv.effect.scroll.slider.init = function(line, bar, bar_focus, content_part){
	
	this.attr.line = line;
	this.attr.bar = bar;
	this.attr.bar_focus = bar_focus;
	this.attr.line_height = this.attr.line.scrollHeight;

	this.attr.part = content_part; // 内容被分成的段数
	this.attr.step = this.attr.line_height / this.attr.part; // 滑块的步长等于滑条总长/内容分段总数
	this.attr.bar_focus.height = this.attr.step + "";

	ntv.log.console("ntv.effect.scroll.slider.init(),"
					+ " line_height: " + this.attr.line_height
					+ " step: " + this.attr.step
					+ " part: " + this.attr.part);
};

ntv.effect.scroll.slider.scroll_top = function(){
	if(this.attr.curr_part > 0)
	{
		var top = 0;
		if(this.attr.bar.style.top)
			top = parseInt(this.attr.bar.style.top);

		// scroll的curr_part从0开始, 这是因为scroll_bar在第一页的时候top属性为0px
		this.attr.curr_part --;
		this.attr.bar.style.top = (this.attr.step * this.attr.curr_part) + "px";
	}

	ntv.log.console("ntv.effect.scroll.slider.scroll_top(),"
					+ " curr_part: " + this.attr.curr_part
					+ " sliderbar-top: " + this.attr.bar.style.top);
};

ntv.effect.scroll.slider.scroll_down = function(){

	if(this.attr.curr_part < this.attr.part-1)
	{
		var top = 0;
		if(this.attr.bar.style.top)
			top = parseInt(this.attr.bar.style.top);

		// scroll的curr_part从0开始, 这是因为scroll_bar在第一页的时候top属性为0px
		this.attr.curr_part ++;
		this.attr.bar.style.top =  (this.attr.step * this.attr.curr_part) + "px";
		
	}

	ntv.log.console("ntv.effect.scroll.slider.scroll_down(),"
					+ " curr_part: " + this.attr.curr_part
					+ " sliderbar-top: " + this.attr.bar.style.top);
};

