(function($){
	$.fn.citySelect=function(settings){
		if(this.length<1){return;};

		// 默认值
		settings=$.extend({
			url:"/admin/district/json.do",
			prov:null,
			city:null,
			dist:null,
			area:null,
			cun:null,
			nodata:null,
			required:false,
			hide:false
		},settings);

		var box_obj=this;
		var prov_obj=box_obj.find(".prov");
		var city_obj=box_obj.find(".city");
		var dist_obj=box_obj.find(".dist");
		var area_obj=box_obj.find(".area");
		var cun_obj=box_obj.find(".cun");
		var prov_val=settings.prov;
		var city_val=settings.city;
		var dist_val=settings.dist;
		var area_val=settings.area;
		var cun_val=settings.cun;
		var select_prehtml=(settings.required) ? "" : "<option value=''>请选择</option>";
		var prov_json;
		var city_json;
		var dist_json;
		var area_json;
		var cun_json;
		var district;

		// 赋值市级函数
		var cityStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
			};
			city_obj.empty().attr("disabled",true);
			dist_obj.empty().attr("disabled",true);
			area_obj.empty().attr("disabled",true);
			cun_obj.empty().attr("disabled",true);
			
			if(prov_id<0){
				if(settings.nodata=="none"){
					city_obj.css("display","none");
					dist_obj.css("display","none");
					area_obj.css("display","none");
					cun_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					city_obj.css("visibility","hidden");
					dist_obj.css("display","none");
					area_obj.css("display","none");
					cun_obj.css("display","none");
				};
				return;
			};
			district=prov_obj.val();
			getData(prov_obj.val(),2,function(json){
				city_json=json;
				// 遍历赋值市级下拉列表
				temp_html=select_prehtml;
				$.each(city_json,function(i,city){
					temp_html+="<option value='"+city.code+"'>"+city.name+"</option>";
				});
				city_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
				distStart();
			})
		};

		// 赋值地区（县）函数
		var distStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			var city_id=city_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
				city_id--;
			};
			area_obj.empty().attr("disabled",true);
			cun_obj.empty().attr("disabled",true);

			if(prov_id<0||city_id<0){
				if(settings.nodata=="none"){
					dist_obj.css("display","none");
					area_obj.css("display","none");
					cun_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					dist_obj.css("visibility","hidden");
					area_obj.css("visibility","hidden");
					cun_obj.css("visibility","hidden");
				};
				return;
			};
			district=city_obj.val();
			getData(city_obj.val(),3,function(json){
				dist_json=json;
				// 遍历赋值市级下拉列表
				temp_html=select_prehtml;
				$.each(dist_json,function(i,dist){
					temp_html+="<option value='"+dist.code+"'>"+dist.name+"</option>";
				});
				dist_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
				areaStart();
			});
		};
		// 赋值乡镇函数
		var areaStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			var city_id=city_obj.get(0).selectedIndex;
			var dist_id=dist_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
				city_id--;
				dist_id--;
			};
			cun_obj.empty().attr("disabled",true);

			if(prov_id<0||city_id<0||dist_id<0){
				if(settings.nodata=="none"){
					area_obj.css("display","none");
					cun_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					area_obj.css("visibility","hidden");
					cun_obj.css("visibility","hidden");
				};
				return;
			};
			district=dist_obj.val();
			getData(dist_obj.val(),4,function(json){
				area_json=json;
				// 遍历赋值市级下拉列表
				temp_html=select_prehtml;
				$.each(area_json,function(i,area){
					temp_html+="<option value='"+area.code+"'>"+area.name+"</option>";
				});
				area_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
				cunStart();
			});
		};
		
		// 赋值乡镇函数
		var cunStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			var city_id=city_obj.get(0).selectedIndex;
			var area_id=area_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
				city_id--;
				area_id--;
			};

			if(prov_id<0||city_id<0||area_id<0){
				if(settings.nodata=="none"){
					cun_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					cun_obj.css("visibility","hidden");
				};
				return;
			};
			district=area_obj.val();
			getData(area_obj.val(),5,function(json){
				cun_json=json;
				// 遍历赋值市级下拉列表
				temp_html=select_prehtml;
				$.each(cun_json,function(i,cun){
					temp_html+="<option value='"+cun.code+"'>"+cun.name+"</option>";
				});
				cun_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			});
		};

		var init=function(){
			// 遍历赋值省份下拉列表
			temp_html=select_prehtml;
			$.each(prov_json,function(i,prov){
				temp_html+="<option value='"+prov.code+"'>"+prov.name+"</option>";
			});
			prov_obj.html(temp_html);

			// 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
			setTimeout(function(){
				if(settings.prov!=null&&settings.prov!=''){
					prov_obj.val(settings.prov);
					prov_obj.attr("disabled",settings.hide);
					cityStart();
					setTimeout(function(){
						if(settings.city!=null&&settings.city!=''){
							city_obj.val(settings.city);
							city_obj.attr("disabled",settings.hide);
							distStart();
							setTimeout(function(){
								if(settings.dist!=null&&settings.dist!=''){
									dist_obj.val(settings.dist);
									dist_obj.attr("disabled",settings.hide);
									areaStart();
									setTimeout(function(){
										if(settings.area!=null&&settings.area!=''){
											area_obj.val(settings.area);
											area_obj.attr("disabled",settings.hide);
											cunStart();
											setTimeout(function(){
												if(settings.cun!=null&&settings.cun!=''){
													cun_obj.val(settings.cun);
													cun_obj.attr("disabled",settings.hide);
												};
											},300);
										};
									},300);
								};
							},300);
						};
					},300);
				};
			},300);

			// 选择省份时发生事件
			prov_obj.bind("change",function(){
				cityStart();
			});

			// 选择市级时发生事件
			city_obj.bind("change",function(){
				distStart();
			});
			
			// 选择市级时发生事件
			dist_obj.bind("change",function(){
				areaStart();
			});
			
			
			// 选择市级时发生事件
			area_obj.bind("change",function(){
				cunStart();
			});
		};
		
		function getData(pcode,level,callback){
			$.getJSON(settings.url,{pcode:pcode,level:level},function(json){
				callback(json);
			});
		}

		// 设置省市json数据
		if(typeof(settings.url)=="string"){
			getData("0",1,function(json){
				prov_json=json;
				init();
			})
		}else{
			city_json=settings.url;
			init();
		};
	};
})(jQuery);