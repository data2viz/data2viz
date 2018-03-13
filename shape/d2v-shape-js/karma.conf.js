module.exports = function (config) {
    config.set({
            frameworks: ['mocha', 'browserify'],
            reporters: ['mocha'],
            files: [
                'build/classes/kotlin/test/*.js'
            ],
            exclude: [],
            colors: true,
            autoWatch: false,
            browsers: [
                // 'Chrome'
                'ChromeHeadless'
            ],
            captureTimeout: 10000,
            singleRun: true,
            // singleRun: false,
            reportSlowerThan: 500,

            preprocessors: {
                'build/classes/kotlin/test/*.js': ['browserify']
            }
        }
    )
};
