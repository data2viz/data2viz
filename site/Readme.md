This project is used to push data2viz js examples in github-pages.

Usage:

```
./gradlew copyJsExamples
cd site/build/gh-pages
git init
git checkout -b gh-pages
git remote add origin git@github.com:data2viz/data2viz.git
git add -A
git commit -m "update examples"
git push origin gh-pages
```