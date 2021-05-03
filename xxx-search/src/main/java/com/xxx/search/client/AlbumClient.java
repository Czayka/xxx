package com.xxx.search.client;

import com.xxx.item.api.AlbumApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface AlbumClient extends AlbumApi {
}
