package com.chris.common.net;

class StackVars {
    private int instVar;
    private static int staticVar;

    //存取堆栈变量
    void stackAccess(int val) {
        int j = 0;
        for (int i = 0; i < val; i++)
            j += 1;
    }

    //存取类的实例变量
    void instanceAccess(int val) {
        for (int i = 0; i < val; i++)
            instVar += 1;
    }

    //存取类的 static 变量
    void staticAccess(int val) {
        for (int i = 0; i < val; i++)
            staticVar += 1;
    }

    class StackVar {
        //与前面相同...
        void instanceAccess(int val) {
            int j = instVar;
            for (int i = 0; i < val; i++)
                j += 1;
            instVar = j;
        }

        void staticAccess(int val) {
            int j = staticVar;
            for (int i = 0; i < val; i++)
                j += 1;
            staticVar = j;
        }
    }
}