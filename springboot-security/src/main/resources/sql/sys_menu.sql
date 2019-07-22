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

 Date: 22/07/2019 15:01:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `menu_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单路径',
  `menu_parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级id',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '/sys', NULL);
INSERT INTO `sys_menu` VALUES ('2', '用户管理', '/sys/user', '1');
INSERT INTO `sys_menu` VALUES ('3', '权限管理', '/sys/authority', '1');
INSERT INTO `sys_menu` VALUES ('4', '菜单管理', '/sys/menu', '1');
INSERT INTO `sys_menu` VALUES ('5', 'XXX菜单', '/menu/xxx', '');
INSERT INTO `sys_menu` VALUES ('6', 'XXX菜单1', '/menu/xxx1', '5');
INSERT INTO `sys_menu` VALUES ('7', 'XXX菜单2', '/menu/xxx2', '5');

SET FOREIGN_KEY_CHECKS = 1;
