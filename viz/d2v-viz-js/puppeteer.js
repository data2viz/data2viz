const puppeteer = require('puppeteer');

var fs = require('fs');
var dir = './build/images';

if (!fs.existsSync(dir)){
    fs.mkdirSync(dir);
}

(async () => {
    const browser = await puppeteer.launch();
    const page = await browser.newPage();
    page.setViewport({ width: 1280, height: 926 });
    const url = "file://" + process.cwd() + "/" + "index.html";
    await page.goto(url);

    //added after all renderings
    await page.waitForSelector('span');

    const canvas = await page.$$('canvas');
    for (let i = 0; i < canvas.length; i++) {
        let canvaHandle = canvas[i];
        let name = await (await canvaHandle.getProperty("id")).jsonValue();
        await canvaHandle.screenshot({
            path: 'build/images/' + name + '-js.png',
            omitBackground: true,
        });    
    } 

    await browser.close();
})();