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

package org.perfidix.socketadapter;


import org.junit.Test;
import org.perfidix.Benchmark;
import org.perfidix.Perfidix;
import org.perfidix.element.BenchmarkMethod;
import org.perfidix.exceptions.PerfidixMethodCheckException;
import org.perfidix.exceptions.SocketViewException;

import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * This class tests the java class {@link org.perfidix.socketadapter.SocketAdapter}.
 *
 * @author Lewandowski Lukas, DiSy, University of Konstanz
 */
public class SocketAdapterTest {

    /**
     * Test method for {@link org.perfidix.socketadapter.SocketAdapter} .
     *
     * @throws InterruptedException         Exception occurred.
     * @throws PerfidixMethodCheckException
     */
    @Test
    public void testMain() throws InterruptedException, SocketViewException, ClassNotFoundException, InstantiationException, IllegalAccessException, PerfidixMethodCheckException {
        final IUpdater iUpdaterMock = mock(IUpdater.class);
        final String[] classes = {"org.perfidix.example.Config", "org.perfidix.example.stack.StackBenchmark", "org.perfidix.socketadapter.BenchWithException"};
        final Benchmark tmpBench = Perfidix.setUpBenchmark(classes, new Benchmark(Perfidix.getConfiguration(classes)));
        final Map<BenchmarkMethod, Integer> vals = tmpBench.getNumberOfMethodsAndRuns();
        when(iUpdaterMock.initProgressView(vals)).thenReturn(true);

        SocketAdapter myInstance = new SocketAdapter(iUpdaterMock, "org.perfidix.example.Config", "org.perfidix.example.stack.StackBenchmark", "org.perfidix.socketadapter.BenchWithException");

        assertTrue(myInstance.registerClasses(classes));
        assertTrue(myInstance.runBenchmark());
    }

}
