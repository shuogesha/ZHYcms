/*-- NTV
====================================================== --*/

var ntv = ntv || {};

/*--Navigation 页面元素导航
====================================================== */
 ntv.navigation = function(){};

 ntv.navigation.coord = "11";
 ntv.navigation.coords = undefined;


 ntv.navigation.init = function(){
 	var log = "ntv.navigation.init()";

 	this.coords = this.getcoords();
 	if(this.coords)
 	{
	 	log += ", coords.length: " + this.coords.length;
	 	this.focus(this.coord);
	 }else
	 	log += ", The page not found coord element!";

	 ntv.log.console(log);
 };

 ntv.navigation.getcoords = function(){
 	var coords = [];
	var imgs = $("img");
	if(imgs.length > 0)
	{
		for(var i=0; i<imgs.length; i++)
		{
			if(imgs[i].alt)
				coords.push(imgs[i]);
		}
		ntv.log.console("ntv.navigation.getcoords(), coords[0]: " + imgs[0].name);
	}else
		coords = undefined;

	return coords;
 };

 ntv.navigation.move = function(keycode, event){
 	switch(keycode)
 	{
 		case ntv.key.keycode.KEY_UP:
 			ntv.navigation.move_up();
 			break;
 		case ntv.key.keycode.KEY_DOWN:
 			ntv.navigation.move_down();
 			break;
 		case ntv.key.keycode.KEY_LEFT:
 			ntv.navigation.move_left();
 			break;
 		case ntv.key.keycode.KEY_RIGHT:
 			ntv.navigation.move_right();
 			break;
 		case ntv.key.keycode.KEY_REFRESH:
 			ntv.page.refresh();
 			break;
 		case ntv.key.keycode.KEY_OK:
 			ntv.navigation.move_ok();
 			break;
 		case ntv.key.keycode.KEY_BACK:
 			ntv.navigation.move_back();
 			break;
 	}
 };

 ntv.navigation.move_up = function(){this.move_control(1)};
 ntv.navigation.move_down = function(){this.move_control(3)};
 ntv.navigation.move_left = function(){this.move_control(4)};
 ntv.navigation.move_right = function(){this.move_control(2)};
 ntv.navigation.move_notcoord_callback = function(num){}; // 超出导向范围的元素,使用时重写
 ntv.navigation.move_done_callback = function(coord){}; // 焦点移动完成的回调函数,使用时重写
 ntv.navigation.move_back = function(){}; // 具体使用时重写
 ntv.navigation.move_ok = function(){
 	var data = this.getElmByCoord(this.coord).name;

 	if(data && data != " ")
 	{
	 	if(data.indexOf("javascript:") == -1)
	 		ntv.page.openurl(data);
	 	else if(data.indexOf("javascript:") == 0)
	 	{
	 		try{
	 			eval(data);
	 		}catch(e){
	 			ntv.log.console("ntv.navigation.move_ok() , eval error!");
	 		};
	 	}
	 	else
	 		ntv.log.console("ntv.navigation.move_ok() , href is null!");
 	}
 };

 ntv.navigation.move_control = function(num){
 	if(this.coords)
 	{
	 	var target_coord = undefined;

		var current_coord = this.get_next_coord(num);
		if(current_coord)
	 		target_coord = this.getElmByCoord(current_coord);

	 	if(target_coord != undefined)
	 	{
	 		if(target_coord != this.getElmByCoord(this.coord))
	 		{
		 		this.blur(this.coord);
		 		this.coord = current_coord;
		 		this.focus(this.coord);
	 		}
	 		else if(current_coord != this.coord) // 1个元素多个坐标
			{
				this.coord = current_coord;
				this.move_control(num);
			}

			ntv.navigation.move_done_callback(current_coord); // 焦点移动完成的回调事件
	 	}else
	 		this.move_notcoord_callback(num); // 超出坐标的元素

	 	ntv.log.console("ntv.navigation.move_control(), target_coord: " + current_coord);
 	}
 };

 ntv.navigation.getElmByCoord = function(coord){
 	ntv.log.console("ntv.navigation.getElmByCoord(), coord: " + coord);
 	var elm = undefined;
 	if(coord)
 	{
 		for(var i=0; i<this.coords.length; i++)
		{
			var alt = this.coords[i].alt;

			if(alt.indexOf(",")) // 兼容1个元素多个坐标
			{
				var coord_ary = alt.split(",");
				for(var j=0; j<coord_ary.length; j++)
				{	
					if(coord_ary[j] == coord)
					{
						elm = this.coords[i];
						break;
					}
				}
			}else{
				if(alt == coord)
				{
					elm = this.coords[i];
					break;
				}
			}
		}
 	}

 	ntv.log.console("ntv.navigation.getElmByCoord(), target_coord: " + coord);
 	return elm;
 };

 ntv.navigation.focus = function(coord){
	var focus_flag = "-f";

	var elm = this.getElmByCoord(coord);
 	if(elm)
 	{
		bg_img = elm.src;
		var ext_name = bg_img.match(/\.[a-z]+/)[0];
		ntv.log.console("ntv.navigation.focus(), is foucs:" + bg_img.indexOf(focus_flag + ext_name));
		if(bg_img.indexOf(focus_flag + ext_name) == -1)
		{
			
			elm.src = bg_img.replace(ext_name, focus_flag + ext_name);
		}
		
		ntv.log.console("ntv.navigation.focus(), coord: " + coord + ", href: " + elm.name);
 	}else
 		ntv.log.console("ntv.navigation.focus(), coord not found!");
 };

 ntv.navigation.blur = function(coord){
 	var focus_flag = "-f";

 	var elm = this.getElmByCoord(coord);

 	if(elm)
 	{
			bg_img = elm.src;
			var ext_name = bg_img.match(/\.[a-z]+/)[0];
 			elm.src = bg_img.substring(0, bg_img.lastIndexOf(focus_flag)) + ext_name;

 			ntv.log.console("ntv.navigation.blur(), coord: " + coord);
 	}else
 		ntv.log.console("ntv.navigation.blur(), coord not found!");
 };

ntv.navigation.move_focus = function(coord){
	if(this.getElmByCoord(coord))
	{
		this.blur(this.coord);
		
		this.focus(coord);
		this.coord = coord;
	}
};

ntv.navigation.get_next_coord = function(num){
	var coord = undefined;
	if(this.coords)
 	{
	 	var current_coord = parseInt(this.coord);
	 	switch(num)
	 	{
	            case 1: // up
	                current_coord -= 10;
	                break;
	            case 2: // right
	                current_coord += 1;
	                break;
	            case 3: // down
	                current_coord += 10;
	                break;
	            case 4: // left
	                current_coord -= 1;
	                break;
	 	}

	 	coord = current_coord + "";
 	}

 	return coord;
};


/*--Navigation对象初始化
====================================================== */
(function(){
	ntv.navigation.init();
})();