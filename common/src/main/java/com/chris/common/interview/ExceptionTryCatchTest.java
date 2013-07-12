package com.chris.common.interview;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 上午11:23
 */

import java.io.IOException;

public class ExceptionTryCatchTest {
    public void doSomething() throws IOException {
        System.out.println("do somthing");
    }

    public static void main(String[] args) {
        ExceptionTryCatchTest etct = new ExceptionTryCatchTest();
        try {
            etct.doSomething();
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }
}
/*
问题:上述程序能否编译通过？为什么？
解答:不能编译通过。因为编译的时候会报错:已捕捉到异常 java.io.IOException。  catch(IOException e)这句有错误。

分析：对于try..catch捕获异常的形式来说，对于异常的捕获，可以有多个catch。
对于try里面发生的异常，他会根据发生的异常和catch里面的进行匹配(怎么匹配，按照catch块从上往下匹配)，
当它匹配某一个catch块的时候，他就直接进入到这个catch块里面去了，后面在再有catch块的话，它不做任何处理，直接跳过去，全部忽略掉。
如果有finally的话进入到finally里面继续执行。换句话说，如果有匹配的catch，它就会忽略掉这个catch后面所有的catch。
对我们这个方法来说，抛出的是IOException，当执行etct.doSomething();时，可能会抛出IOException，一但抛出IOException，
它首先进入到catch (Exception e) {}里面，先和Exception匹配，由于OExceptionextends Exception,根据多态的原则，
IOException是匹配Exception的，所以程序就会进入到catch (Exception e) {}里面，进入到第一个catch后，后面的catch都不会执行了，
所以catch (IOException e) {}永远都执行不到，就给我们报出了前面的错误:已捕捉到异常 java.io.IOException。

总结:在写异常处理的时候，一定要把异常范围小的放在前面，范围大的放在后面，
Exception这个异常的根类一定要刚在最后一个catch里面，如果放在前面或者中间，任何异常都会和Exception匹配的，
就会报已捕获到...异常的错误。
 */
