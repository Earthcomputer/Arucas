package me.senseiwells.arucas.tokens;

import me.senseiwells.arucas.utils.Position;

import java.util.Map;
import java.util.Set;

public class Token {
	
	public final Type type;
	public final String content;
	public final Position startPos;
	public final Position endPos;
	
	public Token(Type type, String content, Position startPos, Position endPos) {
		this.type = type;
		this.content = content;
		this.startPos = startPos;
		this.endPos = endPos;
	}
	
	public Token(Type type, Position startPos, Position endPos) {
		this(type, "", startPos, endPos);
	}
	
	public Token(Type type, Position startPos) {
		this(type, "", startPos, new Position(startPos.index + 1, startPos.line, startPos.column + 1, startPos.fileName));
	}
	
	@Override
	public String toString() {
		return "Token{type=%s, content='%s'}".formatted(this.type, this.content);
	}
	
	public enum Type {
		// Delimiters
		WHITESPACE,
		SEMICOLON,
		COLON,
		IDENTIFIER,
		COMMA,
		FINISH,
		
		// Atoms
		FLOAT,
		BOOLEAN,
		STRING,
		NULL,
		LIST,
		SCOPE,
		
		// Arithmetics
		PLUS,
		MINUS,
		MULTIPLY,
		DIVIDE,
		POWER,
		
		// Boolean operators
		NOT,
		AND,
		OR,
		
		// Brackets
		LEFT_BRACKET,
		RIGHT_BRACKET,
		LEFT_SQUARE_BRACKET,
		RIGHT_SQUARE_BRACKET,
		LEFT_CURLY_BRACKET,
		RIGHT_CURLY_BRACKET,
		
		// Memory Operator
		ASSIGN_OPERATOR,
		INCREMENT,
		DECREMENT,
		
		// Comparisons
		EQUALS,
		NOT_EQUALS,
		LESS_THAN,
		MORE_THAN,
		LESS_THAN_EQUAL,
		MORE_THAN_EQUAL,
		
		// Statements
		IF,
		WHILE,
		ELSE,
		CONTINUE,
		BREAK,
		VAR,
		RETURN,
		FUN,
		TRY,
		CATCH,
		FOREACH,

		// Dot
		DOT,
		;

		public static Set<Type> comparisonTokens = Set.of(
			EQUALS,
			NOT_EQUALS,
			LESS_THAN,
			MORE_THAN,
			LESS_THAN_EQUAL,
			MORE_THAN_EQUAL
		);
	}
}
