package com;

public enum tokenType {
		KEYWORD(1),
		TERMINAL(2),
		NON_TERMINAL(3),
		END_OF_TOKENS(4);
		
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
