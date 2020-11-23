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

package io.data2viz.random

import kotlin.math.*
import kotlin.random.*

public typealias RandomGenerator = () -> Double

/**
 * Generate random numbers from various distributions using a Integer seed
 */
public fun RandomDistribution(seed: Int): RandomDistribution = RandomDistribution(Random(seed))


/**
 * Generate random numbers from various distributions using a Long seed
 */
public fun RandomDistribution(seed: Long): RandomDistribution = RandomDistribution(Random(seed))


/**
 * Generate random numbers from various distributions.
 */
public class RandomDistribution internal constructor(private val random:Random = Random){

    public companion object Default {

        private val randomDistribution= RandomDistribution()

        /**
         * Returns a function for generating random numbers with a uniform distribution.
         * The minimum allowed value of a returned number is min, and the maximum is max.
         * If min is not specified, it defaults to 0; if max is not specified, it defaults to 1.
         */
        public fun uniform(min: Double = .0, max: Double = 1.0): RandomGenerator       = randomDistribution.uniform(min, max)

        /**
         * returns a function for generating random numbers with a Bates distribution with n independent variables.
         */
        public fun bates(n: Double = 1.0): RandomGenerator                             = randomDistribution.bates(n)

        /**
         * returns a function for generating random numbers with a log-normal distribution.
         * The expected value of the random variable’s natural logarithm is mu, with the given
         * standard deviation sigma. If mu is not specified, it defaults to 0;
         * if sigma is not specified, it defaults to 1.
         */
        public fun logNormal(mu: Double = .0, sigma: Double = 1.0): RandomGenerator    = randomDistribution.logNormal(mu, sigma)


        /**
         * Returns a function for generating random numbers with an exponential distribution with
         * the rate lambda; equivalent to time between events in a Poisson process with a mean of 1 / lambda.
         * For example, exponential(1/40) generates random times between events where, on average, one event
         * occurs every 40 units of time.
         */
        public fun exponential(lambda: Double = 1.0): RandomGenerator                  = randomDistribution.exponential(lambda)

        /**
         * Returns a function for generating random numbers with an Irwin–Hall distribution with n independent variables.
         */
        public fun irwinHall(n: Double): RandomGenerator                               = randomDistribution.irwinHall(n)

        /**
         * Returns a function for generating random numbers with a normal (Gaussian) distribution.
         * The expected value of the generated numbers is mu, with the given standard deviation sigma.
         * If mu is not specified, it defaults to 0.0; if sigma is not specified, it defaults to 1.0
         */
        public fun normal(mu: Double = .0, sigma: Double = 1.0): RandomGenerator       = randomDistribution.normal(mu, sigma)
    }



    public fun uniform(min: Double = .0, max: Double = 1.0): RandomGenerator = { random() * (max - min) + min }
    public fun bates(n: Double = 1.0): RandomGenerator = { irwinHall(n)() / n }
    public fun logNormal(mu: Double = .0, sigma: Double = 1.0): RandomGenerator = { exp(normal(mu, sigma)()) }
    public fun exponential(lambda: Double = 1.0): RandomGenerator = { -ln(1 - random()) / lambda }
    public fun irwinHall(n: Double): RandomGenerator = {
        var sum = 0.0
        (0 until n.toInt()).forEach {
            sum += random()
        }
        sum
    }


    public fun normal(mu: Double = .0, sigma: Double = 1.0): RandomGenerator = {
        var x: Double? = null
        var r = 0.0
        var y = 0.0

        if (x != null) {
            y = x
        } else {
            while (r == 0.0 || r > 1) {
                x = random() * 2 - 1
                y = random() * 2 - 1
                r = x * x + y * y
            }
        }
        mu + sigma * y * sqrt(-2 * ln(r) / r)
    }

    public fun random(): Double = random.nextDouble()


}


