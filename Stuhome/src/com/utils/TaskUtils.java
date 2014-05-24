package com.utils;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by storm on 14-4-11.
 */
public class TaskUtils {
    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params) {
            task.execute(params);
    }
}
