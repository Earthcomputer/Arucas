package me.senseiwells.arucas.nodes;

import me.senseiwells.arucas.utils.Context;
import me.senseiwells.arucas.throwables.CodeError;
import me.senseiwells.arucas.throwables.ThrowValue;
import me.senseiwells.arucas.tokens.Token;
import me.senseiwells.arucas.utils.SymbolTable;
import me.senseiwells.arucas.values.functions.BuiltInFunction;
import me.senseiwells.arucas.values.Value;

public class VariableAssignNode extends Node {
	public final Node node;

	public VariableAssignNode(Token token, Node node) {
		super(token, token.startPos, token.endPos);
		this.node = node;
	}

	@Override
	public Value<?> visit(Context context) throws CodeError, ThrowValue {
		String name = this.token.content;
		if (BuiltInFunction.isFunction(name))
			throw new CodeError(CodeError.ErrorType.ILLEGAL_OPERATION_ERROR, "Cannot assign " + name + " value as it is a constant", this.startPos, this.endPos);
		Value<?> value = this.node.visit(context);
		context.setVariable(name, value);
		return value;
	}
}
