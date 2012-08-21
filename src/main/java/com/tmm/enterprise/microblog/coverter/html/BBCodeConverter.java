package com.tmm.enterprise.microblog.coverter.html;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service("bbcodeConverter")
public class BBCodeConverter implements IHtmlConverter {

	private static final String HTML_NEWLINE = "<br/>";
	private static final String HTML_BOLD = "<strong>$1</strong>";
	private static final String HTML_ITALIC = "<span style='font-style:italic;'>$1</span>";
	private static final String HTML_UNDERLINE = "<span style='text-decoration:underline;'>$1</span>";
	private static final String HTML_QUOTE = "<div class=\"prettyprint\">$1</div>";
	private static final String HTML_CODE = "<div class=\"autoWidth\"><pre class=\"prettyprint\"><code>$1</code></pre></div>";
	private static final String HTML_A = "<a href='$1'>$1</a>";
	private static final String HTML_A_LINK = "<a href='$1'>$2</a>";
	private static final String BBVODE_URL_LINK = "\\[url=(.+?)\\](.+?)\\[/url\\]";
	private static final String BBCODE_URL = "\\[url\\](.+?)\\[/url\\]";
	private static final String BBCODE_QUOTE = "\\[quote\\](.+?)\\[/quote\\]";
	private static final String BBCODE_CODE = "\\[code\\](.+?)\\[/code\\]";
	private static final String BBCODE_UNDERLINE = "\\[u\\](.+?)\\[/u\\]";
	private static final String BBCODE_ITALIC = "\\[i\\](.+?)\\[/i\\]";
	private static final String BBCODE_BOLD = "\\[b\\](.+?)\\[/b\\]";
	private static final String BBCODE_NEWLINE = "(\r\n|\r|\n|\n\r)";

	/**
	 * Method to convert a string that may include BBCode formating (from the
	 * markItUp text area) to HTML code for display in browser
	 * 
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String convertToHtml(String input) {
		Map<String, String> bbMap = new HashMap<String, String>();
		bbMap.put(BBCODE_NEWLINE, HTML_NEWLINE);
		bbMap.put(BBCODE_BOLD, HTML_BOLD);
		bbMap.put(BBCODE_ITALIC, HTML_ITALIC);
		bbMap.put(BBCODE_UNDERLINE, HTML_UNDERLINE);
		bbMap.put(BBCODE_QUOTE, HTML_QUOTE);
		bbMap.put(BBCODE_CODE, HTML_CODE);
		bbMap.put(BBCODE_URL, HTML_A);
		bbMap.put(BBVODE_URL_LINK, HTML_A_LINK);

		// first replace any code brackets that are closed and instantly opened
		// again
		input = input.replaceAll("\\[/code\\]\\n\\[code\\]", "\n").replaceAll(">", "&gt;").replaceAll("<", "&lt;");

		for (Map.Entry entry : bbMap.entrySet()) {
			Pattern p = Pattern.compile(entry.getKey().toString(), Pattern.DOTALL);
			Matcher m = p.matcher(input);
			input = m.replaceAll(entry.getValue().toString());
		}
		return input;

	}

}
