package me.senseiwells.arucas.values;

import me.senseiwells.arucas.throwables.CodeError;
import me.senseiwells.arucas.throwables.ErrorRuntime;
import me.senseiwells.arucas.tokens.Token;

public class NumberValue extends Value<Double> {

	public NumberValue(double value) {
		super(value);
	}

	@Override
	public NumberValue addTo(Value<?> other) throws CodeError {
		if (!(other instanceof NumberValue otherValue))
			throw new CodeError(CodeError.ErrorType.ILLEGAL_OPERATION_ERROR, "The 'add' operator cannot be applied to %s and %s".formatted(this, other), this.startPos, this.endPos);
		return new NumberValue(this.value + otherValue.value);
	}

	public NumberValue subtractBy(NumberValue other) {
		return new NumberValue(this.value - other.value);
	}

	public NumberValue multiplyBy(NumberValue other) {
		return new NumberValue(this.value * other.value);
	}

	public NumberValue divideBy(NumberValue other) throws ErrorRuntime {
		if (other.value == 0)
			throw new ErrorRuntime("You cannot divide by 0", other.startPos, other.endPos);
		return new NumberValue(this.value / other.value);
	}

	public NumberValue powerBy(NumberValue other) throws ErrorRuntime {
		if (this.value < 0 || (other.value % 1) != 0)
			throw new ErrorRuntime("You cannot calculate imaginary numbers", other.startPos, other.endPos);
		
		return new NumberValue(Math.pow(this.value, other.value));
	}

	public BooleanValue compareNumber(NumberValue other, Token.Type type) {
		boolean bool;
		switch (type) {
			case LESS_THAN -> bool = this.value < other.value;
			case MORE_THAN -> bool = this.value > other.value;
			case MORE_THAN_EQUAL -> bool = this.value >= other.value;
			case LESS_THAN_EQUAL -> bool = this.value <= other.value;
			default -> bool = false;
		}
		return new BooleanValue(bool);
	}

	@Override
	public Value<Double> copy() {
		return new NumberValue(this.value).setPos(this.startPos, this.endPos);
	}
}
