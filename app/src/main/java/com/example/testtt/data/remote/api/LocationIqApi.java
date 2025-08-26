package com.example.testtt.data.remote.api;

import com.example.testtt.data.remote.dto.liq.LocationIqItemDto;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationIqApi {

    // Endpoint Search (docs: https://locationiq.com/docs)
    // Ví dụ: https://us1.locationiq.com/v1/search?key=YOUR_KEY&q=Hanoi&format=json&addressdetails=1&limit=20

    @GET("/v1/search")
    Single<List<LocationIqItemDto>> search(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("format") String format,          // nên để "json"
            @Query("addressdetails") int addressDetails, // 1 = trả về chi tiết address
            @Query("limit") int limit                // số kết quả, vd: 20
    );
}
