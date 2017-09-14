module.exports = function (config) {
    config.set({
            frameworks: ['mocha', 'commonjs'],
            reporters: ['mocha'],
            files: [
                'build/classes/main/*.js',
                // 'build/classes/main/*.js.map',
                'build/classes/test/*.js',
                'build/node_modules/*.js',
                // 'build/node_modules/*.js.map'
            ],
            exclude: [],
            colors: true,
            autoWatch: false,
            browsers: [
                'PhantomJS',
                'Chrome'
            ],
            captureTimeout: 5000,
            singleRun: true,
            // singleRun: false,
            retryLimit: 0,

            reportSlowerThan: 500,

            preprocessors: {
                '**/*.js': ['commonjs']
            }
        }
    )
};
