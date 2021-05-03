package com.xxx.search.client;

import com.xxx.item.api.SongApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SongClient extends SongApi {
}
