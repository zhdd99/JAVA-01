/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hmily.tcc.dubbo.account.service;

import com.alibaba.nacos.client.utils.JSONUtils;
import com.example.hmily.tcc.dubbo.common.account.api.AccountService;
import com.example.hmily.tcc.dubbo.common.account.dto.AccountDTO;
import com.example.hmily.tcc.dubbo.common.account.mapper.AccountMapper;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The type Account service.
 *
 * @author xiaoyu
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean exchangeSuccess(AccountDTO accountDTO) {
        try {
            LOGGER.info("============执行exchangeSuccess接口===============:" + JSONUtils.serializeObject(accountDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count =  accountMapper.update(accountDTO);
        if (count > 0) {
            return true;
        } else {
            throw new HmilyRuntimeException("账户扣减异常！");
        }
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean exchangeException(AccountDTO accountDTO) {
        try {
            LOGGER.info("============执行exchangeException接口===============:" + JSONUtils.serializeObject(accountDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HmilyRuntimeException("下单异常！");
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean exchangeTimeout(AccountDTO accountDTO) {
        try {
            LOGGER.info("============执行exchangeTimeout接口===============:" + JSONUtils.serializeObject(accountDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //模拟延迟 当前线程暂停10秒
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int count =  accountMapper.update(accountDTO);
        if (count > 0) {
            return true;
        } else {
            throw new HmilyRuntimeException("账户扣减异常！");
        }
    }

    public boolean confirm(AccountDTO accountDTO){
        try {
            LOGGER.info("============执行confirm接口===============:" + JSONUtils.serializeObject(accountDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        accountMapper.confirm(accountDTO);
        return true;
    }

    public boolean cancel(AccountDTO accountDTO){
        try {
            LOGGER.info("============执行cancel接口===============" + JSONUtils.serializeObject(accountDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        accountMapper.cancel(accountDTO);
        return false;
    }
}
