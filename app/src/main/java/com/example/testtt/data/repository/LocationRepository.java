package com.example.testtt.data.repository;

import com.example.testtt.BuildConfig;
import com.example.testtt.core.network.RetrofitClient;
import com.example.testtt.data.remote.api.LocationIqApi;
import com.example.testtt.data.remote.dto.liq.LocationIqItemDto;
import com.example.testtt.domain.model.LocationItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class LocationRepository {

    private static final String BASE_URL = "https://us1.locationiq.com/"; // cụm us1 phổ biến
    private final LocationIqApi api;

    // --- Constructors ---
    public LocationRepository() {
        this(RetrofitClient.getClient(BASE_URL).create(LocationIqApi.class));
    }

    public LocationRepository(LocationIqApi api) {
        this.api = api;
    }

    // --- Public API ---
    /** Tìm kiếm địa điểm theo query. Chặn gọi khi query < 2 ký tự. */
    public Single<List<LocationItem>> search(String query) {
        if (query == null || query.trim().length() < 2) {
            return Single.just(Collections.emptyList());
        }
        String q = query.trim();

        return api.search(
                        BuildConfig.LOCATIONIQ_API_KEY, // đọc từ local.properties qua buildConfigField
                        q,
                        "json",
                        1,   // addressdetails
                        20   // limit
                )
                .map(this::mapToDomain); // DTO -> Domain
    }

    // --- Mapping helpers ---
    private List<LocationItem> mapToDomain(List<LocationIqItemDto> dtos) {
        List<LocationItem> out = new ArrayList<>();
        if (dtos == null) return out;

        for (LocationIqItemDto dto : dtos) {
            String title = safe(dto.getDisplayName());
            double lat = safeParse(dto.getLat());
            double lng = safeParse(dto.getLon());

            out.add(new LocationItem(title, title, lat, lng));
        }
        return out;
    }

    private double safeParse(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
