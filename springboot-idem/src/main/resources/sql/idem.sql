/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 08/04/2022 15:14:17
*/

-- ----------------------------
-- Table structure for idem
-- ----------------------------
DROP TABLE IF EXISTS `idem`;
CREATE TABLE `idem`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一主键',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务数据',
  `version` int(8) NOT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '防重放与操作幂等测试表' ROW_FORMAT = Compact;
