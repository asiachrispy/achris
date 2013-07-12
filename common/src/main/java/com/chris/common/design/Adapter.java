package com.chris.common.design;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 11-12-1
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */
class Adapter extends DeveloperEngineer
{
    private ITestEngineer testEngineer;

    public void setTestEngineer(ITestEngineer testEngineer)
    {
        this.testEngineer = testEngineer;
    }

    public void doTest()
    {
        testEngineer.doTest();
    }
}
