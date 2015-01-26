package info.sigterm.deob.attributes.code.instructions;

import info.sigterm.deob.ClassFile;
import info.sigterm.deob.ConstantPool;
import info.sigterm.deob.attributes.code.Instruction;
import info.sigterm.deob.attributes.code.InstructionType;
import info.sigterm.deob.attributes.code.Instructions;
import info.sigterm.deob.execution.Frame;
import info.sigterm.deob.pool.Method;

import java.io.DataInputStream;
import java.io.IOException;

public class InvokeStatic extends Instruction
{
	private int index;

	public InvokeStatic(Instructions instructions, InstructionType type, int pc) throws IOException
	{
		super(instructions, type, pc);

		DataInputStream is = instructions.getCode().getAttributes().getStream();
		index = is.readUnsignedShort();
		length += 2;
	}

	@Override
	public void execute(Frame e)
	{
		ClassFile thisClass = this.getInstructions().getCode().getAttributes().getClassFile();

		ConstantPool pool = thisClass.getPool();
		Method method = (Method) pool.getEntry(index);
		info.sigterm.deob.pool.Class clazz = method.getClassEntry();
		
		ClassFile otherClass = thisClass.getGroup().findClass(clazz.getName());
		int count = method.getNameAndType().getNumberOfArgs();
		
		Object[] args = new Object[count];
		for (int i = count - 1; i >= 0; --i)
			args[i] = e.getStack().pop();
		
		info.sigterm.deob.Method meth = otherClass.findMethod(method.getNameAndType());
		e.getPath().invoke(meth, args);
	}
}
