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

package com.example.hmily.tcc.dubbo.order.service.impl;

import com.example.hmily.tcc.dubbo.common.account.api.AccountService;
import com.example.hmily.tcc.dubbo.common.account.dto.AccountDTO;
import com.example.hmily.tcc.dubbo.order.service.OrderService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("orderService")
@SuppressWarnings("all")
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private AccountService accountService;

    private AccountDTO accountDTOACNY;
    private AccountDTO accountDTOAUSD;
    private AccountDTO accountDTOBCNY;
    private AccountDTO accountDTOBUSD;

    {
        accountDTOACNY = new AccountDTO();
        accountDTOACNY.setUserId("1");
        accountDTOACNY.setAccountType((byte)1);
        accountDTOACNY.setAmount(new BigDecimal(7));

        accountDTOAUSD = new AccountDTO();
        accountDTOAUSD.setUserId("1");
        accountDTOAUSD.setAccountType((byte)2);
        accountDTOAUSD.setAmount(new BigDecimal(-1));

        accountDTOBCNY = new AccountDTO();
        accountDTOBCNY.setUserId("2");
        accountDTOBCNY.setAccountType((byte)1);
        accountDTOBCNY.setAmount(new BigDecimal(-7));

        accountDTOBUSD = new AccountDTO();
        accountDTOBUSD.setUserId("2");
        accountDTOBUSD.setAccountType((byte)2);
        accountDTOBUSD.setAmount(new BigDecimal(1));

    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void testExchangeSuccess() {
        accountService.exchangeSuccess(accountDTOACNY);
        accountService.exchangeSuccess(accountDTOAUSD);
        accountService.exchangeSuccess(accountDTOBCNY);
        accountService.exchangeSuccess(accountDTOBUSD);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void testExchangeException() {
        accountService.exchangeSuccess(accountDTOACNY);
        accountService.exchangeSuccess(accountDTOAUSD);
        accountService.exchangeException(accountDTOBCNY);
        accountService.exchangeSuccess(accountDTOBUSD);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void testExchangeTimeout() {
        accountService.exchangeSuccess(accountDTOACNY);
        accountService.exchangeSuccess(accountDTOAUSD);
        accountService.exchangeTimeout(accountDTOBCNY);
        accountService.exchangeSuccess(accountDTOBUSD);
    }

    public void confirmOrderStatus() {
        LOGGER.info("=========进行订单confirm操作完成================");
    }

    public void cancelOrderStatus() {
        LOGGER.info("=========进行订单cancel操作完成================");
    }
}
