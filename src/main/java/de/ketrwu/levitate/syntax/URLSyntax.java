package de.ketrwu.levitate.syntax;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.ketrwu.levitate.Message;
import de.ketrwu.levitate.Message.TextMode;
import de.ketrwu.levitate.SyntaxHandler;
import de.ketrwu.levitate.exception.SyntaxResponseException;

/**
 * Checks if user-input is an url
 * @author Kenneth Wussmann
 */
public class URLSyntax implements SyntaxHandler {

	@Override
	public void check(String parameter, String passed) throws SyntaxResponseException {
		HashMap<String, String> replaces = new HashMap<String, String>();
		replaces.put("%arg%", passed);
		
		try {
			new URL(passed);
			if(parameter != null && !parameter.equals("")) {
				if(!passed.toLowerCase().startsWith(parameter.toLowerCase())) {
					replaces.put("%parameter%", parameter);
					throw new SyntaxResponseException(Message.URLSYNTAX_DOES_NOT_START_WITH.get(TextMode.COLOR, replaces));
				}
			}
		} catch (MalformedURLException e) {
			throw new SyntaxResponseException(Message.URLSYNTAX_URL_MALFORMED.get(TextMode.COLOR, replaces));
		}
	}

	@Override
	public List<String> getTabComplete(String parameter, String passed) {
		List<String> complete = new ArrayList<String>();
		complete.add("http://");
		complete.add("https://");
		complete.add("http://www.");
		complete.add("https://www.");
		complete.add("ftp://");
		return complete;
	}
}
