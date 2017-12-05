module.exports = function (config) {
    config.set({
            frameworks: ['qunit', 'browserify'],
            reporters: ['mocha'],
            files: [
                'build/classes/main/*.js',
                'build/classes/test/*.js',
                'build/node_modules/*.js'
            ],
            exclude: [],
            // port: 9876,
            // runnerPort: 9100,
            colors: true,
            autoWatch: false,
            browsers: [
                'PhantomJS'
                // , 'Chrome'
            ],
            captureTimeout: 5000,
            // singleRun: false,
            singleRun: true,
            reportSlowerThan: 500,

            preprocessors: {
                '**/*.js': ['browserify']
            }
        }
    )
};
