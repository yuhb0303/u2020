u2020
=====

A sample Android app which showcases advanced usage of Dagger among other open source libraries.

[Watch the corresponding talk][parleys] or [view the slides][slides].

The `ObjectGraph` is created in the `U2020App`'s `onCreate` method. The `Modules` class provides a
single method, `list`, which returns the list of module instances to use.

In order to add functionality in the 'debug' version of the app, this class is only present in the
`release/` and `debug/` flavor folders. The 'release' version only includes the `U2020Module` while
the 'debug' version includes both `U2020Module` and `DebugU2020Module`, the latter of which is only
present in the `debug/` flavor folder and is an override module.

Through the use of Dagger overrides, the 'debug' version of the app adds a slew of debugging
features to the app which are presented in the Debug Drawer™. The drawer is opened by a bezel
swipe from the right of the screen. From here you can change and view all of the developer options
of the application.

The most notable feature the 'debug' version exposes is the concept of endpoints. Using the spinner
at the top of the drawer, you can change the endpoint to which the app communicates. We also expose
a false endpoint named "Mock Mode" which simulates an in-memory server inside the app. This "Mock
Mode" eases manual testing and also provides a static set of data to write instrumentation tests
against.

"Mock Mode" can be queried when modules are configuring their dependencies which is what allows
simulating the remote server in-memory.
```java
@Provides @Singleton Foo provideFoo(@IsMockMode boolean isMockMode) {
  return isMockMode ? return new MockFoo() : new RealFoo();
}
```
See `DebugDataModule` and `DebugApiModule` to see this in action in the real app.

![Debug drawer](u2020.gif)



Libraries
---------

 * Dagger - http://square.github.io/dagger
 * ButterKnife - http://jakewharton.github.io/butterknife
 * Retrofit - http://square.github.io/retrofit
 * Picasso - http://square.github.io/picasso
 * OkHttp - http://square.github.io/okhttp
 * RxJava - http://github.com/Netflix/RxJava
 * Timber - http://github.com/JakeWharton/timber
 * Madge - http://github.com/JakeWharton/madge
 * Scalpel - http://github.com/JakeWharton/scalpel
 * StaggeredGrid - https://github.com/etsy/AndroidStaggeredGrid



To Do
-----

 * Something with animations to showcase animation control.
 * Network errors probably crash the app.
 * Pressed state no longer works thanks to Etsy.
 * Toss column count into dimen and scale appropriately.
 * Controls to change which gallery we're looking at?



License
-------

    Copyright 2014 Jake Wharton

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [parleys]: http://parleys.com/play/529bde2ce4b0e619540cc3ae
 [slides]: https://speakerdeck.com/jakewharton/android-apps-with-dagger
