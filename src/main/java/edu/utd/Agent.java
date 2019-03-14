package edu.utd;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
//        System.out.println("My Java Agent is now executing");
        inst.addTransformer(new ClassFileTransformer() {
            public byte[] transform(ClassLoader classLoader, String className, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
                // ASM Code
                if (className.startsWith("org/apache/commons/dbutils") ||
                        className.startsWith("org/joda/time") ||
                        className.startsWith("com/fasterxml/aalto") ||
                        className.startsWith("org/neo4j/batchimport") ||
                        className.startsWith("com/github/vbauer/caesar") ||
                        className.startsWith("com/vaadin/demo/dashboard") ||
                        className.startsWith("au/com/ds/ef") ||
                        className.startsWith("de/apaxo/bedcon") ||
                        className.startsWith("com/tagtraum/perf/gcviewer") ||
                        className.startsWith("org/hashids")
                ){
                    System.out.println(className);
                    ClassReader cr = new ClassReader(bytes);
                    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                    ClassTransformVisitor ca = new ClassTransformVisitor(cw);
                    cr.accept(ca, 0);
                    return cw.toByteArray();
                }
                return bytes;
            }
        });
    }
}
