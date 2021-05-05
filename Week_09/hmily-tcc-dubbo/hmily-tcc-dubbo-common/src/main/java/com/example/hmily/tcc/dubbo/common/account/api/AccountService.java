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

package com.example.hmily.tcc.dubbo.common.account.api;

import com.example.hmily.tcc.dubbo.common.account.dto.AccountDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * The interface Account service.
 *
 * @author xiaoyu
 */
public interface AccountService {
    
    /**
     * 外汇交易成功
     *
     * @param accountDTO 参数dto
     */
    @Hmily
    boolean exchangeSuccess(AccountDTO accountDTO);
    
    /**
     * Mock try payment exception.
     *
     * @param accountDTO the account dto
     */
    @Hmily
    boolean exchangeException(AccountDTO accountDTO);
    
    /**
     * Mock try payment timeout.
     *
     * @param accountDTO the account dto
     */
    @Hmily
    boolean exchangeTimeout(AccountDTO accountDTO);
}
