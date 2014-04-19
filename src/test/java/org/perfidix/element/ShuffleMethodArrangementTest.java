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
package org.perfidix.element;


import org.junit.Before;
import org.junit.Test;
import org.perfidix.annotation.Bench;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;


/**
 * Testcase for shuffle method arrangement.
 *
 * @author Sebastian Graf, University of Konstanz
 */
public class ShuffleMethodArrangementTest {

    private transient List<BenchmarkElement> elemSet;

    /**
     * Before method to setUp Benchmarkables.
     */
    @Before
    public void setUp() {
        elemSet = new ArrayList<BenchmarkElement>();
        final Class<?> testClazz = TestBenchClass.class;
        for (final Method meth : testClazz.getDeclaredMethods()) {
            if (BenchmarkMethod.isBenchmarkable(meth)) {
                elemSet.add(new BenchmarkElement(new BenchmarkMethod(meth), new Object[][]{}));
            }
        }
    }

    /**
     * Test method for {@link org.perfidix.element.ShuffleMethodArrangement} .
     */
    @Test
    public void test() {
        try {

            final AbstractMethodArrangement arrangement = AbstractMethodArrangement.getMethodArrangement(elemSet, KindOfArrangement.ShuffleArrangement);
            final String[] expectedNames = {"bench1", "bench2", "bench4"};
            final Iterator<BenchmarkElement> iterBench = arrangement.iterator();
            final BenchmarkElement elem1 = iterBench.next();
            final BenchmarkElement elem2 = iterBench.next();
            final BenchmarkElement elem3 = iterBench.next();
            if ((expectedNames[0].equals(elem1.getMeth().getMethodToBench().getName())) && (expectedNames[1].equals(elem2.getMeth().getMethodToBench().getName())) && (expectedNames[2].equals(elem3.getMeth().getMethodToBench().getName()))) {
                fail("Something has to be arranged in a different way!");

            }
            assertFalse("No more elements should be left", iterBench.hasNext());

        } catch (final Exception e) {
            fail(e.toString());
        }
    }

    class TestBenchClass {

        @Bench
        public void bench1() {
            // Just a bench-sekeleton
        }

        @Bench
        public void bench2() {
            // Just a bench-sekeleton
        }

        public void bench3() {
            // Just a bench-sekeleton
        }

        @Bench
        public void bench4() {
            // Just a bench-sekeleton
        }

    }

}
