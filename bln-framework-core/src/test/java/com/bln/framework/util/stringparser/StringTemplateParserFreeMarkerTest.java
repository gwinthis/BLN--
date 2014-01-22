package com.bln.framework.util.stringparser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import freemarker.template.TemplateException;

public class StringTemplateParserFreeMarkerTest {

	private static final Log logger = LogFactory.getLog(StringTemplateParserFreeMarkerTest.class);
	
	@Test
	public void testParse() throws IOException, TemplateException {
		
		Date now = new Date();
		
		IStringTemplateParser strTemplateParser = StringTemplateParserFactory.singleton().getFreeMarkerParser();
		
		String pattern = "yyyy-MM-dd HH:mm:ss";
		String templateSource = String.format("hello ${user}, now is ${now?string(\"%s\")}, yesterday is ${beforeNow[1]?string(\"%s\")}", pattern, pattern);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", "tester");
		
		String result = strTemplateParser.parse(templateSource, params);
		logger.info(result);
		
		String expect = String.format("hello tester, now is %s, yesterday is %s", DateFormatUtils.format(now, pattern), DateFormatUtils.format(DateUtils.addDays(now, -1), pattern));
		logger.info(expect);
		
		assertEquals(result, expect);

		templateSource = "hello ${user} again";
		result = strTemplateParser.parse(templateSource, params);
		logger.info(result);
		assertEquals(result, "hello tester again");
		
	}

}
