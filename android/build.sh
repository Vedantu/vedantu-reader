#!/bin/bash

#ndk-build && ant.bat install

T_SCRIPT_DIR=`readlink -m $(dirname $0)`
T_MUPDF_PROPERTIES="mupdf.properties"
T_MUPDF_LIBS="libs_mupdf"

. $T_MUPDF_PROPERTIES

VERSION_CODE=`grep "versionCode=" AndroidManifest.xml | xargs | awk -F\= '{print $2}'`
echo "version code $VERSION_CODE"

ORIG_MANIFEST_FILE="AndroidManifest_origional.xml"

T_TYPE=release;

cp AndroidManifest.xml $ORIG_MANIFEST_FILE

{
  APP_APK_RELEASE_DATE="`date +%Y%m%d-%H%M`"
  APP_APK_NAME="`grep "project\ name=" $T_SCRIPT_DIR/build.xml | sed 's#\(.*\)name="\([^"]*\)"\(.*\)#\2#g'`"
  echo "app apk name $APP_APK_NAME"
 
  C_VERSION_CODE="$VERSION_CODE"
  LIBS_PATH=$T_SCRIPT_DIR/libs
  APKS_DIR_PATH=$T_SCRIPT_DIR/apks
   
  for i in ${!MUPDF_BUILD_TYPES[@]}
  do
    BUILD_TYPE=${MUPDF_BUILD_TYPES[$i]}
    P_VERSION_CODE=$C_VERSION_CODE
    C_VERSION_CODE=$((VERSION_CODE+i))
    echo "=== $i $BUILD_TYPE === versionCode: $C_VERSION_CODE "
    sed -i -e 's/versionCode="'$C_VERSION_CODE'"/versionCode="'$C_VERSION_CODE'"/g' "AndroidManifest.xml"
  
    ant clean
    echo "cleaning libs folder"
    
    for LIB_BUILD_TYPE in ${MUPDF_BUILD_TYPES[@]}
    do
        echo " == trying to remove dir $LIB_BUILD_TYPE == "
        rm -r $LIBS_PATH/$LIB_BUILD_TYPE
    done 
  
    echo "copying libmupdf.so file for $BUILD_TYPE to libs"
    LIBMUPDF_BUILD_TYPE_DIR=$T_MUPDF_LIBS/$BUILD_TYPE
    if  [ ! -d "$LIBMUPDF_BUILD_TYPE_DIR" ]
    then
      echo "mupdf library not compiled for build type: $BUILD_TYPE"
      exit 1;
    fi

    cp -r $LIBMUPDF_BUILD_TYPE_DIR $LIBS_PATH 
  
    #android update project -p .  
    ant $T_TYPE
    T_FINAL_APK="bin/$APP_APK_NAME-$T_TYPE.apk"; 
    mkdir -p "$APKS_DIR_PATH" 
    T_FINAL_APK_COPY="$APKS_DIR_PATH/$APP_APK_NAME-$T_TYPE-$APP_APK_RELEASE_DATE-$BUILD_TYPE-$C_VERSION_CODE.apk" 
    echo "copying [$T_FINAL_APK] to [$T_FINAL_APK_COPY]";
    cp "$T_FINAL_APK" "$T_FINAL_APK_COPY";
  done
} || {
 echo "Stopping build operation"
}
mv $ORIG_MANIFEST_FILE AndroidManifest.xml

