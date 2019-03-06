# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

include_directories(src/main/cpp/include)
link_directories(src/main/cpp/libs)

add_library( utility

             SHARED

             src/main/cpp/Utility.cpp )



find_library( log-lib

              log )


target_link_libraries( utility

                       Elastos.ORG.Wallet.Lib.C )

