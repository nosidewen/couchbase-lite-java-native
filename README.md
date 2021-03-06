# couchbase-lite-java-native #


This is a forked version of [couchbase-lite-java-native](https://github.com/couchbase/couchbase-lite-java-native) with modifications made off of the `release/1.4.1` branch in order to add Linux ARM native binaries to the native JAR. For this project, this build includes ARM binaries sepcifically for the Cortex-A7.

The original library provided all of the scripts and source code necessary to build the shared libraries for Windows, MacOS and Linux x86, x86_64 and amd64, and all of these libraries are packaged into a jar. The changes made to this repository are such that it comments out the building and packaging of these libraries and instead packages pre-built libraries for Linux ARM, Cortex A7, iMX6ULL processor. Specifically there are two native libraries that need to be prebuilt: 
* `vendor/sqlite/libs/linux/arm/libsqlite3.so`
* `vendor/customcblsqlite/libcbljavasqlitecustom.so` 

To build this java library to work with other ARM processors, build the above shared libraries for your target machine, then follow the build steps for [couchbase-lite-java](https://github.com/nosidewen/couchbase-lite-java).

## Building Native Binaries
For this development, yocto was used to build the required libraries for the target machine. Notes for configuring yocto to build these libraries are included below.

### libsqlite3
This is a commonly used library in the community, so its recipe was already included in yocto/bitbake. Only needed to run `bitbake sqlite3` to build the library and then copied this output into the `couchbase-lite-java-native` repo. This library is also a dependency of the library below.

### libcbljavasqlitecustom
Based on the gradle build scripts, the source code for this library is in `couchbase-lite-java-native/jni/`. In order to build this library with yocto for our target machine, all of this source code (and headers) were copied to a new bitbake recipe. Fore reference, the recipe file can be found at `couchbase-lite-java-native/vendor/customcblsqlite/cblcustom_1.0.0.bb` and the makefile is at `couchbase-lite-java-native/vendor/customcblsqlite/Makefile`.

## Publishing Java Package
In the `sqlite-custom` directory, the `couchbase-lite-java-sqlite-custom.jar` is the output of the couchbase-lite-java build process. When adding support for different architectures, it is useful to publish a new package so it can be easily used by others. Github Packages is used to host the maven repository. The steps to publish a new package are as follows:
```
Set System Environment variables
 1. GITHUB_ACTOR - github username
 2. GITHUB_TOKEN - github personal access token with permission to write package
 
Edit sqlite-custom/build-java.gradle in the following way:
 1. Update the artifact name to include name of processor/architecture in line 322
 2. Update the desired version number in line 323
 3. Uncomment the finalizedBy() line at line 329
 4. From the couchbase-lite-java repo, follow the build steps to run the gradle clean and test tasks 
 5. Then from the same directory, run `./gradlew build`
```

## SQLCipher
The current project does not require use of sqlcipher, so all references and artifacts related to this are deleted or ignored. In the future, if this becomes a requirement, a similar process will be required to build OpenSSL and the custom cbl sqlcipher library.

