package edu.utd;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            public byte[] transform(ClassLoader classLoader, String className, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {

                // ASM Code
                if (className.startsWith("org/apache/commons/dbutils") || className.startsWith("org/joda/time")){
                    ClassReader reader = new ClassReader(bytes);
                    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                    ClassTransformVisitor visitor = new ClassTransformVisitor(writer);
                    reader.accept(visitor, 0);
                    return writer.toByteArray();
                }
                return bytes;
            }
        });
    }
}
