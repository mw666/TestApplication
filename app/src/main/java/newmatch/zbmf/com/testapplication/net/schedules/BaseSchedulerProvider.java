package newmatch.zbmf.com.testapplication.net.schedules;

import android.support.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

/**
 * Created By pq
 * on 2019/7/31
 */
public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();
}
