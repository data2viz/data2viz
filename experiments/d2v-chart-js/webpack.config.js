var webpack = require("webpack");
var path = require("path");
var outputMin = "build/classes/test/min/";

module.exports = {
    entry: path.resolve(__dirname, outputMin + "d2v-chart-js_test.js"),
    output: {
        path: path.resolve(__dirname, "build"),
        filename: "bundle.js",
        library: 'EntryPoint'
    },
    resolve: {
        alias: {
            'd2v-axis-js':          path.resolve(__dirname, outputMin + "d2v-axis-js.js"),
            'd2v-color-js':          path.resolve(__dirname, outputMin + "d2v-color-js.js"),
            'd2v-core-js':          path.resolve(__dirname, outputMin + "d2v-core-js.js"),
            'd2v-interpolate-js':          path.resolve(__dirname, outputMin + "d2v-interpolate-js.js"),
            'd2v-svg-js':          path.resolve(__dirname, outputMin + "d2v-svg-js.js"),
            'd2v-tests-js':          path.resolve(__dirname, outputMin + "d2v-tests-js.js"),
            'kotlin':           path.resolve(__dirname, outputMin + "kotlin.js"),
        }
    },
    plugins: [
        new webpack.optimize.UglifyJsPlugin()
    ]
};
