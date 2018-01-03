# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# 创建并命名一个库，将其设置为STATIC
# 或SHARED，并提供相对路径来源代码。
# 你可以定义多个库，CMake为你构建它们。
# Gradle会自动将您的APK与共享库打包。

add_library( # Sets the name of the library.
             # 设置库的名称。
             native-lib

             # Sets the library as a shared library.
             # 将库设置为共享库。
             SHARED

             # Provides a relative path to your source file(s).
             # 提供源文件的相对路径。
             # 只需要在这添加源文件依赖，不需要添加多个库名称
             src/main/cpp/native-lib.cpp
             src/main/cpp/myJniLib.cpp
             src/main/cpp/hexJniLib.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
# 搜索指定的预建库，并将路径存储为
# 变量。 因为CMake在搜索路径中包含系统库
# 默认情况下，您只需要指定公共NDK库的名称
# 你想添加。 CMake验证之前存在的库
# 完成它的构建。

find_library( # Sets the name of the path variable.
              # 设置路径变量的名称。
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              # 指定NDK库的名称
              # 你想CMake找到。
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
# 指定库CMake应链接到您的目标库。 您
# 可以链接多个库，比如你在这个库中定义的库
# 构建脚本，预建的第三方库或系统库。
target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )