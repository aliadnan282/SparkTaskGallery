package com.spaktask.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateData<T> {

    @NonNull
    private DataStatus status;

    @Nullable
    private T data;

    @Nullable
    private Throwable error;

    @NonNull
    private double progress;

    public StateData() {
        this.status = DataStatus.CREATED;
        this.data = null;
        this.error = null;
        this.progress=0;
    }

    public StateData<T> loading() {
        this.status = DataStatus.LOADING;
        this.data = null;
        this.error = null;
        this.progress=0;

        return this;
    }

    public StateData<T> success(@NonNull T data) {
        this.status = DataStatus.SUCCESS;
        this.data = data;
        this.error = null;
        this.progress=100;
        return this;
    }
    public StateData<T> progress(@NonNull double progress) {
        this.status = DataStatus.PROGRESS;
        this.progress=progress;
        return this;
    }

    public StateData<T> error(@NonNull Throwable error) {
        this.status = DataStatus.ERROR;
        this.data = null;
        this.error = error;
        this.progress=0;
        return this;
    }

    public StateData<T> complete() {
        this.status = DataStatus.COMPLETE;
        return this;
    }

    @NonNull
    public DataStatus getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public enum DataStatus {
        CREATED,
        SUCCESS,
        ERROR,
        LOADING,
        PROGRESS,
        COMPLETE
    }
}