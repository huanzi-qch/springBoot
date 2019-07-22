/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 22/07/2019 15:03:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu`  (
  `user_menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户菜单表id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`user_menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_menu
-- ----------------------------
INSERT INTO `sys_user_menu` VALUES ('1', '1', '1');
INSERT INTO `sys_user_menu` VALUES ('10', '3', '6');
INSERT INTO `sys_user_menu` VALUES ('11', '3', '7');
INSERT INTO `sys_user_menu` VALUES ('2', '1', '2');
INSERT INTO `sys_user_menu` VALUES ('3', '1', '3');
INSERT INTO `sys_user_menu` VALUES ('4', '1', '4');
INSERT INTO `sys_user_menu` VALUES ('41', '1', '5');
INSERT INTO `sys_user_menu` VALUES ('42', '1', '6');
INSERT INTO `sys_user_menu` VALUES ('43', '1', '7');
INSERT INTO `sys_user_menu` VALUES ('5', '2', '1');
INSERT INTO `sys_user_menu` VALUES ('51', '2', '5');
INSERT INTO `sys_user_menu` VALUES ('52', '2', '6');
INSERT INTO `sys_user_menu` VALUES ('53', '2', '7');
INSERT INTO `sys_user_menu` VALUES ('6', '2', '2');
INSERT INTO `sys_user_menu` VALUES ('7', '2', '3');
INSERT INTO `sys_user_menu` VALUES ('8', '2', '4');
INSERT INTO `sys_user_menu` VALUES ('9', '3', '5');

SET FOREIGN_KEY_CHECKS = 1;
