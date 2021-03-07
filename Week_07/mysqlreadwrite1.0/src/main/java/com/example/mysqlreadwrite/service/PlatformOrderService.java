package com.example.mysqlreadwrite.service;

import com.example.mysqlreadwrite.entity.PlatformOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 平台订单 服务类
 * </p>
 *
 * @author zhdd99
 * @since 2021-03-07
 */
public interface PlatformOrderService extends IService<PlatformOrder> {

    PlatformOrder findUserByFirstDb(long id);

    PlatformOrder findUserBySecondDb(long id);

}
