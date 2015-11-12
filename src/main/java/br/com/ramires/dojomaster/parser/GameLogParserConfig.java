package br.com.ramires.dojomaster.parser;

public class GameLogParserConfig {

	static public final String dateHourFormatInTheGameLogFile   = "dd/MM/yy hh:mm:ss";

	static public final String dateHourRegexExpression 			= "^(\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}) - ";
	static public final String newMatchRegexExpression 			= "New match (\\d+) has started";
	static public final String killMatchRegexExpression 		= "(.+) killed (.+) (using|by) (.+)$";
	static public final String endMatchRegexExpression 			= "Match (\\d+) has ended";

	static public final String dateHourNewMatchRegexExpression 	= dateHourRegexExpression + newMatchRegexExpression;
	static public final String dateHourKillMatchRegexExpression = dateHourRegexExpression + killMatchRegexExpression;
	static public final String dateHourEndMatchRegexExpression 	= dateHourRegexExpression + endMatchRegexExpression;

	static public final int DATE_TIME_POS	=  1;
	static public final int MATCH_POS 		=  2;
	static public final int PLAYER_POS 		=  2;
	static public final int FRAG_POS 		=  3;
	static public final int WEAPON_POS 		=  5;

	static public final int TYPE_NEW_MATCH 	=  0;
	static public final int TYPE_ACTION		=  1;
	static public final int TYPE_END_MATCH	=  2;
}
