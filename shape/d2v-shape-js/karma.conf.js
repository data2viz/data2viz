module.exports = function (config) {
    config.set({
            frameworks: ['qunit', 'browserify'],
            reporters: ['mocha'],
            files: [
                'build/classes/kotlin/test/*.js',
                'build/node_modules/*.js'
            ],
            exclude: [],
            colors: true,
            autoWatch: false,
            browsers: [
                , 'Chrome'
            ],
            captureTimeout: 10000,
            // singleRun: true,
        singleRun: false,
            reportSlowerThan: 500,

            preprocessors: {
                '**/*.js': ['browserify']
            }
        }
    )
};
