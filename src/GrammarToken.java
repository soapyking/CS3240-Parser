import org.apache.regexp.*;

public enum GrammarToken{

    TOKEN_META      	("TOKEN_META"	, new RE("^[ ]*" + "%TOKENS"), TokenType.META),
    NT_META      	("NT_META"	, new RE("^[ ]*" + "%NON-TERMINALS"), TokenType.META),
    START_META       	("START_META"	, new RE("^[ ]*" + "%START"), TokenType.META),
    RULE_META        	("RULE_META" 	, new RE("^[ ]*" + "%RULES"), TokenType.META),
    ASSIGN              ("ASSIGN"       , new RE("^[ ]*" + "(:)"), TokenType.ASSIGN),
    NON_TERMINAL        ("NON_TERMINAL" , new RE("^[ ]*" + "(<.+?>)"), TokenType.NON_TERMINAL),
    RULE_SEP            ("RULE_SEP"	, new RE("^[ ]*" + "(\\|)"), TokenType.RULE_SEP),
    LINE_END            ("LINE_END"     , new RE("^[\\r\\n]+"), TokenType.LINE_END),
    TOKEN               ("TOKEN"	, new RE("^[ ]*" + "[A-Z]+"), TokenType.TERMINAL);


    public final String token;
    public final RE regex;
    public final TokenType type;
    GrammarToken(String token, RE regex, TokenType type){
	this.token = token;
	this.regex = regex;
	this.type = type;
    }

}
