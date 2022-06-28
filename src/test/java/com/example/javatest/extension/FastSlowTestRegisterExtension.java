package com.example.javatest.extension;

import com.example.javatest.annotation.SlowTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class FastSlowTestRegisterExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private long THRESHOLD;

    // 각 테스트 클래스마다 다르게 시간을 설정해서 쓰고싶다면?
    // 이렇게 쓸 경우 선언적으로 @ExtendWith 를 사용할 수 없다.
    // 그래서 @RegisterExtension 을 써야 한다.
    public FastSlowTestRegisterExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        ExtensionContext.Store store = getStore(extensionContext);
        store.put("START_TIME", System.currentTimeMillis());
    }
    /*
        각각 테스트가 실행되기 전, 후 처리기를 사용해서
        테스트 메서드에 어떤 애노테이션이 있는지, 몇초 정도 걸리는데 이것에 따라서 어떤 로그를 남길 경우와 같이
        라이프사이클에 관여할 수 있다.
     */
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        Method requiredTestMethod = extensionContext.getRequiredTestMethod();
        String testMethodName = requiredTestMethod.getName();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);

        ExtensionContext.Store store = getStore(extensionContext);

        long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;

        if(duration > THRESHOLD && annotation == null) {
            System.out.println("this method " + testMethodName + " is required @SlowTest Annotation");
        }
    }

    // store라는것은, 데이터를 넣고 빼는 용도로 쓰는 것.
    private ExtensionContext.Store getStore(ExtensionContext extensionContext) {
        String className = extensionContext.getRequiredTestClass().getName();
        String methodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store =
                extensionContext.getStore(ExtensionContext.Namespace.create(className, methodName));
        return store;
    }
}
