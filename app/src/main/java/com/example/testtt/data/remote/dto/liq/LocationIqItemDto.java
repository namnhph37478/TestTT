package com.example.testtt.data.remote.dto.liq;

import com.google.gson.annotations.SerializedName;

/**
 * DTO đại diện cho 1 item trả về từ LocationIQ Search API
 * Ví dụ JSON:
 * [
 *   {
 *     "display_name": "Hanoi, Vietnam",
 *     "lat": "21.0278",
 *     "lon": "105.8342"
 *   }
 * ]
 */
public class LocationIqItemDto {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lon")
    private String lon;

    // --- Getter/Setter ---

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
