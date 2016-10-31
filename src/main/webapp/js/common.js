 /*  
        序列化表单数据到JSON对象  
    */  
    (function($){  
        $.fn.serializeJson=function(){  
            var serializeObj={};  
            var array=this.serializeArray();  
            var str=this.serialize();  
            $(array).each(function(){  
                if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push(this.value);  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
                    }  
                }else{  
                    serializeObj[this.name]=this.value;   
                }  
            });  
            return serializeObj;  
        };  
    })(jQuery);  
      
      
(function($){
		$(window).load(function(){
			$(".content").delegate("a[rel='load-content']","click",function(e){
				e.preventDefault();
				$(".content a").removeClass("active");
				var url=$(this).attr("href");
				$(this).addClass("active");
				if(url!=''){
					$.get(url,function(data){
						load(data);
					});
				}
			});
			
			$(".cont_col_rt").delegate("form","submit",function(e){
				e.preventDefault();
				ajaxSubmit(this, function(data){
					loadRight(data);
		        });
			    return false;
			});
			
			$(".content").delegate("form","submit",function(e){
				e.preventDefault();
				ajaxSubmit(this, function(data){
		            load(data);
		        });
			    return false;
			});
			
			function load(data){
				$(".content .mCSB_container .rt_content").html(data); //load new content inside .mCSB_container
				//$(".content").mCustomScrollbar("scrollTo","h2:last");
			}

			//将form转为AJAX提交
			function ajaxSubmit(frm, fn) {
			    var dataPara = $(frm).serializeJson;
			    $.ajax({
			        url: frm.action,
			        type: frm.method,
			        data: dataPara,
			        success: fn
			    });
			}

		});
	})(jQuery);