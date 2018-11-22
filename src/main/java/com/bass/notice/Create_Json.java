package com.bass.notice;

import java.io.BufferedReader;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;

import java.net.URL;

import java.util.ArrayList;

import java.util.Iterator;


import net.sf.json.JSONObject;

 
/**
2、方法create_TN_Json用来构造json包，方法getUserData用来获取关注者昵称，需传入客户的openid。方法getUserList用来获取微信关注者列表，
将所有关注者的openid保存在一个ArrayList中。
由于获取关注者列表一次只能获取1000个微信号，
所以当关注者多余1000的时候循环调用方法getUserJson来获取所有关注者账号。
 */
public class Create_Json {

   //获取交易提醒json;
	
	private static final String ACCESS_TOKEN = "";
	public static JSONObject create_TN_Json(String touser,String user){

      JSONObject jsonObject=null;

      //模板id

      String template_id="15Eox4OfGsjFYaVRwk9Dbos_aaIkzveCkpG3AsnKqLA";

      //点击模板后的链接地址

      String url="www.baidu.com";

      //模板的主题颜色

      String topcolor="#008000";

      //构造json包

      TradingNotice wn = new TradingNotice(touser,template_id,url,topcolor,user);

      jsonObject=JSONObject.fromObject(wn.getMap());

      return jsonObject;

   }

   //入口;

   public static void main(String[] args){

      //检查access_token是否过期，如果过期重新产生

      //Get_Token.TwoDate();

      //调用getUserList获取关注者列表

      ArrayList<String>users= getUserList();

      if(users!=null){

         Iterator<String> user = users.iterator();

         JSONObject jsonObject1 = null;

         String open_id = null;

         String userName = null;

         while(user.hasNext()){

            open_id = user.next();

            //调用getUserData获取关注者昵称

            userName = getUserData(open_id);

            if(userName!=null){

                //创建交易提醒json包;

                jsonObject1 = Create_Json.create_TN_Json(open_id,userName);

                //发送交易提醒模板消息;

//              send_Json(jsonObject1.toString(),Get_Token.access_token);
                send_Json(jsonObject1.toString(),ACCESS_TOKEN);

            }

         }

      }

   }

  

   //获取用户基本信息(UnionID机制);

   public static String getUserData(String openid){

        StringBuffer bufferRes = new StringBuffer();

        String result = null;

        try {

//                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + Get_Token.access_token +"&openid=" + openid+"&lang=zh_CN");
                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + ACCESS_TOKEN +"&openid=" + openid+"&lang=zh_CN");

                HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

                // 请求方式

                conn.setDoOutput(true);

                conn.setDoInput(true);

                conn.setRequestMethod("GET");

                conn.setUseCaches(false);

                conn.setInstanceFollowRedirects(true);

                conn.setRequestProperty("Content-Type","application/json");

                conn.connect();

                // 获取URLConnection对象对应的输入流

                InputStream in =conn.getInputStream();

                BufferedReader read =new BufferedReader(new InputStreamReader(in,"UTF-8"));

                String valueString =null;

                while ((valueString=read.readLine())!=null){

                        bufferRes.append(valueString);

                }

                System.out.println(bufferRes);

                in.close();

                if (conn != null){

                    // 关闭连接

                    conn.disconnect();

                }

        } catch (Exception e) {

                e.printStackTrace();

        }

        //将返回的字符串转换成json

        JSONObject jsonObject = JSONObject.fromObject(bufferRes.toString());

        //解析json中的数据

        String subscribe = jsonObject.get("subscribe").toString();

        //等于1表示有关注者，0表示没有关注者

        if("1".equals(subscribe.toString())){

          //解析出关注者的昵称

          result = (String)jsonObject.get("nickname");

        }

        return result;

   }

  

   //获取关注列表;

   @SuppressWarnings("unchecked")

   public static ArrayList<String> getUserList() {

        StringBuffer bufferRes = new StringBuffer();

        ArrayList<String> users = null;

        try {
        		//todo
//                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + Get_Token.access_token);
                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + ACCESS_TOKEN);

                HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

                // 请求方式

                conn.setDoOutput(true);

                conn.setDoInput(true);

                conn.setRequestMethod("GET");

                conn.setUseCaches(false);

                conn.setInstanceFollowRedirects(true);

                conn.setRequestProperty("Content-Type","application/json");

                conn.connect();

                // 获取URLConnection对象对应的输入流

                InputStream in =conn.getInputStream();

                BufferedReader read =new BufferedReader(new InputStreamReader(in,"UTF-8"));

                String valueString =null;

                while ((valueString=read.readLine())!=null){

                        bufferRes.append(valueString);

                }

                System.out.println(bufferRes);

                in.close();

                if (conn !=null){

                    // 关闭连接

                    conn.disconnect();

                }

        } catch (Exception e) {

                e.printStackTrace();

        }

        //将返回的字符串转换成json

        JSONObject jsonObject = JSONObject.fromObject(bufferRes.toString());

        //解析json中表示openid的列表

        JSONObject data = (JSONObject)jsonObject.get("data");

        if(data!=null){

          //将openid列表转化成数组保存

          users = new ArrayList<String>(data.getJSONArray("openid"));

          //获取关注者总数

          int count = Integer.parseInt(jsonObject.get("total").toString());

          if(count>1000){

             JSONObject object = jsonObject;

             String next_openid = null;

             JSONObject ob_data = null;

             ArrayList<String> ob_user = null;

             //大于1000需要多次获取，或许次数为count/1000

             for(int i=0;i<count/1000;i++){

                //解析出下次获取的启示openid

                next_openid = object.get("next_openid").toString();

                object = getUserJson(next_openid);

                ob_data = (JSONObject)object.get("data");

                ob_user = new ArrayList<String>(ob_data.getJSONArray("openid"));

                for(String open_id : ob_user){

                    //将多次获取的openid添加到同一个数组

                    users.add(open_id);

                }

            }

          }

        }

        return users;

   }

   

   //连续获取关注列表;

   public static JSONObject getUserJson(String next_openid) {

        StringBuffer bufferRes =new StringBuffer();

        try {

//                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + Get_Token.access_token +"&next_openid=" + next_openid);
                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + ACCESS_TOKEN +"&next_openid=" + next_openid);

                HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

                // 请求方式

                conn.setDoOutput(true);

               conn.setDoInput(true);

                conn.setRequestMethod("GET");

                conn.setUseCaches(false);

                conn.setInstanceFollowRedirects(true);

                conn.setRequestProperty("Content-Type","application/json");

               conn.connect();

                // 获取URLConnection对象对应的输入流

                InputStream in =conn.getInputStream();

                BufferedReader read =new BufferedReader(new InputStreamReader(in,"UTF-8"));

                String valueString =null;

                while ((valueString=read.readLine())!=null){

                        bufferRes.append(valueString);

                }

                System.out.println(bufferRes);

                in.close();

                if (conn !=null){

                    // 关闭连接

                    conn.disconnect();

                }

        } catch (Exception e) {

                e.printStackTrace();

        }

       

        JSONObject jsonObject = JSONObject.fromObject(bufferRes);

        return jsonObject;

   }

  

   //发送模板;

   public static void send_Json(String params,String accessToken){

        StringBuffer bufferRes =new StringBuffer();

        try {

                URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" +accessToken);

                HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

                // 请求方式

                conn.setDoOutput(true);

                conn.setDoInput(true);

                conn.setRequestMethod("POST");

                conn.setUseCaches(false);

                conn.setInstanceFollowRedirects(true);

                conn.setRequestProperty("Content-Type","application/json");

                conn.connect();

                // 获取URLConnection对象对应的输出流

                OutputStreamWriter out =new OutputStreamWriter(conn.getOutputStream(),"UTF-8");

                // 发送请求参数

                //out.write(URLEncoder.encode(params,"UTF-8"));

                //发送json包

                out.write(params);

               out.flush();

                out.close();

                InputStream in =conn.getInputStream();

                BufferedReader read =new BufferedReader(new InputStreamReader(in,"UTF-8"));

                String valueString =null;

                while ((valueString=read.readLine())!=null){

                        bufferRes.append(valueString);

                }

                //输出返回的json

                System.out.println(bufferRes);

                in.close();

                if (conn !=null){

                    // 关闭连接

                    conn.disconnect();

                }

        } catch (Exception e) {

                e.printStackTrace();

        }

   }

}
