package com.chris.pattern.factory.abstracts;

/**
 * User: chris
 * Date: 12-10-18
 * Time: 下午5:32
 */
public class NvwaMakeHuman {
    public static void main(String[] args) {
        HumanFactory maleHumanFactory = new MaleFactory();

        HumanFactory femaleHumanFactory = new FemaleFactory();

        Human maleYellowHuman = maleHumanFactory.createYellowHuman();

        Human femaleYellowHuman = femaleHumanFactory.createYellowHuman();

        System.out.println(maleYellowHuman.getColor());
        System.out.println(maleYellowHuman.getSex());
        maleYellowHuman.putOn("green coat.");
        maleYellowHuman.talk("hello chris!");

        System.out.println(femaleYellowHuman.getColor());
        System.out.println(femaleYellowHuman.getSex());
        femaleYellowHuman.putOn("red dress.");
        femaleYellowHuman.talk("hello manda!");
    }
}
