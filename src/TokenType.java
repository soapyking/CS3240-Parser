public enum TokenType{
    KEYWORD ("keyword"),
    TERMINAL ("terminal"),
    NON_TERMINAL ("nonterminal"),
    END_OF_TOKENS ("end_of_tokens"),
    END_OF_RULE ("end_of_rule"),
    META ("meta"),
    ASSIGN ("assign"),
    RULE_SEP ("rule_sep"),
    LINE_END ("line_end"),
    DOLLAR ("dollar");

    private final String str_rep;
    TokenType(String str_rep){
	this.str_rep = str_rep;
    }

    public String toString(){
	return this.str_rep;
    }

}
