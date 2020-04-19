--创建表都在这里


--用户订单表
CREATE TABLE IF not exists java_learn.`user_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `order_id` varchar(100) NOT NULL DEFAULT '' COMMENT '用户订单id',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '订单地址',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '价格',
  `pay_type` smallint(1) NOT NULL DEFAULT '0' COMMENT '支付方式，1：支付宝，2：微信，3：其他',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `order_id_user_id_idx` (`order_id`,`user_id`),
  CONSTRAINT `user_order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `common_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户订单表';

CREATE TABLE IF not exists java_learn.`system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '日志级别，40000：error，20000：info，30000：warn，10000：debug，5000：trace',
  `thread_name` varchar(100) NOT NULL DEFAULT '' COMMENT '线程名',
  `class_name` varchar(100) NOT NULL DEFAULT '' COMMENT '类名',
  `exception` text COMMENT '异常',
  `body` text COMMENT '消息体',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `create_time_idx` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';