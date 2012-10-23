package com.chris.pattern.factory.abstracts;

/**
 * User: chris
 * Date: 12-10-18
 * Time: 下午5:20
 */
public interface HumanFactory {
    public Human createYellowHuman();
    public Human createWhiteHuman();
    public Human createBlackHuman();
}
