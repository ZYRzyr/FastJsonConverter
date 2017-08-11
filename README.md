# FastJsonConverter
FastJsonConverter is a converter when you use [Retrofit](https://github.com/square/retrofit) and [fastjson](https://github.com/alibaba/fastjson).
## Dependency
######Gradle:
Add this in your root `build.gradle` file (**not** your module `build.gradle` file):
```gradle
allprojects {
	repositories {
	        ...
        maven { url "https://jitpack.io" }
    }
}
```
Then, add the library to your module `build.gradle`:
```gradle
dependencies {
    compile 'com.github.ZYRzyr:FastJsonConverter:v1.0-beta'
}
```
######Maven:
Add the JitPack repository to your build file:
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add the dependency:
```
<dependency>
    <groupId>com.github.ZYRzyr</groupId>
    <artifactId>FastJsonConverter</artifactId>
    <version>v1.0-beta</version>
</dependency>
```
## Usage
Supply an instance of this converter when building your `Retrofit` instance:
```
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com")
    .addConverterFactory(FastJsonConverterFactory.create())
    .build();
```
That's it!
## Issues
The current version has not been fully tested and should be used with caution!<br>
If you find a problem or have some better advice,please pull a [issue](https://github.com/ZYRzyr/FastJsonConverter/issues) to help me to fix it!

### *License*

FastJsonConverter is released under the [Apache 2.0 license](LICENSE).

```
Copyright 2017 Yuran Zhang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at following link.

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
