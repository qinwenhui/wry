package cn.qinwh.wry.controller.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/*
 * 日期类型转换器，在xml配置后全局有效
 * 如果只想要在某个controller转换类型，使用@InitBinder注解转换的方法
 */
public class CustomDateConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		
		try {
			return format.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
