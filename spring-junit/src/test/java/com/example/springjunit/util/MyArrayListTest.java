package com.example.springjunit.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MyArrayListTest {
    @Autowired
    MyArrayList myArrayList;

    @BeforeEach
    void setUp() {
        // Очистим список перед каждым тестом
        myArrayList.clear();
        // Добавляем несколько элементов перед тестированием
        myArrayList.add(0, 10);
        myArrayList.add(1, 20);
        myArrayList.add(2, 30);
        myArrayList.add(3, 40);
    }

    @Test
    void addValidIndex() {
        myArrayList.add(4, 100);
        myArrayList.print();
        assertEquals(100, myArrayList.getElementByIndex(4));
    }

    @Test
    void addFirst() {
        myArrayList.addFirst(1);
        assertEquals(1, myArrayList.getElementByIndex(0));
    }

    @Test
    void addLast() {
        myArrayList.addLast(50);
        assertEquals(50, myArrayList.getElementByIndex(4));
    }

    @Test
    void addInvalidIndexNegative() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.add(-1, 100)
        );
        assertEquals("OutOfBoundsException", ex.getMessage());
    }

    @Test
    void addInvalidIndexLargeThanSize() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.add(30, 100)
        );
        assertEquals("OutOfBoundsException", ex.getMessage());
    }

    @Disabled
    @Test
    void growArray() {
        for (int i = 4; i < 10; i++) { // Добавляем элементы начиная с индекса 4
            myArrayList.add(i, i + 1);
        }

        myArrayList.add(10, 11);
        assertEquals(11, myArrayList.size());
    }

    @Test
    void removeValidIndex() {
        int removableElement = myArrayList.getElementByIndex(0);
        myArrayList.remove(0);
        assertEquals(10, removableElement);
    }

    @Test
    void removeFirst() {
        myArrayList.removeFirst();
        assertEquals(3, myArrayList.size());
        assertEquals(20, myArrayList.getElementByIndex(0)); //проверяем 1 элемент массива
        assertEquals(40, myArrayList.getElementByIndex(2));//проверяем крайний элемент массива
    }

    @Test
    void removeLast() {
        myArrayList.removeLast();
        assertEquals(3, myArrayList.size());
        assertEquals(10, myArrayList.getElementByIndex(0)); //проверяем 1 элемент массива
        assertEquals(30, myArrayList.getElementByIndex(2));//проверяем крайний элемент массива
    }

    @Test
    void removeNegativeIndex() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.remove(-1)
        );
        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }

    @Test
    void removeIndexLargeThanSize() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.remove(30)
        );
        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }

    @Test
    void getElementValidIndex() {
        int element = myArrayList.getElementByIndex(0);

        assertEquals(10, element);
    }

    @Test
    void getElementNegativeIndex() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.getElementByIndex(-1));

        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }

    @Test
    void getElementLargeThanSize() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.getElementByIndex(5));

        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }
}