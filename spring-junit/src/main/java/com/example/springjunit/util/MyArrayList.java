package com.example.springjunit.util;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.NoSuchElementException;

@Component
public class MyArrayList implements Iterable<Integer> {
    private Integer[] arr;
    private int capacity = 10;
    private int size = 0;

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MyArrayList() {
        this.arr = new Integer[this.capacity];
    }

    public void addLast(int element) {
        if (this.size == this.capacity) {
            this.grow();
        }

        this.arr[this.size] = element;
        ++this.size;
    }

    public void addFirst(int element) {
        this.add(0, element);
    }

    public void add(int index, int element) {
        if (index <= this.size && index >= 0) {
            if (this.size == this.capacity) {
                this.grow();
            }

            for (int i = this.size; i > index; --i) {
                this.arr[i] = this.arr[i - 1];
            }

            this.arr[index] = element;
            ++this.size;
        } else {
            throw new ArrayIndexOutOfBoundsException("OutOfBoundsException");
        }
    }

    public boolean check(int element) {
        for (int i = 0; i < this.size; ++i) {
            if (this.arr[i] == element) {
                return true;
            }
        }

        return false;
    }

    public int indexOf(int element) {
        for (int i = 0; i < this.size; ++i) {
            if (this.arr[i] == element) {
                return i;
            }
        }

        return -1;
    }

    public void remove(int index) {
        if (index < this.size && index >= 0) { //
            for (int i = index; i < this.size - 1; ++i) {
                this.arr[i] = this.arr[i + 1];
            }

            --this.size;
        } else {
            throw new ArrayIndexOutOfBoundsException("Index can not be more than size or negative");
        }
    }

    public void removeLast() {
        this.remove(this.size - 1);
    }

    public void removeFirst() {
        this.remove(0);
    }

    public int getElementByIndex(int index) {
        if (index < this.size && index >= 0) {
            return this.arr[index];
        } else {
            throw new ArrayIndexOutOfBoundsException("Index can not be more than size or negative");
        }
    }

    public void setArr(int value, int index) {
        if (index < this.size && index >= 0) {
            this.arr[index] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException("Index can not be more than size or negative");
        }
    }

    public void print() {
        for (int i = 0; i < this.size; ++i) {
            Integer var10001 = this.arr[i];
            System.out.print("" + var10001 + " ");
        }

    }

    public int size() {
        return size;
    }

    public void clear() {
        this.arr = new Integer[this.capacity];
        this.size = 0;
    }

    private void grow() {
        this.capacity *= 2;
        Integer[] newArr = new Integer[this.capacity];

        for (int i = 0; i < this.arr.length; ++i) {
            newArr[i] = this.arr[i];
            this.arr = newArr;
        }

    }

    public Iterator<Integer> iterator() {
        return new MyIterable();
    }

    private class MyIterable implements Iterator<Integer> {
        int cursor = 0;
        int lastReturned = -1;

        private MyIterable() {
        }

        public boolean hasNext() {
            return this.cursor != MyArrayList.this.size;
        }

        public Integer next() {
            if (this.cursor >= MyArrayList.this.size) {
                throw new NoSuchElementException();
            } else {
                int element = MyArrayList.this.arr[this.cursor];
                this.lastReturned = this.cursor++;
                return element;
            }
        }

        public void remove() {
            if (this.lastReturned < 0) {
                throw new IllegalStateException();
            } else {
                MyArrayList.this.remove(this.lastReturned);
                this.cursor = this.lastReturned;
                this.lastReturned = -1;
            }
        }
    }
}