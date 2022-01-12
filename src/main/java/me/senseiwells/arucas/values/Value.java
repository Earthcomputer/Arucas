package me.senseiwells.arucas.values;

import me.senseiwells.arucas.api.ArucasClassExtension;
import me.senseiwells.arucas.throwables.CodeError;
import me.senseiwells.arucas.utils.ArucasFunctionMap;
import me.senseiwells.arucas.utils.Context;
import me.senseiwells.arucas.values.classes.ArucasClassValue;
import me.senseiwells.arucas.values.functions.MemberFunction;

public abstract class Value<T> extends BaseValue {
	public final T value;
	
	public Value(T value) {
		this.value = value;
	}

	@Override
	public abstract Value<T> copy();

	@Override
	public Value<T> newCopy() {
		return this.copy();
	}

	@Override
	protected final T getValue() {
		return this.value;
	}

	public static class ArucasBaseClass extends ArucasClassExtension {
		public ArucasBaseClass() {
			super("Object");
		}

		@Override
		public Class<?> getValueClass() {
			return Value.class;
		}

		@Override
		public ArucasFunctionMap<MemberFunction> getDefinedMethods() {
			return ArucasFunctionMap.of(
				new MemberFunction("instanceOf", "class", this::instanceOf),
				new MemberFunction("getValueType", this::getValueType),
				new MemberFunction("copy", this::newCopy),
				new MemberFunction("hashCode", this::hashCode),
				new MemberFunction("equals", "other", this::equals),
				new MemberFunction("toString", this::toString)
			);
		}

		private Value<?> instanceOf(Context context, MemberFunction function) throws CodeError {
			Value<?> thisValue = function.getParameterValue(context, 0);
			StringValue stringValue = function.getParameterValueOfType(context, StringValue.class, 1);
			if (stringValue.value.isEmpty()) {
				return BooleanValue.FALSE;
			}

			if (thisValue instanceof ArucasClassValue classValue) {
				return BooleanValue.of(classValue.getName().equals(stringValue.value));
			}

			Class<?> clazz = this.getClass();
			while (clazz != null && clazz != Object.class) {
				if (clazz.getSimpleName().replaceFirst("Value$", "").equals(stringValue.value)) {
					return BooleanValue.TRUE;
				}

				clazz = clazz.getSuperclass();
			}

			return BooleanValue.FALSE;
		}

		private Value<?> getValueType(Context context, MemberFunction function) {
			Value<?> thisValue = function.getParameterValue(context, 0);
			if (thisValue instanceof ArucasClassValue classValue) {
				return StringValue.of(classValue.getName());
			}

			String valueType = this.getClass().getSimpleName().replaceFirst("Value$", "");
			return StringValue.of(valueType);
		}

		private Value<?> newCopy(Context context, MemberFunction function) {
			Value<?> thisValue = function.getParameterValue(context, 0);
			return thisValue.newCopy();
		}

		private NumberValue hashCode(Context context, MemberFunction function) throws CodeError {
			Value<?> thisValue = function.getParameterValue(context, 0);
			return NumberValue.of(thisValue.getHashCode(context));
		}

		private BooleanValue equals(Context context, MemberFunction function) throws CodeError {
			Value<?> thisValue = function.getParameterValue(context, 0);
			Value<?> otherValue = function.getParameterValue(context, 1);
			return BooleanValue.of(thisValue.isEquals(context, otherValue));
		}

		private StringValue toString(Context context, MemberFunction function) throws CodeError {
			Value<?> thisValue = function.getParameterValue(context, 0);
			return StringValue.of(thisValue.getAsString(context));
		}
	}
}
