package com.six.dao;


import com.six.pojo.vo.RoomMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/4
 */
public interface LiveRepository extends JpaRepository<RoomMessage,Integer> {

    RoomMessage findByUid(Integer id);
}
