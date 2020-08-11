package com.ctd.springboot.feign.exception.asserts;

import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;

/**
 * Then
 *
 * @author chentudong
 * @date 2020/8/11 23:39
 * @since 1.0
 */
public class Then {

    private static final Logger LOGGER = LoggerFactory.getLogger(Then.class);

    private final Boolean value;

    private static Class<? extends RuntimeException> defaultException = RuntimeException.class;

    public Then(Boolean value) {
        this.value = value;
    }

    /**
     * 设置全局默认异常
     *
     * @param defaultException defaultException
     */
    public static void setDefaultException(@NonNull Class<? extends RuntimeException> defaultException) {
        //检查构造器
        try {
            defaultException.getConstructor(String.class);
        } catch (Throwable e) {
            AssertUtils.msgDevelopment(" %s 中必须要有RuntimeException#Throwable(String)构造器,如果是内部类需要声明为 public static", defaultException);
        }
        Then.defaultException = defaultException;
    }


    /**
     * 使用默认的exception
     *
     * @param msg
     */
    public void withDefaultException(String msg) {
        this.withCustomException(defaultException, msg);
    }


    /**
     * 将Value返回 手动处理
     */
    public void with(Consumer<Boolean> function) {
        function.accept(this.value);
    }

    /**
     * 抛出自定义Exception
     */
    public void withCustomException(Class<? extends RuntimeException> clazz, String msg) {
        if (this.value) {
            throw createInstance(clazz, msg);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }


    /**
     * 抛出运行时异常
     *
     * @param msg msg
     */
    public void withRuntimeException(String msg) {
        if (this.value) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 抛出空指针异常
     *
     * @param msg msg
     */
    public void withNullPointException(String msg) {
        if (this.value) {
            throw new NullPointerException(msg);
        }
    }

    /**
     * 抛出文件未找到异常
     *
     * @param msg msg
     */
    public void withFileNotFoundException(String msg) throws FileNotFoundException {
        if (this.value) {
            throw new FileNotFoundException(msg);
        }
    }

    /**
     * 抛出class未找到异常
     *
     * @param msg msg
     */
    public void withClassNotFoundException(String msg) throws ClassNotFoundException {
        if (this.value) {
            throw new ClassNotFoundException(msg);
        }
    }

    /**
     * 抛出下标越界异常
     *
     * @param msg msg
     */
    public void withIndexOutOfBoundsException(String msg) {
        if (this.value) {
            throw new IndexOutOfBoundsException(msg);
        }
    }

    /**
     * 抛出算数异常
     *
     * @param msg msg
     */
    public void withArithmeticException(String msg) {
        if (this.value) {
            throw new ArithmeticException(msg);
        }
    }

    /**
     * 抛出异常
     *
     * @param msg msg
     */
    public void withException(String msg) throws Exception {
        if (this.value) {
            throw new Exception(msg);
        }
    }

    /**
     * 通常来说不建议这样使用 因为不管是true还是false都会创建这个异常对象
     *
     * @param exception exception
     */
    public void withRuntimeException(RuntimeException exception) {
        if (this.value) {
            throw exception;
        }
    }

    /**
     * 通常来说不建议这样使用 因为不管是true还是false都会创建这个异常对象
     *
     * @param exception exception
     */
    public void withException(Exception exception) throws Exception {
        if (this.value) {
            throw exception;
        }
    }


    /**
     * 必须实现此构造器
     *
     * @param clazz   clazz
     * @param message message
     * @param <T>     <T>
     * @return T
     */
    public static <T extends RuntimeException> T createInstance(Class<T> clazz, String message) {
        try {
            Constructor<? extends RuntimeException> constructor = clazz.getConstructor(String.class);
            throw constructor.newInstance(message);
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            LOGGER.error("异常Class'{}'中必须要有RuntimeException#Throwable(String)构造器,如果是内部类需要声明为public static", clazz);
            throw new RuntimeException(message);
        }
    }
}
