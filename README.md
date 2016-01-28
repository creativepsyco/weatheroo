# weatheroo
Weather Application

[![Build Status](https://travis-ci.org/creativepsyco/weatheroo.svg?branch=master)](https://travis-ci.org/creativepsyco/weatheroo)

A sample weather application that showcases use of some new libraries:
 * Flow
 * Mortar
 * Google Places Picker
 * Dagger 2

# Requirements
* You need to provide your own Google Places API Key in [build.gradle](./app/build.gradle). Generate [here](https://developers.google.com/places/android-api/signup)
* Also need to provide your own Wunderground API Key. Get it from [Wunderground](www.wunderground.com/weather/api/)

Check [build.gradle](./app/build.gradle) on how to add the keys. You can either export it via your local `${USER/.gradle/gradle.properties` file or supply it via export API_KEY=??

# License
[Apache](./LICENSE)
