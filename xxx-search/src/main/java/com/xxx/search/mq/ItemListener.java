package com.xxx.search.mq;

import com.xxx.item.pojo.Song;
import com.xxx.item.vo.AlbumVo;
import com.xxx.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.song.insert.queue", durable = "true"),
            exchange = @Exchange(name = "xxx.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.song.insert", "item.song.update"}
    ))
    public void listenSongInsertOrUpdate(Song song) {
        if (song == null) {
            return;
        }
        //  处理消息，对索引库进行新增或修改
        searchService.createOrUpdateIndex(song);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.song.delete.queue", durable = "true"),
            exchange = @Exchange(name = "xxx.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.song.delete"}
    ))
    public void listenSongDelete(Long id) {
        if (id == null) {
            return;
        }
        //  处理消息，对索引库进行删除
        searchService.deleteIndex(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.album.update.queue", durable = "true"),
            exchange = @Exchange(name = "xxx.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.album.update"}
    ))
    public void listenAlbumUpdate(List<Song> songs) {
        if (songs == null) {
            return;
        }
        //  处理消息，对索引库进行新增或修改
        searchService.updateIndexes(songs);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.album.delete.queue", durable = "true"),
            exchange = @Exchange(name = "xxx.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.album.delete"}
    ))
    public void listenAlbumDelete(Long id) {
        if (id == null) {
            return;
        }
        //  处理消息，对索引库进行新增或修改
        searchService.deleteIndexByAlbumId(id);
    }

}
