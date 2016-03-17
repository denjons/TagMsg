ECHO moving source

REM move server source
xcopy C:\dev\workspace\cst\src\* C:\dev\TagMsg\server\src /s /i
xcopy C:\dev\workspace\cst\pom.xml C:\dev\TagMsg\server /Y
xcopy C:\dev\workspace\cst\target\cst.war C:\dev\TagMsg\server\target /s /i

REM move android source
xcopy C:\Users\dennis\AndroidStudioProjects\TagMessenger\app\src\* C:\dev\TagMsg\android\src /s /i
xcopy C:\Users\dennis\AndroidStudioProjects\TagMessenger\app\build.gradle C:\dev\TagMsg\android /Y