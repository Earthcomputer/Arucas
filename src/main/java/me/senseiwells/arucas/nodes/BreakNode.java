package me.senseiwells.arucas.nodes;

import me.senseiwells.arucas.tokens.Token;
import me.senseiwells.arucas.throwables.ThrowValue;
import me.senseiwells.arucas.utils.Context;
import me.senseiwells.arucas.utils.Position;
import me.senseiwells.arucas.values.Value;

public class BreakNode extends Node {
	public BreakNode(Position startPos, Position endPos) {
		super(new Token(Token.Type.BREAK, startPos, endPos));
	}

	@Override
	public Value<?> visit(Context context) throws ThrowValue {
		ThrowValue throwValue = new ThrowValue();
		throwValue.shouldBreak = true;
		throw throwValue;
	}
}
