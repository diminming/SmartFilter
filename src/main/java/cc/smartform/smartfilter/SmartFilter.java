package cc.smartform.smartfilter;

import javassist.*;

import java.util.*;

/**
 * Created by Matt Di on 2017/2/21.
 */
public class SmartFilter {

    private static final ClassPool CLASS_POOL = ClassPool.getDefault();
    private final Map<Class, FilterAdapter> filterAdapterMap = new HashMap<Class, FilterAdapter>();

    public final <T> T getProxyObject(T target, Map<String, String[]> blacklists) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        String targetClassName = target.getClass().getName();
        String proxyClassname = targetClassName + "$$Proxy";

        CtClass targetClass = CLASS_POOL.get(targetClassName);
        CtClass proxyClass = CLASS_POOL.getOrNull(proxyClassname);

        Class clazz;
        String[] blacklist = blacklists.get(targetClassName);
        HashSet<String> blackSet = new HashSet<String>(Arrays.asList(blacklist));

        if(proxyClass == null) {

            proxyClass = CLASS_POOL.getAndRename(targetClassName, proxyClassname);
            proxyClass.setSuperclass(targetClass);
            proxyClass.setInterfaces(new CtClass[]{CLASS_POOL.get(ProxyComponent.class.getName())});

            CtMethod[] methods = proxyClass.getDeclaredMethods();

            CtField targetField = CtField.make("private "+targetClassName+" target;", proxyClass);
            CtField blackListField = CtField.make("private java.util.Map blacklists;", proxyClass);
            proxyClass.addField(blackListField);
            proxyClass.addField(targetField);

            CtMethod setTarget = CtMethod.make("public void __setTarget__(Object target) { this.target = ("+targetClassName+")target;}", proxyClass);
            CtMethod setBlacklist = CtMethod.make("public void __setBlacklist__(java.util.Map blacklists) { this.blacklists = blacklists;}", proxyClass);
            proxyClass.addMethod(setTarget);
            proxyClass.addMethod(setBlacklist);


            for(CtMethod method : methods) {
                String methodName = method.getName();
                if(methodName.startsWith("__") && methodName.endsWith("__")) {
                    continue;
                }

                if(blackSet.contains(methodName)) {
                    if(method.getReturnType().equals(CtClass.voidType)) {
                        method.setBody("{return;}");
                    } else if(method.getReturnType().equals(CtClass.booleanType)) {
                        method.setBody("{return false;}");
                    } else if(method.getReturnType().equals(CtClass.byteType)) {
                        method.setBody("{return Byte.MIN_VALUE;}");
                    } else if(method.getReturnType().equals(CtClass.charType)) {
                        method.setBody("{return '';}");
                    } else if(method.getReturnType().equals(CtClass.doubleType)) {
                        method.setBody("{return Double.MIN_VALUE;}");
                    } else if(method.getReturnType().equals(CtClass.floatType)) {
                        method.setBody("{return Float.MIN_VALUE;}");
                    } else if(method.getReturnType().equals(CtClass.intType)) {
                        method.setBody("{return Integer.MIN_VALUE;}");
                    } else if(method.getReturnType().equals(CtClass.longType)) {
                        method.setBody("{return Long.MIN_VALUE;}");
                    } else {
                        method.setBody("{return null;}");
                    }
                } else if(filterAdapterMap.containsKey(method.getReturnType())) {
                    FilterAdapter filterAdapter = filterAdapterMap.get(method.getReturnType());
                    List list = new ArrayList();
                    method.setBody(
                            "{" + "" + "}"
                    );
                } else {
                    if(method.getReturnType().equals(CtClass.voidType)) {
                        method.setBody("{ this.target." + methodName +"($$); }");
                    } else {
                        method.setBody("{ return this.target." + methodName +"($$); }");
                    }
                }

            }

            clazz = proxyClass.toClass();
        } else {
            clazz = Class.forName(proxyClassname);
        }

        Object proxy = clazz.newInstance();
        ProxyComponent utils = (ProxyComponent)proxy;
        utils.__setTarget__(target);
        utils.__setBlacklist__(blacklists);

        return (T)proxy ;

    }

}
