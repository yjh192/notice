package com.bass.voice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class Template {
	private static Logger log = LoggerFactory.getLogger(Template.class);

	/**
	 * 发送模板消息 appId 公众账号的唯一标识 appSecret 公众账号的密钥 openId 用户标识
	 */
	/*public final static String appId ="wx4c4b38667d906b56";
	public final static String appSecret ="ebeba571c6a649e76559ca407400b568";*/
	

	public static void send_template_message(String appId, String appSecret,
			String openId, String userName, Long weChatId, Long userId,
			String format1) {
		// 因为我申请的模板是需要填写当前时间戳的，所以在这里我获取了当前的时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH时mm分");
		String format = simpleDateFormat.format(new Date());
		Token token = CommonUtil.getToken(appId, appSecret);// 这里要注意，如果你是申请的正式公众号的话，获取token的时候，一定要在后台加上你的ip，不然获取token的时候会报错
		String access_token = token.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ access_token;
		String templateId = "GV9wBYNtAZz9_PrI6hnC0QJBPfLk1gEJduYF9Kh4cWc";// 填写申请的模板id
		String goUrl = "";// 填写点击推送的消息需跳转到的url

		Data_style first = new Data_style();
		Data_style keyword1 = new Data_style();
		Data_style keyword2 = new Data_style();
		Data_style keyword3 = new Data_style();
		Data_style remark = new Data_style();

		NewOrdersTemplate temp = new NewOrdersTemplate();
		Data data = new Data();

		first.setValue("你好");
		first.setColor("#173177");

		keyword1.setValue("2015年7月10日");
		keyword1.setColor("#173177");

		keyword2.setValue("户籍相关会议");
		keyword2.setColor("#173177");
		
		keyword3.setValue("丰产路派出所5楼501");
		keyword3.setColor("#173177");

		remark.setValue("请按时参加会议");
		remark.setColor("#173177");

		data.setFirst(first);
		data.setKeyword1(keyword1);
		data.setKeyword2(keyword2);
		data.setRemark(remark);

		temp.setTouser(openId);
		temp.setTemplate_id(templateId);
		temp.setUrl(goUrl);
		temp.setTopcolor("#173177");
		temp.setData(data);

		String jsonString = JSONObject.fromObject(temp).toString().replace("day", "Day");
		JSONObject jsonObject = CommonUtil
				.httpsRequest(url, "POST", jsonString);
		System.out.println(jsonObject);
		int result = 0;
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("错误 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		log.info("模板消息发送结果：" + result);
	}
	
}
