package edu.utd;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


class MethodTransformVisitor extends MethodVisitor implements Opcodes {


    private String className;
    int line;

    public MethodTransformVisitor(final MethodVisitor mv, String className) {
        super(ASM5, mv);
        this.className = className;

    }


    @Override
    public void visitLineNumber(int line, Label start) {
        this.line = line;
        mv.visitLdcInsn(className + ":" + line);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitMethodInsn(INVOKESTATIC, "edu/utd/CoverageCollection", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitLabel(Label arg0) {
        mv.visitLdcInsn(className + ":" + this.line);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitMethodInsn(INVOKESTATIC, "edu/utd/CoverageCollection", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
        super.visitLabel(arg0);
    }
}
