var fs = require('fs'),
    PNG = require('pngjs').PNG,
    pixelmatch = require('pixelmatch');


var referenceFolder = "../d2v-viz-common/src/test/resources/";
var output = 'build/images/';
var suffix = 'jfx';

if (!fs.existsSync(output)) {
    fs.mkdirSync(output);
}

var testFileCount = 0;
var fiteTested = 0;
var max_diff_pixel = 0;

fs.readdirSync(referenceFolder).forEach(file => {
    var suffix_and_extension = '-js.png';
    var referenceFileName = file.replace(/^.*[\\\/]/, '');


    if (referenceFileName.endsWith(suffix_and_extension)) {
        testFileCount++;

        let name = file.substring(0, referenceFileName.length - suffix_and_extension.length);

        var png_ref = fs.readFileSync(referenceFolder + name + '-js.png');
        var img_ref = PNG.sync.read(png_ref);

        var png_tested = fs.readFileSync(output + name + '-' + suffix + '.png');
        var img_tested = PNG.sync.read(png_tested);

        var diff_js = new PNG({width: img_ref.width, height: img_ref.height});
        var numDiffPixels_js = pixelmatch(img_ref.data, img_tested.data, diff_js.data, img_ref.width, img_ref.height, {threshold: 0.1});
        console.log("Diffs pixels ref/" + suffix + " for " + name + "::" + numDiffPixels_js);
        if (numDiffPixels_js > max_diff_pixel) {
            max_diff_pixel = numDiffPixels_js;
        }
        let writeStream = fs.createWriteStream('build/images/' + name + '-diff-ref-' + suffix + '.png');
        writeStream.on('finish', () => {
            fiteTested++;
            checkPixelErrors();
        });
        diff_js.pack().pipe(writeStream);
    }


});

function checkPixelErrors() {
    if (fiteTested < testFileCount)
        return;
    if (max_diff_pixel > 100) {
        throw new Error('To much pixel diff');
    }
}