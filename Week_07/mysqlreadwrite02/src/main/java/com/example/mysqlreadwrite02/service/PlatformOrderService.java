package com.example.mysqlreadwrite02.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mysqlreadwrite02.entity.PlatformOrder;

/**
 * <p>
 * 平台订单 服务类
 * </p>
 *
 * @author zhdd99
 * @since 2021-03-07
 */
public interface PlatformOrderService extends IService<PlatformOrder> {

    PlatformOrder findOrderByFirstDb(long id);

    PlatformOrder findOrderBySecondDb(long id);

}
