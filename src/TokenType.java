public enum TokenType{
    KEYWORD ("keyword"),
    TERMINAL ("terminal"),
    NON_TERMINAL ("non_terminal"),
    END_OF_TOKENS ("end_of_tokens"),
    END_OF_RULE ("end_of_rule"),
    META ("meta"),
    ASSIGN ("assign"),
    DOLLAR ("dollar");

    private final String str_rep;
    TokenType(String str_rep){
	this.str_rep = str_rep;
    }

    public String toString(){
	return this.str_rep;
    }

}
