package com.example.springjunit.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MyArrayListTest {
    @Autowired
    MyArrayList myArrayList;

    @BeforeEach
    void setUp() {
        // Очистим список перед каждым тестом через метод
        //  myArrayList.clear();
        //Очистим список перед каждым тестом через рефлексию
        // Установка значения поля myArray
        Field arrField = ReflectionUtils.findField(MyArrayList.class, "arr");
        if (arrField != null) {
            ReflectionUtils.makeAccessible(arrField);
            ReflectionUtils.setField(arrField, myArrayList, new Integer[myArrayList.getCapacity()]);
        }

        // Установка значения поля size
        Field sizeField = ReflectionUtils.findField(MyArrayList.class, "size");
        if (sizeField != null) {
            ReflectionUtils.makeAccessible(sizeField);
            ReflectionUtils.setField(sizeField, myArrayList, 0);
        }

    }

    @Test
    void add_elementAdded_ifValidIndex() {
        myArrayList.print();
        addElementsToMyArray(4);
        myArrayList.add(4, 100);
        myArrayList.print();
        assertEquals(100, myArrayList.getElementByIndex(4));
    }

    @Test
    void add_elementAdded_ifSizeZero() {
        int size = 0;

        myArrayList.setSize(size);
        myArrayList.add(0, 10);
        assertEquals(10, myArrayList.getElementByIndex(0));
    }

    @Test
    void add_elementAdded_ifSizeThree() {
        int size = 3;

        myArrayList.setSize(size);
        myArrayList.add(3, 50);
        assertEquals(50, myArrayList.getElementByIndex(3));
    }

    @Test
    void add_elementAdded_checkForGrow() {
        int size = 10;

        myArrayList.setSize(size);
        myArrayList.add(10, 100);
        assertEquals(100, myArrayList.getElementByIndex(10));
        assertEquals(20, myArrayList.getCapacity());
    }

    @Test
    void add_throwOutOfBoundsException_ifInvalidIndexNegative() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.add(-1, 100)
        );
        assertEquals("OutOfBoundsException", ex.getMessage());
    }

    @Test
    void add_throwOutOfBoundsException_ifInvalidIndexLargeThanSize() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.add(30, 100)
        );
        assertEquals("OutOfBoundsException", ex.getMessage());
    }

    @Test
    void addFirst_elementAdded_ifValidIndex() {
        myArrayList.addFirst(1);
        assertEquals(1, myArrayList.getElementByIndex(0));
    }

    @Test
    void addFirst_elementAdded_ifSizeZero() {
        int size = 0;

        myArrayList.setSize(size);
        myArrayList.addFirst(10);
        assertEquals(10, myArrayList.getElementByIndex(0));
    }

    @Test
    void addFirst_elementAdded_ifSizeThree() {
        int size = 3;

        myArrayList.setSize(size);
        myArrayList.addFirst(50);
        assertEquals(50, myArrayList.getElementByIndex(0));
    }

    @Test
    void addFirst_elementAdded_checkForGrow() {
        int size = 10;

        myArrayList.setSize(size);
        myArrayList.addFirst(100);
        assertEquals(100, myArrayList.getElementByIndex(0));
        assertEquals(20, myArrayList.getCapacity());
    }

    @Test
    void addLast_elementAdded_ifValidIndex() {
        addElementsToMyArray(4);

        myArrayList.addLast(50);
        assertEquals(50, myArrayList.getElementByIndex(4));
    }

    @Test
    void addLast_elementAdded_ifSizeZero() {
        int size = 0;

        myArrayList.setSize(size);
        myArrayList.addLast(30);
        assertEquals(30, myArrayList.getElementByIndex(0));
    }

    @Test
    void addLast_elementAdded_ifSizeThree() {
        int size = 3;

        myArrayList.setSize(size);
        myArrayList.addLast(50);
        assertEquals(50, myArrayList.getElementByIndex(3));
    }

    @Test
    void addLast_elementAdded_checkForGrow() {
        int size = 10;

        myArrayList.setSize(size);
        myArrayList.addLast(90);
        assertEquals(90, myArrayList.getElementByIndex(10));
        assertEquals(20, myArrayList.getCapacity());
    }

    @Test
    void remove_ifValidIndex() {
        addElementsToMyArray(3);
        int removableElement = myArrayList.getElementByIndex(0);
        myArrayList.remove(0);
        assertEquals(0, removableElement);
    }

    @Test
    void remove_throwArrayIndexOutOfBoundsException_ifNegativeIndex() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.remove(-1)
        );
        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }

    @Test
    void remove_throwArrayIndexOutOfBoundsException_ifIndexLargeThanSize() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.remove(30)
        );
        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }

    @Test
    void removeFirst() {
        addElementsToMyArray(4);
        myArrayList.removeFirst();
        assertEquals(3, myArrayList.size());
        assertEquals(10, myArrayList.getElementByIndex(0)); //проверяем 1 элемент массива
        assertEquals(30, myArrayList.getElementByIndex(2));//проверяем крайний элемент массива
    }

    @Test
    void removeLast() {
        addElementsToMyArray(4);
        myArrayList.removeLast();
        assertEquals(3, myArrayList.size());
        assertEquals(0, myArrayList.getElementByIndex(0)); //проверяем 1 элемент массива
        assertEquals(20, myArrayList.getElementByIndex(2));//проверяем крайний элемент массива
    }

    @Test
    void getElementValidIndex() {
        addElementsToMyArray(4);
        int element = myArrayList.getElementByIndex(0);

        assertEquals(0, element);
    }

    @Test
    void getElement_throwArrayIndexOutOfBoundsException_negativeIndex() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.getElementByIndex(-1));

        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }

    @Test
    void getElement_arrayIndexOutOfBoundsException_indexLargeThanSize() {
        ArrayIndexOutOfBoundsException ex = assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> myArrayList.getElementByIndex(5));

        assertEquals("Index can not be more than size or negative", ex.getMessage());
    }

    private void addElementsToMyArray(int num) {
        for (int i = 0; i < num; i++) {
            myArrayList.add(i, i * 10);

        }
    }
}