package org.moon.framework.context.test;

import org.moon.framework.context.MoonApplicationContext;

/**
 * Created by 明月 on 2019-02-01 / 17:24
 *
 * @email: 1814031271@qq.com
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        // 预记载模式
        MoonApplicationContext context = new MoonApplicationContext(Run.class);

        // 默认为单例Bean
        Object person = context.getBean("person");
        Object person2 = context.getBean("person");

        System.out.println(person == person2);

        // 别名测试
        Object beanAliases1 = context.getBean("beanAliases1");
        Object beanAliases2 = context.getBean("beanAliases2");
        Object beanAliases3 = context.getBean("beanAliases3");
        System.out.println(beanAliases1 == person);
        System.out.println(beanAliases2 == person);
        System.out.println(beanAliases3 == person);
    }
}