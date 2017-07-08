//package com.app.client.builder.helper;
//
//import Book;
//import com.google.gwt.junit.client.GWTTestCase;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * for all methods we need to comment "Window.alert()" in origin class
// */
//public class HelperTest extends GWTTestCase {
//    Helper helper = new Helper();
//
///*
//    @Test
//    public void validateString() throws Exception {
//        boolean test1 = helper.validateString("1");
//        Assert.assertTrue(test1);
//
//        boolean test2 = helper.validateString("");
//        Assert.assertFalse(test2);
//
//        boolean test3 = helper.validateString("30 sybmols only can");// 30<
//        Assert.assertTrue(test3);
//
//        boolean test4 = helper.validateString("Only 30 symbols we can have there");//30>
//        Assert.assertFalse(test4);
//    }
//*/
//
//    @Test
//    public void checkBook() throws Exception {
//        List<Book> list = new ArrayList<>();
//        list.add(new Book("1", "1", 1, 1));
//        list.add(new Book("2", "1", 1, 1));
//        list.add(new Book("3", "3", 3, 3));
//        list.add(new Book("4", "4", 3, 3));
//        list.add(new Book("5", "4", 5, 5));
//
//        Book test1 =helper.checkBook(list, "1", "1", 1, 1);
//        Assert.assertTrue(test1==null);
//
//        Book test2=helper.checkBook(list,"2","1",1,1);
//        Assert.assertTrue(test2==null);
//
//        Book test3 = helper.checkBook(list,"10","1",1,1);
//        Assert.assertFalse(test3==null);
//
//    }
//
//    @Override
//    public String getModuleName() {
//        return null;
//    }
//}