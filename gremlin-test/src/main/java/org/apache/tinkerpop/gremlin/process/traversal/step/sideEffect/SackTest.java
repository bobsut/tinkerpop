/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tinkerpop.gremlin.process.traversal.step.sideEffect;

import org.apache.tinkerpop.gremlin.LoadGraphWith;
import org.apache.tinkerpop.gremlin.process.AbstractGremlinProcessTest;
import org.apache.tinkerpop.gremlin.process.GremlinProcessRunner;
import org.apache.tinkerpop.gremlin.process.traversal.Operator;
import org.apache.tinkerpop.gremlin.process.traversal.SackFunctions;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.LoadGraphWith.GraphData.MODERN;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.outE;
import static org.junit.Assert.*;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
@RunWith(GremlinProcessRunner.class)
public abstract class SackTest extends AbstractGremlinProcessTest {

    public abstract Traversal<Vertex, String> get_g_withSackXhellowX_V_outE_sackXassignX_byXlabelX_inV_sack();

    public abstract Traversal<Vertex, Double> get_g_withSackX0X_V_outE_sackXsumX_byXweightX_inV_sack_sum();

    public abstract Traversal<Vertex, Float> get_g_withSackX0X_V_repeatXoutE_sackXsumX_byXweightX_inVX_timesX2X_sack();

    @Deprecated
    public abstract Traversal<Vertex, Double> get_g_withSackX0X_V_outE_sackXsum_weightX_inV_sack_sum();

    @Deprecated
    public abstract Traversal<Vertex, Float> get_g_withSackX0X_V_repeatXoutE_sackXsum_weightX_inVX_timesX2X_sack();

    public abstract Traversal<Vertex, Map> get_g_withSackXmap__map_cloneX_V_out_out_sackXmap_a_nameX_sack();

    public abstract Traversal<Vertex, Double> get_g_withSackX1_sumX_VX1X_localXoutXknowsX_barrierXnormSackXX_inXknowsX_barrier_sack(final Object v1Id);

    public abstract Traversal<Vertex, Integer> get_g_withBulkXfalseX_withSackX1_sumX_V_out_barrier_sack();

    @Test
    @LoadGraphWith(MODERN)
    public void g_withSackXhellowX_V_outE_sackXassignX_byXlabelX_inV_sack() {
        final Traversal<Vertex, String> traversal = get_g_withSackXhellowX_V_outE_sackXassignX_byXlabelX_inV_sack();
        checkResults(Arrays.asList("knows", "knows", "created", "created", "created", "created"), traversal);
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_withSackX0X_V_outE_sackXsumX_byXweightX_inV_sack_sum() {
        final Traversal<Vertex, Double> traversal = get_g_withSackX0X_V_outE_sackXsumX_byXweightX_inV_sack_sum();
        printTraversalForm(traversal);
        assertEquals(3.5d, traversal.next(), 0.00001d);
        assertFalse(traversal.hasNext());
    }

    @Test
    @LoadGraphWith(MODERN)
    @Deprecated
    public void g_withSackX0X_V_outE_sackXsum_weightX_inV_sack_sum() {
        final Traversal<Vertex, Double> traversal = get_g_withSackX0X_V_outE_sackXsum_weightX_inV_sack_sum();
        printTraversalForm(traversal);
        assertEquals(3.5d, traversal.next(), 0.00001d);
        assertFalse(traversal.hasNext());
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_withSackX0X_V_repeatXoutE_sackXsumX_byXweightX_inVX_timesX2X_sack() {
        final Traversal<Vertex, Float> traversal = get_g_withSackX0X_V_repeatXoutE_sackXsumX_byXweightX_inVX_timesX2X_sack();
        printTraversalForm(traversal);
        checkResults(Arrays.asList(2.0f, 1.4f), traversal);
    }

    @Test
    @LoadGraphWith(MODERN)
    @Deprecated
    public void g_withSackX0X_V_repeatXoutE_sackXsum_weightX_inVX_timesX2X_sack() {
        final Traversal<Vertex, Float> traversal = get_g_withSackX0X_V_repeatXoutE_sackXsum_weightX_inVX_timesX2X_sack();
        printTraversalForm(traversal);
        checkResults(Arrays.asList(2.0f, 1.4f), traversal);
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_withSackXmap__map_cloneX_V_out_out_sackXmap_a_nameX_sack() {
        final Traversal<Vertex, Map> traversal = get_g_withSackXmap__map_cloneX_V_out_out_sackXmap_a_nameX_sack();
        printTraversalForm(traversal);
        int counter = 0;
        while (traversal.hasNext()) {
            final Map map = traversal.next();
            assertEquals(1, map.size());
            assertTrue(map.containsKey("a"));
            assertTrue(map.get("a").equals("ripple") || map.get("a").equals("lop"));
            counter++;
        }
        assertEquals(2, counter);
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_withSackX1_sumX_VX1X_localXoutXknowsX_barrierXnormSackXX_inXknowsX_barrier_sack() {
        final Traversal<Vertex, Double> traversal = get_g_withSackX1_sumX_VX1X_localXoutXknowsX_barrierXnormSackXX_inXknowsX_barrier_sack(convertToVertexId("marko"));
        printTraversalForm(traversal);
        checkResults(Arrays.asList(1.0d, 1.0d), traversal);
    }

    @Test
    @LoadGraphWith(MODERN)
    public void g_withBulkXfalseX_withSackX1_sumX_V_out_barrier_sack() {
        final Traversal<Vertex, Integer> traversal = get_g_withBulkXfalseX_withSackX1_sumX_V_out_barrier_sack();
        printTraversalForm(traversal);
        checkResults(Arrays.asList(1, 1, 1, 3), traversal); // josh, vadas, ripple, lop
    }

    public static class Traversals extends SackTest {

        @Override
        public Traversal<Vertex, String> get_g_withSackXhellowX_V_outE_sackXassignX_byXlabelX_inV_sack() {
            return g.withSack("hello").V().outE().sack(Operator.assign).by(T.label).inV().sack();
        }

        @Override
        public Traversal<Vertex, Double> get_g_withSackX0X_V_outE_sackXsumX_byXweightX_inV_sack_sum() {
            return g.withSack(0.0f).V().outE().sack(Operator.sum).by("weight").inV().sack().sum();
        }

        @Override
        public Traversal<Vertex, Float> get_g_withSackX0X_V_repeatXoutE_sackXsumX_byXweightX_inVX_timesX2X_sack() {
            return g.withSack(0.0f).V().repeat(outE().sack(Operator.sum).by("weight").inV()).times(2).sack();
        }

        @Override
        public Traversal<Vertex, Double> get_g_withSackX0X_V_outE_sackXsum_weightX_inV_sack_sum() {
            return g.withSack(0.0f).V().outE().sack(Operator.sum, "weight").inV().sack().sum();
        }

        @Override
        public Traversal<Vertex, Float> get_g_withSackX0X_V_repeatXoutE_sackXsum_weightX_inVX_timesX2X_sack() {
            return g.withSack(0.0f).V().repeat(outE().sack(Operator.sum, "weight").inV()).times(2).sack();
        }

        @Override
        public Traversal<Vertex, Map> get_g_withSackXmap__map_cloneX_V_out_out_sackXmap_a_nameX_sack() {
            return g.<HashMap>withSack(HashMap::new, m -> (HashMap) m.clone()).V().out().out().<Map, Vertex>sack((map, vertex) -> {
                map.put("a", vertex.value("name"));
                return map;
            }).sack();
        }

        @Override
        public Traversal<Vertex, Double> get_g_withSackX1_sumX_VX1X_localXoutXknowsX_barrierXnormSackXX_inXknowsX_barrier_sack(final Object v1Id) {
            return g.withSack(1.0d, Operator.sum).V(v1Id).local(__.out("knows").barrier(SackFunctions.Barrier.normSack)).in("knows").barrier().sack();
        }

        @Override
        public Traversal<Vertex, Integer> get_g_withBulkXfalseX_withSackX1_sumX_V_out_barrier_sack() {
            return g.withBulk(false).withSack(1, Operator.sum).V().out().barrier().sack();
        }
    }
}