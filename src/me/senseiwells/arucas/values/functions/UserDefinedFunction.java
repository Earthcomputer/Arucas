package me.senseiwells.arucas.values.functions;

import me.senseiwells.arucas.utils.Context;
import me.senseiwells.arucas.throwables.CodeError;
import me.senseiwells.arucas.throwables.ThrowValue;
import me.senseiwells.arucas.utils.Interpreter;
import me.senseiwells.arucas.nodes.Node;
import me.senseiwells.arucas.values.NullValue;
import me.senseiwells.arucas.values.Value;

import java.util.List;

public class UserDefinedFunction extends FunctionValue {

    Node bodyNode;
    List<String> argumentNames;

    public UserDefinedFunction(String name, Node bodyNode, List<String> argumentNames) {
        super(name);
        this.bodyNode = bodyNode;
        this.argumentNames = argumentNames;
    }

    public Value<?> execute(List<Value<?>> arguments) throws CodeError {
        Interpreter interpreter = new Interpreter();
        Context context = this.generateNewContext();
        this.checkAndPopulateArguments(arguments, this.argumentNames, context);
        try {
            interpreter.visit(this.bodyNode, context);
            return new NullValue();
        }
        catch (ThrowValue tv) {
            return tv.returnValue;
        }
    }

    @Override
    public Value<?> copy() {
        return new UserDefinedFunction(this.value, this.bodyNode, this.argumentNames).setPos(this.startPos, this.endPos).setContext(this.context);
    }
}
