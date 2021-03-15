package com.six.dao;

import com.six.pojo.vo.UserGift;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/8
 */

@Mapper
public interface UserGiftMapper {

    UserGift findByUid(@Param("uid")Integer id);

    Integer updateByUid(UserGift userGift);


}
