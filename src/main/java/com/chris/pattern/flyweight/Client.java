package com.chris.pattern.flyweight;

/**
 * User: chris
 * Date: 12-11-1
 * Time: 下午4:27
 */
public class Client
{
    private FlyWeight f1 = null;
    private FlyWeight f2 = null;
    private FlyWeight f3 = null;
    private FlyWeight f4 = null;
    FlyWeightFactory factory = new FlyWeightFactory();

    public Client()
    {
        f1 = factory.getFlyweight("baidu");
        f2 = factory.getFlyweight("google");
        f3 = factory.getFlyweight("baidu");
        f4 = factory.getFlyweight("google");
    }

    public void show()
    {
        f1.operation();
        f2.operation();
        f3.operation();
        f4.operation();
        System.out.println(factory.getFlyWeightsSize());
    }

    public static void main(String[] args)
    {
        Client client = new Client();
        client.show();
    }
}

