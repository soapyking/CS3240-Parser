import org.apache.regexp.*;

public enum SourceToken{


    // Terminals

    LEFTPAR     	("LEFTPAR"	, new RE("^" + "(\\()"), TokenType.TERMINAL),
    RIGHTPAR    	("RIGHTPAR"	, new RE("^" + "(\\))"), TokenType.TERMINAL),
    ASSIGN      	("ASSIGN"	, new RE("^" + "(:=)"), TokenType.ASSIGN),
    COMMA       	("COMMA"	, new RE("^" + "(,)"), TokenType.TERMINAL),
    SEMICOLON   	("SEMICOLON"	, new RE("^" + "(;)"), TokenType.TERMINAL),
    PLUS        	("PLUS"		, new RE("^" + "(\\+)"), TokenType.TERMINAL),
    MINUS       	("MINUS"	, new RE("^" + "(-)"), TokenType.TERMINAL),
    MULTIPLY    	("MULTIPLY"	, new RE("^" + "(\\*)"), TokenType.TERMINAL),
    MODULO      	("MODULO"	, new RE("^" + "(%)"), TokenType.TERMINAL),

    // Keywords
    BEGIN       	("BEGIN"	, new RE("^" + "(BEGIN)"), TokenType.KEYWORD),
    END         	("END"		, new RE("^" + "(END)"), TokenType.KEYWORD),
    PRINT       	("PRINT"	, new RE("^" + "(PRINT)"), TokenType.KEYWORD),

    // Non-implemented
    READ        	("READ"		, new RE("^" + "(READ)"), TokenType.KEYWORD),
    WRITE           ("WRITE"	, new RE("^" + "(WRITE)"), TokenType.KEYWORD),
    IF          	("IF"		, new RE("^" + "(IF)"), TokenType.KEYWORD),
    THEN        	("THEN"		, new RE("^" + "(THEN)"), TokenType.KEYWORD),
    UNTIL       	("UNTIL"	, new RE("^" + "(UNTIL)"), TokenType.KEYWORD),
    REPEAT      	("REPEAT"	, new RE("^" + "(REPEAT)"), TokenType.KEYWORD),
    LESSTHAN    	("LESSTHAN"	, new RE("^" + "(<)"), TokenType.TERMINAL),
    GREATERTHAN 	("GREATERTHAN"	, new RE("^" + "(>)"), TokenType.TERMINAL),
    EQUALTO     	("EQUALTO"	, new RE("^" + "(=)"), TokenType.TERMINAL),


    // Char classes
    INTNUM      	("INTNUM"	, new RE("(^0)|(^[1-9][0-9]*)", RE.REPLACE_BACKREFERENCES), TokenType.TERMINAL),
    COMMENT         ("COMMENT"	, new RE("\\{(.*?)\\}"), TokenType.TERMINAL),
    ID          	("ID"		, new RE("(^[_A-Za-z][_A-Za-z0-9]{0,9})"), TokenType.TERMINAL);

    public final String token;
    public final RE regex;
    public final TokenType type;
    SourceToken(String token, RE regex, TokenType type){
	this.token = token;
	this.regex = regex;
	this.type = type;
    }

}
