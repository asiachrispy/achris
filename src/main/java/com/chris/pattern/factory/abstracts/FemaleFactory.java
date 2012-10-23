package com.chris.pattern.factory.abstracts;

/**
 * User: chris
 * Date: 12-10-18
 * Time: 下午5:21
 */
public class FemaleFactory implements HumanFactory {

    @Override
    public Human createYellowHuman() {
        return new FemaleYellowHuman();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Human createWhiteHuman() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Human createBlackHuman() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
