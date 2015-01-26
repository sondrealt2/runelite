package info.sigterm.deob.attributes.code.instructions;

import info.sigterm.deob.attributes.code.Instruction;
import info.sigterm.deob.attributes.code.InstructionType;
import info.sigterm.deob.attributes.code.Instructions;
import info.sigterm.deob.execution.Frame;
import info.sigterm.deob.execution.Stack;

import java.io.IOException;

public class Dup2_X2 extends Instruction
{
	public Dup2_X2(Instructions instructions, InstructionType type, int pc) throws IOException
	{
		super(instructions, type, pc);
	}

	@Override
	public void execute(Frame frame)
	{
		Stack stack = frame.getStack();

		Object one = stack.pop();
		Object two = null;
		if (!(one instanceof Double) && !(one instanceof Long))
			two = stack.pop();
		Object three = stack.pop();
		Object four = null;
		if (!(three instanceof Double) && !(three instanceof Long))
			four = stack.pop();

		if (!(one instanceof Double) && !(one instanceof Long))
			stack.push(this, two);
		stack.push(this, one);

		if (!(three instanceof Double) && !(three instanceof Long))
			stack.push(this, four);
		stack.push(this, three);

		if (!(one instanceof Double) && !(one instanceof Long))
			stack.push(this, two);
		stack.push(this, one);
	}
}
