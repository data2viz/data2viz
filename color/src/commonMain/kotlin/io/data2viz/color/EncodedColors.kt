/*
 * Copyright (c) 2018-2021. data2viz sàrl.
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

package io.data2viz.color


import kotlin.math.floor

public fun String.toColors(): List<Color> {
    require(length % 6 == 0) { "String size should be a multiple of 6, format RRGGBBRRGGBB..." }
    return (0 until (length / 6)).map { "#${substring(6 * it, 6 * it + 6)}".col }
}

/**
 * Predefined colors defined as concatenated hex strings.
 */
public class EncodedColors(
    public val colors: List<Color>) {

        public companion object {

            // CATEGORICAL
            public val category10   : EncodedColors by lazy { EncodedColors("1f77b4ff7f0e2ca02cd627289467bd8c564be377c27f7f7fbcbd2217becf".toColors()) }
            public val category20   : EncodedColors by lazy { EncodedColors("1f77b4aec7e8ff7f0effbb782ca02c98df8ad62728ff98969467bdc5b0d58c564bc49c94e377c2f7b6d27f7f7fc7c7c7bcbd22dbdb8d17becf9edae5".toColors()) }
            public val category20b  : EncodedColors by lazy { EncodedColors("393b795254a36b6ecf9c9ede6379398ca252b5cf6bcedb9c8c6d31bd9e39e7ba52e7cb94843c39ad494ad6616be7969c7b4173a55194ce6dbdde9ed6".toColors()) }
            public val category20c  : EncodedColors by lazy { EncodedColors("3182bd6baed69ecae1c6dbefe6550dfd8d3cfdae6bfdd0a231a35474c476a1d99bc7e9c0756bb19e9ac8bcbddcdadaeb636363969696bdbdbdd9d9d9".toColors()) }
            public val accents      : EncodedColors by lazy { EncodedColors("7fc97fbeaed4fdc086ffff99386cb0f0027fbf5b17666666".toColors()) }
            public val dark2        : EncodedColors by lazy { EncodedColors("1b9e77d95f027570b3e7298a66a61ee6ab02a6761d666666".toColors()) }
            public val paired       : EncodedColors by lazy { EncodedColors("a6cee31f78b4b2df8a33a02cfb9a99e31a1cfdbf6fff7f00cab2d66a3d9affff99b15928".toColors()) }
            public val pastel1      : EncodedColors by lazy { EncodedColors("fbb4aeb3cde3ccebc5decbe4fed9a6ffffcce5d8bdfddaecf2f2f2".toColors()) }
            public val pastel2      : EncodedColors by lazy { EncodedColors("b3e2cdfdcdaccbd5e8f4cae4e6f5c9fff2aef1e2cccccccc".toColors()) }
            public val set1         : EncodedColors by lazy { EncodedColors("e41a1c377eb84daf4a984ea3ff7f00ffff33a65628f781bf999999".toColors()) }
            public val set2         : EncodedColors by lazy { EncodedColors("66c2a5fc8d628da0cbe78ac3a6d854ffd92fe5c494b3b3b3".toColors()) }
            public val set3         : EncodedColors by lazy { EncodedColors("8dd3c7ffffb3bebadafb807280b1d3fdb462b3de69fccde5d9d9d9bc80bdccebc5ffed6f".toColors()) }

            // DIVERGING
            public val BrBG3   : EncodedColors by  lazy { EncodedColors("d8b365f5f5f55ab4ac"                                                     .toColors()) }
            public val BrBG4   : EncodedColors by  lazy { EncodedColors("a6611adfc27d80cdc1018571"                                               .toColors()) }
            public val BrBG5   : EncodedColors by  lazy { EncodedColors("a6611adfc27df5f5f580cdc1018571"                                         .toColors()) }
            public val BrBG6   : EncodedColors by  lazy { EncodedColors("8c510ad8b365f6e8c3c7eae55ab4ac01665e"                                   .toColors()) }
            public val BrBG7   : EncodedColors by  lazy { EncodedColors("8c510ad8b365f6e8c3f5f5f5c7eae55ab4ac01665e"                             .toColors()) }
            public val BrBG8   : EncodedColors by  lazy { EncodedColors("8c510abf812ddfc27df6e8c3c7eae580cdc135978f01665e"                       .toColors()) }
            public val BrBG9   : EncodedColors by  lazy { EncodedColors("8c510abf812ddfc27df6e8c3f5f5f5c7eae580cdc135978f01665e"                 .toColors()) }
            public val BrBG10  : EncodedColors by lazy { EncodedColors("5430058c510abf812ddfc27df6e8c3c7eae580cdc135978f01665e003c30"           .toColors()) }
            public val BrBG11  : EncodedColors by lazy { EncodedColors("5430058c510abf812ddfc27df6e8c3f5f5f5c7eae580cdc135978f01665e003c30"     .toColors()) }
            //        val BrBG       by lazy { listOf(BrBG3, BrBG4, BrBG5, BrBG6, BrBG7, BrBG8, BrBG9, BrBG10, BrBG11.toColors()) }

            public val PiYG3  : EncodedColors by  lazy { EncodedColors("e9a3c9f7f7f7a1d76a"                                                     .toColors()) }
            public val PiYG4  : EncodedColors by  lazy { EncodedColors("d01c8bf1b6dab8e1864dac26"                                               .toColors()) }
            public val PiYG5  : EncodedColors by  lazy { EncodedColors("d01c8bf1b6daf7f7f7b8e1864dac26"                                         .toColors()) }
            public val PiYG6  : EncodedColors by  lazy { EncodedColors("c51b7de9a3c9fde0efe6f5d0a1d76a4d9221"                                   .toColors()) }
            public val PiYG7  : EncodedColors by  lazy { EncodedColors("c51b7de9a3c9fde0eff7f7f7e6f5d0a1d76a4d9221"                             .toColors()) }
            public val PiYG8  : EncodedColors by  lazy { EncodedColors("c51b7dde77aef1b6dafde0efe6f5d0b8e1867fbc414d9221"                       .toColors()) }
            public val PiYG9  : EncodedColors by  lazy { EncodedColors("c51b7dde77aef1b6dafde0eff7f7f7e6f5d0b8e1867fbc414d9221"                 .toColors()) }
            public val PiYG10 : EncodedColors by lazy { EncodedColors("8e0152c51b7dde77aef1b6dafde0efe6f5d0b8e1867fbc414d9221276419"           .toColors()) }
            public val PiYG11 : EncodedColors by lazy { EncodedColors("8e0152c51b7dde77aef1b6dafde0eff7f7f7e6f5d0b8e1867fbc414d9221276419"     .toColors()) }
    //        val PiYG       by lazy { listOf(PiYG3, PiYG4, PiYG5, PiYG6, PiYG7, PiYG8, PiYG9, PiYG10, PiYG11.toColors()) }

            public val PRGn3   : EncodedColors by lazy { EncodedColors("af8dc3f7f7f77fbf7b"                                                     .toColors()) }
            public val PRGn4   : EncodedColors by lazy { EncodedColors("7b3294c2a5cfa6dba0008837"                                               .toColors()) }
            public val PRGn5   : EncodedColors by lazy { EncodedColors("7b3294c2a5cff7f7f7a6dba0008837"                                         .toColors()) }
            public val PRGn6   : EncodedColors by lazy { EncodedColors("762a83af8dc3e7d4e8d9f0d37fbf7b1b7837"                                   .toColors()) }
            public val PRGn7   : EncodedColors by lazy { EncodedColors("762a83af8dc3e7d4e8f7f7f7d9f0d37fbf7b1b7837"                             .toColors()) }
            public val PRGn8   : EncodedColors by lazy { EncodedColors("762a839970abc2a5cfe7d4e8d9f0d3a6dba05aae611b7837"                       .toColors()) }
            public val PRGn9   : EncodedColors by lazy { EncodedColors("762a839970abc2a5cfe7d4e8f7f7f7d9f0d3a6dba05aae611b7837"                 .toColors()) }
            public val PRGn10  : EncodedColors by lazy { EncodedColors("40004b762a839970abc2a5cfe7d4e8d9f0d3a6dba05aae611b783700441b"           .toColors()) }
            public val PRGn11  : EncodedColors by lazy { EncodedColors("40004b762a839970abc2a5cfe7d4e8f7f7f7d9f0d3a6dba05aae611b783700441b"     .toColors()) }
    //        val PRGn       by lazy { listOf(PRGn3, PRGn4, PRGn5, PRGn6, PRGn7, PRGn8, PRGn9, PRGn10, PRGn11.toColors()) }

            public val PuOR3   : EncodedColors by lazy { EncodedColors("998ec3f7f7f7f1a340"                                                     .toColors()) }
            public val PuOR4   : EncodedColors by lazy { EncodedColors("5e3c99b2abd2fdb863e66101"                                               .toColors()) }
            public val PuOR5   : EncodedColors by lazy { EncodedColors("5e3c99b2abd2f7f7f7fdb863e66101"                                         .toColors()) }
            public val PuOR6   : EncodedColors by lazy { EncodedColors("542788998ec3d8daebfee0b6f1a340b35806"                                   .toColors()) }
            public val PuOR7   : EncodedColors by lazy { EncodedColors("542788998ec3d8daebf7f7f7fee0b6f1a340b35806"                             .toColors()) }
            public val PuOR8   : EncodedColors by lazy { EncodedColors("5427888073acb2abd2d8daebfee0b6fdb863e08214b35806"                       .toColors()) }
            public val PuOR9   : EncodedColors by lazy { EncodedColors("5427888073acb2abd2d8daebf7f7f7fee0b6fdb863e08214b35806"                 .toColors()) }
            public val PuOR10  : EncodedColors by lazy { EncodedColors("2d004b5427888073acb2abd2d8daebfee0b6fdb863e08214b358067f3b08"           .toColors()) }
            public val PuOR11  : EncodedColors by lazy { EncodedColors("2d004b5427888073acb2abd2d8daebf7f7f7fee0b6fdb863e08214b358067f3b08"     .toColors()) }
    //        val PuOR       by lazy { listOf(PuOR3, PuOR4, PuOR5, PuOR6, PuOR7, PuOR8, PuOR9, PuOR10, PuOR11.toColors()) }

            public val RdBU3   : EncodedColors by lazy { EncodedColors("ef8a62f7f7f767a9cf"                                                     .toColors()) }
            public val RdBU4   : EncodedColors by lazy { EncodedColors("ca0020f4a58292c5de0571b0"                                               .toColors()) }
            public val RdBU5   : EncodedColors by lazy { EncodedColors("ca0020f4a582f7f7f792c5de0571b0"                                         .toColors()) }
            public val RdBU6   : EncodedColors by lazy { EncodedColors("b2182bef8a62fddbc7d1e5f067a9cf2166ac"                                   .toColors()) }
            public val RdBU7   : EncodedColors by lazy { EncodedColors("b2182bef8a62fddbc7f7f7f7d1e5f067a9cf2166ac"                             .toColors()) }
            public val RdBU8   : EncodedColors by lazy { EncodedColors("b2182bd6604df4a582fddbc7d1e5f092c5de4393c32166ac"                       .toColors()) }
            public val RdBU9   : EncodedColors by lazy { EncodedColors("b2182bd6604df4a582fddbc7f7f7f7d1e5f092c5de4393c32166ac"                 .toColors()) }
            public val RdBU10  : EncodedColors by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7d1e5f092c5de4393c32166ac053061"           .toColors()) }
            public val RdBU11  : EncodedColors by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7f7f7f7d1e5f092c5de4393c32166ac053061"     .toColors()) }
    //        val RdBU       by lazy { listOf(RdBU3, RdBU4, RdBU5, RdBU6, RdBU7, RdBU8, RdBU9, RdBU10, RdBU11.toColors()) }

            public val RdGY3  : EncodedColors by lazy { EncodedColors("ef8a62ffffff999999"                                                     .toColors()) }
            public val RdGY4  : EncodedColors by lazy { EncodedColors("ca0020f4a582bababa404040"                                               .toColors()) }
            public val RdGY5  : EncodedColors by lazy { EncodedColors("ca0020f4a582ffffffbababa404040"                                         .toColors()) }
            public val RdGY6  : EncodedColors by lazy { EncodedColors("b2182bef8a62fddbc7e0e0e09999994d4d4d"                                   .toColors()) }
            public val RdGY7  : EncodedColors by lazy { EncodedColors("b2182bef8a62fddbc7ffffffe0e0e09999994d4d4d"                             .toColors()) }
            public val RdGY8  : EncodedColors by lazy { EncodedColors("b2182bd6604df4a582fddbc7e0e0e0bababa8787874d4d4d"                       .toColors()) }
            public val RdGY9  : EncodedColors by lazy { EncodedColors("b2182bd6604df4a582fddbc7ffffffe0e0e0bababa8787874d4d4d"                 .toColors()) }
            public val RdGY10 : EncodedColors by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7e0e0e0bababa8787874d4d4d1a1a1a"           .toColors()) }
            public val RdGY11 : EncodedColors by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7ffffffe0e0e0bababa8787874d4d4d1a1a1a"     .toColors()) }
    //        val RdGY       by lazy { listOf(RdGY3, RdGY4, RdGY5, RdGY6, RdGY7, RdGY8, RdGY9, RdGY10, RdGY11.toColors()) }

            public val RdYlBu3                 : EncodedColors by lazy { EncodedColors("fc8d59ffffbf91bfdb"                                                   .toColors()) }
            public val RdYlBu4                 : EncodedColors by lazy { EncodedColors("d7191cfdae61abd9e92c7bb6"                                             .toColors()) }
            public val RdYlBu5                 : EncodedColors by lazy { EncodedColors("d7191cfdae61ffffbfabd9e92c7bb6"                                       .toColors()) }
            public val RdYlBu6                 : EncodedColors by lazy { EncodedColors("d73027fc8d59fee090e0f3f891bfdb4575b4"                                 .toColors()) }
            public val RdYlBu7                 : EncodedColors by lazy { EncodedColors("d73027fc8d59fee090ffffbfe0f3f891bfdb4575b4"                           .toColors()) }
            public val RdYlBu8                 : EncodedColors by lazy { EncodedColors("d73027f46d43fdae61fee090e0f3f8abd9e974add14575b4"                     .toColors()) }
            public val RdYlBu9                 : EncodedColors by lazy { EncodedColors("d73027f46d43fdae61fee090ffffbfe0f3f8abd9e974add14575b4"               .toColors()) }
            public val RdYlBu10                : EncodedColors by lazy { EncodedColors("a50026d73027f46d43fdae61fee090e0f3f8abd9e974add14575b4313695"         .toColors()) }
            public val RdYlBu11                : EncodedColors by lazy { EncodedColors("a50026d73027f46d43fdae61fee090ffffbfe0f3f8abd9e974add14575b4313695"   .toColors()) }
            public val BuYlRd3                 : EncodedColors = RdYlBu3.reversed()
            public val BuYlRd4                 : EncodedColors = RdYlBu4.reversed()
            public val BuYlRd5                 : EncodedColors = RdYlBu5.reversed()
            public val BuYlRd6                 : EncodedColors = RdYlBu6.reversed()
            public val BuYlRd7                 : EncodedColors = RdYlBu7.reversed()
            public val BuYlRd8                 : EncodedColors = RdYlBu8.reversed()
            public val BuYlRd9                 : EncodedColors = RdYlBu9.reversed()
            public val BuYlRd10                : EncodedColors = RdYlBu10.reversed()
            public val BuYlRd11                : EncodedColors = RdYlBu11.reversed()
            public val blue_red_yellow         : EncodedColors = BuYlRd11
    //        val RdYlBu       by lazy { listOf(RdYlBu3, RdYlBu4, RdYlBu5, RdYlBu6, RdYlBu7, RdYlBu8, RdYlBu9, RdYlBu10, RdYlBu11.toColors()) }

            public val RdYlGn3  : EncodedColors by lazy { EncodedColors("fc8d59ffffbf91cf60"                                                   .toColors()) }
            public val RdYlGn4  : EncodedColors by lazy { EncodedColors("d7191cfdae61a6d96a1a9641"                                             .toColors()) }
            public val RdYlGn5  : EncodedColors by lazy { EncodedColors("d7191cfdae61ffffbfa6d96a1a9641"                                       .toColors()) }
            public val RdYlGn6  : EncodedColors by lazy { EncodedColors("d73027fc8d59fee08bd9ef8b91cf601a9850"                                 .toColors()) }
            public val RdYlGn7  : EncodedColors by lazy { EncodedColors("d73027fc8d59fee08bffffbfd9ef8b91cf601a9850"                           .toColors()) }
            public val RdYlGn8  : EncodedColors by lazy { EncodedColors("d73027f46d43fdae61fee08bd9ef8ba6d96a66bd631a9850"                     .toColors()) }
            public val RdYlGn9  : EncodedColors by lazy { EncodedColors("d73027f46d43fdae61fee08bffffbfd9ef8ba6d96a66bd631a9850"               .toColors()) }
            public val RdYlGn10 : EncodedColors by lazy { EncodedColors("a50026d73027f46d43fdae61fee08bd9ef8ba6d96a66bd631a9850006837"         .toColors()) }
            public val RdYlGn11 : EncodedColors by lazy { EncodedColors("a50026d73027f46d43fdae61fee08bffffbfd9ef8ba6d96a66bd631a9850006837"   .toColors()) }
    //        val RdYlGn       by lazy { listOf(RdYlGn3, RdYlGn4, RdYlGn5, RdYlGn6, RdYlGn7, RdYlGn8, RdYlGn9, RdYlGn10, RdYlGn11.toColors()) }

            public val spectral3  : EncodedColors by lazy { EncodedColors("fc8d59ffffbf99d594"                                                 .toColors()) }
            public val spectral4  : EncodedColors by lazy { EncodedColors("d7191cfdae61abdda42b83ba"                                           .toColors()) }
            public val spectral5  : EncodedColors by lazy { EncodedColors("d7191cfdae61ffffbfabdda42b83ba"                                     .toColors()) }
            public val spectral6  : EncodedColors by lazy { EncodedColors("d53e4ffc8d59fee08be6f59899d5943288bd"                               .toColors()) }
            public val spectral7  : EncodedColors by lazy { EncodedColors("d53e4ffc8d59fee08bffffbfe6f59899d5943288bd"                         .toColors()) }
            public val spectral8  : EncodedColors by lazy { EncodedColors("d53e4ff46d43fdae61fee08be6f598abdda466c2a53288bd"                   .toColors()) }
            public val spectral9  : EncodedColors by lazy { EncodedColors("d53e4ff46d43fdae61fee08bffffbfe6f598abdda466c2a53288bd"             .toColors()) }
            public val spectral10 : EncodedColors by lazy { EncodedColors("9e0142d53e4ff46d43fdae61fee08be6f598abdda466c2a53288bd5e4fa2"       .toColors()) }
            public val spectral11 : EncodedColors by lazy { EncodedColors("9e0142d53e4ff46d43fdae61fee08bffffbfe6f598abdda466c2a53288bd5e4fa2" .toColors()) }
    //        val spectral   by lazy { listOf(spectral3, spectral4, spectral5, spectral6, spectral7, spectral8, spectral9, spectral10, spectral11) }

            // SEQUENTIAL-MULTI
            // TODO CUBEHELIX + RAINBOW
            public val viridis  : EncodedColors by lazy { EncodedColors("44015444025645045745055946075a46085c460a5d460b5e470d60470e6147106347116447136548146748166848176948186a481a6c481b6d481c6e481d6f481f70482071482173482374482475482576482677482878482979472a7a472c7a472d7b472e7c472f7d46307e46327e46337f463480453581453781453882443983443a83443b84433d84433e85423f854240864241864142874144874045884046883f47883f48893e49893e4a893e4c8a3d4d8a3d4e8a3c4f8a3c508b3b518b3b528b3a538b3a548c39558c39568c38588c38598c375a8c375b8d365c8d365d8d355e8d355f8d34608d34618d33628d33638d32648e32658e31668e31678e31688e30698e306a8e2f6b8e2f6c8e2e6d8e2e6e8e2e6f8e2d708e2d718e2c718e2c728e2c738e2b748e2b758e2a768e2a778e2a788e29798e297a8e297b8e287c8e287d8e277e8e277f8e27808e26818e26828e26828e25838e25848e25858e24868e24878e23888e23898e238a8d228b8d228c8d228d8d218e8d218f8d21908d21918c20928c20928c20938c1f948c1f958b1f968b1f978b1f988b1f998a1f9a8a1e9b8a1e9c891e9d891f9e891f9f881fa0881fa1881fa1871fa28720a38620a48621a58521a68522a78522a88423a98324aa8325ab8225ac8226ad8127ad8128ae8029af7f2ab07f2cb17e2db27d2eb37c2fb47c31b57b32b67a34b67935b77937b87838b9773aba763bbb753dbc743fbc7340bd7242be7144bf7046c06f48c16e4ac16d4cc26c4ec36b50c46a52c56954c56856c66758c7655ac8645cc8635ec96260ca6063cb5f65cb5e67cc5c69cd5b6ccd5a6ece5870cf5773d05675d05477d1537ad1517cd2507fd34e81d34d84d44b86d54989d5488bd6468ed64590d74393d74195d84098d83e9bd93c9dd93ba0da39a2da37a5db36a8db34aadc32addc30b0dd2fb2dd2db5de2bb8de29bade28bddf26c0df25c2df23c5e021c8e020cae11fcde11dd0e11cd2e21bd5e21ad8e219dae319dde318dfe318e2e418e5e419e7e419eae51aece51befe51cf1e51df4e61ef6e620f8e621fbe723fde725".toColors()) }
            public val magma    : EncodedColors by lazy { EncodedColors("00000401000501010601010802010902020b02020d03030f03031204041405041606051806051a07061c08071e0907200a08220b09240c09260d0a290e0b2b100b2d110c2f120d31130d34140e36150e38160f3b180f3d19103f1a10421c10441d11471e114920114b21114e22115024125325125527125829115a2a115c2c115f2d11612f116331116533106734106936106b38106c390f6e3b0f703d0f713f0f72400f74420f75440f764510774710784910784a10794c117a4e117b4f127b51127c52137c54137d56147d57157e59157e5a167e5c167f5d177f5f187f601880621980641a80651a80671b80681c816a1c816b1d816d1d816e1e81701f81721f817320817521817621817822817922827b23827c23827e24828025828125818326818426818627818827818928818b29818c29818e2a81902a81912b81932b80942c80962c80982d80992d809b2e7f9c2e7f9e2f7fa02f7fa1307ea3307ea5317ea6317da8327daa337dab337cad347cae347bb0357bb2357bb3367ab5367ab73779b83779ba3878bc3978bd3977bf3a77c03a76c23b75c43c75c53c74c73d73c83e73ca3e72cc3f71cd4071cf4070d0416fd2426fd3436ed5446dd6456cd8456cd9466bdb476adc4869de4968df4a68e04c67e24d66e34e65e44f64e55064e75263e85362e95462ea5661eb5760ec5860ed5a5fee5b5eef5d5ef05f5ef1605df2625df2645cf3655cf4675cf4695cf56b5cf66c5cf66e5cf7705cf7725cf8745cf8765cf9785df9795df97b5dfa7d5efa7f5efa815ffb835ffb8560fb8761fc8961fc8a62fc8c63fc8e64fc9065fd9266fd9467fd9668fd9869fd9a6afd9b6bfe9d6cfe9f6dfea16efea36ffea571fea772fea973feaa74feac76feae77feb078feb27afeb47bfeb67cfeb77efeb97ffebb81febd82febf84fec185fec287fec488fec68afec88cfeca8dfecc8ffecd90fecf92fed194fed395fed597fed799fed89afdda9cfddc9efddea0fde0a1fde2a3fde3a5fde5a7fde7a9fde9aafdebacfcecaefceeb0fcf0b2fcf2b4fcf4b6fcf6b8fcf7b9fcf9bbfcfbbdfcfdbf".toColors()) }
            public val inferno  : EncodedColors by lazy { EncodedColors("00000401000501010601010802010a02020c02020e03021004031204031405041706041907051b08051d09061f0a07220b07240c08260d08290e092b10092d110a30120a32140b34150b37160b39180c3c190c3e1b0c411c0c431e0c451f0c48210c4a230c4c240c4f260c51280b53290b552b0b572d0b592f0a5b310a5c320a5e340a5f3609613809623909633b09643d09653e0966400a67420a68440a68450a69470b6a490b6a4a0c6b4c0c6b4d0d6c4f0d6c510e6c520e6d540f6d550f6d57106e59106e5a116e5c126e5d126e5f136e61136e62146e64156e65156e67166e69166e6a176e6c186e6d186e6f196e71196e721a6e741a6e751b6e771c6d781c6d7a1d6d7c1d6d7d1e6d7f1e6c801f6c82206c84206b85216b87216b88226a8a226a8c23698d23698f24699025689225689326679526679727669827669a28659b29649d29649f2a63a02a63a22b62a32c61a52c60a62d60a82e5fa92e5eab2f5ead305dae305cb0315bb1325ab3325ab43359b63458b73557b93556ba3655bc3754bd3853bf3952c03a51c13a50c33b4fc43c4ec63d4dc73e4cc83f4bca404acb4149cc4248ce4347cf4446d04545d24644d34743d44842d54a41d74b3fd84c3ed94d3dda4e3cdb503bdd513ade5238df5337e05536e15635e25734e35933e45a31e55c30e65d2fe75e2ee8602de9612bea632aeb6429eb6628ec6726ed6925ee6a24ef6c23ef6e21f06f20f1711ff1731df2741cf3761bf37819f47918f57b17f57d15f67e14f68013f78212f78410f8850ff8870ef8890cf98b0bf98c0af98e09fa9008fa9207fa9407fb9606fb9706fb9906fb9b06fb9d07fc9f07fca108fca309fca50afca60cfca80dfcaa0ffcac11fcae12fcb014fcb216fcb418fbb61afbb81dfbba1ffbbc21fbbe23fac026fac228fac42afac62df9c72ff9c932f9cb35f8cd37f8cf3af7d13df7d340f6d543f6d746f5d949f5db4cf4dd4ff4df53f4e156f3e35af3e55df2e661f2e865f2ea69f1ec6df1ed71f1ef75f1f179f2f27df2f482f3f586f3f68af4f88ef5f992f6fa96f8fb9af9fc9dfafda1fcffa4".toColors()) }
            public val plasma   : EncodedColors by lazy { EncodedColors("0d088710078813078916078a19068c1b068d1d068e20068f2206902406912605912805922a05932c05942e05952f059631059733059735049837049938049a3a049a3c049b3e049c3f049c41049d43039e44039e46039f48039f4903a04b03a14c02a14e02a25002a25102a35302a35502a45601a45801a45901a55b01a55c01a65e01a66001a66100a76300a76400a76600a76700a86900a86a00a86c00a86e00a86f00a87100a87201a87401a87501a87701a87801a87a02a87b02a87d03a87e03a88004a88104a78305a78405a78606a68707a68808a68a09a58b0aa58d0ba58e0ca48f0da4910ea3920fa39410a29511a19613a19814a099159f9a169f9c179e9d189d9e199da01a9ca11b9ba21d9aa31e9aa51f99a62098a72197a82296aa2395ab2494ac2694ad2793ae2892b02991b12a90b22b8fb32c8eb42e8db52f8cb6308bb7318ab83289ba3388bb3488bc3587bd3786be3885bf3984c03a83c13b82c23c81c33d80c43e7fc5407ec6417dc7427cc8437bc9447aca457acb4679cc4778cc4977cd4a76ce4b75cf4c74d04d73d14e72d24f71d35171d45270d5536fd5546ed6556dd7566cd8576bd9586ada5a6ada5b69db5c68dc5d67dd5e66de5f65de6164df6263e06363e16462e26561e26660e3685fe4695ee56a5de56b5de66c5ce76e5be76f5ae87059e97158e97257ea7457eb7556eb7655ec7754ed7953ed7a52ee7b51ef7c51ef7e50f07f4ff0804ef1814df1834cf2844bf3854bf3874af48849f48948f58b47f58c46f68d45f68f44f79044f79143f79342f89441f89540f9973ff9983ef99a3efa9b3dfa9c3cfa9e3bfb9f3afba139fba238fca338fca537fca636fca835fca934fdab33fdac33fdae32fdaf31fdb130fdb22ffdb42ffdb52efeb72dfeb82cfeba2cfebb2bfebd2afebe2afec029fdc229fdc328fdc527fdc627fdc827fdca26fdcb26fccd25fcce25fcd025fcd225fbd324fbd524fbd724fad824fada24f9dc24f9dd25f8df25f8e125f7e225f7e425f6e626f6e826f5e926f5eb27f4ed27f3ee27f3f027f2f227f1f426f1f525f0f724f0f921".toColors()) }

            public val BuGN3 : EncodedColors by lazy { EncodedColors("e5f5f999d8c92ca25f"                                                      .toColors()) }
            public val BuGN4 : EncodedColors by lazy { EncodedColors("edf8fbb2e2e266c2a4238b45"                                                .toColors()) }
            public val BuGN5 : EncodedColors by lazy { EncodedColors("edf8fbb2e2e266c2a42ca25f006d2c"                                          .toColors()) }
            public val BuGN6 : EncodedColors by lazy { EncodedColors("edf8fbccece699d8c966c2a42ca25f006d2c"                                    .toColors()) }
            public val BuGN7 : EncodedColors by lazy { EncodedColors("edf8fbccece699d8c966c2a441ae76238b45005824"                              .toColors()) }
            public val BuGN8 : EncodedColors by lazy { EncodedColors("f7fcfde5f5f9ccece699d8c966c2a441ae76238b45005824"                        .toColors()) }
            public val BuGN9 : EncodedColors by lazy { EncodedColors("f7fcfde5f5f9ccece699d8c966c2a441ae76238b45006d2c00441b"                  .toColors()) }
    //        val BuGN       by lazy { listOf(BuGN3, BuGN4, BuGN5, BuGN6, BuGN7, BuGN8, BuGN9.toColors()) }

            public val BuPu3 : EncodedColors by lazy { EncodedColors("e0ecf49ebcda8856a7"                                                      .toColors()) }
            public val BuPu4 : EncodedColors by lazy { EncodedColors("edf8fbb3cde38c96c688419d"                                                .toColors()) }
            public val BuPu5 : EncodedColors by lazy { EncodedColors("edf8fbb3cde38c96c68856a7810f7c"                                          .toColors()) }
            public val BuPu6 : EncodedColors by lazy { EncodedColors("edf8fbbfd3e69ebcda8c96c68856a7810f7c"                                    .toColors()) }
            public val BuPu7 : EncodedColors by lazy { EncodedColors("edf8fbbfd3e69ebcda8c96c68c6bb188419d6e016b"                              .toColors()) }
            public val BuPu8 : EncodedColors by lazy { EncodedColors("f7fcfde0ecf4bfd3e69ebcda8c96c68c6bb188419d6e016b"                        .toColors()) }
            public val BuPu9 : EncodedColors by lazy { EncodedColors("f7fcfde0ecf4bfd3e69ebcda8c96c68c6bb188419d810f7c4d004b"                  .toColors()) }
    //        val BuPu       by lazy { listOf(BuPu3, BuPu4, BuPu5, BuPu6, BuPu7, BuPu8, BuPu9.toColors()) }

            public val GnBu3 : EncodedColors by lazy { EncodedColors("e0f3dba8ddb543a2ca"                                                      .toColors()) }
            public val GnBu4 : EncodedColors by lazy { EncodedColors("f0f9e8bae4bc7bccc42b8cbe"                                                .toColors()) }
            public val GnBu5 : EncodedColors by lazy { EncodedColors("f0f9e8bae4bc7bccc443a2ca0868ac"                                          .toColors()) }
            public val GnBu6 : EncodedColors by lazy { EncodedColors("f0f9e8ccebc5a8ddb57bccc443a2ca0868ac"                                    .toColors()) }
            public val GnBu7 : EncodedColors by lazy { EncodedColors("f0f9e8ccebc5a8ddb57bccc44eb3d32b8cbe08589e"                              .toColors()) }
            public val GnBu8 : EncodedColors by lazy { EncodedColors("f7fcf0e0f3dbccebc5a8ddb57bccc44eb3d32b8cbe08589e"                        .toColors()) }
            public val GnBu9 : EncodedColors by lazy { EncodedColors("f7fcf0e0f3dbccebc5a8ddb57bccc44eb3d32b8cbe0868ac084081"                  .toColors()) }
    //        val GnBu       by lazy { listOf(GnBu3, GnBu4, GnBu5, GnBu6, GnBu7, GnBu8, GnBu9.toColors()) }

            public val OrRd3 : EncodedColors by lazy { EncodedColors("fee8c8fdbb84e34a33"                                                      .toColors()) }
            public val OrRd4 : EncodedColors by lazy { EncodedColors("fef0d9fdcc8afc8d59d7301f"                                                .toColors()) }
            public val OrRd5 : EncodedColors by lazy { EncodedColors("fef0d9fdcc8afc8d59e34a33b30000"                                          .toColors()) }
            public val OrRd6 : EncodedColors by lazy { EncodedColors("fef0d9fdd49efdbb84fc8d59e34a33b30000"                                    .toColors()) }
            public val OrRd7 : EncodedColors by lazy { EncodedColors("fef0d9fdd49efdbb84fc8d59ef6548d7301f990000"                              .toColors()) }
            public val OrRd8 : EncodedColors by lazy { EncodedColors("fff7ecfee8c8fdd49efdbb84fc8d59ef6548d7301f990000"                        .toColors()) }
            public val OrRd9 : EncodedColors by lazy { EncodedColors("fff7ecfee8c8fdd49efdbb84fc8d59ef6548d7301fb300007f0000"                  .toColors()) }
    //        val OrRd       by lazy { listOf(OrRd3, OrRd4, OrRd5, OrRd6, OrRd7, OrRd8, OrRd9.toColors()) }

            public val PuBu3 : EncodedColors by lazy { EncodedColors("ece7f2a6bddb2b8cbe"                                                      .toColors()) }
            public val PuBu4 : EncodedColors by lazy { EncodedColors("f1eef6bdc9e174a9cf0570b0"                                                .toColors()) }
            public val PuBu5 : EncodedColors by lazy { EncodedColors("f1eef6bdc9e174a9cf2b8cbe045a8d"                                          .toColors()) }
            public val PuBu6 : EncodedColors by lazy { EncodedColors("f1eef6d0d1e6a6bddb74a9cf2b8cbe045a8d"                                    .toColors()) }
            public val PuBu7 : EncodedColors by lazy { EncodedColors("f1eef6d0d1e6a6bddb74a9cf3690c00570b0034e7b"                              .toColors()) }
            public val PuBu8 : EncodedColors by lazy { EncodedColors("fff7fbece7f2d0d1e6a6bddb74a9cf3690c00570b0034e7b"                        .toColors()) }
            public val PuBu9 : EncodedColors by lazy { EncodedColors("fff7fbece7f2d0d1e6a6bddb74a9cf3690c00570b0045a8d023858"                  .toColors()) }
    //        val PuBu       by lazy { listOf(PuBu3, PuBu4, PuBu5, PuBu6, PuBu7, PuBu8, PuBu9.toColors()) }

            public val PuBuGn3 : EncodedColors by lazy { EncodedColors("ece2f0a6bddb1c9099"                                                    .toColors()) }
            public val PuBuGn4 : EncodedColors by lazy { EncodedColors("f6eff7bdc9e167a9cf02818a"                                              .toColors()) }
            public val PuBuGn5 : EncodedColors by lazy { EncodedColors("f6eff7bdc9e167a9cf1c9099016c59"                                        .toColors()) }
            public val PuBuGn6 : EncodedColors by lazy { EncodedColors("f6eff7d0d1e6a6bddb67a9cf1c9099016c59"                                  .toColors()) }
            public val PuBuGn7 : EncodedColors by lazy { EncodedColors("f6eff7d0d1e6a6bddb67a9cf3690c002818a016450"                            .toColors()) }
            public val PuBuGn8 : EncodedColors by lazy { EncodedColors("fff7fbece2f0d0d1e6a6bddb67a9cf3690c002818a016450"                      .toColors()) }
            public val PuBuGn9 : EncodedColors by lazy { EncodedColors("fff7fbece2f0d0d1e6a6bddb67a9cf3690c002818a016c59014636"                .toColors()) }
    //        val PuBuGn       by lazy { listOf(PuBuGn3, PuBuGn4, PuBuGn5, PuBuGn6, PuBuGn7, PuBuGn8, PuBuGn9.toColors()) }

            public val PuRd3 : EncodedColors by lazy { EncodedColors("e7e1efc994c7dd1c77"                                                      .toColors()) }
            public val PuRd4 : EncodedColors by lazy { EncodedColors("f1eef6d7b5d8df65b0ce1256"                                                .toColors()) }
            public val PuRd5 : EncodedColors by lazy { EncodedColors("f1eef6d7b5d8df65b0dd1c77980043"                                          .toColors()) }
            public val PuRd6 : EncodedColors by lazy { EncodedColors("f1eef6d4b9dac994c7df65b0dd1c77980043"                                    .toColors()) }
            public val PuRd7 : EncodedColors by lazy { EncodedColors("f1eef6d4b9dac994c7df65b0e7298ace125691003f"                              .toColors()) }
            public val PuRd8 : EncodedColors by lazy { EncodedColors("f7f4f9e7e1efd4b9dac994c7df65b0e7298ace125691003f"                        .toColors()) }
            public val PuRd9 : EncodedColors by lazy { EncodedColors("f7f4f9e7e1efd4b9dac994c7df65b0e7298ace125698004367001f"                  .toColors()) }
    //        val PuRd       by lazy { listOf(PuRd3, PuRd4, PuRd5, PuRd6, PuRd7, PuRd8, PuRd9.toColors()) }

            public val RdPu3 : EncodedColors by lazy { EncodedColors("fde0ddfa9fb5c51b8a"                                                      .toColors()) }
            public val RdPu4 : EncodedColors by lazy { EncodedColors("feebe2fbb4b9f768a1ae017e"                                                .toColors()) }
            public val RdPu5 : EncodedColors by lazy { EncodedColors("feebe2fbb4b9f768a1c51b8a7a0177"                                          .toColors()) }
            public val RdPu6 : EncodedColors by lazy { EncodedColors("feebe2fcc5c0fa9fb5f768a1c51b8a7a0177"                                    .toColors()) }
            public val RdPu7 : EncodedColors by lazy { EncodedColors("feebe2fcc5c0fa9fb5f768a1dd3497ae017e7a0177"                              .toColors()) }
            public val RdPu8 : EncodedColors by lazy { EncodedColors("fff7f3fde0ddfcc5c0fa9fb5f768a1dd3497ae017e7a0177"                        .toColors()) }
            public val RdPu9 : EncodedColors by lazy { EncodedColors("fff7f3fde0ddfcc5c0fa9fb5f768a1dd3497ae017e7a017749006a"                  .toColors()) }
    //        val RdPu       by lazy { listOf(RdPu3, RdPu4, RdPu5, RdPu6, RdPu7, RdPu8, RdPu9.toColors()) }

            public val YlGn3: EncodedColors  by lazy { EncodedColors("f7fcb9addd8e31a354"                                                      .toColors()) }
            public val YlGn4: EncodedColors  by lazy { EncodedColors("ffffccc2e69978c679238443"                                                .toColors()) }
            public val YlGn5: EncodedColors  by lazy { EncodedColors("ffffccc2e69978c67931a354006837"                                          .toColors()) }
            public val YlGn6: EncodedColors  by lazy { EncodedColors("ffffccd9f0a3addd8e78c67931a354006837"                                    .toColors()) }
            public val YlGn7: EncodedColors  by lazy { EncodedColors("ffffccd9f0a3addd8e78c67941ab5d238443005a32"                              .toColors()) }
            public val YlGn8: EncodedColors  by lazy { EncodedColors("ffffe5f7fcb9d9f0a3addd8e78c67941ab5d238443005a32"                        .toColors()) }
            public val YlGn9: EncodedColors  by lazy { EncodedColors("ffffe5f7fcb9d9f0a3addd8e78c67941ab5d238443006837004529"                  .toColors()) }
    //        val YlGn       by lazy { listOf(YlGn3, YlGn4, YlGn5, YlGn6, YlGn7, YlGn8, YlGn9.toColors()) }

            public val YlGnbU3 : EncodedColors by lazy { EncodedColors("edf8b17fcdbb2c7fb8"                                                    .toColors()) }
            public val YlGnbU4 : EncodedColors by lazy { EncodedColors("ffffcca1dab441b6c4225ea8"                                              .toColors()) }
            public val YlGnbU5 : EncodedColors by lazy { EncodedColors("ffffcca1dab441b6c42c7fb8253494"                                        .toColors()) }
            public val YlGnbU6 : EncodedColors by lazy { EncodedColors("ffffccc7e9b47fcdbb41b6c42c7fb8253494"                                  .toColors()) }
            public val YlGnbU7 : EncodedColors by lazy { EncodedColors("ffffccc7e9b47fcdbb41b6c41d91c0225ea80c2c84"                            .toColors()) }
            public val YlGnbU8 : EncodedColors by lazy { EncodedColors("ffffd9edf8b1c7e9b47fcdbb41b6c41d91c0225ea80c2c84"                      .toColors()) }
            public val YlGnbU9 : EncodedColors by lazy { EncodedColors("ffffd9edf8b1c7e9b47fcdbb41b6c41d91c0225ea8253494081d58"                .toColors()) }
    //        val YlGnbU       by lazy { listOf(YlGnbU3, YlGnbU4, YlGnbU5, YlGnbU6, YlGnbU7, YlGnbU8, YlGnbU9.toColors()) }

            public val YlGnBr3 : EncodedColors by lazy { EncodedColors("fff7bcfec44fd95f0e"                                                    .toColors()) }
            public val YlGnBr4 : EncodedColors by lazy { EncodedColors("ffffd4fed98efe9929cc4c02"                                              .toColors()) }
            public val YlGnBr5 : EncodedColors by lazy { EncodedColors("ffffd4fed98efe9929d95f0e993404"                                        .toColors()) }
            public val YlGnBr6 : EncodedColors by lazy { EncodedColors("ffffd4fee391fec44ffe9929d95f0e993404"                                  .toColors()) }
            public val YlGnBr7 : EncodedColors by lazy { EncodedColors("ffffd4fee391fec44ffe9929ec7014cc4c028c2d04"                            .toColors()) }
            public val YlGnBr8 : EncodedColors by lazy { EncodedColors("ffffe5fff7bcfee391fec44ffe9929ec7014cc4c028c2d04"                      .toColors()) }
            public val YlGnBr9 : EncodedColors by lazy { EncodedColors("ffffe5fff7bcfee391fec44ffe9929ec7014cc4c02993404662506"                .toColors()) }
    //        val YlGnBr       by lazy { listOf(YlGnBr3, YlGnBr4, YlGnBr5, YlGnBr6, YlGnBr7, YlGnBr8, YlGnBr9.toColors()) }

            public val  YlGnRd3 : EncodedColors by lazy { EncodedColors("ffeda0feb24cf03b20"                                                    .toColors()) }
            public val  YlGnRd4 : EncodedColors by lazy { EncodedColors("ffffb2fecc5cfd8d3ce31a1c"                                              .toColors()) }
            public val  YlGnRd5 : EncodedColors by lazy { EncodedColors("ffffb2fecc5cfd8d3cf03b20bd0026"                                        .toColors()) }
            public val  YlGnRd6 : EncodedColors by lazy { EncodedColors("ffffb2fed976feb24cfd8d3cf03b20bd0026"                                  .toColors()) }
            public val  YlGnRd7 : EncodedColors by lazy { EncodedColors("ffffb2fed976feb24cfd8d3cfc4e2ae31a1cb10026"                            .toColors()) }
            public val  YlGnRd8 : EncodedColors by lazy { EncodedColors("ffffccffeda0fed976feb24cfd8d3cfc4e2ae31a1cb10026"                      .toColors()) }
            public val  YlGnRd9 : EncodedColors by lazy { EncodedColors("ffffccffeda0fed976feb24cfd8d3cfc4e2ae31a1cbd0026800026"                .toColors()) }
    //        val YlGnRd       by lazy { listOf(YlGnRd3, YlGnRd4, YlGnRd5, YlGnRd6, YlGnRd7, YlGnRd8, YlGnRd9.toColors()) }


            // SEQUENTIAL SINGLE
            public val blues3 : EncodedColors by lazy { EncodedColors("deebf79ecae13182bd"                                                     .toColors()) }
            public val blues4 : EncodedColors by lazy { EncodedColors("eff3ffbdd7e76baed62171b5"                                               .toColors()) }
            public val blues5 : EncodedColors by lazy { EncodedColors("eff3ffbdd7e76baed63182bd08519c"                                         .toColors()) }
            public val blues6 : EncodedColors by lazy { EncodedColors("eff3ffc6dbef9ecae16baed63182bd08519c"                                   .toColors()) }
            public val blues7 : EncodedColors by lazy { EncodedColors("eff3ffc6dbef9ecae16baed64292c62171b5084594"                             .toColors()) }
            public val blues8 : EncodedColors by lazy { EncodedColors("f7fbffdeebf7c6dbef9ecae16baed64292c62171b5084594"                       .toColors()) }
            public val blues9 : EncodedColors by lazy { EncodedColors("f7fbffdeebf7c6dbef9ecae16baed64292c62171b508519c08306b"                 .toColors()) }
    //        val blues       by lazy { listOf(blues3, blues4, blues5, blues6, blues7, blues8, blues9.toColors()) }

            public val greens3 : EncodedColors by lazy { EncodedColors("e5f5e0a1d99b31a354"                                                    .toColors()) }
            public val greens4 : EncodedColors by lazy { EncodedColors("edf8e9bae4b374c476238b45"                                              .toColors()) }
            public val greens5 : EncodedColors by lazy { EncodedColors("edf8e9bae4b374c47631a354006d2c"                                        .toColors()) }
            public val greens6 : EncodedColors by lazy { EncodedColors("edf8e9c7e9c0a1d99b74c47631a354006d2c"                                  .toColors()) }
            public val greens7 : EncodedColors by lazy { EncodedColors("edf8e9c7e9c0a1d99b74c47641ab5d238b45005a32"                            .toColors()) }
            public val greens8 : EncodedColors by lazy { EncodedColors("f7fcf5e5f5e0c7e9c0a1d99b74c47641ab5d238b45005a32"                      .toColors()) }
            public val greens9 : EncodedColors by lazy { EncodedColors("f7fcf5e5f5e0c7e9c0a1d99b74c47641ab5d238b45006d2c00441b"                .toColors()) }
    //        val greens       by lazy { listOf(greens3, greens4, greens5, greens6, greens7, greens8, greens9.toColors()) }

            public val greys3 : EncodedColors by lazy { EncodedColors("f0f0f0bdbdbd636363"                                                     .toColors()) }
            public val greys4 : EncodedColors by lazy { EncodedColors("f7f7f7cccccc969696525252"                                               .toColors()) }
            public val greys5 : EncodedColors by lazy { EncodedColors("f7f7f7cccccc969696636363252525"                                         .toColors()) }
            public val greys6 : EncodedColors by lazy { EncodedColors("f7f7f7d9d9d9bdbdbd969696636363252525"                                   .toColors()) }
            public val greys7 : EncodedColors by lazy { EncodedColors("f7f7f7d9d9d9bdbdbd969696737373525252252525"                             .toColors()) }
            public val greys8 : EncodedColors by lazy { EncodedColors("fffffff0f0f0d9d9d9bdbdbd969696737373525252252525"                       .toColors()) }
            public val greys9 : EncodedColors by lazy { EncodedColors("fffffff0f0f0d9d9d9bdbdbd969696737373525252252525000000"                 .toColors()) }
    //        val greys       by lazy { listOf(greys3, greys4, greys5, greys6, greys7, greys8, greys9.toColors()) }

            public val oranges3 : EncodedColors by lazy { EncodedColors("fee6cefdae6be6550d"                                                   .toColors()) }
            public val oranges4 : EncodedColors by lazy { EncodedColors("feeddefdbe85fd8d3cd94701"                                             .toColors()) }
            public val oranges5 : EncodedColors by lazy { EncodedColors("feeddefdbe85fd8d3ce6550da63603"                                       .toColors()) }
            public val oranges6 : EncodedColors by lazy { EncodedColors("feeddefdd0a2fdae6bfd8d3ce6550da63603"                                 .toColors()) }
            public val oranges7 : EncodedColors by lazy { EncodedColors("feeddefdd0a2fdae6bfd8d3cf16913d948018c2d04"                           .toColors()) }
            public val oranges8 : EncodedColors by lazy { EncodedColors("fff5ebfee6cefdd0a2fdae6bfd8d3cf16913d948018c2d04"                     .toColors()) }
            public val oranges9 : EncodedColors by lazy { EncodedColors("fff5ebfee6cefdd0a2fdae6bfd8d3cf16913d94801a636037f2704"               .toColors()) }
    //        val oranges       by lazy { listOf(oranges3, oranges4, oranges5, oranges6, oranges7, oranges8, oranges9.toColors()) }

            public val purples3 : EncodedColors by lazy { EncodedColors("efedf5bcbddc756bb1"                                                   .toColors()) }
            public val purples4 : EncodedColors by lazy { EncodedColors("f2f0f7cbc9e29e9ac86a51a3"                                             .toColors()) }
            public val purples5 : EncodedColors by lazy { EncodedColors("f2f0f7cbc9e29e9ac8756bb154278f"                                       .toColors()) }
            public val purples6 : EncodedColors by lazy { EncodedColors("f2f0f7dadaebbcbddc9e9ac8756bb154278f"                                 .toColors()) }
            public val purples7 : EncodedColors by lazy { EncodedColors("f2f0f7dadaebbcbddc9e9ac8807dba6a51a34a1486"                           .toColors()) }
            public val purples8 : EncodedColors by lazy { EncodedColors("fcfbfdefedf5dadaebbcbddc9e9ac8807dba6a51a34a1486"                     .toColors()) }
            public val purples9 : EncodedColors by lazy { EncodedColors("fcfbfdefedf5dadaebbcbddc9e9ac8807dba6a51a354278f3f007d"               .toColors()) }
    //        val purples       by lazy { listOf(purples3, purples4, purples5, purples6, purples7, purples8, purples9.toColors()) }

            public val reds3 : EncodedColors by lazy { EncodedColors("fee0d2fc9272de2d26"                                                      .toColors()) }
            public val reds4 : EncodedColors by lazy { EncodedColors("fee5d9fcae91fb6a4acb181d"                                                .toColors()) }
            public val reds5 : EncodedColors by lazy { EncodedColors("fee5d9fcae91fb6a4ade2d26a50f15"                                          .toColors()) }
            public val reds6 : EncodedColors by lazy { EncodedColors("fee5d9fcbba1fc9272fb6a4ade2d26a50f15"                                    .toColors()) }
            public val reds7 : EncodedColors by lazy { EncodedColors("fee5d9fcbba1fc9272fb6a4aef3b2ccb181d99000d"                              .toColors()) }
            public val reds8 : EncodedColors by lazy { EncodedColors("fff5f0fee0d2fcbba1fc9272fb6a4aef3b2ccb181d99000d"                        .toColors()) }
            public val reds9 : EncodedColors by lazy { EncodedColors("fff5f0fee0d2fcbba1fc9272fb6a4aef3b2ccb181da50f1567000d"                  .toColors()) }
    //        val reds       by lazy { listOf(reds3, reds4, reds5, reds6, reds7, reds8, reds9.toColors()) }
        }

    public fun reversed(): EncodedColors = EncodedColors(colors.reversed())

    /**
     * Returns the color corresponding at the given percentage of the gradient
     */
    public fun color(percent: Double): Color = colors[floor(percent * colors.size).toInt()
        .coerceAtLeast(0)
        .coerceAtMost(colors.size - 1)]
}
