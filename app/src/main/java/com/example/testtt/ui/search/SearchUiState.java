package com.example.testtt.ui.search;

import com.example.testtt.domain.model.LocationItem;
import java.util.List;

/**
 * Trạng thái UI cho màn hình search.
 */
public class SearchUiState {

    public enum Status {
        IDLE,       // chưa nhập gì
        LOADING,    // đang load
        SUCCESS,    // có kết quả
        EMPTY,      // không có kết quả
        ERROR       // lỗi
    }

    private final Status status;
    private final List<LocationItem> results;
    private final String errorMessage;

    private SearchUiState(Status status, List<LocationItem> results, String errorMessage) {
        this.status = status;
        this.results = results;
        this.errorMessage = errorMessage;
    }

    public static SearchUiState idle() {
        return new SearchUiState(Status.IDLE, null, null);
    }

    public static SearchUiState loading() {
        return new SearchUiState(Status.LOADING, null, null);
    }

    public static SearchUiState success(List<LocationItem> results) {
        return new SearchUiState(Status.SUCCESS, results, null);
    }

    public static SearchUiState empty() {
        return new SearchUiState(Status.EMPTY, null, null);
    }

    public static SearchUiState error(String message) {
        return new SearchUiState(Status.ERROR, null, message);
    }

    // --- Getter ---
    public Status getStatus() {
        return status;
    }

    public List<LocationItem> getResults() {
        return results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
