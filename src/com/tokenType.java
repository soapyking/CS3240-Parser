
public enum tokenType {
		KEYWORD(0),
		TERMINAL(1),
		NON_TERMINAL(2),
		END_OF_TOKENS(3);
		
		private final int type;
		
		private tokenType(int type)
		{
			this.type = type;
		}
		
		public int getType()
		{
			return type;
		}
}
