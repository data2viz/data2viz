/*
 * Copyright (c) 2018-2019. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.geo.projection.common


/**
 * The unitary mathematical function that transform geographic coordinates (lambda, phi) into
 * cartesian coordinates (x,y).
 */
public interface Projectable {
    public fun project(lambda: Double, phi: Double): DoubleArray
}


/**
 * The unitary mathematical function that transform cartesian coordinates (x,y) into
 * geographic coordinates (lambda,phi). Not all projections are invertable.
 */
public interface Invertable {
    public fun invert(x: Double, y: Double): DoubleArray
}


public interface Projector: Projectable, Invertable