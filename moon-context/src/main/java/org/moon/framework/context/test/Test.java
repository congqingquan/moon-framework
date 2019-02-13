package org.moon.framework.context.test;

import org.moon.framework.context.MoonApplicationContext;

/**
 * Created by 明月 on 2019-02-01 / 17:24
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws Exception {

        // 预记载模式
        MoonApplicationContext context = new MoonApplicationContext(Run.class);
        Object sf = context.getBean("sf");
        System.out.println(sf);

        Object foot = context.getBean("foot");
        System.out.println(foot);

        // 默认为单例Bean
        Object person = context.getBean("person");
        Object person2 = context.getBean("person");
        System.out.println(person == person2);

        // 注入的单例Bean是否为单例
        Object dylan = context.getBean("dylan");
        System.out.println(((Person) person).getFoot() == ((Dylan) dylan).getFoot());
        System.out.println(((Person) person).getHand() == ((Dylan) dylan).getHand());

        System.out.println("==============");
        System.out.println(((Person) person).getFoot().getToe() == ((Dylan) dylan).getFoot().getToe());
        System.out.println("==============");


        // 别名测试
        Object beanAliases1 = context.getBean("beanAliases1");
        Object beanAliases2 = context.getBean("beanAliases2");
        Object beanAliases3 = context.getBean("beanAliases3");
        System.out.println(beanAliases1 == person);
        System.out.println(beanAliases2 == person);
        System.out.println(beanAliases3 == person);

        // 通过Type获取Bean
        Person p1 = context.getBean(Person.class);
        Person p2 = context.getBean(Person.class);
        System.out.println(p1 == p2);

        Object temp = context.getBean(Object.class);
        System.out.println(temp);
    }
}