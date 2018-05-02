# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\sdk/tools/proguard/proguard-android.txt
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
-dontoptimize
-dontnote
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
############################################
# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

-keepattributes *Annotation*


#-libraryjars   libs/Android-support-v4.jar
#-dontwarn android.support.v4.**
#-keep class android.support.v4.** { *; }
#-keep interface android.support.v4.app.** { *; }
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.app.Fragment




#-libraryjars libs/core-3.1.0.jar
#-libraryjars libs/gson-2.2.2.jar
#-libraryjars libs/MobCommons.jar
#-libraryjars libs/MobTools.jar
#-libraryjars libs/securepay_auth_2.4.2.04.jar
#-libraryjars libs/ShareSDK-Core-2.6.5.jar
#-libraryjars libs/ShareSDK-QQ-2.6.5.jar
#-libraryjars libs/ShareSDK-SinaWeibo-2.6.5.jar
#-libraryjars libs/ShareSDK-Wechat-2.6.5.jar
#-libraryjars libs/ShareSDK-Wechat-Core-2.6.5.jar
#-libraryjars libs/ShareSDK-Wechat-Favorite-2.6.5.jar
#-libraryjars libs/ShareSDK-Wechat-Moments-2.6.5.jar
#-libraryjars libs/umeng-analytics-v5.6.3.jar
#-libraryjars libs/fraudmetrix-2.0.6.jar
#
#-libraryjars ../appcompat_v7/libs/android-support-v4.jar
#-libraryjars ../appcompat_v7/libs/android-support-v7-appcompat.jar

##---------------Begin: proguard configuration for Gson ----------
#-keepattributes Signature
#
#-keep public class com.google.gson.**
#
#-keep public class com.google.gson.** {
#	public private protected *;
#}

#-keep public class com.ssjr.utils.MyAndroidJS {
#	public private protected *;
#}

#-keepclassmembers class com.ssjr.bean.** {
#   <fields>;
#}
#-keepclassmembers class com.ssjr.bean2.** {
#    <fields>;
#}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
#-keepclassmembers public class * extends android.view.View {
#   void set*(***);
#   *** get*();
#}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#-keepclassmembers class **.R$* {
#    public static <fields>;
#}
#-keepclassmembers class * {
#   public <init>(org.json.JSONObject);
#}
#sharesdk
#-keep class cn.sharesdk.**{*;}
#-keep class com.sina.**{*;}
#-dontwarn cn.sharesdk.**
#连连支付的
#-keep class com.yintong.**{*;}
#-dontwarn com.yintong.**
# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
#FMAgent
-dontwarn android.os.**
-dontwarn com.android.internal.**
#同盾的混淆
#-keepclasseswithmembers class cn.fraudmetrix.android.**{*;}
#-dontwarn cn.fraudmetrix.android.**
#okhttp的混淆
#-keep class okhttp3.**{*;}
#-dontwarn okhttp3.**
#-keep class okio.**{*;}
#-dontwarn okio.**


#mpchart的混淆
#-dontwarn com.github.mikephil.charting.**
#-keep class com.github.mikephil.charting.** { *; }
#-keep class com.github.mikephil.charting-dontskipnonpubliclibraryclassmembers
#-dontwarn io.realm.**
#-keep class io.realm.** { *; }
#-keep interface io.realm.** { *; }

#-dontwarn de.innosystec.unrar.**
#-keep class de.innosystec.unrar.** { *; }
#-keep interface de.innosystec.unrar.** { *; }

#SweetAlert的混淆
-keep class cn.pedant.SweetAlert.** { *; }
-keep interface cn.pedant.SweetAlert.** { *; }
-keep class com.pnikosis.materialishprogress.** { *; }

#
#-dontwarn org.apache.**
#-keep class org.apache.** { *; }