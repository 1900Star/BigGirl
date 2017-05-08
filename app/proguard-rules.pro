# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#忽略警告
-ignorewarnings


#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *;}

#v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *;}

#v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *;}

#annotation
-dontwarn android.support.annotation.**
-keep class android.support.annotation.** { *;}

-dontwarn android.net.**
-keep class android.net.** { *;}

-dontwarn org.apache.**
-keep class org.apache.** { *;}

-dontwarn com.android.internal.com.yibao.biggirl.http.multipart.**
-keep class com.android.internal.com.yibao.biggirl.http.multipart.** { *;}
#fresco
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

# OkHttp3----------------------------------------------------------------
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**

# Retrofit---------------------------------------------------------------


#fresco------------------------------------------------------------------
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**

#Eventbus---------------------------------------------------------------
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode {*;}
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.com.yibao.biggirl.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}