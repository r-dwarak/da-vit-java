Download and Java FX from the below link
https://download2.gluonhq.com/openjfx/25.0.1/openjfx-25.0.1_windows-x64_bin-sdk.zip.sha256 

# replace you open javafx lib path
javac --module-path "D:\softwares\openjfx-25.0.1_windows-x64_bin-sdk\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.graphics *.java
java  --module-path "D:\softwares\openjfx-25.0.1_windows-x64_bin-sdk\javafx-sdk-25.0.1\lib" --add-modules javafx.controls,javafx.graphics BookingApp

