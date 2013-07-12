package com.chris.common.design;

//一个项目需要开发人员也需要测试人员，开发人员之作开发，测试人员只做测试，那么如何让这2个人能完成这个项目
//Adapter就是这样一个角色，协调这2个人来完成项目。

public class TestAdapter
{
    public static void main(String[]args)
    {
        Adapter adapter = new Adapter();        
        adapter.setTestEngineer(new TestEngineer());
        adapter.doDevelop();
        adapter.doTest();
    }
}

