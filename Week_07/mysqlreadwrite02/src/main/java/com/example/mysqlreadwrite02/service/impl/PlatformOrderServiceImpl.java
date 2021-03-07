package com.example.mysqlreadwrite02.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mysqlreadwrite02.entity.PlatformOrder;
import com.example.mysqlreadwrite02.mapper.PlatformOrderMapper;
import com.example.mysqlreadwrite02.service.PlatformOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * <p>
 * 平台订单 服务实现类
 * </p>
 *
 * @author zhdd99
 * @since 2021-03-07
 */
@Slf4j
@Service
public class PlatformOrderServiceImpl extends ServiceImpl<PlatformOrderMapper, PlatformOrder> implements PlatformOrderService {

    private ApplicationContext applicationContext;

    @Override
    public PlatformOrder findOrderByFirstDb(long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public PlatformOrder findOrderBySecondDb(long id) {
        return this.baseMapper.selectById(id);
    }
}
