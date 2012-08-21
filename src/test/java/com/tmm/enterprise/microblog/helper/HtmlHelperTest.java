package com.tmm.enterprise.microblog.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class HtmlHelperTest {

	@Test
	public void testItalics() {
		String s = HtmlHelper.italics("italicise");
		assertEquals("<i>italicise</i>", s);
	}

	@Test
	public void testBold() {
		String s = HtmlHelper.bold("boldify");
		assertEquals("<b>boldify</b>", s);
	}

}
