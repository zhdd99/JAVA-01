package com.example.mysqlreadwrite.service.impl;

import com.example.mysqlreadwrite.datasource.CurDataSource;
import com.example.mysqlreadwrite.datasource.DataSourceNames;
import com.example.mysqlreadwrite.entity.PlatformOrder;
import com.example.mysqlreadwrite.mapper.PlatformOrderMapper;
import com.example.mysqlreadwrite.service.PlatformOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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

    @Override
    @CurDataSource(name = DataSourceNames.FIRST)
    public PlatformOrder findUserByFirstDb(long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @CurDataSource(name = DataSourceNames.SECOND)
    public PlatformOrder findUserBySecondDb(long id) {
        return this.baseMapper.selectById(id);
    }
}
