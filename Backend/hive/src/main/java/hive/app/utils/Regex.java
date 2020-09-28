package hive.app.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	public static List<String> getUserNamesMentionedInText(String postText){
    	List<String> list = new ArrayList<String>();
		Pattern checkRegex = Pattern.compile("@\\S{1,30}");
		Matcher regexMatcher = checkRegex.matcher(postText);
		while ( regexMatcher.find() ){
			if (regexMatcher.group().length() != 0 && list.contains((regexMatcher.group().trim()).substring(1)) != true){
				list.add((regexMatcher.group().trim()).substring(1));
			}
		}
		return list;
	}
}