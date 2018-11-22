package com.bass.notice;
import java.util.LinkedHashMap;
import java.util.Map;

/**1、构造模板体（json包），以交易提醒模板为例，这里封装成一个TradingNotice类。
 * 构造该类时需传入接收者微信的openid、模 板id、模板主体颜色、用户名。
 * 代码中采用LinkedHashMap是为了保证数据是以存入的顺序排序，从而保证json格式的结构不被打乱。
 */

public class TradingNotice {

   private Map<String,Object> map;

   private Map<String,Object> data;

  

   public TradingNotice(String touser, String template_id, String url, String topcolor,String user) {

      map=new LinkedHashMap<String, Object>();

      data=new LinkedHashMap<String, Object>();

     

      LinkedHashMap<String,String> first = new LinkedHashMap<String,String>();

      first.put("value","尊敬的" +user + "：\n\n您尾号为0426的招商银行卡最近有一笔交易(测试)");

      first.put("color","#743A3A");

      data.put("first",first);

 

      LinkedHashMap<String,String> keyword1 = new LinkedHashMap<String,String>();

      keyword1.put("value","YXJ134953845");

      keyword1.put("color","#FF0000");

      data.put("keyword1",keyword1);

 

      LinkedHashMap<String,String> keyword2 = new LinkedHashMap<String,String>();

      keyword2.put("value","2014/08/18 13:18");

      keyword2.put("color","#C4C400");

      data.put("keyword2",keyword2);

 

      LinkedHashMap<String,String> keyword3 = new LinkedHashMap<String,String>();

      keyword3.put("value","1888888888");

      keyword3.put("color","#0000FF");

      data.put("keyword3",keyword3);

 

      LinkedHashMap<String,String> keyword4 = new LinkedHashMap<String,String>();

      keyword4.put("value","消费");

      keyword4.put("color","#008000");

      data.put("keyword4",keyword4);

     

      LinkedHashMap<String,String> keyword5 = new LinkedHashMap<String,String>();

      keyword5.put("value","26万元");

      keyword5.put("color","#008000");

      data.put("keyword5",keyword5);

 

      LinkedHashMap<String,String> remark = new LinkedHashMap<String,String>();

      remark.put("value","\n\n截止2014/08/18 13:18您招商信用账户可用余额未20000元");

      remark.put("color","#000000");

      data.put("remark",remark);

 

      map.put("touser",touser);

      map.put("template_id",template_id);

      map.put("url",url);

      map.put("topcolor",topcolor);

      map.put("data",data);

   }

 

   public Map<String,Object> getMap() {

      return map;

   }

 

   public void setMap(Map<String,Object> map){

      this.map = map;

   }

 

   public Map<String,Object> getDate() {

      return data;

   }

 

   public void setDate(Map<String,Object> date){

      this.data =date;

   }

}