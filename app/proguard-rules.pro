# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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


# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}



#1.基本指令区
-optimizationpasses 5         # 指定代码的压缩级别
-dontusemixedcaseclassnames     # 是否使用大小写混合
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify          # 混淆时是否做预校验
-verbose               # 混淆时是否记录日志
#-ignorewarning
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*    # 混淆时所采用的算法
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

#2.默认保留区    保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}

#3.webview
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}


-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class [com.tymall].R$*{
public static final int *;
}

#腾讯bugly混淆
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


  #GreenDao
  -keep class org.greenrobot.greendao.**{*;}
  -keep public interface org.greenrobot.greendao.**
  -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
  public static java.lang.String TABLENAME;
  }
  -keep class **$Properties
  -keep class net.sqlcipher.database.**{*;}
  -keep public interface net.sqlcipher.database.**
  -dontwarn net.sqlcipher.database.**
  -dontwarn org.greenrobot.greendao.**



  #fastJson
  -keepattributes Signature
  -dontwarn com.alibaba.fastjson.**
  -keep class com.alibaba.fastjson.**{*; }
  -keepattributes Signature



  # glide 的混淆代码
  -keep public class * implements com.bumptech.glide.module.GlideModule
  -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
  }


   #butterknife
   -keep class butterknife.** { *; }
   -dontwarn butterknife.internal.**
   -keep class **$$ViewBinder { *; }

   -keepclasseswithmembernames class * {
       @butterknife.* <fields>;
   }

   -keepclasseswithmembernames class * {
       @butterknife.* <methods>;
   }


     #okgo
       #okhttp
       -dontwarn okhttp3.**
       -keep class okhttp3.**{*;}
       -keep interface okhttp3.**{*;}

       #okio
       -dontwarn okio.**
       -keep class okio.**{*;}
       -keep interface okio.**{*;}

         #EventBus
        -keepattributes *Annotation*
        -keepclassmembers class ** {
            @org.greenrobot.eventbus.Subscribe <methods>;
        }
        -keep enum org.greenrobot.eventbus.ThreadMode { *; }


 #Gson
   # removes such information by default, so configure it to keep all of it.
   -keepattributes Signature
 # Gson specific classes
   -keep class sun.misc.Unsafe { *; }
   -keep class com.google.gson.stream.** { *; }
 # Application classes that will be serialized/deserialized over Gson
    -keep class com.google.gson.examples.android.model.** { *; }
    -keep class com.google.gson.** { *;}


  #这句非常重要，主要是滤掉 com.sdbz.dbank.model包下的所有.class文件不进行混淆编译
  -keep class com.tymall.model.** {*;}

  #fresco
  -keep class com.facebook.** {*;}
  -dontwarn okio.**
  -dontwarn com.squareup.okhttp.**
  -dontwarn okhttp3.**
  -dontwarn javax.annotation.**
  -dontwarn com.android.volley.toolbox.**
  -dontwarn com.facebook.**
  -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip



 ####################zxing#####################
  -keep class com.google.zxing.** {*;}
  -dontwarn com.google.zxing.**


  -keep class org.web3j.** {*;}
  -keep class org.bitcoinj.** {*;}
  -keep class com.fasterxml.jackson.** {*;}



#takephoto
-keep class com.jph.takephoto.** { *; }
-dontwarn com.jph.takephoto.**

-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**

-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**

-keep class com.tymall.ui.fragment.MainFragment{*;}
-keep class com.tymall.ui.my.HelpCenterActivity{*;}
-keep class com.tymall.ui.my.CustomWebViewActivity{*;}
-keep class com.tymall.ui.my.InviteFriendsActivity{*;}
-keep class com.tymall.ui.wallet.ReceiveActivity{*;}
-keep class com.tymall.view.WebViewPopuWindow{*;}
-keepclassmembers class com.tymall.ui.mall.DBMallH5Activity$WebViewInterface {
   public *;
}
-keepclassmembers class com.tymall.ui.mall.DbmBpOneActivity$WebViewInterface {
   public *;
}
-keepclassmembers class com.tymall.ui.mall.DbmBpTwoActivity$WebViewInterface {
   public *;
}
-keepclassmembers class com.tymall.ui.my.InviteFriendsActivity$WebViewInterface {
   public *;
}
-keepclassmembers class com.tymall.ui.my.CustomWebViewActivity$WebViewInterface {
   public *;
}
-keepclassmembers class com.tymall.ui.my.HelpCenterActivity$WebViewInterface {
   public *;
}
-keepclassmembers class com.tymall.ui.monopoly.GameStrategyActivity$WebViewInterface {
   public *;
}
-keepclassmembers class com.tymall.view.WebViewPopuWindow$WebViewInterface {
   public *;
}

#友盟
-keep class com.umeng.commonsdk.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-dontwarn com.umeng.**
-keep class com.umeng.** {*;}


#AgentWeb
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
#-keep public class [您的应用包名].R$*{
#public static final int *;
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okrx
-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.**{*;}

#okrx2
-dontwarn com.lzy.okrx2.**
-keep class com.lzy.okrx2.**{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}

#
-keep class com.fcoin.exchange.net.response.** { *; }

#
-keep class com.fcoin.exchange.net.response.** { *; }

-dontwarn com.tymall.okrx2**
-dontwarn com.tymall.model**

-keep class com.tymall.okrx2** { *; }

#gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.**{ *; }
-keep class com.google.gson.examples.android.model.**{ *; }
-keep class com.google.gson.**{ *;}
-keep class * implements java.io.Serializable {*;}

-keep class com.aries.ui.widget.alert.** { *; }
-keep class com.cazaea.sweetalert.** { *; }

-keep class com.sdbz.dbank.okrx2.** { *; }

-keep class io.reactivex.** { *; }
-keep class rx.** { *; }

-keep class com.github.ybq.android.spinkit.** { *; }

-keep class com.tencent.** { *; }

-keepattributes LineNumberTable,SourceFile

-keep class com.bugtags.library.** {*;}

-dontwarn com.bugtags.library.**

-keep class io.bugtags.** {*;}

-dontwarn io.bugtags.**

-dontwarn org.apache.http.**

-dontwarn android.net.http.AndroidHttpClient

-keep class  org.apache.http.** {*;}

-keep public class com.alibaba.android.arouter.routes.**{*;}

-keep public class com.alibaba.android.arouter.facade.**{*;}

-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

-keep class * implements com.alibaba.android.arouter.facade.template.IProvider


#okhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

# Retrolambda
-dontwarn java.lang.invoke.*

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
###rxandroid-1.2.1
-keepclassmembers class rx.android.**{*;}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class org.xz_sale.entity.**{*;}
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

#RxEasyHttp
-keep class com.zhouyou.http.model.** {*;}
-keep class com.zhouyou.http.cache.model.** {*;}
-keep class com.zhouyou.http.cache.stategy.**{*;}


-keep class com.sdbz.dbank.utils.**{*;}

-keepclasseswithmembernames class ** {
    native <methods>;
}

-keep class org.devio.takephoto.** { *; }
-keep class com.huantansheng.easyphotos.models.** { *; }
-keep class com.wildma.idcardcamera { *; }
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-keep class com.ut.** {*;}
-keep class com.ta.** {*;}
-keep class com.github.** {*;}
-keep class anet.**{*;}
-keep class anetwork.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-keep class android.os.**{*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**
-dontwarn anetwork.**
-dontwarn com.ut.**
-dontwarn com.ta.**