/*-- NTV Effect
 * 滑动菜单
====================================================== --*/

/*--依赖 ntv.js 之后引用
====================================================== */
var ntv = ntv || {};
ntv.effect = ntv.effect || function(){};

ntv.effect.slidemenu = function(){};
ntv.effect.slidemenu._slide_direction = "horizontal or vertical";
ntv.effect.slidemenu._menu_data = [];
ntv.effect.slidemenu._menu_elms = [];
ntv.effect.slidemenu._menu_hit_elms = [];
ntv.effect.slidemenu._move_step = 0;

ntv.effect.slidemenu.init = function(data, elms, direction){
	this._menu_data = data; // 菜单所有数据

	this._get_menu_elm(elms);
	this._load_menu();
	this._slide_direction = direction || "horizontal";

	ntv.navigation.move_notcoord_callback = ntv.effect.slidemenu._bind_navigation;
};

ntv.effect.slidemenu._get_menu_elm = function(elms){
	if(elms && elms.length > 0)
	{
		this._menu_hit_elms[0] = elms[0];
		this._menu_hit_elms[1] = elms[elms.length - 1];
		for(var i = 1; i < elms.length - 1; i++)
		{
			this._menu_elms[i-1] = elms[i];
		}
	}
};

ntv.effect.slidemenu._load_menu = function(){
	if(this._iscan_move())
	{
		for(var i = 0; i < this._menu_elms.length; i++)
		{
			this._menu_elms[i].src = this._menu_data[i].img;
			this._menu_elms[i].name = this._menu_data[i].url;
		}
	}

	this._focus(this._menu_elms[0]);
};

ntv.effect.slidemenu._bind_navigation = function(num){

	if(ntv.effect.slidemenu._slide_direction == "horizontal")
	{
		switch(num)
		{
			case 4: // left
				ntv.effect.slidemenu._move_prev();
				break;
			case 2: // right
				ntv.effect.slidemenu._move_next();
				break;
		}
	}else
	{
		switch(num)
		{
			case 1: // up
				ntv.effect.slidemenu._move_prev();
				break;
			case 3: // down
				ntv.effect.slidemenu._move_next();
				break;
		}
	}
};

ntv.effect.slidemenu._move_prev = function(){
	if(this._iscan_move() && this._move_step > 0)
	{
		this._move_step --;

		this._menu_refresh();
		
		this._focus(this._menu_elms[0]);

		this._menu_hit_refresh();
	}
};

ntv.effect.slidemenu._move_next = function(){
	if(this._iscan_move() 
		&& (this._menu_elms.length + this._move_step + 1) <= this._menu_data.length)
	{
		this._move_step ++;

		this._menu_refresh();

		this._focus(this._menu_elms[this._menu_elms.length-1]);

		this._menu_hit_refresh();
	}
};

ntv.effect.slidemenu._iscan_move = function(){
	if(this._menu_elms && this._menu_data 
		&& (this._menu_elms.length <= this._menu_data.length))
		return true;
	else
		return false;
}; 

ntv.effect.slidemenu._menu_refresh = function(){
	var step = this._move_step, elms = this._menu_elms, data = this._menu_data;

	for(var i = 0; i < elms.length; i++)
	{
		elms[i].src = data[i+step].img;
		elms[i].name = data[i+step].url;
	}
}; 

ntv.effect.slidemenu._menu_hit_refresh = function(){

	if(!this._move_step)
	{
		this._blur(this._menu_hit_elms[0]);
		this._focus(this._menu_hit_elms[this._menu_hit_elms.length-1]);
	}

	if((this._menu_elms.length + this._move_step) == this._menu_data.length)
	{
		this._focus(this._menu_hit_elms[0]);
		this._blur(this._menu_hit_elms[this._menu_hit_elms.length-1]);
	}
};

ntv.effect.slidemenu._focus = function(elm){
	var focus_flag = "-f";
 	if(elm)
 	{
		bg_img = elm.src;
		var ext_name = bg_img.match(/\.[a-z]+/)[0];
		if(bg_img.indexOf(focus_flag + ext_name) == -1)
			elm.src = bg_img.replace(ext_name, focus_flag + ext_name);

		ntv.log.console("ntv.effect.slidemenu._focus(), coord: " + elm.alt + ", href: " + elm.name);
 	}else
 		ntv.log.console("ntv.effect.slidemenu._focus(), Can't focus this element!");
 };

ntv.effect.slidemenu._blur = function(elm){
 	var focus_flag = "-f";

 	if(elm)
 	{
		bg_img = elm.src;
		elm.src = bg_img.replace(focus_flag, "");

		ntv.log.console("ntv.effect.slidemenu._blur(), coord: " + elm.alt + ", href: " + elm.name);
 	}else
 		ntv.log.console("ntv.effect.slidemenu._blur(), Can't focus this element!");
 };