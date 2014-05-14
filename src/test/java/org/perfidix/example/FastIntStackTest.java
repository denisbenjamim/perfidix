/**
 * Copyright (c) 2012, University of Konstanz, Distributed Systems Group All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. * Neither the name of the University of Konstanz nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 *
 */
package org.perfidix.example;


import org.junit.BeforeClass;
import org.junit.Test;
import org.perfidix.annotation.BeforeBenchClass;
import org.perfidix.annotation.BenchClass;

import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.assertEquals;


@BenchClass(runs = 100)
public class FastIntStackTest {

    private static final int[] data = new int[15];

    @BeforeBenchClass
    @BeforeClass
    public static void beforeClass() {
        for (int i = 0; i < data.length; i++) {
            data[i] = new Random().nextInt();
        }
    }

    @Test
    public void myStackTest() {
        final FastIntStack myStack = new FastIntStack();
        for (int i = 0; i < data.length; i++) {
            myStack.push(data[i]);
        }
        for (int i = data.length - 1; i > 0; i--) {
            assertEquals(data[i], myStack.pop());
        }
    }

    @Test
    public void normalStackTest() {
        final Stack<Integer> normalStack = new Stack<Integer>();
        for (int i = 0; i < data.length; i++) {
            normalStack.push(data[i]);
        }
        for (int i = data.length - 1; i > 0; i--) {
            assertEquals(data[i], normalStack.pop().intValue());
        }
    }

    final class FastIntStack {

        private int[] stack;

        private int size;

        FastIntStack() {
            stack = new int[32];
            size = 0;
        }

        final void push(final int element) {
            if (stack.length == size) {
                int[] biggerStack = new int[stack.length << 1];
                System.arraycopy(stack, 0, biggerStack, 0, stack.length);
                stack = biggerStack;
            }
            stack[size++] = element;
        }

        final int peek() {
            return stack[size - 1];
        }

        final int get(final int position) {
            return stack[position];
        }

        final int pop() {
            return stack[--size];
        }

        final void clear() {
            size = 0;
        }

        final int size() {
            return size;
        }

    }
}
