/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tymall.okrx2;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * <p>描述：订阅的基类</p>
 * 3.统一处理了异常<br>
 * 版本： v2.0<br>
 */
public abstract class BaseSubscriber<T> implements Observer<T> {

    public BaseSubscriber() {
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }


    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public final void onError(java.lang.Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onComplete() {

    }


    public abstract void onError(ApiException e);

}
