package org.sarah;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("StudentSpring.xml");
        Student1 obj = (Student)context.getBean("Student");
        obj.id();

    }
}

