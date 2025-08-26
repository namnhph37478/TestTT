package com.example.testtt.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testtt.data.repository.LocationRepository;
import com.example.testtt.domain.model.LocationItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchViewModel extends ViewModel {

    private final LocationRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final PublishSubject<String> querySubject = PublishSubject.create();

    private final MutableLiveData<SearchUiState> _uiState = new MutableLiveData<>(SearchUiState.idle());
    public LiveData<SearchUiState> uiState = _uiState;

    public SearchViewModel() {
        this.repository = new LocationRepository();
        observeQuery();
    }

    // --- Lắng nghe query và debounce 1s ---
    private void observeQuery() {
        Disposable d = querySubject
                .debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(query -> {
                    if (query == null || query.trim().length() < 2) {
                        return Observable.just(SearchUiState.empty());
                    }
                    _uiState.postValue(SearchUiState.loading());
                    return repository.search(query)
                            .subscribeOn(Schedulers.io())
                            .map((List<LocationItem> list) -> {
                                if (list.isEmpty()) {
                                    return SearchUiState.empty();
                                } else {
                                    return SearchUiState.success(list);
                                }
                            })
                            .onErrorReturn(throwable -> SearchUiState.error(throwable.getMessage()))
                            .toObservable();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> _uiState.setValue(state));

        disposables.add(d);
    }

    // --- Hàm gọi từ UI khi text thay đổi ---
    public void onQueryChanged(String query) {
        querySubject.onNext(query);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
