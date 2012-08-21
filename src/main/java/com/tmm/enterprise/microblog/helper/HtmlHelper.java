/**
 * 
 */
package com.tmm.enterprise.microblog.helper;

/**
 * @author robert.hinds
 * 
 *         Class to adapt strings for HTML formating
 */
public class HtmlHelper {

	public static final String NEWLINE = "<br/>";
	public static final String UL_OPEN = "<ul>";
	public static final String UL_CLOSE = "</ul>";
	public static final String LI_OPEN = "<li>";
	public static final String LI_CLOSE = "</li>";

	public static String italics(String input) {
		return "<i>" + input + "</i>";
	}

	public static String bold(String input) {
		return "<b>" + input + "</b>";
	}
}
