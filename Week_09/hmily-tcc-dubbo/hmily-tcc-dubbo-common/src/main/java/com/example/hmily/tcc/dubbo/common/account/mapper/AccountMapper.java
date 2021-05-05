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

package com.example.hmily.tcc.dubbo.common.account.mapper;

import com.example.hmily.tcc.dubbo.common.account.dto.AccountDTO;
import com.example.hmily.tcc.dubbo.common.account.entity.AccountDO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * The interface Account mapper.
 *
 * @author xiaoyu
 */
public interface AccountMapper {
    
    /**
     * Update int.
     *
     * @param accountDTO the account dto
     * @return the int
     */
    @Update("update account set balance = balance - #{amount}," +
            " freeze_amount= freeze_amount + #{amount} ,update_time = now()" +
            " where user_id =#{userId} and account_type = #{accountType} and  balance >= #{amount}  ")
    int update(AccountDTO accountDTO);
    
    /**
     * Confirm int.
     *
     * @param accountDTO the account dto
     * @return the int
     */
    @Update("update account set " +
            " freeze_amount= freeze_amount - #{amount}" +
            " where user_id =#{userId} and account_type = #{accountType} and freeze_amount >= #{amount} ")
    int confirm(AccountDTO accountDTO);
    
    /**
     * Cancel int.
     *
     * @param accountDTO the account dto
     * @return the int
     */
    @Update("update account set balance = balance + #{amount}," +
            " freeze_amount= freeze_amount -  #{amount} " +
            " where user_id =#{userId} and account_type = #{accountType} and freeze_amount >= #{amount}")
    int cancel(AccountDTO accountDTO);

}
