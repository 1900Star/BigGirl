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



# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

# OkHttp3----------------------------------------------------------------
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**

#greendao3.2.2
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keep class com.yibao.biggirl.model.* {*;}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#不混淆资源类
-keep class **.R$* {
 *;
}
#retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
