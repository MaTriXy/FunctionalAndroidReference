/*
 * Copyright (c) pakoito 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pacoworks.dereference.features.global;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.jakewharton.rxrelay.BehaviorRelay;
import com.pacoworks.dereference.core.reactive.ConductorLifecycle;
import com.pacoworks.dereference.core.reactive.buddies.ControllerReactiveBuddy;
import com.pacoworks.dereference.core.reactive.buddies.ReactiveController;
import com.pacoworks.dereference.core.ui.BoundView;
import com.pacoworks.dereference.core.ui.ControllerBinderKt;
import com.pacoworks.rxpartialapplication.RxPartialAction;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Action3;

public abstract class BaseController extends Controller implements BoundView {
    ReactiveController reactiveController = new ReactiveController();

    public BaseController() {
        super();
        reactiveController.onEnter();
    }

    public BaseController(Bundle args) {
        super(args);
        reactiveController.onEnter();
    }

    @NonNull
    @Override
    protected final View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final View view = createView(inflater, container);
        reactiveController.onCreate();
        return view;
    }

    @NonNull
    @CheckResult
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        reactiveController.onAttach();
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        reactiveController.onDetach();
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        reactiveController.onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reactiveController.onFinish();
    }

    @Override
    @NonNull
    public <T> Action2<BehaviorRelay<T>, Function1<T, Unit>> createBinder() {
        return RxPartialAction.apply(new Action3<Observable<ConductorLifecycle>, BehaviorRelay<T>, Function1<T, Unit>>() {
            @Override
            public void call(Observable<ConductorLifecycle> lifecycle, BehaviorRelay<T> state, Function1<T, Unit> view) {
                ControllerBinderKt.bind(lifecycle, AndroidSchedulers.mainThread(), state, view);
            }

        }, reactiveController.createBuddy().lifecycle());
    }

    protected ControllerReactiveBuddy createBuddy() {
        return reactiveController.createBuddy();
    }

    protected MainActivity getCastedActivity() {
        return (MainActivity) getActivity();
    }
}