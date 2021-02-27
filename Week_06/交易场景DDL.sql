CREATE TABLE `platform_user` (
	`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '修改时间',
	`user_id` varchar(128) NOT NULL COMMENT '用户ID',
	`user_name` varchar(128) NOT NULL COMMENT '用户名',
	`password` varchar(128) NOT NULL COMMENT '密码',
	`nick_name` varchar(128) NULL COMMENT '昵称',
	`id_card_number` varchar(18) NULL COMMENT '身份证号',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_user_id` (`user_id`),
	UNIQUE KEY `uk_id_card` (`id_card_number`)
) DEFAULT CHARACTER SET=utf8mb4 COMMENT='平台用户';

CREATE TABLE `platform_order` (
	`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL on update CURRENT_TIMESTAMP COMMENT '修改时间',
	`order_id` varchar(128) NOT NULL COMMENT '订单id',
	`user_id` varchar(128) NOT NULL COMMENT '用户id',
	`amount` bigint unsigned NOT NULL COMMENT '金额(分)',
	`status` tinyint unsigned NOT NULL COMMENT '状态',
	`gmt_pay` datetime NULL COMMENT '支付时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_order_id` (`order_id`)
) DEFAULT CHARACTER SET=utf8mb4 COMMENT='平台订单';

CREATE TABLE `platform_goods` (
	`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '修改时间',
	`goods_code` varchar(128) NOT NULL COMMENT '商品编码',
	`goods_name` varchar(128) NOT NULL COMMENT '商品名称',
	`goods_category` int NOT NULL COMMENT '商品分类',
	`weight` bigint unsigned NULL COMMENT '商品重量(克)',
	`price` bigint unsigned NOT NULL COMMENT '商品价格(分)',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_goods` (`goods_code`)
) DEFAULT CHARACTER SET=utf8mb4 COMMENT='平台商品';

CREATE TABLE `platform_order_goods` (
	`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
	`gmt_create` datetime NOT NULL COMMENT '创建时间',
	`gmt_modified` datetime NOT NULL COMMENT '修改时间',
	`order_id` varchar(128) NOT NULL COMMENT '订单ID',
	`goods_code` varchar(128) NOT NULL COMMENT '商品编码',
	`goods_name` varchar(128) NOT NULL COMMENT '商品名称',
	`quantity` int unsigned NOT NULL COMMENT '商品数量',
	`total_prize` bigint unsigned NOT NULL COMMENT '商品总金额(分)',
	PRIMARY KEY (`id`),
	KEY `idx_order_id` (`order_id`),
	KEY `idx_goods_code` (`goods_code`)
) DEFAULT CHARACTER SET=utf8mb4 COMMENT='订单商品明细表';

