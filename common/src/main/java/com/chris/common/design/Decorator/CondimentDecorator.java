package com.chris.common.design.Decorator;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午9:37
 */
//让我们也来实现Condiment(调料) 抽象类,也就是装饰者类吧
public abstract class CondimentDecorator extends Beverage{
    public abstract String getDescription();
}
