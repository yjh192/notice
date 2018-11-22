package com.bass.voice;

import static org.junit.Assert.*;

import org.junit.Test;

public class TemplateTest {
	/*or3XU0xilw7qS11IhbMOEVKvLVeI*/
	public final static String appId ="wx4c4b38667d906b56";
	public final static String appSecret ="ebeba571c6a649e76559ca407400b568";
	public final static String openId = "or3XU0xilw7qS11IhbMOEVKvLVeI";

	@Test
	public void testSend_template_message() {
		Template.send_template_message(appId, appSecret, openId, "", Long.valueOf(0), Long.valueOf(0), "");
	}

}
