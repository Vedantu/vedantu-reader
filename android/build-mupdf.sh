T_SCRIPT_DIR=`readlink -m $(dirname $0)`
T_MUPDF_PROPERTIES="mupdf.properties"
T_OUT_LIBS_MUPDF="$T_SCRIPT_DIR/libs_mupdf"

function __exit_on_error__ {
  _exitCode="$?";
  if [ "$_exitCode" -ne "0" ]
  then
    echo "Some error occurred. exitCode=$_exitCode";
    exit $_exitCode;
  fi;
}

echo $T_OUT_LIBS_MUPDF


. "$T_MUPDF_PROPERTIES"

echo " == mupdf path: $MUPDF_PATH == "

cd $MUPDF_PATH
__exit_on_error__;

echo " == git checkout $MUPDF_TAG == "

git checkout $MUPDF_TAG
__exit_on_error__;

echo " == git checkout submodules == "
git submodule update --init
__exit_on_error__;

make clean
__exit_on_error__;
make generate
__exit_on_error__;

cd -;
__exit_on_error__;

ORIGINAL_MAKE_FILE=$MUPDF_ANDROID_DIR/jni/Application_original.mk

echo "== creating local backup of origional Application.mk == "
mv $MUPDF_ANDROID_DIR/jni/Application.mk $ORIGINAL_MAKE_FILE
__exit_on_error__;

for BUILD_TYPE in ${MUPDF_BUILD_TYPES[@]}
do
    echo "============ building package  $BUILD_TYPE =================== "
    cd  $MUPDF_ANDROID_DIR
    SDK_VERSION="android-9"
    
    if [[ $BUILD_TYPE == *armeabi* ]]
    then
      SDK_VERSION="android-8"
    fi;    
    echo "sdk version $SDK_VERSION "
    cp $T_SCRIPT_DIR/Application_build.mk $MUPDF_ANDROID_DIR/jni/Application.mk
    __exit_on_error__;
    sed -i -e 's/\[SDK_VERSION\]/'$SDK_VERSION'/g' \
        -e 's/\[BUILD_TYPE\]/'$BUILD_TYPE'/g' \
           "$MUPDF_ANDROID_DIR/jni/Application.mk"
    __exit_on_error__;
    ndk-build clean
    __exit_on_error__;
    ndk-build     
    __exit_on_error__;
    echo "copying libs/$BUILD_TYPE to $T_OUT_LIBS_MUPDF"
    cp -r "libs/$BUILD_TYPE" $T_OUT_LIBS_MUPDF  
    __exit_on_error__;
    cd -;
done

mv $ORIGINAL_MAKE_FILE $MUPDF_ANDROID_DIR/jni/Application.mk

