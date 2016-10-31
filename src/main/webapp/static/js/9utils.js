 /*-- NTV
====================================================== --*/

 var ntv = ntv || {};

/*-- Utils 工具类
====================================================== */
 ntv.utils = function(){};


/*-- 字符串相关
====================================================== */
 ntv.utils.string = function(){};
 ntv.utils.string.substring = function(str,len){
  var r = /[^\x00-\xff]/g; // 支持中英文数字混合模式

  if(str.replace(r,"mm").length <= len)
	return str;

  var m = Math.floor(len / 2);
  for(var i = m; i < str.length ; i++){
      if(str.substr(0, i).replace(r,"mm").length >= len){
          return str.substr(0, i) + "...";
      }
  }
  return str;
};

/*-- 机顶盒相关
====================================================== */
 ntv.utils.stb = function(){};
 ntv.utils.stb.clear_html = function(str){
  var rs = this.clear_a_mailto(str);

  return rs;
 };

 /* 解决HTML<a href="mailto:li.wen@shanghaiik.com"></a>
  * 含带href="mailto:**"导致机顶盒的浏览器无法解析。*/
 ntv.utils.stb.clear_a_mailto = function(str){
  return str.replace(/href="mailto:/g, " style='color:#000;' href='#mailto:'");
 };