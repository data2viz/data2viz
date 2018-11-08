package io.data2viz.color


import kotlin.math.floor


/**
 * Predefined colors defined as concatenated hex strings.
 */
class EncodedColors(colorsAsString: String) {
    companion object {

        // CATEGORICAL
        val category10 by lazy { EncodedColors("1f77b4ff7f0e2ca02cd627289467bd8c564be377c27f7f7fbcbd2217becf") }
        val category20 by lazy { EncodedColors("1f77b4aec7e8ff7f0effbb782ca02c98df8ad62728ff98969467bdc5b0d58c564bc49c94e377c2f7b6d27f7f7fc7c7c7bcbd22dbdb8d17becf9edae5") }
        val category20b by lazy { EncodedColors("393b795254a36b6ecf9c9ede6379398ca252b5cf6bcedb9c8c6d31bd9e39e7ba52e7cb94843c39ad494ad6616be7969c7b4173a55194ce6dbdde9ed6") }
        val category20c by lazy { EncodedColors("3182bd6baed69ecae1c6dbefe6550dfd8d3cfdae6bfdd0a231a35474c476a1d99bc7e9c0756bb19e9ac8bcbddcdadaeb636363969696bdbdbdd9d9d9") }
        val accents by lazy { EncodedColors("7fc97fbeaed4fdc086ffff99386cb0f0027fbf5b17666666") }
        val dark2 by lazy { EncodedColors("1b9e77d95f027570b3e7298a66a61ee6ab02a6761d666666") }
        val paired by lazy { EncodedColors("a6cee31f78b4b2df8a33a02cfb9a99e31a1cfdbf6fff7f00cab2d66a3d9affff99b15928") }
        val pastel1 by lazy { EncodedColors("fbb4aeb3cde3ccebc5decbe4fed9a6ffffcce5d8bdfddaecf2f2f2") }
        val pastel2 by lazy { EncodedColors("b3e2cdfdcdaccbd5e8f4cae4e6f5c9fff2aef1e2cccccccc") }
        val set1 by lazy { EncodedColors("e41a1c377eb84daf4a984ea3ff7f00ffff33a65628f781bf999999") }
        val set2 by lazy { EncodedColors("66c2a5fc8d628da0cbe78ac3a6d854ffd92fe5c494b3b3b3") }
        val set3 by lazy { EncodedColors("8dd3c7ffffb3bebadafb807280b1d3fdb462b3de69fccde5d9d9d9bc80bdccebc5ffed6f") }

        // DIVERGING
        private val BrBG3   by lazy { EncodedColors("d8b365f5f5f55ab4ac"                                                    ) }
        private val BrBG4   by lazy { EncodedColors("a6611adfc27d80cdc1018571"                                              ) }
        private val BrBG5   by lazy { EncodedColors("a6611adfc27df5f5f580cdc1018571"                                        ) }
        private val BrBG6   by lazy { EncodedColors("8c510ad8b365f6e8c3c7eae55ab4ac01665e"                                  ) }
        private val BrBG7   by lazy { EncodedColors("8c510ad8b365f6e8c3f5f5f5c7eae55ab4ac01665e"                            ) }
        private val BrBG8   by lazy { EncodedColors("8c510abf812ddfc27df6e8c3c7eae580cdc135978f01665e"                      ) }
        private val BrBG9   by lazy { EncodedColors("8c510abf812ddfc27df6e8c3f5f5f5c7eae580cdc135978f01665e"                ) }
        private val BrBG10  by lazy { EncodedColors("5430058c510abf812ddfc27df6e8c3c7eae580cdc135978f01665e003c30"          ) }
        private val BrBG11  by lazy { EncodedColors("5430058c510abf812ddfc27df6e8c3f5f5f5c7eae580cdc135978f01665e003c30"    ) }
        val BrBG       by lazy { listOf(BrBG3, BrBG4, BrBG5, BrBG6, BrBG7, BrBG8, BrBG9, BrBG10, BrBG11) }

        private val PiYG3   by lazy { EncodedColors("e9a3c9f7f7f7a1d76a"                                                    ) }
        private val PiYG4   by lazy { EncodedColors("d01c8bf1b6dab8e1864dac26"                                              ) }
        private val PiYG5   by lazy { EncodedColors("d01c8bf1b6daf7f7f7b8e1864dac26"                                        ) }
        private val PiYG6   by lazy { EncodedColors("c51b7de9a3c9fde0efe6f5d0a1d76a4d9221"                                  ) }
        private val PiYG7   by lazy { EncodedColors("c51b7de9a3c9fde0eff7f7f7e6f5d0a1d76a4d9221"                            ) }
        private val PiYG8   by lazy { EncodedColors("c51b7dde77aef1b6dafde0efe6f5d0b8e1867fbc414d9221"                      ) }
        private val PiYG9   by lazy { EncodedColors("c51b7dde77aef1b6dafde0eff7f7f7e6f5d0b8e1867fbc414d9221"                ) }
        private val PiYG10  by lazy { EncodedColors("8e0152c51b7dde77aef1b6dafde0efe6f5d0b8e1867fbc414d9221276419"          ) }
        private val PiYG11  by lazy { EncodedColors("8e0152c51b7dde77aef1b6dafde0eff7f7f7e6f5d0b8e1867fbc414d9221276419"    ) }
        val PiYG       by lazy { listOf(PiYG3, PiYG4, PiYG5, PiYG6, PiYG7, PiYG8, PiYG9, PiYG10, PiYG11) }

        private val PRGn3   by lazy { EncodedColors("af8dc3f7f7f77fbf7b"                                                    ) }
        private val PRGn4   by lazy { EncodedColors("7b3294c2a5cfa6dba0008837"                                              ) }
        private val PRGn5   by lazy { EncodedColors("7b3294c2a5cff7f7f7a6dba0008837"                                        ) }
        private val PRGn6   by lazy { EncodedColors("762a83af8dc3e7d4e8d9f0d37fbf7b1b7837"                                  ) }
        private val PRGn7   by lazy { EncodedColors("762a83af8dc3e7d4e8f7f7f7d9f0d37fbf7b1b7837"                            ) }
        private val PRGn8   by lazy { EncodedColors("762a839970abc2a5cfe7d4e8d9f0d3a6dba05aae611b7837"                      ) }
        private val PRGn9   by lazy { EncodedColors("762a839970abc2a5cfe7d4e8f7f7f7d9f0d3a6dba05aae611b7837"                ) }
        private val PRGn10  by lazy { EncodedColors("40004b762a839970abc2a5cfe7d4e8d9f0d3a6dba05aae611b783700441b"          ) }
        private val PRGn11  by lazy { EncodedColors("40004b762a839970abc2a5cfe7d4e8f7f7f7d9f0d3a6dba05aae611b783700441b"    ) }
        val PRGn       by lazy { listOf(PRGn3, PRGn4, PRGn5, PRGn6, PRGn7, PRGn8, PRGn9, PRGn10, PRGn11) }

        private val PuOR3   by lazy { EncodedColors("998ec3f7f7f7f1a340"                                                    ) }
        private val PuOR4   by lazy { EncodedColors("5e3c99b2abd2fdb863e66101"                                              ) }
        private val PuOR5   by lazy { EncodedColors("5e3c99b2abd2f7f7f7fdb863e66101"                                        ) }
        private val PuOR6   by lazy { EncodedColors("542788998ec3d8daebfee0b6f1a340b35806"                                  ) }
        private val PuOR7   by lazy { EncodedColors("542788998ec3d8daebf7f7f7fee0b6f1a340b35806"                            ) }
        private val PuOR8   by lazy { EncodedColors("5427888073acb2abd2d8daebfee0b6fdb863e08214b35806"                      ) }
        private val PuOR9   by lazy { EncodedColors("5427888073acb2abd2d8daebf7f7f7fee0b6fdb863e08214b35806"                ) }
        private val PuOR10  by lazy { EncodedColors("2d004b5427888073acb2abd2d8daebfee0b6fdb863e08214b358067f3b08"          ) }
        private val PuOR11  by lazy { EncodedColors("2d004b5427888073acb2abd2d8daebf7f7f7fee0b6fdb863e08214b358067f3b08"    ) }
        val PuOR       by lazy { listOf(PuOR3, PuOR4, PuOR5, PuOR6, PuOR7, PuOR8, PuOR9, PuOR10, PuOR11) }

        private val RdBU3   by lazy { EncodedColors("ef8a62f7f7f767a9cf"                                                    ) }
        private val RdBU4   by lazy { EncodedColors("ca0020f4a58292c5de0571b0"                                              ) }
        private val RdBU5   by lazy { EncodedColors("ca0020f4a582f7f7f792c5de0571b0"                                        ) }
        private val RdBU6   by lazy { EncodedColors("b2182bef8a62fddbc7d1e5f067a9cf2166ac"                                  ) }
        private val RdBU7   by lazy { EncodedColors("b2182bef8a62fddbc7f7f7f7d1e5f067a9cf2166ac"                            ) }
        private val RdBU8   by lazy { EncodedColors("b2182bd6604df4a582fddbc7d1e5f092c5de4393c32166ac"                      ) }
        private val RdBU9   by lazy { EncodedColors("b2182bd6604df4a582fddbc7f7f7f7d1e5f092c5de4393c32166ac"                ) }
        private val RdBU10  by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7d1e5f092c5de4393c32166ac053061"          ) }
        private val RdBU11  by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7f7f7f7d1e5f092c5de4393c32166ac053061"    ) }
        val RdBU       by lazy { listOf(RdBU3, RdBU4, RdBU5, RdBU6, RdBU7, RdBU8, RdBU9, RdBU10, RdBU11) }

        private val RdGY3   by lazy { EncodedColors("ef8a62ffffff999999"                                                    ) }
        private val RdGY4   by lazy { EncodedColors("ca0020f4a582bababa404040"                                              ) }
        private val RdGY5   by lazy { EncodedColors("ca0020f4a582ffffffbababa404040"                                        ) }
        private val RdGY6   by lazy { EncodedColors("b2182bef8a62fddbc7e0e0e09999994d4d4d"                                  ) }
        private val RdGY7   by lazy { EncodedColors("b2182bef8a62fddbc7ffffffe0e0e09999994d4d4d"                            ) }
        private val RdGY8   by lazy { EncodedColors("b2182bd6604df4a582fddbc7e0e0e0bababa8787874d4d4d"                      ) }
        private val RdGY9   by lazy { EncodedColors("b2182bd6604df4a582fddbc7ffffffe0e0e0bababa8787874d4d4d"                ) }
        private val RdGY10  by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7e0e0e0bababa8787874d4d4d1a1a1a"          ) }
        private val RdGY11  by lazy { EncodedColors("67001fb2182bd6604df4a582fddbc7ffffffe0e0e0bababa8787874d4d4d1a1a1a"    ) }
        val RdGY       by lazy { listOf(RdGY3, RdGY4, RdGY5, RdGY6, RdGY7, RdGY8, RdGY9, RdGY10, RdGY11) }

        private val RdYlBu3   by lazy { EncodedColors("fc8d59ffffbf91bfdb"                                                  ) }
        private val RdYlBu4   by lazy { EncodedColors("d7191cfdae61abd9e92c7bb6"                                            ) }
        private val RdYlBu5   by lazy { EncodedColors("d7191cfdae61ffffbfabd9e92c7bb6"                                      ) }
        private val RdYlBu6   by lazy { EncodedColors("d73027fc8d59fee090e0f3f891bfdb4575b4"                                ) }
        private val RdYlBu7   by lazy { EncodedColors("d73027fc8d59fee090ffffbfe0f3f891bfdb4575b4"                          ) }
        private val RdYlBu8   by lazy { EncodedColors("d73027f46d43fdae61fee090e0f3f8abd9e974add14575b4"                    ) }
        private val RdYlBu9   by lazy { EncodedColors("d73027f46d43fdae61fee090ffffbfe0f3f8abd9e974add14575b4"              ) }
        private val RdYlBu10  by lazy { EncodedColors("a50026d73027f46d43fdae61fee090e0f3f8abd9e974add14575b4313695"        ) }
        private val RdYlBu11  by lazy { EncodedColors("a50026d73027f46d43fdae61fee090ffffbfe0f3f8abd9e974add14575b4313695"  ) }
        val RdYlBu       by lazy { listOf(RdYlBu3, RdYlBu4, RdYlBu5, RdYlBu6, RdYlBu7, RdYlBu8, RdYlBu9, RdYlBu10, RdYlBu11) }

        private val RdYlGn3   by lazy { EncodedColors("fc8d59ffffbf91cf60"                                                  ) }
        private val RdYlGn4   by lazy { EncodedColors("d7191cfdae61a6d96a1a9641"                                            ) }
        private val RdYlGn5   by lazy { EncodedColors("d7191cfdae61ffffbfa6d96a1a9641"                                      ) }
        private val RdYlGn6   by lazy { EncodedColors("d73027fc8d59fee08bd9ef8b91cf601a9850"                                ) }
        private val RdYlGn7   by lazy { EncodedColors("d73027fc8d59fee08bffffbfd9ef8b91cf601a9850"                          ) }
        private val RdYlGn8   by lazy { EncodedColors("d73027f46d43fdae61fee08bd9ef8ba6d96a66bd631a9850"                    ) }
        private val RdYlGn9   by lazy { EncodedColors("d73027f46d43fdae61fee08bffffbfd9ef8ba6d96a66bd631a9850"              ) }
        private val RdYlGn10  by lazy { EncodedColors("a50026d73027f46d43fdae61fee08bd9ef8ba6d96a66bd631a9850006837"        ) }
        private val RdYlGn11  by lazy { EncodedColors("a50026d73027f46d43fdae61fee08bffffbfd9ef8ba6d96a66bd631a9850006837"  ) }
        val RdYlGn       by lazy { listOf(RdYlGn3, RdYlGn4, RdYlGn5, RdYlGn6, RdYlGn7, RdYlGn8, RdYlGn9, RdYlGn10, RdYlGn11) }

        private val spectral3   by lazy { EncodedColors("fc8d59ffffbf99d594"                                                ) }
        private val spectral4   by lazy { EncodedColors("d7191cfdae61abdda42b83ba"                                          ) }
        private val spectral5   by lazy { EncodedColors("d7191cfdae61ffffbfabdda42b83ba"                                    ) }
        private val spectral6   by lazy { EncodedColors("d53e4ffc8d59fee08be6f59899d5943288bd"                              ) }
        private val spectral7   by lazy { EncodedColors("d53e4ffc8d59fee08bffffbfe6f59899d5943288bd"                        ) }
        private val spectral8   by lazy { EncodedColors("d53e4ff46d43fdae61fee08be6f598abdda466c2a53288bd"                  ) }
        private val spectral9   by lazy { EncodedColors("d53e4ff46d43fdae61fee08bffffbfe6f598abdda466c2a53288bd"            ) }
        private val spectral10  by lazy { EncodedColors("9e0142d53e4ff46d43fdae61fee08be6f598abdda466c2a53288bd5e4fa2"      ) }
        private val spectral11  by lazy { EncodedColors("9e0142d53e4ff46d43fdae61fee08bffffbfe6f598abdda466c2a53288bd5e4fa2") }
        val spectral       by lazy { listOf(spectral3, spectral4, spectral5, spectral6, spectral7, spectral8, spectral9, spectral10, spectral11) }

        // SEQUENTIAL-MULTI
        // TODO CUBEHELIX + RAINBOW
        val viridis by lazy { EncodedColors("44015444025645045745055946075a46085c460a5d460b5e470d60470e6147106347116447136548146748166848176948186a481a6c481b6d481c6e481d6f481f70482071482173482374482475482576482677482878482979472a7a472c7a472d7b472e7c472f7d46307e46327e46337f463480453581453781453882443983443a83443b84433d84433e85423f854240864241864142874144874045884046883f47883f48893e49893e4a893e4c8a3d4d8a3d4e8a3c4f8a3c508b3b518b3b528b3a538b3a548c39558c39568c38588c38598c375a8c375b8d365c8d365d8d355e8d355f8d34608d34618d33628d33638d32648e32658e31668e31678e31688e30698e306a8e2f6b8e2f6c8e2e6d8e2e6e8e2e6f8e2d708e2d718e2c718e2c728e2c738e2b748e2b758e2a768e2a778e2a788e29798e297a8e297b8e287c8e287d8e277e8e277f8e27808e26818e26828e26828e25838e25848e25858e24868e24878e23888e23898e238a8d228b8d228c8d228d8d218e8d218f8d21908d21918c20928c20928c20938c1f948c1f958b1f968b1f978b1f988b1f998a1f9a8a1e9b8a1e9c891e9d891f9e891f9f881fa0881fa1881fa1871fa28720a38620a48621a58521a68522a78522a88423a98324aa8325ab8225ac8226ad8127ad8128ae8029af7f2ab07f2cb17e2db27d2eb37c2fb47c31b57b32b67a34b67935b77937b87838b9773aba763bbb753dbc743fbc7340bd7242be7144bf7046c06f48c16e4ac16d4cc26c4ec36b50c46a52c56954c56856c66758c7655ac8645cc8635ec96260ca6063cb5f65cb5e67cc5c69cd5b6ccd5a6ece5870cf5773d05675d05477d1537ad1517cd2507fd34e81d34d84d44b86d54989d5488bd6468ed64590d74393d74195d84098d83e9bd93c9dd93ba0da39a2da37a5db36a8db34aadc32addc30b0dd2fb2dd2db5de2bb8de29bade28bddf26c0df25c2df23c5e021c8e020cae11fcde11dd0e11cd2e21bd5e21ad8e219dae319dde318dfe318e2e418e5e419e7e419eae51aece51befe51cf1e51df4e61ef6e620f8e621fbe723fde725") }
        val magma by lazy { EncodedColors("00000401000501010601010802010902020b02020d03030f03031204041405041606051806051a07061c08071e0907200a08220b09240c09260d0a290e0b2b100b2d110c2f120d31130d34140e36150e38160f3b180f3d19103f1a10421c10441d11471e114920114b21114e22115024125325125527125829115a2a115c2c115f2d11612f116331116533106734106936106b38106c390f6e3b0f703d0f713f0f72400f74420f75440f764510774710784910784a10794c117a4e117b4f127b51127c52137c54137d56147d57157e59157e5a167e5c167f5d177f5f187f601880621980641a80651a80671b80681c816a1c816b1d816d1d816e1e81701f81721f817320817521817621817822817922827b23827c23827e24828025828125818326818426818627818827818928818b29818c29818e2a81902a81912b81932b80942c80962c80982d80992d809b2e7f9c2e7f9e2f7fa02f7fa1307ea3307ea5317ea6317da8327daa337dab337cad347cae347bb0357bb2357bb3367ab5367ab73779b83779ba3878bc3978bd3977bf3a77c03a76c23b75c43c75c53c74c73d73c83e73ca3e72cc3f71cd4071cf4070d0416fd2426fd3436ed5446dd6456cd8456cd9466bdb476adc4869de4968df4a68e04c67e24d66e34e65e44f64e55064e75263e85362e95462ea5661eb5760ec5860ed5a5fee5b5eef5d5ef05f5ef1605df2625df2645cf3655cf4675cf4695cf56b5cf66c5cf66e5cf7705cf7725cf8745cf8765cf9785df9795df97b5dfa7d5efa7f5efa815ffb835ffb8560fb8761fc8961fc8a62fc8c63fc8e64fc9065fd9266fd9467fd9668fd9869fd9a6afd9b6bfe9d6cfe9f6dfea16efea36ffea571fea772fea973feaa74feac76feae77feb078feb27afeb47bfeb67cfeb77efeb97ffebb81febd82febf84fec185fec287fec488fec68afec88cfeca8dfecc8ffecd90fecf92fed194fed395fed597fed799fed89afdda9cfddc9efddea0fde0a1fde2a3fde3a5fde5a7fde7a9fde9aafdebacfcecaefceeb0fcf0b2fcf2b4fcf4b6fcf6b8fcf7b9fcf9bbfcfbbdfcfdbf") }
        val inferno by lazy { EncodedColors("00000401000501010601010802010a02020c02020e03021004031204031405041706041907051b08051d09061f0a07220b07240c08260d08290e092b10092d110a30120a32140b34150b37160b39180c3c190c3e1b0c411c0c431e0c451f0c48210c4a230c4c240c4f260c51280b53290b552b0b572d0b592f0a5b310a5c320a5e340a5f3609613809623909633b09643d09653e0966400a67420a68440a68450a69470b6a490b6a4a0c6b4c0c6b4d0d6c4f0d6c510e6c520e6d540f6d550f6d57106e59106e5a116e5c126e5d126e5f136e61136e62146e64156e65156e67166e69166e6a176e6c186e6d186e6f196e71196e721a6e741a6e751b6e771c6d781c6d7a1d6d7c1d6d7d1e6d7f1e6c801f6c82206c84206b85216b87216b88226a8a226a8c23698d23698f24699025689225689326679526679727669827669a28659b29649d29649f2a63a02a63a22b62a32c61a52c60a62d60a82e5fa92e5eab2f5ead305dae305cb0315bb1325ab3325ab43359b63458b73557b93556ba3655bc3754bd3853bf3952c03a51c13a50c33b4fc43c4ec63d4dc73e4cc83f4bca404acb4149cc4248ce4347cf4446d04545d24644d34743d44842d54a41d74b3fd84c3ed94d3dda4e3cdb503bdd513ade5238df5337e05536e15635e25734e35933e45a31e55c30e65d2fe75e2ee8602de9612bea632aeb6429eb6628ec6726ed6925ee6a24ef6c23ef6e21f06f20f1711ff1731df2741cf3761bf37819f47918f57b17f57d15f67e14f68013f78212f78410f8850ff8870ef8890cf98b0bf98c0af98e09fa9008fa9207fa9407fb9606fb9706fb9906fb9b06fb9d07fc9f07fca108fca309fca50afca60cfca80dfcaa0ffcac11fcae12fcb014fcb216fcb418fbb61afbb81dfbba1ffbbc21fbbe23fac026fac228fac42afac62df9c72ff9c932f9cb35f8cd37f8cf3af7d13df7d340f6d543f6d746f5d949f5db4cf4dd4ff4df53f4e156f3e35af3e55df2e661f2e865f2ea69f1ec6df1ed71f1ef75f1f179f2f27df2f482f3f586f3f68af4f88ef5f992f6fa96f8fb9af9fc9dfafda1fcffa4") }
        val plasma by lazy { EncodedColors("0d088710078813078916078a19068c1b068d1d068e20068f2206902406912605912805922a05932c05942e05952f059631059733059735049837049938049a3a049a3c049b3e049c3f049c41049d43039e44039e46039f48039f4903a04b03a14c02a14e02a25002a25102a35302a35502a45601a45801a45901a55b01a55c01a65e01a66001a66100a76300a76400a76600a76700a86900a86a00a86c00a86e00a86f00a87100a87201a87401a87501a87701a87801a87a02a87b02a87d03a87e03a88004a88104a78305a78405a78606a68707a68808a68a09a58b0aa58d0ba58e0ca48f0da4910ea3920fa39410a29511a19613a19814a099159f9a169f9c179e9d189d9e199da01a9ca11b9ba21d9aa31e9aa51f99a62098a72197a82296aa2395ab2494ac2694ad2793ae2892b02991b12a90b22b8fb32c8eb42e8db52f8cb6308bb7318ab83289ba3388bb3488bc3587bd3786be3885bf3984c03a83c13b82c23c81c33d80c43e7fc5407ec6417dc7427cc8437bc9447aca457acb4679cc4778cc4977cd4a76ce4b75cf4c74d04d73d14e72d24f71d35171d45270d5536fd5546ed6556dd7566cd8576bd9586ada5a6ada5b69db5c68dc5d67dd5e66de5f65de6164df6263e06363e16462e26561e26660e3685fe4695ee56a5de56b5de66c5ce76e5be76f5ae87059e97158e97257ea7457eb7556eb7655ec7754ed7953ed7a52ee7b51ef7c51ef7e50f07f4ff0804ef1814df1834cf2844bf3854bf3874af48849f48948f58b47f58c46f68d45f68f44f79044f79143f79342f89441f89540f9973ff9983ef99a3efa9b3dfa9c3cfa9e3bfb9f3afba139fba238fca338fca537fca636fca835fca934fdab33fdac33fdae32fdaf31fdb130fdb22ffdb42ffdb52efeb72dfeb82cfeba2cfebb2bfebd2afebe2afec029fdc229fdc328fdc527fdc627fdc827fdca26fdcb26fccd25fcce25fcd025fcd225fbd324fbd524fbd724fad824fada24f9dc24f9dd25f8df25f8e125f7e225f7e425f6e626f6e826f5e926f5eb27f4ed27f3ee27f3f027f2f227f1f426f1f525f0f724f0f921") }

        private val BuGN3   by lazy { EncodedColors("e5f5f999d8c92ca25f"                                                    ) }
        private val BuGN4   by lazy { EncodedColors("edf8fbb2e2e266c2a4238b45"                                              ) }
        private val BuGN5   by lazy { EncodedColors("edf8fbb2e2e266c2a42ca25f006d2c"                                        ) }
        private val BuGN6   by lazy { EncodedColors("edf8fbccece699d8c966c2a42ca25f006d2c"                                  ) }
        private val BuGN7   by lazy { EncodedColors("edf8fbccece699d8c966c2a441ae76238b45005824"                            ) }
        private val BuGN8   by lazy { EncodedColors("f7fcfde5f5f9ccece699d8c966c2a441ae76238b45005824"                      ) }
        private val BuGN9   by lazy { EncodedColors("f7fcfde5f5f9ccece699d8c966c2a441ae76238b45006d2c00441b"                ) }
        val BuGN       by lazy { listOf(BuGN3, BuGN4, BuGN5, BuGN6, BuGN7, BuGN8, BuGN9) }

        private val BuPu3   by lazy { EncodedColors("e0ecf49ebcda8856a7"                                                    ) }
        private val BuPu4   by lazy { EncodedColors("edf8fbb3cde38c96c688419d"                                              ) }
        private val BuPu5   by lazy { EncodedColors("edf8fbb3cde38c96c68856a7810f7c"                                        ) }
        private val BuPu6   by lazy { EncodedColors("edf8fbbfd3e69ebcda8c96c68856a7810f7c"                                  ) }
        private val BuPu7   by lazy { EncodedColors("edf8fbbfd3e69ebcda8c96c68c6bb188419d6e016b"                            ) }
        private val BuPu8   by lazy { EncodedColors("f7fcfde0ecf4bfd3e69ebcda8c96c68c6bb188419d6e016b"                      ) }
        private val BuPu9   by lazy { EncodedColors("f7fcfde0ecf4bfd3e69ebcda8c96c68c6bb188419d810f7c4d004b"                ) }
        val BuPu       by lazy { listOf(BuPu3, BuPu4, BuPu5, BuPu6, BuPu7, BuPu8, BuPu9) }

        private val GnBu3   by lazy { EncodedColors("e0f3dba8ddb543a2ca"                                                    ) }
        private val GnBu4   by lazy { EncodedColors("f0f9e8bae4bc7bccc42b8cbe"                                              ) }
        private val GnBu5   by lazy { EncodedColors("f0f9e8bae4bc7bccc443a2ca0868ac"                                        ) }
        private val GnBu6   by lazy { EncodedColors("f0f9e8ccebc5a8ddb57bccc443a2ca0868ac"                                  ) }
        private val GnBu7   by lazy { EncodedColors("f0f9e8ccebc5a8ddb57bccc44eb3d32b8cbe08589e"                            ) }
        private val GnBu8   by lazy { EncodedColors("f7fcf0e0f3dbccebc5a8ddb57bccc44eb3d32b8cbe08589e"                      ) }
        private val GnBu9   by lazy { EncodedColors("f7fcf0e0f3dbccebc5a8ddb57bccc44eb3d32b8cbe0868ac084081"                ) }
        val GnBu       by lazy { listOf(GnBu3, GnBu4, GnBu5, GnBu6, GnBu7, GnBu8, GnBu9) }

        private val OrRd3   by lazy { EncodedColors("fee8c8fdbb84e34a33"                                                    ) }
        private val OrRd4   by lazy { EncodedColors("fef0d9fdcc8afc8d59d7301f"                                              ) }
        private val OrRd5   by lazy { EncodedColors("fef0d9fdcc8afc8d59e34a33b30000"                                        ) }
        private val OrRd6   by lazy { EncodedColors("fef0d9fdd49efdbb84fc8d59e34a33b30000"                                  ) }
        private val OrRd7   by lazy { EncodedColors("fef0d9fdd49efdbb84fc8d59ef6548d7301f990000"                            ) }
        private val OrRd8   by lazy { EncodedColors("fff7ecfee8c8fdd49efdbb84fc8d59ef6548d7301f990000"                      ) }
        private val OrRd9   by lazy { EncodedColors("fff7ecfee8c8fdd49efdbb84fc8d59ef6548d7301fb300007f0000"                ) }
        val OrRd       by lazy { listOf(OrRd3, OrRd4, OrRd5, OrRd6, OrRd7, OrRd8, OrRd9) }

        private val PuBu3   by lazy { EncodedColors("ece7f2a6bddb2b8cbe"                                                    ) }
        private val PuBu4   by lazy { EncodedColors("f1eef6bdc9e174a9cf0570b0"                                              ) }
        private val PuBu5   by lazy { EncodedColors("f1eef6bdc9e174a9cf2b8cbe045a8d"                                        ) }
        private val PuBu6   by lazy { EncodedColors("f1eef6d0d1e6a6bddb74a9cf2b8cbe045a8d"                                  ) }
        private val PuBu7   by lazy { EncodedColors("f1eef6d0d1e6a6bddb74a9cf3690c00570b0034e7b"                            ) }
        private val PuBu8   by lazy { EncodedColors("fff7fbece7f2d0d1e6a6bddb74a9cf3690c00570b0034e7b"                      ) }
        private val PuBu9   by lazy { EncodedColors("fff7fbece7f2d0d1e6a6bddb74a9cf3690c00570b0045a8d023858"                ) }
        val PuBu       by lazy { listOf(PuBu3, PuBu4, PuBu5, PuBu6, PuBu7, PuBu8, PuBu9) }

        private val PuBuGn3   by lazy { EncodedColors("ece2f0a6bddb1c9099"                                                  ) }
        private val PuBuGn4   by lazy { EncodedColors("f6eff7bdc9e167a9cf02818a"                                            ) }
        private val PuBuGn5   by lazy { EncodedColors("f6eff7bdc9e167a9cf1c9099016c59"                                      ) }
        private val PuBuGn6   by lazy { EncodedColors("f6eff7d0d1e6a6bddb67a9cf1c9099016c59"                                ) }
        private val PuBuGn7   by lazy { EncodedColors("f6eff7d0d1e6a6bddb67a9cf3690c002818a016450"                          ) }
        private val PuBuGn8   by lazy { EncodedColors("fff7fbece2f0d0d1e6a6bddb67a9cf3690c002818a016450"                    ) }
        private val PuBuGn9   by lazy { EncodedColors("fff7fbece2f0d0d1e6a6bddb67a9cf3690c002818a016c59014636"              ) }
        val PuBuGn       by lazy { listOf(PuBuGn3, PuBuGn4, PuBuGn5, PuBuGn6, PuBuGn7, PuBuGn8, PuBuGn9) }

        private val PuRd3   by lazy { EncodedColors("e7e1efc994c7dd1c77"                                                    ) }
        private val PuRd4   by lazy { EncodedColors("f1eef6d7b5d8df65b0ce1256"                                              ) }
        private val PuRd5   by lazy { EncodedColors("f1eef6d7b5d8df65b0dd1c77980043"                                        ) }
        private val PuRd6   by lazy { EncodedColors("f1eef6d4b9dac994c7df65b0dd1c77980043"                                  ) }
        private val PuRd7   by lazy { EncodedColors("f1eef6d4b9dac994c7df65b0e7298ace125691003f"                            ) }
        private val PuRd8   by lazy { EncodedColors("f7f4f9e7e1efd4b9dac994c7df65b0e7298ace125691003f"                      ) }
        private val PuRd9   by lazy { EncodedColors("f7f4f9e7e1efd4b9dac994c7df65b0e7298ace125698004367001f"                ) }
        val PuRd       by lazy { listOf(PuRd3, PuRd4, PuRd5, PuRd6, PuRd7, PuRd8, PuRd9) }

        private val RdPu3   by lazy { EncodedColors("fde0ddfa9fb5c51b8a"                                                    ) }
        private val RdPu4   by lazy { EncodedColors("feebe2fbb4b9f768a1ae017e"                                              ) }
        private val RdPu5   by lazy { EncodedColors("feebe2fbb4b9f768a1c51b8a7a0177"                                        ) }
        private val RdPu6   by lazy { EncodedColors("feebe2fcc5c0fa9fb5f768a1c51b8a7a0177"                                  ) }
        private val RdPu7   by lazy { EncodedColors("feebe2fcc5c0fa9fb5f768a1dd3497ae017e7a0177"                            ) }
        private val RdPu8   by lazy { EncodedColors("fff7f3fde0ddfcc5c0fa9fb5f768a1dd3497ae017e7a0177"                      ) }
        private val RdPu9   by lazy { EncodedColors("fff7f3fde0ddfcc5c0fa9fb5f768a1dd3497ae017e7a017749006a"                ) }
        val RdPu       by lazy { listOf(RdPu3, RdPu4, RdPu5, RdPu6, RdPu7, RdPu8, RdPu9) }

        private val YlGn3   by lazy { EncodedColors("f7fcb9addd8e31a354"                                                    ) }
        private val YlGn4   by lazy { EncodedColors("ffffccc2e69978c679238443"                                              ) }
        private val YlGn5   by lazy { EncodedColors("ffffccc2e69978c67931a354006837"                                        ) }
        private val YlGn6   by lazy { EncodedColors("ffffccd9f0a3addd8e78c67931a354006837"                                  ) }
        private val YlGn7   by lazy { EncodedColors("ffffccd9f0a3addd8e78c67941ab5d238443005a32"                            ) }
        private val YlGn8   by lazy { EncodedColors("ffffe5f7fcb9d9f0a3addd8e78c67941ab5d238443005a32"                      ) }
        private val YlGn9   by lazy { EncodedColors("ffffe5f7fcb9d9f0a3addd8e78c67941ab5d238443006837004529"                ) }
        val YlGn       by lazy { listOf(YlGn3, YlGn4, YlGn5, YlGn6, YlGn7, YlGn8, YlGn9) }

        private val YlGnbU3   by lazy { EncodedColors("edf8b17fcdbb2c7fb8"                                                  ) }
        private val YlGnbU4   by lazy { EncodedColors("ffffcca1dab441b6c4225ea8"                                            ) }
        private val YlGnbU5   by lazy { EncodedColors("ffffcca1dab441b6c42c7fb8253494"                                      ) }
        private val YlGnbU6   by lazy { EncodedColors("ffffccc7e9b47fcdbb41b6c42c7fb8253494"                                ) }
        private val YlGnbU7   by lazy { EncodedColors("ffffccc7e9b47fcdbb41b6c41d91c0225ea80c2c84"                          ) }
        private val YlGnbU8   by lazy { EncodedColors("ffffd9edf8b1c7e9b47fcdbb41b6c41d91c0225ea80c2c84"                    ) }
        private val YlGnbU9   by lazy { EncodedColors("ffffd9edf8b1c7e9b47fcdbb41b6c41d91c0225ea8253494081d58"              ) }
        val YlGnbU       by lazy { listOf(YlGnbU3, YlGnbU4, YlGnbU5, YlGnbU6, YlGnbU7, YlGnbU8, YlGnbU9) }

        private val YlGnBr3   by lazy { EncodedColors("fff7bcfec44fd95f0e"                                                  ) }
        private val YlGnBr4   by lazy { EncodedColors("ffffd4fed98efe9929cc4c02"                                            ) }
        private val YlGnBr5   by lazy { EncodedColors("ffffd4fed98efe9929d95f0e993404"                                      ) }
        private val YlGnBr6   by lazy { EncodedColors("ffffd4fee391fec44ffe9929d95f0e993404"                                ) }
        private val YlGnBr7   by lazy { EncodedColors("ffffd4fee391fec44ffe9929ec7014cc4c028c2d04"                          ) }
        private val YlGnBr8   by lazy { EncodedColors("ffffe5fff7bcfee391fec44ffe9929ec7014cc4c028c2d04"                    ) }
        private val YlGnBr9   by lazy { EncodedColors("ffffe5fff7bcfee391fec44ffe9929ec7014cc4c02993404662506"              ) }
        val YlGnBr       by lazy { listOf(YlGnBr3, YlGnBr4, YlGnBr5, YlGnBr6, YlGnBr7, YlGnBr8, YlGnBr9) }

        private val YlGnRd3   by lazy { EncodedColors("ffeda0feb24cf03b20"                                                  ) }
        private val YlGnRd4   by lazy { EncodedColors("ffffb2fecc5cfd8d3ce31a1c"                                            ) }
        private val YlGnRd5   by lazy { EncodedColors("ffffb2fecc5cfd8d3cf03b20bd0026"                                      ) }
        private val YlGnRd6   by lazy { EncodedColors("ffffb2fed976feb24cfd8d3cf03b20bd0026"                                ) }
        private val YlGnRd7   by lazy { EncodedColors("ffffb2fed976feb24cfd8d3cfc4e2ae31a1cb10026"                          ) }
        private val YlGnRd8   by lazy { EncodedColors("ffffccffeda0fed976feb24cfd8d3cfc4e2ae31a1cb10026"                    ) }
        private val YlGnRd9   by lazy { EncodedColors("ffffccffeda0fed976feb24cfd8d3cfc4e2ae31a1cbd0026800026"              ) }
        val YlGnRd       by lazy { listOf(YlGnRd3, YlGnRd4, YlGnRd5, YlGnRd6, YlGnRd7, YlGnRd8, YlGnRd9) }

        
        // SEQUENTIAL SINGLE
        private val blues3   by lazy { EncodedColors("deebf79ecae13182bd"                                                   ) }
        private val blues4   by lazy { EncodedColors("eff3ffbdd7e76baed62171b5"                                             ) }
        private val blues5   by lazy { EncodedColors("eff3ffbdd7e76baed63182bd08519c"                                       ) }
        private val blues6   by lazy { EncodedColors("eff3ffc6dbef9ecae16baed63182bd08519c"                                 ) }
        private val blues7   by lazy { EncodedColors("eff3ffc6dbef9ecae16baed64292c62171b5084594"                           ) }
        private val blues8   by lazy { EncodedColors("f7fbffdeebf7c6dbef9ecae16baed64292c62171b5084594"                     ) }
        private val blues9   by lazy { EncodedColors("f7fbffdeebf7c6dbef9ecae16baed64292c62171b508519c08306b"               ) }
        val blues       by lazy { listOf(blues3, blues4, blues5, blues6, blues7, blues8, blues9) }

        private val greens3   by lazy { EncodedColors("e5f5e0a1d99b31a354"                                                  ) }
        private val greens4   by lazy { EncodedColors("edf8e9bae4b374c476238b45"                                            ) }
        private val greens5   by lazy { EncodedColors("edf8e9bae4b374c47631a354006d2c"                                      ) }
        private val greens6   by lazy { EncodedColors("edf8e9c7e9c0a1d99b74c47631a354006d2c"                                ) }
        private val greens7   by lazy { EncodedColors("edf8e9c7e9c0a1d99b74c47641ab5d238b45005a32"                          ) }
        private val greens8   by lazy { EncodedColors("f7fcf5e5f5e0c7e9c0a1d99b74c47641ab5d238b45005a32"                    ) }
        private val greens9   by lazy { EncodedColors("f7fcf5e5f5e0c7e9c0a1d99b74c47641ab5d238b45006d2c00441b"              ) }
        val greens       by lazy { listOf(greens3, greens4, greens5, greens6, greens7, greens8, greens9) }

        private val greys3   by lazy { EncodedColors("f0f0f0bdbdbd636363"                                                   ) }
        private val greys4   by lazy { EncodedColors("f7f7f7cccccc969696525252"                                             ) }
        private val greys5   by lazy { EncodedColors("f7f7f7cccccc969696636363252525"                                       ) }
        private val greys6   by lazy { EncodedColors("f7f7f7d9d9d9bdbdbd969696636363252525"                                 ) }
        private val greys7   by lazy { EncodedColors("f7f7f7d9d9d9bdbdbd969696737373525252252525"                           ) }
        private val greys8   by lazy { EncodedColors("fffffff0f0f0d9d9d9bdbdbd969696737373525252252525"                     ) }
        private val greys9   by lazy { EncodedColors("fffffff0f0f0d9d9d9bdbdbd969696737373525252252525000000"               ) }
        val greys       by lazy { listOf(greys3, greys4, greys5, greys6, greys7, greys8, greys9) }

        private val oranges3   by lazy { EncodedColors("fee6cefdae6be6550d"                                                 ) }
        private val oranges4   by lazy { EncodedColors("feeddefdbe85fd8d3cd94701"                                           ) }
        private val oranges5   by lazy { EncodedColors("feeddefdbe85fd8d3ce6550da63603"                                     ) }
        private val oranges6   by lazy { EncodedColors("feeddefdd0a2fdae6bfd8d3ce6550da63603"                               ) }
        private val oranges7   by lazy { EncodedColors("feeddefdd0a2fdae6bfd8d3cf16913d948018c2d04"                         ) }
        private val oranges8   by lazy { EncodedColors("fff5ebfee6cefdd0a2fdae6bfd8d3cf16913d948018c2d04"                   ) }
        private val oranges9   by lazy { EncodedColors("fff5ebfee6cefdd0a2fdae6bfd8d3cf16913d94801a636037f2704"             ) }
        val oranges       by lazy { listOf(oranges3, oranges4, oranges5, oranges6, oranges7, oranges8, oranges9) }

        private val purples3   by lazy { EncodedColors("efedf5bcbddc756bb1"                                                 ) }
        private val purples4   by lazy { EncodedColors("f2f0f7cbc9e29e9ac86a51a3"                                           ) }
        private val purples5   by lazy { EncodedColors("f2f0f7cbc9e29e9ac8756bb154278f"                                     ) }
        private val purples6   by lazy { EncodedColors("f2f0f7dadaebbcbddc9e9ac8756bb154278f"                               ) }
        private val purples7   by lazy { EncodedColors("f2f0f7dadaebbcbddc9e9ac8807dba6a51a34a1486"                         ) }
        private val purples8   by lazy { EncodedColors("fcfbfdefedf5dadaebbcbddc9e9ac8807dba6a51a34a1486"                   ) }
        private val purples9   by lazy { EncodedColors("fcfbfdefedf5dadaebbcbddc9e9ac8807dba6a51a354278f3f007d"             ) }
        val purples       by lazy { listOf(purples3, purples4, purples5, purples6, purples7, purples8, purples9) }

        private val reds3   by lazy { EncodedColors("fee0d2fc9272de2d26"                                                    ) }
        private val reds4   by lazy { EncodedColors("fee5d9fcae91fb6a4acb181d"                                              ) }
        private val reds5   by lazy { EncodedColors("fee5d9fcae91fb6a4ade2d26a50f15"                                        ) }
        private val reds6   by lazy { EncodedColors("fee5d9fcbba1fc9272fb6a4ade2d26a50f15"                                  ) }
        private val reds7   by lazy { EncodedColors("fee5d9fcbba1fc9272fb6a4aef3b2ccb181d99000d"                            ) }
        private val reds8   by lazy { EncodedColors("fff5f0fee0d2fcbba1fc9272fb6a4aef3b2ccb181d99000d"                      ) }
        private val reds9   by lazy { EncodedColors("fff5f0fee0d2fcbba1fc9272fb6a4aef3b2ccb181da50f1567000d"                ) }
        val reds       by lazy { listOf(reds3, reds4, reds5, reds6, reds7, reds8, reds9) }
    }

    init {
        require(colorsAsString.length % 6 == 0) { "colorsAsString size should be a multiple of 6" }
    }

    val colors by lazy {
        with(colorsAsString) {
            (0..((this.length / 6) - 1)).map { "#${substring(6 * it, 6 * it + 6)}".col }
        }
    }

    /**
     * Returns the color corresponding at the given percentage of the gradient
     */
    fun color(percent: Double) = colors[floor(percent * colors.size).toInt()
            .coerceAtLeast(0)
            .coerceAtMost(colors.size - 1)]
}
