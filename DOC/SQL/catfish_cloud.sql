/*
Navicat MySQL Data Transfer

Source Server         : hisaige.top
Source Server Version : 50732
Source Host           : 81.69.57.41:3336
Source Database       : catfish_cloud

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2021-03-22 00:43:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(64) NOT NULL COMMENT '类型',
  `key` varchar(64) NOT NULL COMMENT '字典标签',
  `value` varchar(255) NOT NULL COMMENT '字典值',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) DEFAULT NULL COMMENT '0--禁用，1--启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_type_key` (`type`,`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='数据字典详情';

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO `sys_dict_detail` VALUES ('1', 'category_permission', '商品模块', '商品模块', '1', '1', '2020-05-18 22:08:23', '2020-05-18 22:08:25');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL COMMENT '字典类型 --英文',
  `label` varchar(255) DEFAULT NULL COMMENT '描述 --中文描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('1', 'category_permission', '权限分类', '2020-05-18 22:04:10');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(38) NOT NULL,
  `file_path` varchar(255) NOT NULL COMMENT '文件路径',
  `md5` varchar(255) NOT NULL,
  `systematic` tinyint(1) NOT NULL COMMENT '是否系统文件，是则不允许删除（可能会影响系统运行）',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `path_index` (`file_path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES ('033ab509a20746fea1b818a557e035c5', 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/2021/01/17/c515575b-4173-4c56-b90e-c5669077521afile.png', '1MPB88DBCIIVE2RD57I6BC4M0V', '0', '2021-01-17 10:34:00', '2021-01-17 10:34:00');
INSERT INTO `sys_file` VALUES ('1c9c042d85894cebb165b7c5f7d584fe', 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/2021/01/17/ce91fda5-75a7-4dda-b74f-00d736c2fe9efile.png', '22JR1T615SM1SJK42NAOI2478B', '0', '2021-01-17 10:36:58', '2021-01-17 10:36:58');
INSERT INTO `sys_file` VALUES ('5d1524653aa642829844be987b11fb99', 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/testPath/cb4e7757-88ec-4ed8-ac70-e09418e1a964微信图片_20210105215110.jpg', '3BCRFQ29V2F4RPLFUQIEJ8MCL5', '0', '2021-01-17 10:31:30', '2021-01-17 10:31:30');
INSERT INTO `sys_file` VALUES ('87f829720824474ab29a4f19fef9188d', 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/2021/01/17/bc04e9d1-82b5-45c9-97aa-c7514261c56dfile.png', '1GOH5RMOT5THJKNMM5E55S1E0T', '0', '2021-01-17 10:36:14', '2021-01-17 10:36:14');
INSERT INTO `sys_file` VALUES ('e14ae073a9714bfabf1c6500222c6e4c', 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/2021/01/17/4efb5a97-3c33-482b-803b-12ae4aa089d4file.png', 'BL4VS5NATIPAB2BCA1OBLJ1G0', '0', '2021-01-17 10:35:37', '2021-01-17 10:35:37');
INSERT INTO `sys_file` VALUES ('e67b9d44233344789fc3eab5c6f385a6', 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/2021/01/17/ca602f86-3a44-4eb5-8823-30e2f10f67c5file.png', '1IPMCMBTPHIG7HND1JU64CCINH', '0', '2021-01-17 10:38:26', '2021-01-17 10:38:26');

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int(4) DEFAULT NULL COMMENT '菜单级数',
  `sort` int(4) DEFAULT NULL COMMENT '菜单排序',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用隐藏；1->启用展示',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES ('1', '1', '1', '1', '1', '1', '1', '1', '2020-12-24 00:24:36');

-- ----------------------------
-- Table structure for ums_organization
-- ----------------------------
DROP TABLE IF EXISTS `ums_organization`;
CREATE TABLE `ums_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组织id，这里用数字当作id主要是为了方便ancestors字段',
  `org_name` varchar(64) NOT NULL,
  `org_code` varchar(64) NOT NULL,
  `org_pid` bigint(20) NOT NULL,
  `ancestors` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_code` (`org_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_organization
-- ----------------------------
INSERT INTO `ums_organization` VALUES ('1', '根组织', '001', '0', '0', '2021-01-25 23:52:55', '2021-01-25 23:52:57');
INSERT INTO `ums_organization` VALUES ('2', '子组织1', '001001', '1', '0,1', '2021-01-25 23:53:57', '2021-01-25 23:54:00');
INSERT INTO `ums_organization` VALUES ('3', '子组织2', '001002', '1', '0,1', '2021-01-25 23:54:31', '2021-01-25 23:54:35');
INSERT INTO `ums_organization` VALUES ('4', '子组织333', '001001001111', '2', '0,1,2', '2021-01-25 23:55:13', '2021-01-25 23:55:15');
INSERT INTO `ums_organization` VALUES ('5', '子组织343567', '00100100111123', '3', '0,1,3', '2021-01-26 16:31:36', '2021-01-26 16:31:36');
INSERT INTO `ums_organization` VALUES ('7', '子组织220112', '2201', '3', '0,1,3', '2021-01-27 12:41:22', '2021-01-27 12:41:22');
INSERT INTO `ums_organization` VALUES ('10', '子组织312', '141312', '3', '0,1,3', '2021-01-27 12:47:58', '2021-01-27 12:47:58');
INSERT INTO `ums_organization` VALUES ('11', '子组织212', '212', '10', '0,1,3,10', '2021-01-27 12:48:34', '2021-01-27 12:48:34');
INSERT INTO `ums_organization` VALUES ('14', '子组织', '3123', '10', '0,1,3,10', '2021-01-27 13:29:38', '2021-01-27 13:29:38');
INSERT INTO `ums_organization` VALUES ('16', '子组织', '342425', '14', '0,1,3,10,14', '2021-01-27 13:46:06', '2021-01-27 13:46:06');
INSERT INTO `ums_organization` VALUES ('23', '32232', '3232312', '14', '0,1,3,10,14', '2021-01-27 15:42:05', '2021-01-27 15:42:05');
INSERT INTO `ums_organization` VALUES ('24', '子组织', '3123123', '10', '0,1,3,10', '2021-01-27 15:48:53', '2021-01-27 15:48:53');

-- ----------------------------
-- Table structure for ums_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_permission`;
CREATE TABLE `ums_permission` (
  `id` varchar(38) NOT NULL COMMENT '主键',
  `parent_id` varchar(38) NOT NULL DEFAULT '' COMMENT '主键ID',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `icon` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_permission
-- ----------------------------
INSERT INTO `ums_permission` VALUES ('1', '', '商品品牌管理', '/brand/**', null, '', '2020-05-18 22:12:03', null);
INSERT INTO `ums_permission` VALUES ('2', '', '新增订单', '/order/add/**', null, null, '2020-05-18 22:42:34', null);
INSERT INTO `ums_permission` VALUES ('3', '', '用户管理', '/user/**', null, null, '2020-06-08 00:03:30', null);

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
  `id` bigint(20) NOT NULL,
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源id',
  `resource_name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `resource_type` varchar(64) DEFAULT NULL COMMENT '资源类型',
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_resource
-- ----------------------------

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` varchar(38) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `role_code` varchar(100) DEFAULT NULL COMMENT '描述',
  `description` varchar(500) DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_uniq` (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('1', '超级管理员', 'admin', 'dsadsadsadsa', '1', '0', '2020-05-26 23:17:29');
INSERT INTO `ums_role` VALUES ('2', '操作员', 'editor', '这是一段关于角色的描述', '1', '0', '2020-12-29 23:29:02');

-- ----------------------------
-- Table structure for ums_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu`;
CREATE TABLE `ums_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu
-- ----------------------------
INSERT INTO `ums_role_menu` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for ums_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_permission`;
CREATE TABLE `ums_role_permission` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_role_permission
-- ----------------------------
INSERT INTO `ums_role_permission` VALUES ('1', '1', '2', '2020-05-26 23:17:53');

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user` (
  `id` varchar(38) NOT NULL,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `org_id` varchar(38) DEFAULT NULL,
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(200) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(20) DEFAULT NULL COMMENT '证件号码',
  `introduction` varchar(500) DEFAULT NULL COMMENT '用户简介',
  `valid_date` datetime DEFAULT NULL COMMENT '过期时间',
  `status` tinyint(1) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_uniq` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES ('0e960339d8c44e70b39dbd93e15f630c', 'hisaige', '$2a$10$GF0M0uKtUHLy.JiUO1YjEuEJufCDdhp697RZJVpQDG03j6fv/OY8.', '2', 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/2021/01/17/ca602f86-3a44-4eb5-8823-30e2f10f67c5file.png', 'hisaige@163.com', '1234', 'hisiage002', 'cyj', '', '目光呆滞的5741号代码搬运工', null, '0', '5', '2021-01-17 16:57:08', null);
INSERT INTO `ums_user` VALUES ('1', 'admin', '$2a$10$xquKmbZnVcR18fr3Z/s5YewvK6Hy6vZdt2poUN0HyzEVpjIlQa7sS', '1', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'hisaige@163.com', '123000', 'hisaige啊', 'hisaige', '12300000', '超级管理员', null, '1', '0', '2020-06-06 10:58:54', null);
INSERT INTO `ums_user` VALUES ('124', 'AA', '$2a$10$VI2b7vZGliM1Sfl1f9w8jeOb.Cfp9nXrIWkxzOmpa9c8lRoH8.Zgq', null, '/aa/ss', 'hisaige@163.com', '1256321s', 'hisiage升级版3', 'hisiage4444', '123ss', 'hisiage111', null, '0', '99999999', '2020-06-07 15:23:27', null);
INSERT INTO `ums_user` VALUES ('6a250b2271e642a6a590ee6bdadc75fa', 'testAdd', '$2a$10$eMqNoBDK4H74W4yiaW6sq.sMGPMwfiyh/CWi6ogvB90z/OgbaiuEG', '4', '', '1170@163.com', '187', 'testAdd', 'testAdd', '', 'sdadadsd', '2021-03-24 16:00:00', '1', '2', '2021-03-21 14:22:19', null);
INSERT INTO `ums_user` VALUES ('7', 'hisiage5555', 'hisiage333', null, '/aa/ss', 'hisaige@163.com', '1256321s', 'hisiage', 'hisiage4444', '123ss', 'hisiage111', null, '1', null, '2020-05-23 11:14:38', null);
INSERT INTO `ums_user` VALUES ('7a641835140f403d80623c755d899e7a', 'hisaige_add', '$2a$10$.QvoJZhEcI1bw02AVJioJOFzVNyd6xKUz/fN0D9hx8MS55YH/5sLG', '2', '', 'hisaige@163.com', '187', 'sss', 'chenyj', '', null, '2021-04-15 16:00:00', '1', null, '2021-03-21 13:39:48', null);
INSERT INTO `ums_user` VALUES ('9d6dff659d844370bc40830dbfbdde0a', 'qweqwe', '$2a$10$PTrcO572FImCNB5nBhjTcec71VQKly0WnROG7Au.iFl4ZqaDBOV.y', null, '', 'hisaige@163.com', '', 'ewqe', 'ewqe', '', null, null, '1', null, '2021-01-17 17:48:07', null);
INSERT INTO `ums_user` VALUES ('a1a00ccb1316496eb373f4cfa84d5255', 'testAdd2', '$2a$10$/LTst5a6f5lTZkaLiW1UEuRug7Me6IMKJuIcxKF4LJPcc3nwjsWNO', '4', '', 'hisaige@163.com', '18789622545', 'testAdd2', 'testAdd2', '', 'sdadasd', '2021-03-22 16:00:00', '1', '999', '2021-03-21 14:57:46', null);
INSERT INTO `ums_user` VALUES ('b55eb5ef25124c54924c85f407bb1e5f', '', '$2a$10$hV35jRPgYXW2dk6vQJMuC.pWx2Is3oif04Tfwsfe5RS336P6n8jX.', null, '', 'dasd', 'asdas', 'dsada', 'sdsadas', '', null, null, '1', null, '2021-01-17 17:46:46', null);
INSERT INTO `ums_user` VALUES ('dd0bcf1090e845598b266737ad61e852', '312312', '$2a$10$q2gtR77vlnVXjuaFJrlYnupWcC.9HRBMpbt2YReso2lGzzdQ4rA/O', null, 'https://catfish01.oss-cn-shenzhen.aliyuncs.com/2021/01/17/ca602f86-3a44-4eb5-8823-30e2f10f67c5file.png', 'hisaige', '12', '3123', '123123', 'da', 'sdasd', '2021-01-19 16:00:00', '1', null, '2021-01-17 17:42:20', null);

-- ----------------------------
-- Table structure for ums_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_permission`;
CREATE TABLE `ums_user_permission` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id',
  `status` tinyint(1) DEFAULT '1' COMMENT '0->启用;1->禁用',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_user_permission
-- ----------------------------
INSERT INTO `ums_user_permission` VALUES ('1', '6', '1', '0', '2020-05-27 23:11:59');
INSERT INTO `ums_user_permission` VALUES ('2', '123', '3', '1', null);

-- ----------------------------
-- Table structure for ums_user_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_resource`;
CREATE TABLE `ums_user_resource` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_user_resource
-- ----------------------------

-- ----------------------------
-- Table structure for ums_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role`;
CREATE TABLE `ums_user_role` (
  `id` varchar(38) NOT NULL,
  `user_id` varchar(38) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL COMMENT '资源id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_user_role
-- ----------------------------
INSERT INTO `ums_user_role` VALUES ('1', '1', '1', '2020-05-27 23:11:40');
INSERT INTO `ums_user_role` VALUES ('2', '1', '2', '2020-06-09 00:02:52');
INSERT INTO `ums_user_role` VALUES ('3', '0e960339d8c44e70b39dbd93e15f630c', '1', '2021-03-21 13:30:18');
INSERT INTO `ums_user_role` VALUES ('4', '0e960339d8c44e70b39dbd93e15f630c', '2', '2021-03-21 13:30:26');
