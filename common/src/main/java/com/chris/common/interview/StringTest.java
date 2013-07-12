package com.chris.common.interview;

/**
 * User: zhong.huang
 * Date: 12-8-22
 * Time: 上午11:48
 */
public class StringTest {

    public static void main(String[] args) {
        String str = new String("abc"); //语句(1)
        String str1 = "abc";//语句(2)
        String str2 = new String("abc");//语句(3)

        System.out.println(str == str1);//语句(4)   false
        System.out.println(str == str2);//语句(5)    false
        System.out.println(str1 == str2);//语句(6)   false

        System.out.println(str == str.intern());//语句(7)  false
        System.out.println(str1 == str1.intern());//语句(8)      true
        System.out.println(str.intern() == str2.intern());//语句(9)   true

        String hello = "hello";//语句(10)
        String hel = "hel";//语句(11)
        String lo = "lo";//语句(12)

        System.out.println(hello == "hel" + "lo");//语句(13)    true
        System.out.println(hello == "hel" + lo);//语句(14)       false
    }
}

/*
问题1：当执行完语句(1)时，在内存里面生成几个对象?它们是什么?在什么地方?

解答：当执行完语句(1)时，内存里面生成2个对象，它们的内容分别都是abc，
    注意：str不是对象，它是对象的地址，它叫做引用(reference)，str指向new...生成的对象。
    换句话说，在java里面，当我们定义一个类的变量(如：String str;)，它永远都是引用，不是对象。
    那么什么是对象呢？当我们用关键字new时，它生成出来的东西叫做对象。为什么是两个对象呢？
    首先它生成一个对象是abc,这个abc对象在什么地方呢？它在一个叫String Pool的字符串池里面，
    只有String有这样一个String池。String池是一个什么概念呢？我们知道，String类是一个不可变的类，一但它的内容确定，
    它就不能去更改了。当你去生成一个字符串对象的时候，它的执行流程是这样的：它首先在你的String Pool里面去找，
    看有没有一个内容为abc的对象存在，因为tring str = new String("abc")它是main方法的第一个语句，
    那么在刚开始执行的时候，String Pool里面是没有对象的。它发现String Pool里面没有abc这个对象，
    那么它首先把new String("abc")的括号里面的abc对象放到String Pool里面，接下来它执行new ...这行语句，
    执行String的构造方法。我们知道new它生成一个对象，这个对象在什么地方呢？在java的堆里面。
    我们知道java的内存分为2部分，一个叫栈(Stack),一个叫堆(Heap)。
    那么new String("abc")时，它在堆(Heap)里面,生成一个内容为abc的这样一个对象。
    这样就造成了在String Pool里面一个叫abc的对象，堆里面也有一个叫abc的对象。
    我们这里用的是public String(String original)这个构造方法。jdk api是这样叙述的：
Initializes a newly created String object so that is represents the same sequence of characters as the argument;in other words,the newly created string is a copy of the argument string.Unless an explicit copy of original is needed,use of this constructor is unnecessary since Strings are immutable.
(翻译：初始化一个新创建的String对象，表示一个与该参数相同的字符序列；换句话说，新创建的字符串是该参数字符串的一个副本。
由于 String 是不可变的，不必使用该构造方法，除非需要original的显式副本。)

问题2：当执行完语句(2)时，在内存里面一共有几个对象?它们是什么?在什么地方?

解答：当执行完语句(2)时，内存里面一个新的对象都没有生成。为什么这么说？当我们定义语句(2)的时候，如果我们用字符串的常量值(字面值)给str1赋值的话，那么首先java还是从String Pool里面去查找没有有内容为abc的这样一个对象存在，我们发现当我们执行完语句(1)的时候，StringPool里面已经存在了内容为abc的对象，那么就不会再在tring Pool里面去生成内容为abc的字符串对象了。而是会使用已经存在String Pool里面的内容为abc的字符串对象，并且会将str2这个引用指向String Pool里面的内容为abc的字符串对象，str2存放的是String Pool里面的内容为abc的字符串对像的地址。也就是说当你使用String str2 = "abc"，即使用字符串常量("abc")给定义的引用(str2)赋值的话，那么它首先是在String Pool里面去找有没有内容为abc的字符串对象存在，如果有的话，就不用创建新的对象，直接引用String Pool里面已经存在的对象；如果没有的话，就在 String Pool里面去创建一个新的对象，接着将引用指向这个新创建的对象。所以，当执行完语句(2)时内存里面一共有2个对象，它们的内容分别都是abc， 在String Pool里面一个内容abc的对象，在堆里面有一个内容为abc的对象。

问题3：当执行完语句(3)时，在内存里面一共有几个对象?它们是什么?在什么地方?

解答：执行完语句(3)时，执行过程是这样的：它首先在String Pool里面去查找有没有内容为abc的字符串对象存在，发现有这个对象存在，它就不去创建 一个新的对象。接着执行new...，只要在java里面有关键字new存在，就表示它生成一个新的对象，new多少次，就生成多少个对象，而且新生成的对象都是在堆里面，所以它会在堆里面生成一个内容为abc的对象，并且将它的地址赋给了引用str2,str2就指向刚在堆里面生成的内容为abc的对象。所以，当执行完语句(3)时，内存里面一共有3个对象，其中包含了一个在String Pool里面内容为abc的字符串对象，另外在堆里面包含了两个内容为abc的字符串对象。

问题4：当执行完语句(4)(5)(6)后，它们的结果分别是什么?

解答：在java里面，对象用"=="永远比较的是两个对象的内存地址，换句话说，是比较"=="左右两边的两个引用是否指向同一个对象。对于java里面的8种原生数据类型来说，"=="比较的是它们的字面值是不是一样的；对应用类型来说，比较的是它们的内存地址是不是一样的。在语句(1)(2)(3)中，由于str、str1、str2指向不同的对象，它们的内存地址就不一样，因此可以说当执行完语句(4)(5)(6),它们返回的结果都是false。

问题5：当执行完语句(7)(8)(9)后，它们的结果分别是什么?

解答：jdk api里对方法public String intern()是这样叙述的：
Return a canonical representation for the string object.(翻译：返回字符串对象的标准化表示形式。)A pool of strings,initially empty,is maintained privately by the class String.(翻译：一个初始时为空的字符串池，它由类 String 私有地维护。) When the intern method is invoked,if the pool already contains a string equeal to this String objectas determined by the equals(Object) method,then the string from the pool is returned.Otherwise,this String object is added to the pool and a reference to the String object is returned.(翻译：当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串（该对象由 equals(Object) 方法确定），则返回池中的字符串。否则，将此 String 对象添加到池中，并且返回此 String 对象的引用。)If follows that for any two strings s and t,s.intern() == t.intern() is true if and only if s.equals(t) is true.(翻译：它遵循对于任何两个字符串 s 和 t，当且仅当 s.equals(t) 为 true 时，s.intern() == t.intern() 才为 true。)All literal strings and string-valued consrant expressions are interned.String literals and defined in §3.10.5 of the Java Language Specification.(翻译：所有字面值字符串和字符串赋值常量表达式都是内部的。字符串字面值在《Java Language Specification》的 §3.10.5 中已定义。) Returns:a string that has the same contents as this string,but is guaranteed ro be from a pool of unique strings.  (翻译：返回一个字符串，内容与此字符串相同，但它保证来自字符串池中。) 当执行语句(7)时，首先，str这个对象指向的是堆中第一次new...生成的对象，根据jdk的api叙述，当调用 intern 方法时，如果String Pool已经包含一个等于此 String 对象的字符串(该对象由equals(Object)方法确定），则返回String Pool中的字符串对象的内存地址。因为String Pool中有内容为abc的对象，所以str.intern()返回的是String Pool中的内容为abc的字符串对象的内存地址，即是str1。而str==str1为false，所以，str == str.intern() 为false，即是语句(7)结果为false。而对于str1.intern(),它还是会首先检查String Pool中是否有内容为abc的对象，发现有，则将String Pool中内容为abc的对象的地址赋给str1.intern()方法的返回值，即str1.intern()的结果为str1。所以，str1 == str1.intern()的结果为true,，即是语句(8)结果为true。对于str.intern(),它首先检查String Pool中是否有内容为abc的对象，发现有，则将String Pool中内容为abc的对象的赋给str.intern()方法的返回值，即str.intern()的结果为str1。对于st2r.intern(),首先检查String Pool中是否有内容为abc的对象，发现有，则将String Pool中内容为abc的对象的地址赋给str2.intern()方法的返回值，即str2.intern()的结果为str1。所以，str.intern() == str2.intern()的结果为true,，即是语句(9)结果为true。因此，当执行完语句(7)(8)(9)后，它们的结果分别是false、true、true。

问题6：当执行完语句(13)(14)后，它们的结果分别是什么?

解答：执行完语句(13)结果为true，执行完语句(14)结果为false。分析:对于hello == "hel" + "lo"，hello指向的是String Pool里面的内容为hello的字符串对象，对于"hel" + "lo"，当"+"两边都是字面值(字符串常量)的时候，在执行完"+"之后，它接着去判断String Pool里面有没有内容为hello的字符串对象存在，有的话就直接返回String Pool里面的内容为hello的字符串对象的内存地址，所以，hello == "hel" + "lo"结果为true;对于hello == "hel" + lo，lo不是字面值，当"+"两边有一个不是字面值(字符串常量)的时候，那么"+"操作后又会在堆里面生成一个新的对象，也就是说hello的引用是指向String Pool里面的内容为hello的字符串对象，"hel" + lo的结果是返回在堆里面生成一个新的对象，一个在String Pool里面，一个在堆里面，当然为false了。
*/