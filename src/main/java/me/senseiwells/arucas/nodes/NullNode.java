package me.senseiwells.arucas.nodes;

import me.senseiwells.arucas.tokens.Token;
import me.senseiwells.arucas.utils.Context;
import me.senseiwells.arucas.values.NullValue;
import me.senseiwells.arucas.values.Value;

public class NullNode extends Node {
	private final NullValue value;

	public NullNode(Token token) {
		super(token);
		this.value = new NullValue();
	}

	@Override
	public Value<?> visit(Context context) {
		return this.value;
	}
}
