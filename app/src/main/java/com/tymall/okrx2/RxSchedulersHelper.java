package com.tymall.okrx2;


import com.tymall.mvp.GEMUI;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * user：lqm
 * desc：compose()里接收一个Transformer对象，ObservableTransformer
 * 可以通过它将一种类型的Observable转换成另一种类型的Observable。
 * 现在.subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread())
 * 的地方可以用.compose(RxSchedulersHelper.io_main())代替。
 */

public class RxSchedulersHelper {
    private static final String TAG = "RxSchedulersHelper";


//
//    public static <T> ObservableTransformer<ApiResponse<T>, T> io_main() {
//        return upstream ->
//                upstream.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    public static  <T> Observable.Transformer<T, T> applySchedulers() {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }



    /**
     * 网络请求的线程切换
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> observeOnMainThread() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 网络请求的线程切换
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> observeOnMainThread(GEMUI ui) {
        return observeOnMainThread(ui, Schedulers.io());
    }

    public static <T> Observable.Transformer<T, T> observeOnMainThread(GEMUI ui, Scheduler scheduler) {
        if (ui == null) {
            return observable -> observable.subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return observable -> observable.subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
//                    .takeUntil(ui.getUIChangeSubject().filter(uievent -> //保证在相关的生命周期发生的时候，能够及时的取消订阅
//                            uievent == UIChangeEvent.ONDESTROY
//                                    || uievent == UIChangeEvent.ONDETTACH
//                                    || uievent == UIChangeEvent.ONDESTROYVIEW
//                    ).take(1))
                    .filter(data -> ui.isAlive());  //有可能用户界面已经finish掉了，但是还没有调用相关的回调
        }
    }



}
