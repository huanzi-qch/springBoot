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

 Date: 06/08/2021 18:20:11
*/

-- ----------------------------
-- Table structure for tb_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task`  (
  `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定时任务id',
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务名称',
  `task_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务描述',
  `task_exp` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务Cron表达式',
  `task_status` int(1) NULL DEFAULT NULL COMMENT '定时任务状态，0停用 1启用',
  `task_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务的Runnable任务类完整路径',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '动态定时任务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_task
-- ----------------------------
INSERT INTO `tb_task` VALUES ('1', 'task1', '测试动态定时任务1', '0/5 * * * * ?', 0, 'cn.huanzi.qch.springboottimer.task.MyRunnable1', '2021-08-06 17:39:23', '2021-08-06 17:39:25');
INSERT INTO `tb_task` VALUES ('2', 'task2', '测试动态定时任务2', '0/5 * * * * ?', 0, 'cn.huanzi.qch.springboottimer.task.MyRunnable2', '2021-08-06 17:39:23', '2021-08-06 17:39:25');
