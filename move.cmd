ECHO moving source

REM move server source
xcopy C:\Users\dennis\git\tm\src\* C:\dev\TagMsg\server\src /s /i
xcopy C:\Users\dennis\git\tm\pom.xml C:\dev\TagMsg\server /Y
xcopy C:\Users\dennis\git\tm\target\cst.war C:\dev\TagMsg\server\target /s /i

REM move android source
xcopy C:\Users\dennis\AndroidStudioProjects\TagMessenger\app\src\* C:\dev\TagMsg\android\src /s /i
xcopy C:\Users\dennis\AndroidStudioProjects\TagMessenger\app\build.gradle C:\dev\TagMsg\android /Y