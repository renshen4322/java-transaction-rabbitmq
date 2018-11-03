CREATE TABLE `tb_distributed_message` (
  `unique_id` varchar(255) DEFAULT NULL COMMENT 'ΨһID',
  `msg_content` varchar(1024) DEFAULT NULL COMMENT '��Ϣ����',
  `msg_status` int(11) DEFAULT '0' COMMENT '�Ƿ��͵�MQ��0:δ���ͣ�1:�ѷ���',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '��Ϣ����ʱ��'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����-�ֲ�ʽ����-������Ϣ��';


CREATE TABLE `table_order` (
  `order_id` varchar(255) NOT NULL COMMENT '������',
  `user_id` varchar(255) NOT NULL COMMENT '�û����',
  `order_content` varchar(255) NOT NULL COMMENT '��������(������Щ�������ͻ���ַ)',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������Ϣ��';



CREATE TABLE `table_dispatch` (
  `dispatch_seq` varchar(255) NOT NULL COMMENT '������ˮ��',
  `order_id` varchar(255) NOT NULL COMMENT '�������',
  `dispatch_status` varchar(255) DEFAULT NULL COMMENT '����״̬',
  `dispatch_content` varchar(255) DEFAULT NULL COMMENT '��������(�Ͳ�Ա��·��)',
  PRIMARY KEY (`dispatch_seq`,`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������Ϣ��';

