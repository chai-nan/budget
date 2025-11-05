/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : budget

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 05/11/2025 08:58:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 703 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '部门属性',
  `level` int(11) NULL DEFAULT NULL COMMENT '部门级别',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 192 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dept_copy
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_copy`;
CREATE TABLE `sys_dept_copy`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '部门属性',
  `level` int(11) NULL DEFAULT NULL COMMENT '部门级别',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 192 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(11) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 366 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 191 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_logininfor_s`(`status`) USING BTREE,
  INDEX `idx_sys_logininfor_lt`(`login_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4405 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由名称',
  `is_frame` int(11) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(11) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1179 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(11) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(11) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(11) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type`) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status`) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20243 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(11) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `company_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '业财审核能够审核的公司',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_actual_costs
-- ----------------------------
DROP TABLE IF EXISTS `t_actual_costs`;
CREATE TABLE `t_actual_costs`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_id` bigint(20) NULL DEFAULT NULL COMMENT '文件ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '填报目录',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门',
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '预算科目ID',
  `actual_incurred` decimal(12, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）（本位币借方）',
  `estimated_incurred` decimal(12, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）（余额）',
  `bwbdf` decimal(12, 2) NULL DEFAULT NULL COMMENT '本位币贷方',
  `slzj` decimal(10, 2) NULL DEFAULT NULL COMMENT '数量增加',
  `sljs` decimal(10, 2) NULL DEFAULT NULL COMMENT '数量减少',
  `fx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '方向',
  `slye` decimal(10, 2) NULL DEFAULT NULL COMMENT '数量余额',
  `zzrq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '总账日期',
  `kjkm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会计科目',
  `kjkmsm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会计科目说明',
  `pzbh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '凭证编号',
  `ly` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '来源',
  `rjztsm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日记账头说明',
  `zy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摘要',
  `bz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '币种',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58962 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '实际费用表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_actual_costs_file
-- ----------------------------
DROP TABLE IF EXISTS `t_actual_costs_file`;
CREATE TABLE `t_actual_costs_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '源文件',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件路径',
  `info_num` int(11) NULL DEFAULT NULL COMMENT '明细数量',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '实际费用文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_adjustment
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_adjustment`;
CREATE TABLE `t_budget_adjustment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `budget_year` int(11) NULL DEFAULT NULL COMMENT '预算年份',
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '调整科目',
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '填报表',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '1：年度 2：季度（不做统计）',
  `out_dept` bigint(20) NULL DEFAULT NULL COMMENT '调出部门',
  `in_dept` bigint(20) NULL DEFAULT NULL COMMENT '调入部门',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '调整金额',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'OA编号',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算调整（OA填报）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_adjustment_info
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_adjustment_info`;
CREATE TABLE `t_budget_adjustment_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adjustment_id` bigint(20) NULL DEFAULT NULL,
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '调整科目',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '1：年度 2：季度（不做统计）',
  `out_dept` bigint(20) NULL DEFAULT NULL COMMENT '调出部门',
  `in_dept` bigint(20) NULL DEFAULT NULL COMMENT '调入部门',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '调整金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算调整明细（OA填报）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_item
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_item`;
CREATE TABLE `t_budget_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_display_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '显示表名',
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据表名',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预算类型',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '管控部门',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '预算科目',
  `reporting_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '填报类型 1：动态预算 2：订制预算 3:工资薪酬',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态标志（0代表存在 1代表停用 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `table_display_name`(`table_display_name`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `subject_id`(`subject_id`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE,
  INDEX `type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算项目配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_item_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_item_dept`;
CREATE TABLE `t_budget_item_dept`  (
  `item_id` bigint(20) NOT NULL COMMENT '填报项目',
  `dept_id` bigint(20) NOT NULL COMMENT '填报部门',
  PRIMARY KEY (`item_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '填报科目部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_oa_adjustment
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_oa_adjustment`;
CREATE TABLE `t_budget_oa_adjustment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '任务ID',
  `out_dept` bigint(20) NULL DEFAULT NULL COMMENT '调出部门',
  `out_subject` bigint(20) NULL DEFAULT NULL COMMENT '调出科目',
  `in_dept` bigint(20) NULL DEFAULT NULL COMMENT '调入部门',
  `in_subject` bigint(20) NULL DEFAULT NULL COMMENT '调入科目',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '调整金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OA预算填报调整' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_subject`;
CREATE TABLE `t_budget_subject`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父id',
  `level` int(11) NULL DEFAULT 1 COMMENT '科目级别',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '祖级列表',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预算科目',
  `report_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上报科目',
  `budget_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用类型',
  `financel_subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '财务科目',
  `special_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模板科目',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '科目类型  1：销售  2：管理  3：研发  4：制造',
  `growth_rate` double(10, 2) NULL DEFAULT NULL COMMENT '增长率',
  `budget_ratio` double(10, 2) NULL DEFAULT NULL COMMENT '预算比例',
  `hz_export` int(11) NULL DEFAULT NULL COMMENT '销售是否统计',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `xs_export` int(11) NULL DEFAULT NULL COMMENT '销售是否统计',
  `xs_number` int(11) NULL DEFAULT NULL COMMENT '销售排序',
  `gl_export` int(11) NULL DEFAULT NULL COMMENT '管理是否统计',
  `gl_number` int(11) NULL DEFAULT NULL COMMENT '管理排序',
  `yf_export` int(11) NULL DEFAULT NULL COMMENT '研发是否统计',
  `yf_number` int(11) NULL DEFAULT NULL COMMENT '研发排序',
  `zz_export` int(11) NULL DEFAULT NULL COMMENT '制造是否统计',
  `zz_number` int(11) NULL DEFAULT NULL COMMENT '制造排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE,
  INDEX `financel_subject`(`financel_subject`) USING BTREE,
  INDEX `special_type`(`special_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 230 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算科目' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_subject_finance
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_subject_finance`;
CREATE TABLE `t_budget_subject_finance`  (
  `subject_id` bigint(20) NOT NULL COMMENT '科目ID',
  `financel_subject` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '财务科目',
  PRIMARY KEY (`subject_id`, `financel_subject`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算科目财务科目' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_warning
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_warning`;
CREATE TABLE `t_budget_warning`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '任务ID',
  `type` int(11) NULL DEFAULT NULL COMMENT '预警类型 1：填报预警  2：超出预警 3：费用报销 4：经济事项 5：未及时审核',
  `item_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '填报表ID（填报预警）',
  `start_time` datetime NULL DEFAULT NULL COMMENT '预警时间',
  `proportion` double(5, 2) NULL DEFAULT NULL COMMENT '比例（超出预警）',
  `version_id` bigint(20) NULL DEFAULT NULL COMMENT '版本ID（超出预警）',
  `remind` int(11) NULL DEFAULT NULL COMMENT '提醒：1：一日一次  2：一周一次',
  `last_time` datetime NULL DEFAULT NULL COMMENT '上次预警时间',
  `subjects` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '科目（超出预警）',
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项名称（经济事项）',
  `company_id` bigint(20) NULL DEFAULT NULL COMMENT '公司id（经济事项）',
  `dept_id` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '部门id（经济事项）',
  `pending_review_days` int(11) NULL DEFAULT NULL COMMENT '未及时审核天数（未及时审核）',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态：1：开启  2：关闭',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预警配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_budget_warning_info
-- ----------------------------
DROP TABLE IF EXISTS `t_budget_warning_info`;
CREATE TABLE `t_budget_warning_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '任务名称',
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '表ID',
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '表名',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `parent_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '任务名称',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '任务id',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '任务名称',
  `actual_money` decimal(20, 2) NULL DEFAULT NULL COMMENT '实际发生费用',
  `budget_money` decimal(20, 2) NULL DEFAULT NULL COMMENT '预算费用',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型 1：填报提醒  2：超出提醒',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '提示内容',
  `created_time` datetime NULL DEFAULT NULL COMMENT '提示时间',
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '预算科目id',
  `subject_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预算科目名称',
  `proportion` double(10, 2) NULL DEFAULT NULL COMMENT '比例',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态标志（0代表存在  2代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预警表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reported_subjects
-- ----------------------------
DROP TABLE IF EXISTS `t_reported_subjects`;
CREATE TABLE `t_reported_subjects`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上报名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '科目类型  1：销售  2：管理  3：研发  4：制造',
  `budget_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用类型',
  `subjects` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '填报科目',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `one_quarterly_ratio` decimal(10, 2) NULL DEFAULT NULL COMMENT '1季度比例',
  `two_quarterly_ratio` decimal(10, 2) NULL DEFAULT NULL COMMENT '2季度比例',
  `three_quarterly_ratio` decimal(10, 2) NULL DEFAULT NULL COMMENT '3季度比例',
  `four_quarterly_ratio` decimal(10, 2) NULL DEFAULT NULL COMMENT '4季度比例',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 490 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '上报科目' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_log
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_log`;
CREATE TABLE `t_reporting_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '填报项目ID',
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预算ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '操作部门',
  `status` int(11) NULL DEFAULT NULL COMMENT '操作状态（1：提交申请  2：审批通过 3：审批驳回）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注描述',
  `time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE,
  INDEX `budget_id`(`budget_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11709 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '填报记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_charity
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_charity`;
CREATE TABLE `t_reporting_table_charity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '填报部门',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用预算（元）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详情说明',
  `audit_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `last_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年预算（元）',
  `last_accumulated_expenses` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年累计发生费用（元）',
  `adjustment_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `adjustment_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调剂明细',
  `projected_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【慈善公益】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_charity_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_charity_version`;
CREATE TABLE `t_reporting_table_charity_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '填报部门',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用预算（元）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详情说明',
  `audit_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `last_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年预算（元）',
  `last_accumulated_expenses` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年累计发生费用（元）',
  `adjustment_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `adjustment_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调剂明细',
  `projected_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【慈善公益】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic1
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic1`;
CREATE TABLE `t_reporting_table_dynamic1`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用具购置】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic10
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic10`;
CREATE TABLE `t_reporting_table_dynamic10`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（非安全类）劳动保护】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic10_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic10_extension`;
CREATE TABLE `t_reporting_table_dynamic10_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` int(11) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field2` int(11) NULL DEFAULT NULL COMMENT '公共劳保',
  `field3` int(11) NULL DEFAULT NULL COMMENT '工装',
  `field4` int(11) NULL DEFAULT NULL COMMENT '劳保鞋',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公共劳保',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工装',
  `field13` int(11) NULL DEFAULT NULL COMMENT '劳保鞋',
  `field14` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field16` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field17` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field18` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field24` decimal(10, 2) NULL DEFAULT NULL COMMENT '工装',
  `field25` decimal(10, 2) NULL DEFAULT NULL COMMENT '公共劳保',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（非安全类）劳动保护】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic10_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic10_extension_version`;
CREATE TABLE `t_reporting_table_dynamic10_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` int(11) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field2` int(11) NULL DEFAULT NULL COMMENT '公共劳保',
  `field3` int(11) NULL DEFAULT NULL COMMENT '工装',
  `field4` int(11) NULL DEFAULT NULL COMMENT '劳保鞋',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公共劳保',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工装',
  `field13` int(11) NULL DEFAULT NULL COMMENT '劳保鞋',
  `field14` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field15` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field16` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field17` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field18` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field24` decimal(10, 2) NULL DEFAULT NULL COMMENT '工装',
  `field25` decimal(10, 2) NULL DEFAULT NULL COMMENT '公共劳保',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（非安全类）劳动保护】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic10_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic10_version`;
CREATE TABLE `t_reporting_table_dynamic10_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（非安全类）劳动保护】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic11
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic11`;
CREATE TABLE `t_reporting_table_dynamic11`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【消防费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic11_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic11_extension`;
CREATE TABLE `t_reporting_table_dynamic11_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field6` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field7` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【消防费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic11_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic11_extension_version`;
CREATE TABLE `t_reporting_table_dynamic11_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field6` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field7` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【消防费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic11_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic11_version`;
CREATE TABLE `t_reporting_table_dynamic11_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【消防费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic12
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic12`;
CREATE TABLE `t_reporting_table_dynamic12`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【资产保险费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic12_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic12_extension`;
CREATE TABLE `t_reporting_table_dynamic12_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【资产保险费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic12_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic12_extension_version`;
CREATE TABLE `t_reporting_table_dynamic12_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【资产保险费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic12_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic12_version`;
CREATE TABLE `t_reporting_table_dynamic12_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【资产保险费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic13
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic13`;
CREATE TABLE `t_reporting_table_dynamic13`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全责任险】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic13_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic13_extension`;
CREATE TABLE `t_reporting_table_dynamic13_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全责任险】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic13_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic13_extension_version`;
CREATE TABLE `t_reporting_table_dynamic13_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全责任险】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic13_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic13_version`;
CREATE TABLE `t_reporting_table_dynamic13_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全责任险】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic14
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic14`;
CREATE TABLE `t_reporting_table_dynamic14`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【组织工作经费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic14_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic14_extension`;
CREATE TABLE `t_reporting_table_dynamic14_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` int(11) NULL DEFAULT NULL COMMENT '党员人数',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年党组织费用预算（元）',
  `field3` int(11) NULL DEFAULT NULL COMMENT '团员人数',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年团组织费用预算（元）',
  `field5` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【组织工作经费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic14_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic14_extension_version`;
CREATE TABLE `t_reporting_table_dynamic14_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` int(11) NULL DEFAULT NULL COMMENT '党员人数',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年党组织费用预算（元）',
  `field3` int(11) NULL DEFAULT NULL COMMENT '团员人数',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年团组织费用预算（元）',
  `field5` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【组织工作经费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic14_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic14_version`;
CREATE TABLE `t_reporting_table_dynamic14_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【组织工作经费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic15
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic15`;
CREATE TABLE `t_reporting_table_dynamic15`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【广告费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic15_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic15_extension`;
CREATE TABLE `t_reporting_table_dynamic15_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【广告费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic15_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic15_extension_version`;
CREATE TABLE `t_reporting_table_dynamic15_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【广告费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic15_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic15_version`;
CREATE TABLE `t_reporting_table_dynamic15_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【广告费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic16
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic16`;
CREATE TABLE `t_reporting_table_dynamic16`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【票据印刷费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic16_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic16_extension`;
CREATE TABLE `t_reporting_table_dynamic16_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【票据印刷费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic16_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic16_extension_version`;
CREATE TABLE `t_reporting_table_dynamic16_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【票据印刷费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic16_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic16_version`;
CREATE TABLE `t_reporting_table_dynamic16_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【票据印刷费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic17
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic17`;
CREATE TABLE `t_reporting_table_dynamic17`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【非安全类宣传费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic17_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic17_extension`;
CREATE TABLE `t_reporting_table_dynamic17_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '服务类预算（元）',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '百尊类预算（元）',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '市场类预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '其他类预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'VI更新（仅用于宣传）（元）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field7` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【非安全类宣传费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic17_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic17_extension_version`;
CREATE TABLE `t_reporting_table_dynamic17_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '服务类预算（元）',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '百尊类预算（元）',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '市场类预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '其他类预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'VI更新（仅用于宣传）（元）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field7` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【非安全类宣传费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic17_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic17_version`;
CREATE TABLE `t_reporting_table_dynamic17_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【非安全类宣传费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic18
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic18`;
CREATE TABLE `t_reporting_table_dynamic18`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全类宣传费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic18_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic18_extension`;
CREATE TABLE `t_reporting_table_dynamic18_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '安全类预算（元）',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全类宣传费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic18_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic18_extension_version`;
CREATE TABLE `t_reporting_table_dynamic18_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '安全类预算（元）',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全类宣传费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic18_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic18_version`;
CREATE TABLE `t_reporting_table_dynamic18_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【安全类宣传费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic19
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic19`;
CREATE TABLE `t_reporting_table_dynamic19`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【IT耗材】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic19_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic19_extension`;
CREATE TABLE `t_reporting_table_dynamic19_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '耗材名称',
  `field2` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【IT耗材】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic19_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic19_extension_version`;
CREATE TABLE `t_reporting_table_dynamic19_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '耗材名称',
  `field2` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【IT耗材】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic19_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic19_version`;
CREATE TABLE `t_reporting_table_dynamic19_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【IT耗材】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic1_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic1_extension`;
CREATE TABLE `t_reporting_table_dynamic1_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024结算资金及明细',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '今年结算资金及明细',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '今年结算资金及明细',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '今年结算资金及明细',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编制说明',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2023预计结算总金额',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023全年预计结算金额（万元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field12` int(11) NULL DEFAULT NULL COMMENT '2026年',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '大大后年预计金额',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field20` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field21` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field22` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field23` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年结算资金及明细（万元）',
  `field24` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field25` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年预计结算总金额',
  `field26` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field27` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field28` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field29` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field30` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  `field31` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field37` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用具购置】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic1_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic1_extension_version`;
CREATE TABLE `t_reporting_table_dynamic1_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024结算资金及明细',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '今年结算资金及明细',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '今年结算资金及明细',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '今年结算资金及明细',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编制说明',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2023预计结算总金额',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023全年预计结算金额（万元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field12` int(11) NULL DEFAULT NULL COMMENT '2026年',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '大大后年预计金额',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field19` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field20` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '办公用具名称',
  `field21` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field22` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field23` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年结算资金及明细（万元）',
  `field24` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field25` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年预计结算总金额',
  `field26` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field27` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field28` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field29` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field30` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  `field31` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field37` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用具购置】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic1_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic1_version`;
CREATE TABLE `t_reporting_table_dynamic1_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用具购置】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic2
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic2`;
CREATE TABLE `t_reporting_table_dynamic2`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆购置】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic20
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic20`;
CREATE TABLE `t_reporting_table_dynamic20`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【房屋维修】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic20_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic20_extension`;
CREATE TABLE `t_reporting_table_dynamic20_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目类别 （结转项目/新增项目）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修地点',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '维修面积（㎡）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修内容',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'VI（用于房屋维修）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【房屋维修】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic20_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic20_extension_version`;
CREATE TABLE `t_reporting_table_dynamic20_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目类别 （结转项目/新增项目）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修地点',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '维修面积（㎡）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修内容',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'VI（用于房屋维修）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【房屋维修】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic20_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic20_version`;
CREATE TABLE `t_reporting_table_dynamic20_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【房屋维修】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic21
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic21`;
CREATE TABLE `t_reporting_table_dynamic21`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【绿化费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic21_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic21_extension`;
CREATE TABLE `t_reporting_table_dynamic21_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '绿化面积（平方米）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '绿化区域（具体位置）',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年发生费用预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年今年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【绿化费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic21_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic21_extension_version`;
CREATE TABLE `t_reporting_table_dynamic21_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '绿化面积（平方米）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '绿化区域（具体位置）',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年发生费用预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年今年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【绿化费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic21_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic21_version`;
CREATE TABLE `t_reporting_table_dynamic21_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【绿化费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic22
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic22`;
CREATE TABLE `t_reporting_table_dynamic22`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用品费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic22_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic22_extension`;
CREATE TABLE `t_reporting_table_dynamic22_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '标准 （元/人/月）',
  `field2` int(11) NULL DEFAULT NULL COMMENT '人数',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '办公用品预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '打印纸预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field6` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用品费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic22_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic22_extension_version`;
CREATE TABLE `t_reporting_table_dynamic22_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '标准 （元/人/月）',
  `field2` int(11) NULL DEFAULT NULL COMMENT '人数',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '办公用品预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '打印纸预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field6` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用品费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic22_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic22_version`;
CREATE TABLE `t_reporting_table_dynamic22_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公用品费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic23
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic23`;
CREATE TABLE `t_reporting_table_dynamic23`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【书报费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic23_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic23_extension`;
CREATE TABLE `t_reporting_table_dynamic23_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` int(11) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field6` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field7` int(11) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【书报费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic23_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic23_extension_version`;
CREATE TABLE `t_reporting_table_dynamic23_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` int(11) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field6` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field7` int(11) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【书报费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic23_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic23_version`;
CREATE TABLE `t_reporting_table_dynamic23_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【书报费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic24
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic24`;
CREATE TABLE `t_reporting_table_dynamic24`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【卫生保洁费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic24_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic24_extension`;
CREATE TABLE `t_reporting_table_dynamic24_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '保洁具体位置',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '具体项目',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【卫生保洁费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic24_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic24_extension_version`;
CREATE TABLE `t_reporting_table_dynamic24_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '保洁具体位置',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '具体项目',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【卫生保洁费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic24_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic24_version`;
CREATE TABLE `t_reporting_table_dynamic24_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【卫生保洁费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic25
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic25`;
CREATE TABLE `t_reporting_table_dynamic25`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【物业管理费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic25_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic25_extension`;
CREATE TABLE `t_reporting_table_dynamic25_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物业具体位置',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '具体项目',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【物业管理费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic25_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic25_extension_version`;
CREATE TABLE `t_reporting_table_dynamic25_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物业具体位置',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '具体项目',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【物业管理费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic25_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic25_version`;
CREATE TABLE `t_reporting_table_dynamic25_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【物业管理费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic26
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic26`;
CREATE TABLE `t_reporting_table_dynamic26`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水电费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic26_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic26_extension`;
CREATE TABLE `t_reporting_table_dynamic26_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年总费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024用电金额（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '审核调整原因',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水电费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic26_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic26_extension_version`;
CREATE TABLE `t_reporting_table_dynamic26_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年总费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024用电金额（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '审核调整原因',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水电费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic26_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic26_version`;
CREATE TABLE `t_reporting_table_dynamic26_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水电费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic27
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic27`;
CREATE TABLE `t_reporting_table_dynamic27`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【防汛费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic27_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic27_extension`;
CREATE TABLE `t_reporting_table_dynamic27_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物资名称',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【防汛费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic27_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic27_extension_version`;
CREATE TABLE `t_reporting_table_dynamic27_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物资名称',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【防汛费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic27_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic27_version`;
CREATE TABLE `t_reporting_table_dynamic27_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【防汛费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic28
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic28`;
CREATE TABLE `t_reporting_table_dynamic28`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【保安费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic28_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic28_extension`;
CREATE TABLE `t_reporting_table_dynamic28_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设置保安地点',
  `field2` int(11) NULL DEFAULT NULL COMMENT '保安人数',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '每人每月（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【保安费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic28_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic28_extension_version`;
CREATE TABLE `t_reporting_table_dynamic28_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设置保安地点',
  `field2` int(11) NULL DEFAULT NULL COMMENT '保安人数',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '每人每月（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【保安费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic28_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic28_version`;
CREATE TABLE `t_reporting_table_dynamic28_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【保安费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic29
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic29`;
CREATE TABLE `t_reporting_table_dynamic29`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic29_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic29_extension`;
CREATE TABLE `t_reporting_table_dynamic29_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁地点',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic29_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic29_extension_version`;
CREATE TABLE `t_reporting_table_dynamic29_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁地点',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic29_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic29_version`;
CREATE TABLE `t_reporting_table_dynamic29_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic2_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic2_extension`;
CREATE TABLE `t_reporting_table_dynamic2_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '车辆名称',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024结算资金及明细（万元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性质 (更新或新购)',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023全年预计结算金额 （万元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  `field19` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '车辆类型（生产性/非生产性）',
  `field20` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性质（更新或新购）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆购置】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic2_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic2_extension_version`;
CREATE TABLE `t_reporting_table_dynamic2_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '车辆名称',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024结算资金及明细（万元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性质 (更新或新购)',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023全年预计结算金额 （万元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  `field19` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '车辆类型（生产性/非生产性）',
  `field20` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性质（更新或新购）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆购置】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic2_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic2_version`;
CREATE TABLE `t_reporting_table_dynamic2_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆购置】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic3
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic3`;
CREATE TABLE `t_reporting_table_dynamic3`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【信息设备购置】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic30
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic30`;
CREATE TABLE `t_reporting_table_dynamic30`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic30_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic30_extension`;
CREATE TABLE `t_reporting_table_dynamic30_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '台数',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '通行费',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '停车费',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '燃料费',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '修理费',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '年审费',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '车船税',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '保险费',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '其他',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field14` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field16` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic30_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic30_extension_version`;
CREATE TABLE `t_reporting_table_dynamic30_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '台数',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '通行费',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '停车费',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '燃料费',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '修理费',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '年审费',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '车船税',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '保险费',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '其他',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field14` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field16` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic30_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic30_version`;
CREATE TABLE `t_reporting_table_dynamic30_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic31
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic31`;
CREATE TABLE `t_reporting_table_dynamic31`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic31_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic31_extension`;
CREATE TABLE `t_reporting_table_dynamic31_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修项目',
  `field2` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '维修单价（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年预算（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年累计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修项目',
  `field14` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '维修单价（元）',
  `field16` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field19` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field20` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic31_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic31_extension_version`;
CREATE TABLE `t_reporting_table_dynamic31_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修项目',
  `field2` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '维修单价（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年预算（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年累计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修项目',
  `field14` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '维修单价（元）',
  `field16` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field18` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field19` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field20` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic31_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic31_version`;
CREATE TABLE `t_reporting_table_dynamic31_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic32
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic32`;
CREATE TABLE `t_reporting_table_dynamic32`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【通讯费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic32_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic32_extension`;
CREATE TABLE `t_reporting_table_dynamic32_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目类别 （固话或专线）',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '数量 （专线需要填编号）',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年每部/月计划金额（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '每部/月计划金额（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【通讯费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic32_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic32_extension_version`;
CREATE TABLE `t_reporting_table_dynamic32_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目类别 （固话或专线）',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '数量 （专线需要填编号）',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年每部/月计划金额（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '每部/月计划金额（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【通讯费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic32_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic32_version`;
CREATE TABLE `t_reporting_table_dynamic32_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【通讯费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic33
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic33`;
CREATE TABLE `t_reporting_table_dynamic33`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【业务招待费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic33_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic33_extension`;
CREATE TABLE `t_reporting_table_dynamic33_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【业务招待费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic33_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic33_extension_version`;
CREATE TABLE `t_reporting_table_dynamic33_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【业务招待费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic33_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic33_version`;
CREATE TABLE `t_reporting_table_dynamic33_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【业务招待费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic34
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic34`;
CREATE TABLE `t_reporting_table_dynamic34`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【会议费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic34_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic34_extension`;
CREATE TABLE `t_reporting_table_dynamic34_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【会议费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic34_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic34_extension_version`;
CREATE TABLE `t_reporting_table_dynamic34_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【会议费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic34_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic34_version`;
CREATE TABLE `t_reporting_table_dynamic34_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【会议费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic35
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic35`;
CREATE TABLE `t_reporting_table_dynamic35`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic35_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic35_extension`;
CREATE TABLE `t_reporting_table_dynamic35_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic35_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic35_extension_version`;
CREATE TABLE `t_reporting_table_dynamic35_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项',
  `field2` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic35_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic35_version`;
CREATE TABLE `t_reporting_table_dynamic35_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic36
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic36`;
CREATE TABLE `t_reporting_table_dynamic36`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic36_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic36_extension`;
CREATE TABLE `t_reporting_table_dynamic36_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数量',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic36_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic36_extension_version`;
CREATE TABLE `t_reporting_table_dynamic36_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数量',
  `field3` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调整明细（登记调整OA编号及调整科目）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic36_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic36_version`;
CREATE TABLE `t_reporting_table_dynamic36_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic37
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic37`;
CREATE TABLE `t_reporting_table_dynamic37`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗（加臭剂）】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic37_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic37_extension`;
CREATE TABLE `t_reporting_table_dynamic37_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '加臭剂数量（吨）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗（加臭剂）】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic37_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic37_extension_version`;
CREATE TABLE `t_reporting_table_dynamic37_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '加臭剂数量（吨）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗（加臭剂）】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic37_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic37_version`;
CREATE TABLE `t_reporting_table_dynamic37_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【机物料消耗（加臭剂）】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic38
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic38`;
CREATE TABLE `t_reporting_table_dynamic38`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-法律类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic38_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic38_extension`;
CREATE TABLE `t_reporting_table_dynamic38_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项 （诉讼费和法律服务费等）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-法律类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic38_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic38_extension_version`;
CREATE TABLE `t_reporting_table_dynamic38_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项 （诉讼费和法律服务费等）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-法律类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic38_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic38_version`;
CREATE TABLE `t_reporting_table_dynamic38_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-法律类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic39
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic39`;
CREATE TABLE `t_reporting_table_dynamic39`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-信息类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic39_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic39_extension`;
CREATE TABLE `t_reporting_table_dynamic39_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-信息类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic39_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic39_extension_version`;
CREATE TABLE `t_reporting_table_dynamic39_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-信息类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic39_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic39_version`;
CREATE TABLE `t_reporting_table_dynamic39_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-信息类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic3_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic3_extension`;
CREATE TABLE `t_reporting_table_dynamic3_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备名称',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '部门现有该类设备数量',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '需使用该类设备的岗位',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '需使用该类设备的人数',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024结算资金及明细（万元）',
  `field8` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024全年预计结算金额 （万元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field20` int(11) NULL DEFAULT NULL COMMENT '2026年',
  `field21` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `field22` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【信息设备购置】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic3_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic3_extension_version`;
CREATE TABLE `t_reporting_table_dynamic3_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备名称',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '部门现有该类设备数量',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '需使用该类设备的岗位',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '需使用该类设备的人数',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024结算资金及明细（万元）',
  `field8` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024全年预计结算金额 （万元）',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field14` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field20` int(11) NULL DEFAULT NULL COMMENT '2026年',
  `field21` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `field22` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【信息设备购置】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic3_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic3_version`;
CREATE TABLE `t_reporting_table_dynamic3_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【信息设备购置】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic4
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic4`;
CREATE TABLE `t_reporting_table_dynamic4`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【生产设备购置】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic40
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic40`;
CREATE TABLE `t_reporting_table_dynamic40`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-技术、咨询类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic40_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic40_extension`;
CREATE TABLE `t_reporting_table_dynamic40_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-技术、咨询类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic40_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic40_extension_version`;
CREATE TABLE `t_reporting_table_dynamic40_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-技术、咨询类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic40_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic40_version`;
CREATE TABLE `t_reporting_table_dynamic40_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-技术、咨询类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic41
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic41`;
CREATE TABLE `t_reporting_table_dynamic41`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-安全类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic41_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic41_extension`;
CREATE TABLE `t_reporting_table_dynamic41_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全生产检查咨询类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-安全类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic41_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic41_extension_version`;
CREATE TABLE `t_reporting_table_dynamic41_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全生产检查咨询类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-安全类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic41_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic41_version`;
CREATE TABLE `t_reporting_table_dynamic41_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【专业机构费-安全类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic42
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic42`;
CREATE TABLE `t_reporting_table_dynamic42`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公费（其他类）】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic42_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic42_extension`;
CREATE TABLE `t_reporting_table_dynamic42_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公费（其他类）】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic42_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic42_extension_version`;
CREATE TABLE `t_reporting_table_dynamic42_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公费（其他类）】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic42_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic42_version`;
CREATE TABLE `t_reporting_table_dynamic42_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【办公费（其他类）】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic43
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic43`;
CREATE TABLE `t_reporting_table_dynamic43`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【差旅费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic43_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic43_extension`;
CREATE TABLE `t_reporting_table_dynamic43_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【差旅费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic43_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic43_extension_version`;
CREATE TABLE `t_reporting_table_dynamic43_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【差旅费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic43_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic43_version`;
CREATE TABLE `t_reporting_table_dynamic43_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【差旅费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic44
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic44`;
CREATE TABLE `t_reporting_table_dynamic44`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【交通费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic44_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic44_extension`;
CREATE TABLE `t_reporting_table_dynamic44_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【交通费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic44_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic44_extension_version`;
CREATE TABLE `t_reporting_table_dynamic44_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【交通费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic44_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic44_version`;
CREATE TABLE `t_reporting_table_dynamic44_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【交通费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic45
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic45`;
CREATE TABLE `t_reporting_table_dynamic45`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备一般性维修】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic45_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic45_extension`;
CREATE TABLE `t_reporting_table_dynamic45_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备一般性维修】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic45_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic45_extension_version`;
CREATE TABLE `t_reporting_table_dynamic45_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '事项',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备一般性维修】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic45_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic45_version`;
CREATE TABLE `t_reporting_table_dynamic45_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备一般性维修】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic46
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic46`;
CREATE TABLE `t_reporting_table_dynamic46`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（管理销售）】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic46_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic46_extension`;
CREATE TABLE `t_reporting_table_dynamic46_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（管理销售）】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic46_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic46_extension_version`;
CREATE TABLE `t_reporting_table_dynamic46_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（管理销售）】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic46_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic46_version`;
CREATE TABLE `t_reporting_table_dynamic46_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（管理销售）】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic47
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic47`;
CREATE TABLE `t_reporting_table_dynamic47`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（研发）】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic47_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic47_extension`;
CREATE TABLE `t_reporting_table_dynamic47_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '折旧费用计提基数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '折旧费用详细说明',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（研发）】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic47_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic47_extension_version`;
CREATE TABLE `t_reporting_table_dynamic47_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '折旧费用计提基数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '折旧费用详细说明',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（研发）】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic47_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic47_version`;
CREATE TABLE `t_reporting_table_dynamic47_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧摊销（研发）】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic48
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic48`;
CREATE TABLE `t_reporting_table_dynamic48`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【协会会费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic48_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic48_extension`;
CREATE TABLE `t_reporting_table_dynamic48_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【协会会费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic48_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic48_extension_version`;
CREATE TABLE `t_reporting_table_dynamic48_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【协会会费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic48_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic48_version`;
CREATE TABLE `t_reporting_table_dynamic48_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【协会会费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic49
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic49`;
CREATE TABLE `t_reporting_table_dynamic49`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【能源费气费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic49_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic49_extension`;
CREATE TABLE `t_reporting_table_dynamic49_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【能源费气费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic49_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic49_extension_version`;
CREATE TABLE `t_reporting_table_dynamic49_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【能源费气费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic49_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic49_version`;
CREATE TABLE `t_reporting_table_dynamic49_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【能源费气费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic4_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic4_extension`;
CREATE TABLE `t_reporting_table_dynamic4_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生产设备名称',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生产设备名称',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field4` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年结算资金及明细（万元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2025年',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2026年',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2027年',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2028年',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field19` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `field20` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【生产设备购置】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic4_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic4_extension_version`;
CREATE TABLE `t_reporting_table_dynamic4_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生产设备名称',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生产设备名称',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field4` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年结算资金及明细（万元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2025年',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2026年',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2027年',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2028年',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field19` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `field20` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【生产设备购置】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic4_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic4_version`;
CREATE TABLE `t_reporting_table_dynamic4_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【生产设备购置】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic5
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic5`;
CREATE TABLE `t_reporting_table_dynamic5`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【计量器具购置】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic50
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic50`;
CREATE TABLE `t_reporting_table_dynamic50`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【运输费及装卸驳运费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic50_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic50_extension`;
CREATE TABLE `t_reporting_table_dynamic50_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【运输费及装卸驳运费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic50_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic50_extension_version`;
CREATE TABLE `t_reporting_table_dynamic50_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【运输费及装卸驳运费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic50_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic50_version`;
CREATE TABLE `t_reporting_table_dynamic50_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【运输费及装卸驳运费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic51
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic51`;
CREATE TABLE `t_reporting_table_dynamic51`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧(销售)】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic51_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic51_extension`;
CREATE TABLE `t_reporting_table_dynamic51_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧(销售)】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic51_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic51_extension_version`;
CREATE TABLE `t_reporting_table_dynamic51_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧(销售)】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic51_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic51_version`;
CREATE TABLE `t_reporting_table_dynamic51_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【折旧(销售)】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic52
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic52`;
CREATE TABLE `t_reporting_table_dynamic52`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(销售)】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic52_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic52_extension`;
CREATE TABLE `t_reporting_table_dynamic52_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(销售)】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic52_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic52_extension_version`;
CREATE TABLE `t_reporting_table_dynamic52_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(销售)】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic52_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic52_version`;
CREATE TABLE `t_reporting_table_dynamic52_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(销售)】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic53
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic53`;
CREATE TABLE `t_reporting_table_dynamic53`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(管理)】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic53_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic53_extension`;
CREATE TABLE `t_reporting_table_dynamic53_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(管理)】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic53_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic53_extension_version`;
CREATE TABLE `t_reporting_table_dynamic53_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '摊销费用计提基数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摊销费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(管理)】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic53_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic53_version`;
CREATE TABLE `t_reporting_table_dynamic53_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(管理)】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic54
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic54`;
CREATE TABLE `t_reporting_table_dynamic54`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(研发)】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic54_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic54_extension`;
CREATE TABLE `t_reporting_table_dynamic54_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '折旧费用计提基数',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '折旧费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(研发)】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic54_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic54_extension_version`;
CREATE TABLE `t_reporting_table_dynamic54_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '折旧费用计提基数',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '折旧费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(研发)】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic54_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic54_version`;
CREATE TABLE `t_reporting_table_dynamic54_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【摊销(研发)】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic55
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic55`;
CREATE TABLE `t_reporting_table_dynamic55`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水费表】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic55_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic55_extension`;
CREATE TABLE `t_reporting_table_dynamic55_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水费表】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic55_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic55_extension_version`;
CREATE TABLE `t_reporting_table_dynamic55_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水费表】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic55_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic55_version`;
CREATE TABLE `t_reporting_table_dynamic55_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【水费表】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic56
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic56`;
CREATE TABLE `t_reporting_table_dynamic56`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（汇总）】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic56_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic56_extension`;
CREATE TABLE `t_reporting_table_dynamic56_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '高压管线维修+市区管网维修',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '庭院管网维修（户外+户内）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '加装安全装置',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（汇总）】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic56_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic56_extension_version`;
CREATE TABLE `t_reporting_table_dynamic56_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '高压管线维修+市区管网维修',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '庭院管网维修（户外+户内）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '加装安全装置',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（汇总）】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic56_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic56_version`;
CREATE TABLE `t_reporting_table_dynamic56_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（汇总）】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic57
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic57`;
CREATE TABLE `t_reporting_table_dynamic57`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（加装安全装置）】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic57_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic57_extension`;
CREATE TABLE `t_reporting_table_dynamic57_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（加装安全装置）】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic57_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic57_extension_version`;
CREATE TABLE `t_reporting_table_dynamic57_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（加装安全装置）】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic57_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic57_version`;
CREATE TABLE `t_reporting_table_dynamic57_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修（加装安全装置）】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic58
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic58`;
CREATE TABLE `t_reporting_table_dynamic58`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费-土地租赁】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic58_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic58_extension`;
CREATE TABLE `t_reporting_table_dynamic58_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁地点',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费-土地租赁】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic58_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic58_extension_version`;
CREATE TABLE `t_reporting_table_dynamic58_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁地点',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费-土地租赁】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic58_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic58_version`;
CREATE TABLE `t_reporting_table_dynamic58_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【租赁费-土地租赁】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic59
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic59`;
CREATE TABLE `t_reporting_table_dynamic59`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备租赁】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic59_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic59_extension`;
CREATE TABLE `t_reporting_table_dynamic59_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备名称',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备租赁】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic59_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic59_extension_version`;
CREATE TABLE `t_reporting_table_dynamic59_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备名称',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备租赁】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic59_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic59_version`;
CREATE TABLE `t_reporting_table_dynamic59_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备租赁】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic5_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic5_extension`;
CREATE TABLE `t_reporting_table_dynamic5_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '计量器具名称',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年结算资金及明细（万元）',
  `field5` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编制说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `field19` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【计量器具购置】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic5_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic5_extension_version`;
CREATE TABLE `t_reporting_table_dynamic5_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '计量器具名称',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '型号及要求',
  `field3` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field4` decimal(10, 2) NULL DEFAULT NULL COMMENT '2024年结算资金及明细（万元）',
  `field5` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '购置原因',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '编制说明',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '2023年全年预计结算金额 （万元）',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '2025年',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '2026年',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '2027年',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '2028年',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '管控部门',
  `field18` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `field19` decimal(10, 2) NULL DEFAULT NULL COMMENT '2029年',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【计量器具购置】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic5_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic5_version`;
CREATE TABLE `t_reporting_table_dynamic5_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【计量器具购置】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic6
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic6`;
CREATE TABLE `t_reporting_table_dynamic6`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic60
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic60`;
CREATE TABLE `t_reporting_table_dynamic60`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆租赁】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic60_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic60_extension`;
CREATE TABLE `t_reporting_table_dynamic60_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` int(11) NULL DEFAULT NULL COMMENT '租赁车牌号',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆租赁】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic60_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic60_extension_version`;
CREATE TABLE `t_reporting_table_dynamic60_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` int(11) NULL DEFAULT NULL COMMENT '租赁车牌号',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁时间',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '对方名称',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '租赁事由',
  `field10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆租赁】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic60_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic60_version`;
CREATE TABLE `t_reporting_table_dynamic60_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆租赁】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic61
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic61`;
CREATE TABLE `t_reporting_table_dynamic61`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用（槽车）】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic61_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic61_extension`;
CREATE TABLE `t_reporting_table_dynamic61_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '台数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '车辆类型',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '通行费',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '停车费',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '燃料费',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '修理费',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '年审费',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '车船税',
  `field14` decimal(10, 2) NULL DEFAULT NULL COMMENT '保险费',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '其他',
  `field16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用（槽车）】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic61_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic61_extension_version`;
CREATE TABLE `t_reporting_table_dynamic61_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` decimal(10, 2) NULL DEFAULT NULL COMMENT '台数',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '车辆类型',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '通行费',
  `field9` decimal(10, 2) NULL DEFAULT NULL COMMENT '停车费',
  `field10` decimal(10, 2) NULL DEFAULT NULL COMMENT '燃料费',
  `field11` decimal(10, 2) NULL DEFAULT NULL COMMENT '修理费',
  `field12` decimal(10, 2) NULL DEFAULT NULL COMMENT '年审费',
  `field13` decimal(10, 2) NULL DEFAULT NULL COMMENT '车船税',
  `field14` decimal(10, 2) NULL DEFAULT NULL COMMENT '保险费',
  `field15` decimal(10, 2) NULL DEFAULT NULL COMMENT '其他',
  `field16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用（槽车）】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic61_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic61_version`;
CREATE TABLE `t_reporting_table_dynamic61_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【车辆费用（槽车）】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic62
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic62`;
CREATE TABLE `t_reporting_table_dynamic62`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-1专业机构费-安全类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic62_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic62_extension`;
CREATE TABLE `t_reporting_table_dynamic62_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全生产检查咨询类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-1专业机构费-安全类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic62_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic62_extension_version`;
CREATE TABLE `t_reporting_table_dynamic62_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全生产检查咨询类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-1专业机构费-安全类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic62_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic62_version`;
CREATE TABLE `t_reporting_table_dynamic62_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-1专业机构费-安全类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic63
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic63`;
CREATE TABLE `t_reporting_table_dynamic63`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-2专业机构费-安全类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic63_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic63_extension`;
CREATE TABLE `t_reporting_table_dynamic63_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全宣传教育及培训类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用明细',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-2专业机构费-安全类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic63_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic63_extension_version`;
CREATE TABLE `t_reporting_table_dynamic63_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全宣传教育及培训类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用明细',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-2专业机构费-安全类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic63_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic63_version`;
CREATE TABLE `t_reporting_table_dynamic63_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-2专业机构费-安全类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic64
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic64`;
CREATE TABLE `t_reporting_table_dynamic64`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-3专业机构费-安全类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic64_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic64_extension`;
CREATE TABLE `t_reporting_table_dynamic64_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全设备检验检测类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-3专业机构费-安全类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic64_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic64_extension_version`;
CREATE TABLE `t_reporting_table_dynamic64_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业机构费事项（安全设备检验检测类）',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-3专业机构费-安全类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic64_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic64_version`;
CREATE TABLE `t_reporting_table_dynamic64_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【40-5-3专业机构费-安全类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic65
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic65`;
CREATE TABLE `t_reporting_table_dynamic65`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【邮电费】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic65_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic65_extension`;
CREATE TABLE `t_reporting_table_dynamic65_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【邮电费】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic65_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic65_extension_version`;
CREATE TABLE `t_reporting_table_dynamic65_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【邮电费】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic65_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic65_version`;
CREATE TABLE `t_reporting_table_dynamic65_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【邮电费】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic66
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic66`;
CREATE TABLE `t_reporting_table_dynamic66`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费-信息类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic66_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic66_extension`;
CREATE TABLE `t_reporting_table_dynamic66_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修信息类用品名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费-信息类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic66_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic66_extension_version`;
CREATE TABLE `t_reporting_table_dynamic66_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '维修信息类用品名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价（元）',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费-信息类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic66_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic66_version`;
CREATE TABLE `t_reporting_table_dynamic66_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【维修费-信息类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic67
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic67`;
CREATE TABLE `t_reporting_table_dynamic67`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-工具购置类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic67_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic67_extension`;
CREATE TABLE `t_reporting_table_dynamic67_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-工具购置类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic67_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic67_extension_version`;
CREATE TABLE `t_reporting_table_dynamic67_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-工具购置类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic67_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic67_version`;
CREATE TABLE `t_reporting_table_dynamic67_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-工具购置类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic68
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic68`;
CREATE TABLE `t_reporting_table_dynamic68`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-办公用品类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic68_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic68_extension`;
CREATE TABLE `t_reporting_table_dynamic68_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-办公用品类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic68_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic68_extension_version`;
CREATE TABLE `t_reporting_table_dynamic68_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-办公用品类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic68_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic68_version`;
CREATE TABLE `t_reporting_table_dynamic68_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-办公用品类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic69
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic69`;
CREATE TABLE `t_reporting_table_dynamic69`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-计量器类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic69_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic69_extension`;
CREATE TABLE `t_reporting_table_dynamic69_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-计量器类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic69_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic69_extension_version`;
CREATE TABLE `t_reporting_table_dynamic69_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field7` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-计量器类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic69_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic69_version`;
CREATE TABLE `t_reporting_table_dynamic69_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-计量器类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic6_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic6_extension`;
CREATE TABLE `t_reporting_table_dynamic6_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目类别',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目分类',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单位',
  `field4` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2025年预算数据（单位：元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field8` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field15` int(11) NULL DEFAULT NULL COMMENT '综合单价（元）（可拆分成材料费单价（元）和人工费单价（元））（单位：元）',
  `field16` decimal(10, 2) NULL DEFAULT NULL COMMENT '综合单价（元）测算标准或依据（历史数据、合同等）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic6_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic6_extension_version`;
CREATE TABLE `t_reporting_table_dynamic6_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目类别',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目分类',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单位',
  `field4` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2025年预算数据（单位：元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field7` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field8` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field15` int(11) NULL DEFAULT NULL COMMENT '综合单价（元）（可拆分成材料费单价（元）和人工费单价（元））（单位：元）',
  `field16` decimal(10, 2) NULL DEFAULT NULL COMMENT '综合单价（元）测算标准或依据（历史数据、合同等）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic6_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic6_version`;
CREATE TABLE `t_reporting_table_dynamic6_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【管网维修】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic7
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic7`;
CREATE TABLE `t_reporting_table_dynamic7`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备大中修】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic70
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic70`;
CREATE TABLE `t_reporting_table_dynamic70`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-信息类】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic70_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic70_extension`;
CREATE TABLE `t_reporting_table_dynamic70_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-信息类】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic70_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic70_extension_version`;
CREATE TABLE `t_reporting_table_dynamic70_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` int(11) NULL DEFAULT NULL COMMENT '数量',
  `field7` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `field8` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `field9` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-信息类】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic70_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic70_version`;
CREATE TABLE `t_reporting_table_dynamic70_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【表44低值易耗品-信息类】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic71
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic71`;
CREATE TABLE `t_reporting_table_dynamic71`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【其他费用】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic71_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic71_extension`;
CREATE TABLE `t_reporting_table_dynamic71_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【其他费用】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic71_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic71_extension_version`;
CREATE TABLE `t_reporting_table_dynamic71_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field6` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【其他费用】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic71_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic71_version`;
CREATE TABLE `t_reporting_table_dynamic71_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【其他费用】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic7_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic7_extension`;
CREATE TABLE `t_reporting_table_dynamic7_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备大中修】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic7_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic7_extension_version`;
CREATE TABLE `t_reporting_table_dynamic7_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备大中修】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic7_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic7_version`;
CREATE TABLE `t_reporting_table_dynamic7_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【设备大中修】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic8
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic8`;
CREATE TABLE `t_reporting_table_dynamic8`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【特种设备检验】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic8_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic8_extension`;
CREATE TABLE `t_reporting_table_dynamic8_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【特种设备检验】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic8_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic8_extension_version`;
CREATE TABLE `t_reporting_table_dynamic8_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field3` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field4` int(11) NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field5` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【特种设备检验】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic8_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic8_version`;
CREATE TABLE `t_reporting_table_dynamic8_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【特种设备检验】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic9
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic9`;
CREATE TABLE `t_reporting_table_dynamic9`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '提交人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（安全类）劳动保护】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic9_extension
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic9_extension`;
CREATE TABLE `t_reporting_table_dynamic9_extension`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field1` int(11) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field2` int(11) NULL DEFAULT NULL COMMENT '公共劳保',
  `field3` int(11) NULL DEFAULT NULL COMMENT '工装',
  `field4` int(11) NULL DEFAULT NULL COMMENT '劳保鞋',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field10` int(11) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公共劳保',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工装',
  `field13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '劳保鞋',
  `field14` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field15` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field16` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field18` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field19` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（安全类）劳动保护】扩展' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic9_extension_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic9_extension_version`;
CREATE TABLE `t_reporting_table_dynamic9_extension_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `field1` int(11) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field2` int(11) NULL DEFAULT NULL COMMENT '公共劳保',
  `field3` int(11) NULL DEFAULT NULL COMMENT '工装',
  `field4` int(11) NULL DEFAULT NULL COMMENT '劳保鞋',
  `field5` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年费用预算（元）',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说',
  `field7` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `field9` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field10` int(11) NULL DEFAULT NULL COMMENT '日常劳保 （标准500元/人）',
  `field11` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公共劳保',
  `field12` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工装',
  `field13` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '劳保鞋',
  `field14` int(11) NULL DEFAULT NULL COMMENT '2025年费用预算（元）',
  `field15` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `field16` int(11) NULL DEFAULT NULL COMMENT '2024年预算（元）',
  `field17` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '2024年累计发生费用（元）',
  `field18` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  `field19` int(11) NULL DEFAULT NULL COMMENT '2024年全年预计发生费用（元）',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（安全类）劳动保护】扩展版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_dynamic9_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_dynamic9_version`;
CREATE TABLE `t_reporting_table_dynamic9_version`  (
  `budget_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '填报ID',
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '预算',
  `budget_year` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年预算',
  `actual_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '本年实际发生额（元）',
  `estimated_incurred` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `cost_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详细说明',
  `review_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算',
  `review_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`budget_id`, `version_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报动态表【（安全类）劳动保护】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_housing
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_housing`;
CREATE TABLE `t_reporting_table_housing`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类型：1：房屋建筑物-办公楼 2：房屋装修 3：房屋建筑物-房屋装修 4：房屋建筑物-土地',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '投资分析',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '已结算资金',
  `actual_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结算资金明细',
  `expected_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年预计结算金额',
  `audit_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算金额',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `project_type`(`project_type`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【房屋建设、装修】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_housing_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_housing_version`;
CREATE TABLE `t_reporting_table_housing_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类型：1：房屋建设 2：房屋装修',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '投资分析',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '已结算资金',
  `actual_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结算资金明细',
  `expected_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年预计结算金额',
  `audit_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算金额',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `project_type`(`project_type`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【房屋建设、装修】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_information_system
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_information_system`;
CREATE TABLE `t_reporting_table_information_system`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `operational_time` datetime NULL DEFAULT NULL COMMENT '拟投运时间',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '截止去年已结算资金',
  `actual_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `intangible_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '无形资产',
  `fixed_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '固定资产',
  `audit_intangible_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核无形资产',
  `audit_fixed_assets` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职能部门审核固定资产',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【信息系统】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_information_system_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_information_system_version`;
CREATE TABLE `t_reporting_table_information_system_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `operational_time` datetime NULL DEFAULT NULL COMMENT '拟投运时间',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '截止去年已结算资金',
  `actual_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `intangible_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '无形资产',
  `fixed_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '固定资产',
  `audit_intangible_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核无形资产',
  `audit_fixed_assets` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职能部门审核固定资产',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【信息系统】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_lowvalue
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_lowvalue`;
CREATE TABLE `t_reporting_table_lowvalue`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '填报部门',
  `reporting_type` int(11) NULL DEFAULT NULL COMMENT '1:工具购置类 2：办公用品类 3：计量器具类 4：信息类',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格（元）',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用预算（元）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详情说明',
  `audit_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `last_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年预算（元）',
  `last_accumulated_expenses` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年累计发生费用（元）',
  `adjustment_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `adjustment_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调剂明细',
  `projected_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `reporting_type`(`reporting_type`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【低值易耗品】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_lowvalue_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_lowvalue_version`;
CREATE TABLE `t_reporting_table_lowvalue_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '填报部门',
  `reporting_type` int(11) NULL DEFAULT NULL COMMENT '1:工具购置类 2：办公用品类 3：计量器具类 4：信息类',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格（元）',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用预算（元）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详情说明',
  `audit_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `last_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年预算（元）',
  `last_accumulated_expenses` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年累计发生费用（元）',
  `adjustment_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `adjustment_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调剂明细',
  `projected_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `reporting_type`(`reporting_type`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【低值易耗品】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_meter
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_meter`;
CREATE TABLE `t_reporting_table_meter`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '填报部门',
  `reporting_type` int(11) NULL DEFAULT NULL COMMENT '填报类型：1:安全类  2：非安全类',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类别：1:校准 2：维修 3：改造',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格（元）',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用预算（元）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详情说明',
  `audit_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `last_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年预算（元）',
  `last_accumulated_expenses` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年累计发生费用（元）',
  `adjustment_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `adjustment_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调剂明细',
  `projected_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `reporting_type`(`reporting_type`) USING BTREE,
  INDEX `project_type`(`project_type`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 513 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【计量器】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_meter_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_meter_version`;
CREATE TABLE `t_reporting_table_meter_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '填报部门',
  `reporting_type` int(11) NULL DEFAULT NULL COMMENT '填报类型：1:安全类  2：非安全类',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类别：1:校准 2：维修 3：改造',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格（元）',
  `budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用预算（元）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用详情说明',
  `audit_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `last_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年预算（元）',
  `last_accumulated_expenses` decimal(10, 2) NULL DEFAULT NULL COMMENT '上年累计发生费用（元）',
  `adjustment_budget` decimal(10, 2) NULL DEFAULT NULL COMMENT '调剂后预算额度',
  `adjustment_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '调剂明细',
  `projected_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '全年预计发生费用（元）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `reporting_type`(`reporting_type`) USING BTREE,
  INDEX `project_type`(`project_type`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 244 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【计量器】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_pipeline
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_pipeline`;
CREATE TABLE `t_reporting_table_pipeline`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类型：1：燃气管道-高压管网 2：燃气管道-中压管网 3：燃气管道-低压管网 4：燃气管道-其他',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '投资分析',
  `length` decimal(10, 2) NULL DEFAULT NULL COMMENT '管网总长度',
  `investment` decimal(12, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '已完结长度',
  `completed_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '已完结资金',
  `actual_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算长度',
  `actual_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结算资金明细',
  `expected_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '预计结算长度',
  `expected_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '预计结算金额',
  `audit_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算长度',
  `audit_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算金额',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1预计结算长度',
  `year1_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年1预计结算金额',
  `year2_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2预计结算长度',
  `year2_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年2预计结算金额',
  `year3_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3预计结算长度',
  `year3_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年3预计结算金额',
  `year4_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4预计结算长度',
  `year4_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年4预计结算金额',
  `year5_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年5预计结算长度',
  `year5_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年5预计结算金额',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【天然气管线】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_pipeline_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_pipeline_version`;
CREATE TABLE `t_reporting_table_pipeline_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类型：1：燃气管道-高压管网 2：燃气管道-中压管网 3：燃气管道-低压管网 4：燃气管道-其他',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '投资分析',
  `length` decimal(10, 2) NULL DEFAULT NULL COMMENT '管网总长度',
  `investment` decimal(12, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '已完结长度',
  `completed_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '已完结资金',
  `actual_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算长度',
  `actual_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结算资金明细',
  `expected_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '预计结算长度',
  `expected_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '预计结算金额',
  `audit_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算长度',
  `audit_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算金额',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1预计结算长度',
  `year1_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年1预计结算金额',
  `year2_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2预计结算长度',
  `year2_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年2预计结算金额',
  `year3_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3预计结算长度',
  `year3_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年3预计结算金额',
  `year4_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4预计结算长度',
  `year4_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年4预计结算金额',
  `year5_length` decimal(10, 2) NULL DEFAULT NULL COMMENT '年5预计结算长度',
  `year5_settlement` decimal(12, 2) NULL DEFAULT NULL COMMENT '年5预计结算金额',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【天然气管线】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_research
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_research`;
CREATE TABLE `t_reporting_table_research`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '立项单位',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '1:技术创新 2：技术引进 3：软课题研究',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目编号',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `cooperative_unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '协作单位',
  `start_time` datetime NULL DEFAULT NULL COMMENT '计划开题时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '计划结题时间',
  `research_contents` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主要研究内容',
  `deliverables` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目预期主要成果形式',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '项目总预算金额（万元）',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '已支出金额（万元）',
  `expected_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '预计支出总金额（万元）',
  `intangible_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '无形资产',
  `fixed_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '固定资产',
  `fixed_assets_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资产预算说明',
  `subtotal_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用小计',
  `subtotal_costs_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用预算说明',
  `audit_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_type`(`project_type`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【科研计划申报】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_research_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_research_version`;
CREATE TABLE `t_reporting_table_research_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '立项单位',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '1:技术创新 2：技术引进 3：软课题研究',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目编号',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `cooperative_unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '协作单位',
  `start_time` datetime NULL DEFAULT NULL COMMENT '计划开题时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '计划结题时间',
  `research_contents` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主要研究内容',
  `deliverables` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目预期主要成果形式',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '项目总预算金额（万元）',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '已支出金额（万元）',
  `expected_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '预计支出总金额（万元）',
  `intangible_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '无形资产',
  `fixed_assets` decimal(10, 2) NULL DEFAULT NULL COMMENT '固定资产',
  `fixed_assets_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资产预算说明',
  `subtotal_costs` decimal(10, 2) NULL DEFAULT NULL COMMENT '费用小计',
  `subtotal_costs_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '费用预算说明',
  `audit_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部室审核后预算（元）',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_type`(`project_type`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【科研计划申报】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_station
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_station`;
CREATE TABLE `t_reporting_table_station`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类型：\r\n1：CNG加气站\r\n2：LNG加气站\r\n3：L-CNG合建站\r\n4：LPG加气站\r\n5：门站\r\n6：高中压调压站\r\n7：气源厂|储配站\r\n8：其他一\r\n9：其他二\r\n10：其他三',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '截止去年已结算金额',
  `actual_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结算资金明细',
  `expected_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年预计结算金额',
  `audit_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算金额',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【场站】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_station_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_station_version`;
CREATE TABLE `t_reporting_table_station_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `project_status` int(11) NULL DEFAULT NULL COMMENT '项目状态：1:续建项目 2:新增项目',
  `project_type` int(11) NULL DEFAULT NULL COMMENT '项目类型：\r\n1：CNG加气站\r\n2：LNG加气站\r\n3：L-CNG合建站\r\n4：LPG加气站\r\n5：门站\r\n6：高中压调压站\r\n7：气源厂|储配站\r\n8：其他一\r\n9：其他二\r\n10：其他三',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目名称',
  `situation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '项目现状',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `content_progress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '建设内容、进度',
  `investment` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资总额',
  `completed_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '截止去年已结算金额',
  `actual_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '今年实际结算金额',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '结算资金明细',
  `expected_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '明年预计结算金额',
  `audit_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '职能部门审核结算金额',
  `audit_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核调整原因',
  `year1_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年1',
  `year2_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年2',
  `year3_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年3',
  `year4_settlement` decimal(10, 2) NULL DEFAULT NULL COMMENT '年4',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `project_status`(`project_status`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【场站】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_wages
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_wages`;
CREATE TABLE `t_reporting_table_wages`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门',
  `staff` decimal(10, 2) NULL DEFAULT NULL COMMENT '员工',
  `labor` decimal(10, 2) NULL DEFAULT NULL COMMENT '劳务费',
  `welfare` decimal(10, 2) NULL DEFAULT NULL COMMENT '福利费',
  `education` decimal(10, 2) NULL DEFAULT NULL COMMENT '教育经费',
  `union_funds` decimal(10, 2) NULL DEFAULT NULL COMMENT '工会经费',
  `endowment` decimal(10, 2) NULL DEFAULT NULL COMMENT '养老保险',
  `work_injury` decimal(10, 2) NULL DEFAULT NULL COMMENT '工伤保险',
  `unemployment` decimal(10, 2) NULL DEFAULT NULL COMMENT '失业保险',
  `medical` decimal(10, 2) NULL DEFAULT NULL COMMENT '医疗保险',
  `maternity` decimal(10, 2) NULL DEFAULT NULL COMMENT '生育保险',
  `social` decimal(10, 2) NULL DEFAULT NULL COMMENT '社会保险费-单位部分',
  `provident` decimal(10, 2) NULL DEFAULT NULL COMMENT '住房公积金-单位部分',
  `annuity` decimal(10, 2) NULL DEFAULT NULL COMMENT '企业年金-单位部分',
  `research` decimal(10, 2) NULL DEFAULT NULL COMMENT '研发人工成本',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1：新添加 2：已提交 3：职能审核通过 4：职能审核拒绝 5：业财审核通过 6：业财审核拒绝）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【工资福利】' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_table_wages_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_table_wages_version`;
CREATE TABLE `t_reporting_table_wages_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_id` bigint(20) NOT NULL COMMENT '版本ID',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '填报任务ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门',
  `staff` decimal(10, 2) NULL DEFAULT NULL COMMENT '员工',
  `labor` decimal(10, 2) NULL DEFAULT NULL COMMENT '劳务费',
  `welfare` decimal(10, 2) NULL DEFAULT NULL COMMENT '福利费',
  `education` decimal(10, 2) NULL DEFAULT NULL COMMENT '教育经费',
  `union_funds` decimal(10, 2) NULL DEFAULT NULL COMMENT '工会经费',
  `endowment` decimal(10, 2) NULL DEFAULT NULL COMMENT '养老保险',
  `work_injury` decimal(10, 2) NULL DEFAULT NULL COMMENT '工伤保险',
  `unemployment` decimal(10, 2) NULL DEFAULT NULL COMMENT '失业保险',
  `medical` decimal(10, 2) NULL DEFAULT NULL COMMENT '医疗保险',
  `maternity` decimal(10, 2) NULL DEFAULT NULL COMMENT '生育保险',
  `social` decimal(10, 2) NULL DEFAULT NULL COMMENT '社会保险费-单位部分',
  `provident` decimal(10, 2) NULL DEFAULT NULL COMMENT '住房公积金-单位部分',
  `annuity` decimal(10, 2) NULL DEFAULT NULL COMMENT '企业年金-单位部分',
  `research` decimal(10, 2) NULL DEFAULT NULL COMMENT '研发人工成本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `version_id`(`version_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报订制表【工资福利】版本数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_task
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_task`;
CREATE TABLE `t_reporting_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '填报任务ID',
  `last_task` bigint(20) NULL DEFAULT NULL COMMENT '上次任务',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `budget_year` int(11) NULL DEFAULT NULL COMMENT '预算年度',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '任务名称',
  `start_time` datetime NULL DEFAULT NULL COMMENT '起始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `reporting_explain` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '填报说明',
  `completion_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '完成情况 （1：进行中 2：已完成）',
  `audit_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核状态 （1：待审核 2：已通过 3：未通过）',
  `audit_explain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核说明',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `year`(`year`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `completion_status`(`completion_status`) USING BTREE,
  INDEX `audit_status`(`audit_status`) USING BTREE,
  INDEX `budget_year`(`budget_year`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算填报任务任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_task_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_task_dept`;
CREATE TABLE `t_reporting_task_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NULL DEFAULT NULL,
  `dept_id` bigint(20) NULL DEFAULT NULL,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门任务填报情况' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_reporting_version
-- ----------------------------
DROP TABLE IF EXISTS `t_reporting_version`;
CREATE TABLE `t_reporting_version`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '版本名称',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '1' COMMENT '审核状态（1：生成中 2：生成成功  3：生成失败）',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '任务ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `status`(`status`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预算版本控制' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_table_model
-- ----------------------------
DROP TABLE IF EXISTS `t_table_model`;
CREATE TABLE `t_table_model`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NULL DEFAULT NULL COMMENT '填报项目ID',
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '库表名称',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字段名称（规则生成）',
  `field_display_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字段显示名称',
  `type` int(11) NULL DEFAULT 2 COMMENT '字段类型 1:系统字段 2:扩展字段',
  `field_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字段屬性（1:字符 2：整数 3：小数 4：字典）',
  `field_length` int(11) NULL DEFAULT NULL COMMENT '字段长度',
  `field_decimal_length` int(11) NULL DEFAULT NULL COMMENT '小数长度',
  `dict_id` bigint(20) NULL DEFAULT NULL COMMENT '字典ID',
  `field_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否必填（0：是 1：否）',
  `field_display` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否显示（0：是 1：否）',
  `field_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否查询（0：是 1：否）',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '删除标志（0代表正常 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE,
  INDEX `del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 854 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '填报表模块' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_task_tips
-- ----------------------------
DROP TABLE IF EXISTS `t_task_tips`;
CREATE TABLE `t_task_tips`  (
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  INDEX `task_id`(`task_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '任务须知弹窗提示' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_wages_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_wages_subject`;
CREATE TABLE `t_wages_subject`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字段名',
  `field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '字段',
  `type` bigint(20) NULL DEFAULT NULL COMMENT '费用科目',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '工资字段类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_warning
-- ----------------------------
DROP TABLE IF EXISTS `t_warning`;
CREATE TABLE `t_warning`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '部门名称',
  `actual_money` decimal(20, 2) NULL DEFAULT NULL COMMENT '实际发生费用',
  `budget_money` decimal(20, 2) NULL DEFAULT NULL COMMENT '预算费用',
  `money_type` int(11) NULL DEFAULT NULL COMMENT '预警状态（1：未超出，2：已超出）实际费用和预算费用对比',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '任务id',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '任务名称',
  `message_type` int(11) NULL DEFAULT NULL COMMENT '提示消息类型（1：费用预警）',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '提示内容',
  `created_time` datetime NULL DEFAULT NULL COMMENT '提示时间',
  `reamrk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `year` int(11) NULL DEFAULT NULL COMMENT '年度',
  `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '预算科目id',
  `subject_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预算科目名称',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '0' COMMENT '状态标志（0代表存在 1代表停用 2代表删除）',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预警表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Procedure structure for actual_cost_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `actual_cost_by_dept`;
delimiter ;;
CREATE PROCEDURE `actual_cost_by_dept`(IN p_task_id INT, IN p_parent_id INT)
BEGIN
    
    SET @sql = '';
    
    
    SET @base_subquery = '
    SELECT
			d.dept_id, d.dept_name, IFNULL( SUM( t.actual_incurred ), 0 ) - IFNULL( SUM( t.bwbdf ), 0 ) AS total 
		FROM
			`t_actual_costs` t
			LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
			LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
		WHERE
			t.del_flag = "0" 
			AND t.zzrq IS NOT NULL 
			AND t.subject_id != 228
			AND t.subject_id != 50
			AND t.subject_id IS NOT NULL ';
    
    
    SET @conditions = '';
    IF p_task_id IS NOT NULL THEN
        SET @conditions = CONCAT(@conditions, ' and t.`year` = (SELECT t.budget_year FROM t_reporting_task 	t WHERE t.id = ', p_task_id ,')');
    END IF;
    IF p_parent_id != 1 THEN
        SET @conditions = CONCAT(@conditions, ' AND d.parent_id = ', p_parent_id);
    END IF;
    
    
    SET @subquery = CONCAT(@base_subquery, @conditions, ' GROUP BY d.dept_id, d.dept_name');
    
    
    IF p_parent_id != 1 THEN
        
        SET @sql = CONCAT('
        SELECT
            t.dept_id AS Id,
            t.dept_name AS areaName,
            t.total AS total
        FROM (', @subquery, ') t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
        ORDER BY t.dept_id');
    ELSE
        
        SET @sql = CONCAT('
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total) AS total
        FROM (', @subquery, ') t  
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY 
            company.dept_id, company.dept_name
        ORDER BY 
            company.dept_id');
    END IF;
    
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for actual_cost_by_month
-- ----------------------------
DROP PROCEDURE IF EXISTS `actual_cost_by_month`;
delimiter ;;
CREATE PROCEDURE `actual_cost_by_month`(IN p_task_id INT, IN p_dept_id INT, IN type INT)
BEGIN
    DECLARE v_sql VARCHAR(1000);
    DECLARE v_where_condition VARCHAR(5000) DEFAULT '';
    
    
    IF p_task_id IS NOT NULL THEN
        
        SET v_where_condition = CONCAT(' AND t.year = (SELECT t.budget_year 
                                      FROM t_reporting_task t 
                                      WHERE t.id = ', p_task_id, ')');
    END IF;
		
		
		IF p_dept_id != 1 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' AND t.dept_id IN (SELECT dept_id 
																											 FROM sys_dept 
																											 WHERE parent_id = ', 
																											 p_dept_id, ')');
		END IF;
		
		IF type = 53 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' AND t.subject_id in (SELECT s.id
				from t_budget_subject s
				left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
				WHERE s.del_flag = "0" and dd.dict_label = "与员工成本相关的费用")');
		END IF;
		
		IF type = 61 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' and t.subject_id in (SELECT s.id
				from t_budget_subject s
				left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
				WHERE s.del_flag = "0" and dd.dict_label = "与资产投资相关费用")');
		END IF;
		
		IF type = 62 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' and t.subject_id in (SELECT s.id
				from t_budget_subject s
				left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
				WHERE s.del_flag = "0" and dd.dict_label = "与安全生产相关费用")');
		END IF;
		
		IF type = 63 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' and t.subject_id in (SELECT s.id
				from t_budget_subject s
				left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
				WHERE s.del_flag = "0" and dd.dict_label = "与业务拓展相关的费用")');
		END IF;
		
		IF type = 60 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' and t.subject_id in (SELECT s.id
				from t_budget_subject s
				left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
				WHERE s.del_flag = "0" and dd.dict_label = "与日常生产经营相关的费用")');
		END IF;
		
		IF type = 59 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' and t.subject_id in (SELECT s.id
				from t_budget_subject s
				left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
				WHERE s.del_flag = "0" and dd.dict_label = "其他可控费用")');
		END IF;
    
    
    SET v_sql = CONCAT('SELECT DATE_FORMAT(t.zzrq, "%m") AS month, IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
				FROM  `t_actual_costs` t
				WHERE t.del_flag = "0" and  t.zzrq IS NOT NULL and t.subject_id is not NULL AND t.subject_id != 228 AND t.subject_id != 50 ',
												v_where_condition, ' GROUP BY month');
    
    
    SET @sql = v_sql;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for actual_cost_by_year
-- ----------------------------
DROP PROCEDURE IF EXISTS `actual_cost_by_year`;
delimiter ;;
CREATE PROCEDURE `actual_cost_by_year`(IN p_year INT, IN typeCode INT, IN p_dept_id INT)
BEGIN
    DECLARE v_sql VARCHAR(5000);
		DECLARE v_where_condition VARCHAR(500) DEFAULT '';
		
		
		IF p_dept_id != 1 THEN
				SET v_where_condition = CONCAT(' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', p_dept_id, ') ');
		END IF; 
    
    
    SET v_sql = CONCAT('select IFNULL(SUM(t.total), 0) as total
								from
								(SELECT t.`year`, t.dept_id, t.subject_id,"0" AS month,SUM(t.actual_incurred) AS actual_incurred, SUM(t.bwbdf) AS bwbdf ,  IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
												FROM  `t_actual_costs` t
												WHERE t.del_flag = "0" and t.zzrq IS NOT NULL AND t.subject_id IS NOT NULL AND t.subject_id != 228 AND t.subject_id != 50 AND t.`year` = ', p_year ,'
												GROUP BY t.`year`, t.dept_id, t.subject_id
												ORDER BY year, dept_id, subject_id, month ) t
								where t.month = 0 ', v_where_condition ,' 
								and t.subject_id in (
									select s.id
									from t_budget_subject s
									left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
									where dd.dict_code = ', typeCode ,' and s.del_flag = 0 )' );
     
    
    SET @sql = v_sql;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for actual_cost_lastyear
-- ----------------------------
DROP PROCEDURE IF EXISTS `actual_cost_lastyear`;
delimiter ;;
CREATE PROCEDURE `actual_cost_lastyear`(IN p_task_id INT, IN p_dept_id INT)
BEGIN
    DECLARE v_sql VARCHAR(1000);
    DECLARE v_where_condition VARCHAR(500) DEFAULT '';
    
    
    IF p_task_id IS NOT NULL THEN
        
        SET v_where_condition = CONCAT(' AND t.year = (SELECT (t.budget_year - 1) as budget_year 
                                      FROM t_reporting_task t 
                                      WHERE t.id = ', p_task_id, ')');
    END IF;
		
		
		IF p_dept_id != 1 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' AND t.dept_id IN (SELECT dept_id 
																											 FROM sys_dept 
																											 WHERE parent_id = ', 
																											 p_dept_id, ')');
		END IF;


    
    
    SET v_sql = CONCAT('SELECT IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
									FROM  `t_actual_costs` t
									LEFT JOIN t_budget_subject s on t.subject_id = s.id
									WHERE t.del_flag = "0" and t.zzrq IS NOT NULL AND t.subject_id IS NOT NULL AND t.subject_id != 228 ', 
									v_where_condition);
    
    
    SET @sql = v_sql;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Aqsc
-- ----------------------------
DROP PROCEDURE IF EXISTS `Aqsc`;
delimiter ;;
CREATE PROCEDURE `Aqsc`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND ( dd.dict_label = '与安全生产相关费用' or t.table_name = 'meter' ) ;
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
				
				
        IF table_name = 'meter' THEN
            SET where_condition = CONCAT(where_condition, ' AND project_type IS NOT NULL');
        END IF;
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                         table_name, where_condition);
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for calculate_actual_cost
-- ----------------------------
DROP PROCEDURE IF EXISTS `calculate_actual_cost`;
delimiter ;;
CREATE PROCEDURE `calculate_actual_cost`(IN p_task_id INT, IN gap INT, IN p_dept_id INT)
BEGIN
    DECLARE v_sql VARCHAR(1000);
    DECLARE v_where_condition VARCHAR(500) DEFAULT '';
    
    
    IF p_task_id IS NOT NULL THEN
        
        SET v_where_condition = CONCAT(' AND t.year = (SELECT t.budget_year - ', gap ,'
                                      FROM t_reporting_task t 
                                      WHERE t.id = ', p_task_id, ')');
    END IF;
		
		
		IF p_dept_id != 1 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' AND t.dept_id IN (SELECT dept_id 
																											 FROM sys_dept 
																											 WHERE parent_id = ', 
																											 p_dept_id, ')');
		END IF;


    
    
    SET v_sql = CONCAT('SELECT IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
									FROM  `t_actual_costs` t
									LEFT JOIN t_budget_subject s on t.subject_id = s.id
									WHERE t.del_flag = "0" and t.zzrq IS NOT NULL AND t.subject_id IS NOT NULL AND t.subject_id != 228 AND t.subject_id != 50 ', 
									v_where_condition);
    
    
    SET @sql = v_sql;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for calculate_total_money1
-- ----------------------------
DROP PROCEDURE IF EXISTS `calculate_total_money1`;
delimiter ;;
CREATE PROCEDURE `calculate_total_money1`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT t.table_name 
        FROM t_budget_item t 
        WHERE t.reporting_type = '1' AND t.del_flag = 0;
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                         table_name, where_condition);
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for calculate_total_money2
-- ----------------------------
DROP PROCEDURE IF EXISTS `calculate_total_money2`;
delimiter ;;
CREATE PROCEDURE `calculate_total_money2`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE base_condition VARCHAR(200) DEFAULT ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
    DECLARE full_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT t.table_name 
        FROM t_budget_item t 
        WHERE t.reporting_type = '2' AND t.del_flag = 0;
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET full_condition = base_condition;
        
        
        IF p_taskId IS NOT NULL THEN
            SET full_condition = CONCAT(full_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId IS NOT NULL THEN
            SET full_condition = CONCAT(full_condition, 
                                      ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                      p_deptId, ')');
        END IF;
        
        
        IF table_name IN ('pipeline', 'station', 'housing') THEN
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(expected_settlement), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, full_condition);
        ELSEIF table_name IN ('research', 'information_system') THEN
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(intangible_assets) + SUM(fixed_assets), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, full_condition);
        ELSE
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, full_condition);
        END IF;
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for fydb_sjfy
-- ----------------------------
DROP PROCEDURE IF EXISTS `fydb_sjfy`;
delimiter ;;
CREATE PROCEDURE `fydb_sjfy`(IN p_task_id INT, IN gap INT ,IN p_dept_id INT)
BEGIN
    DECLARE v_sql VARCHAR(1000);
    DECLARE v_where_condition VARCHAR(500) DEFAULT '';
    
    
    IF p_task_id IS NOT NULL THEN
        
        SET v_where_condition = CONCAT(' and t.year = (SELECT t.budget_year - ', gap ,'
                                      FROM t_reporting_task t 
                                      WHERE t.id = ', p_task_id, ')');
        

    END IF;
		
		IF p_dept_id != 1 THEN
				SET v_where_condition = CONCAT(v_where_condition, 
																		 ' AND t.dept_id IN (SELECT dept_id 
																											 FROM sys_dept 
																											 WHERE parent_id = ', 
																											 p_dept_id, ')');
		END IF;

    
    
    SET v_sql = CONCAT('SELECT 
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total, 
            dd.dict_label AS itemType
        FROM  
            t_actual_costs t
        LEFT JOIN 
            t_budget_subject s ON t.subject_id = s.id
        JOIN 
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
						where t.del_flag = 0             
						AND t.zzrq IS NOT NULL 
            AND t.subject_id IS NOT NULL 
            AND t.subject_id != 228
						AND t.subject_id != 50 ', v_where_condition, ' GROUP BY dd.dict_label');
    
    
    SET @sql = v_sql;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for fydb_sjfy_by_type
-- ----------------------------
DROP PROCEDURE IF EXISTS `fydb_sjfy_by_type`;
delimiter ;;
CREATE PROCEDURE `fydb_sjfy_by_type`(IN p_task_id INT, IN p_dept_id INT, IN type INT)
BEGIN
    DECLARE v_year INT;
    DECLARE v_dept_ids TEXT;
    DECLARE v_subject_ids TEXT;
    
    
    IF p_task_id IS NOT NULL THEN
        SELECT budget_year INTO v_year FROM t_reporting_task WHERE id = p_task_id;
    END IF;
    
    
    IF p_dept_id != 1 THEN
        SELECT GROUP_CONCAT(dept_id) INTO v_dept_ids 
        FROM sys_dept 
        WHERE parent_id = p_dept_id;
    END IF;
    
    
    CASE type
        WHEN 53 THEN
            SELECT GROUP_CONCAT(s.id) INTO v_subject_ids
            FROM t_budget_subject s
            LEFT JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
            WHERE s.del_flag = '0' AND dd.dict_label = '与员工成本相关的费用';
            
        WHEN 61 THEN
            SELECT GROUP_CONCAT(s.id) INTO v_subject_ids
            FROM t_budget_subject s
            LEFT JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
            WHERE s.del_flag = '0' AND dd.dict_label = '与资产投资相关费用';
            
        WHEN 62 THEN
            SELECT GROUP_CONCAT(s.id) INTO v_subject_ids
            FROM t_budget_subject s
            LEFT JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
            WHERE s.del_flag = '0' AND dd.dict_label = '与安全生产相关费用';
            
        WHEN 63 THEN
            SELECT GROUP_CONCAT(s.id) INTO v_subject_ids
            FROM t_budget_subject s
            LEFT JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
            WHERE s.del_flag = '0' AND dd.dict_label = '与业务拓展相关的费用';
            
        WHEN 60 THEN
            SELECT GROUP_CONCAT(s.id) INTO v_subject_ids
            FROM t_budget_subject s
            LEFT JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
            WHERE s.del_flag = '0' AND dd.dict_label = '与日常生产经营相关的费用';
            
        WHEN 59 THEN
            SELECT GROUP_CONCAT(s.id) INTO v_subject_ids
            FROM t_budget_subject s
            LEFT JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
            WHERE s.del_flag = '0' AND dd.dict_label = '其他可控费用';
    END CASE;
    
    
    SET @sql = CONCAT('
        SELECT 
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total, 
            dd.dict_label AS itemType
        FROM  
            t_actual_costs t
        LEFT JOIN 
            t_budget_subject s ON t.subject_id = s.id
        JOIN 
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
        WHERE 
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id IS NOT NULL 
            AND t.subject_id != 228
            ', IF(v_year IS NOT NULL, CONCAT(' AND t.year = ', v_year), ''), '
            ', IF(v_dept_ids IS NOT NULL, CONCAT(' AND t.dept_id IN (', v_dept_ids, ')'), ''), '
            ', IF(v_subject_ids IS NOT NULL, CONCAT(' AND t.subject_id IN (', v_subject_ids, ')'), ''), '
        GROUP BY 
            itemType
    ');
    
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GetPreviousYearTaskId
-- ----------------------------
DROP PROCEDURE IF EXISTS `GetPreviousYearTaskId`;
delimiter ;;
CREATE PROCEDURE `GetPreviousYearTaskId`(IN p_current_task_id INT, IN gap INT)
BEGIN
    DECLARE v_current_year INT;
    DECLARE v_prev_year INT;
    DECLARE v_prev_task_id INT DEFAULT NULL;
    
    
    SELECT budget_year INTO v_current_year
		FROM t_reporting_task
		WHERE id = p_current_task_id;
		
    
    IF v_current_year IS NOT NULL THEN
        SET v_prev_year = v_current_year - gap;  
        
        
				SELECT
					t.task_id INTO v_prev_task_id
				FROM t_reporting_version t
					LEFT JOIN t_reporting_task r ON t.task_id = r.id
				where t.`year` = v_prev_year
				ORDER BY t.create_time desc LIMIT 1;
    END IF;
    
    
    SELECT v_prev_task_id AS previous_task_id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_aqsc_table
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_aqsc_table`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_aqsc_table`(IN taskId INT,          
    IN deptId INT)
BEGIN
    
    SET @sql = NULL;
		
		
    SET @company_id = deptId;
		
		SET @task_id = taskId;
		
		SET @dict_code = 62;
    
    
    SET @sql = CONCAT('
    SELECT
        ', IF(@company_id = 1, 'all_depts.dept_name AS `公司名称`', 'all_depts.dept_name AS `部门名称`'), ',
				SUM(CASE WHEN src.name = ''维修保养费-设备-一般性维修'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修保养费-设备-一般性维修`,
				SUM(CASE WHEN src.name = ''维修保养费-主干管'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修保养费-主干管`,
				SUM(CASE WHEN src.name = ''维修及保养费-建筑物及其附属物'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修及保养费-建筑物及其附属物`,
				SUM(CASE WHEN src.name = ''维修保养费-庭院管'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修保养费-庭院管`,
				SUM(CASE WHEN src.name = ''维修保养费-办公维修（电脑耗材）'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修保养费-办公维修（电脑耗材）`,
				SUM(CASE WHEN src.name = ''维修及保养费-其他'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修及保养费-其他`,
				SUM(CASE WHEN src.name = ''维修保养费-试验检验费'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修保养费-试验检验费`,
				-- SUM(CASE WHEN src.name = ''维修保养费-燃气流量表'' THEN IFNULL(total, 0) ELSE 0 END) AS `维修保养费-燃气流量表`,
				
        SUM(CASE WHEN src.name = ''安全生产费-设备安全防护支出类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-设备安全防护支出类`,
        SUM(CASE WHEN src.name = ''安全生产费-应急设备支出演练类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-应急设备支出演练类`,
        SUM(CASE WHEN src.name = ''安全生产费-管网维护整改评估类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-管网维护整改评估类`,
        SUM(CASE WHEN src.name = ''安全生产费-安全生产检查咨询类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-安全生产检查咨询类`,
        SUM(CASE WHEN src.name = ''安全生产费-人员防护支出类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-人员防护支出类`,
        SUM(CASE WHEN src.name = ''安全生产费-安全宣传教育及培训类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-安全宣传教育及培训类`,
				SUM(CASE WHEN src.name = ''安全生产费-安全生产推广应用类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-安全生产推广应用类`,
				SUM(CASE WHEN src.name = ''安全生产费-安全设备检验检测类'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-安全设备检验检测类`,
				SUM(CASE WHEN src.name = ''安全生产费-安全生产责任保险支出'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-安全生产责任保险支出`,
				SUM(CASE WHEN src.name = ''安全生产费-其他安全生产费'' THEN IFNULL(total, 0) ELSE 0 END) AS `安全生产费-其他安全生产费`
    FROM
        -- 获取所有符合条件的部门（动态company_id）
        (SELECT dept_name FROM sys_dept WHERE del_flag = 0 AND parent_id = ', @company_id, ') AS all_depts
    -- 左连接数据汇总结果
    LEFT JOIN (
        SELECT
            ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ',
            s.name,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
            JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
						', IF(@company_id = 1, ' LEFT JOIN sys_dept sd ON d.parent_id = sd.dept_id ', ' '), '
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = ', @task_id, ')  -- 动态task_id
            AND dd.dict_code = ', @dict_code, '  -- 动态dict_code
            AND ( @company_id = 1 OR d.parent_id = ', @company_id, ' )  -- 动态parent_id
        GROUP BY ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ', s.name
    ) AS src ON all_depts.dept_name = src.dept_name
    GROUP BY all_depts.dept_name
    ');
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_qt_table
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_qt_table`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_qt_table`(IN taskId INT,          
    IN deptId INT)
BEGIN
    
    SET @sql = NULL;
		
		
    SET @company_id = deptId;
		
		SET @task_id = taskId;
		
		SET @dict_code = 59;
    
    
    SET @sql = CONCAT('
    SELECT
        ', IF(@company_id = 1, 'all_depts.dept_name AS `公司名称`', 'all_depts.dept_name AS `部门名称`'), ',
				SUM(CASE WHEN src.name = ''办公费-文具印刷费'' THEN IFNULL(total, 0) ELSE 0 END) AS `办公费-文具印刷费`,
				SUM(CASE WHEN src.name = ''办公费-图书资料费'' THEN IFNULL(total, 0) ELSE 0 END) AS `办公费-图书资料费`,
				SUM(CASE WHEN src.name = ''办公费-通讯费'' THEN IFNULL(total, 0) ELSE 0 END) AS `办公费-通讯费`,
				SUM(CASE WHEN src.name = ''办公费-邮电费'' THEN IFNULL(total, 0) ELSE 0 END) AS `办公费-邮电费`,
				SUM(CASE WHEN src.name = ''办公费-其他-其他办公费'' THEN IFNULL(total, 0) ELSE 0 END) AS `办公费-其他-其他办公费`,
				SUM(CASE WHEN src.name = ''差旅费'' THEN IFNULL(total, 0) ELSE 0 END) AS `差旅费`,
				SUM(CASE WHEN src.name = ''交通费'' THEN IFNULL(total, 0) ELSE 0 END) AS `交通费`,
				SUM(CASE WHEN src.name = ''会议费'' THEN IFNULL(total, 0) ELSE 0 END) AS `会议费`,
				SUM(CASE WHEN src.name = ''车辆费用-路桥费'' THEN IFNULL(total, 0) ELSE 0 END) AS `车辆费用-路桥费`,
				SUM(CASE WHEN src.name = ''车辆费用-车位费'' THEN IFNULL(total, 0) ELSE 0 END) AS `车辆费用-车位费`,
				SUM(CASE WHEN src.name = ''车辆费用-加油（气、电）费'' THEN IFNULL(total, 0) ELSE 0 END) AS `车辆费用-加油（气、电）费`,
				SUM(CASE WHEN src.name = ''车辆费用-维修费'' THEN IFNULL(total, 0) ELSE 0 END) AS `车辆费用-维修费`,
				SUM(CASE WHEN src.name = ''车辆费用-年审费'' THEN IFNULL(total, 0) ELSE 0 END) AS `车辆费用-年审费`,
				SUM(CASE WHEN src.name = ''车辆费用-保险费'' THEN IFNULL(total, 0) ELSE 0 END) AS `车辆费用-保险费`,
				SUM(CASE WHEN src.name = ''车辆费用-其他'' THEN IFNULL(total, 0) ELSE 0 END) AS `车辆费用-其他`,
				SUM(CASE WHEN src.name = ''耗用品费用'' THEN IFNULL(total, 0) ELSE 0 END) AS `耗用品费用`,
				SUM(CASE WHEN src.name = ''交际应酬费'' THEN IFNULL(total, 0) ELSE 0 END) AS `交际应酬费`,
				SUM(CASE WHEN src.name = ''安保服务费'' THEN IFNULL(total, 0) ELSE 0 END) AS `安保服务费`,
				SUM(CASE WHEN src.name = ''其他-消防费用（物业管理费）'' THEN IFNULL(total, 0) ELSE 0 END) AS `其他-消防费用（物业管理费）`,
				SUM(CASE WHEN src.name = ''物业管理费用（大楼物业费）'' THEN IFNULL(total, 0) ELSE 0 END) AS `物业管理费用（大楼物业费）`,
				SUM(CASE WHEN src.name = ''清洁费用'' THEN IFNULL(total, 0) ELSE 0 END) AS `清洁费用`,
				SUM(CASE WHEN src.name = ''其他-绿化养护费'' THEN IFNULL(total, 0) ELSE 0 END) AS `其他-绿化养护费`,
				SUM(CASE WHEN src.name = ''其他-协会会费'' THEN IFNULL(total, 0) ELSE 0 END) AS `其他-协会会费`,
				SUM(CASE WHEN src.name = ''其他-其他'' THEN IFNULL(total, 0) ELSE 0 END) AS `其他-其他`,
				SUM(CASE WHEN src.name = ''其他-机物料消耗'' THEN IFNULL(total, 0) ELSE 0 END) AS `其他-机物料消耗`,
				SUM(CASE WHEN src.name = ''A组织工作经费'' THEN IFNULL(total, 0) ELSE 0 END) AS `A组织工作经费`,
				SUM(CASE WHEN src.name = ''研究开发费用-其他'' THEN IFNULL(total, 0) ELSE 0 END) AS `研究开发费用-其他`		
    FROM
        -- 获取所有符合条件的部门（动态company_id）
        (SELECT dept_name FROM sys_dept WHERE del_flag = 0 AND parent_id = ', @company_id, ') AS all_depts
    -- 左连接数据汇总结果
    LEFT JOIN (
        SELECT
            ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ',
            s.name,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
            JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
						', IF(@company_id = 1, ' LEFT JOIN sys_dept sd ON d.parent_id = sd.dept_id ', ' '), '
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = ', @task_id, ')  -- 动态task_id
            AND dd.dict_code = ', @dict_code, '  -- 动态dict_code
            AND ( @company_id = 1 OR d.parent_id = ', @company_id, ' )  -- 动态parent_id
        GROUP BY ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ', s.name
    ) AS src ON all_depts.dept_name = src.dept_name
    GROUP BY all_depts.dept_name
    ');
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_quarterly
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_quarterly`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_quarterly`(IN taskId INT, IN deptId INT, IN type INT)
BEGIN
    SET @budget_year = NULL;
    SELECT budget_year INTO @budget_year FROM t_reporting_task WHERE id = taskId;
    SET @last_year = @budget_year - 1 ;
    
    
    SELECT 
        q.date_time,
        IFNULL(d.total, 0) AS total
    FROM (
        
        SELECT CONCAT(@last_year, '-Q1') AS date_time UNION ALL
        SELECT CONCAT(@last_year, '-Q2') AS date_time UNION ALL
        SELECT CONCAT(@last_year, '-Q3') AS date_time UNION ALL
        SELECT CONCAT(@last_year, '-Q4') AS date_time
    ) q
    LEFT JOIN (
        
        SELECT
            CONCAT(YEAR(t.zzrq), '-Q', QUARTER(t.zzrq)) AS date_time,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM `t_actual_costs` t
        LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
        LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
        JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = @last_year  
            AND dd.dict_code = type
            AND (deptId = 1 OR d.parent_id = deptId)
        GROUP BY date_time
    ) d ON q.date_time = d.date_time
    
    UNION ALL
    
    SELECT 
        q.date_time,
        IFNULL(d.total, 0) AS total
    FROM (
        
        SELECT CONCAT(@budget_year, '-Q1') AS date_time UNION ALL
        SELECT CONCAT(@budget_year, '-Q2') AS date_time UNION ALL
        SELECT CONCAT(@budget_year, '-Q3') AS date_time UNION ALL
        SELECT CONCAT(@budget_year, '-Q4') AS date_time
    ) q
    LEFT JOIN (
        
        SELECT
            CONCAT(YEAR(t.zzrq), '-Q', QUARTER(t.zzrq)) AS date_time,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM `t_actual_costs` t
        LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
        LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
        JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = @budget_year  
            AND dd.dict_code = type
            AND (deptId = 1 OR d.parent_id = deptId)
        GROUP BY date_time
    ) d ON q.date_time = d.date_time
    
    ORDER BY date_time;  
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_rcsc_table
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_rcsc_table`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_rcsc_table`(IN taskId INT,          
    IN deptId INT)
BEGIN
    
    SET @sql = NULL;
		
		
    SET @company_id = deptId;
		
		SET @task_id = taskId;
		
		SET @dict_code = 60;
    
    
    SET @sql = CONCAT('
    SELECT
        ', IF(@company_id = 1, 'all_depts.dept_name AS `公司名称`', 'all_depts.dept_name AS `部门名称`'), ',
		SUM(CASE WHEN src.name = ''能源费-水费'' THEN IFNULL(total, 0) ELSE 0 END) AS `能源费-水费`,
		SUM(CASE WHEN src.name = ''能源费-电费'' THEN IFNULL(total, 0) ELSE 0 END) AS `能源费-电费`,
		SUM(CASE WHEN src.name = ''能源费-气费'' THEN IFNULL(total, 0) ELSE 0 END) AS `能源费-气费`,
		SUM(CASE WHEN src.name = ''租赁费用-房屋租赁费'' THEN IFNULL(total, 0) ELSE 0 END) AS `租赁费用-房屋租赁费`,
		SUM(CASE WHEN src.name = ''租赁费用-土地租赁'' THEN IFNULL(total, 0) ELSE 0 END) AS `租赁费用-土地租赁`,
		SUM(CASE WHEN src.name = ''租赁费用-设备租赁'' THEN IFNULL(total, 0) ELSE 0 END) AS `租赁费用-设备租赁`,
		SUM(CASE WHEN src.name = ''租赁费用-车辆租赁'' THEN IFNULL(total, 0) ELSE 0 END) AS `租赁费用-车辆租赁`,
		SUM(CASE WHEN src.name = ''保险费'' THEN IFNULL(total, 0) ELSE 0 END) AS `保险费`,
		SUM(CASE WHEN src.name = ''专业机构费'' THEN IFNULL(total, 0) ELSE 0 END) AS `专业机构费`,
		SUM(CASE WHEN src.name = ''运输费用及装卸驳运费'' THEN IFNULL(total, 0) ELSE 0 END) AS `运输费用及装卸驳运费`
				
    FROM
        -- 获取所有符合条件的部门（动态company_id）
        (SELECT dept_name FROM sys_dept WHERE del_flag = 0 AND parent_id = ', @company_id, ') AS all_depts
    -- 左连接数据汇总结果
    LEFT JOIN (
        SELECT
            ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ',
            s.name,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
            JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
						', IF(@company_id = 1, ' LEFT JOIN sys_dept sd ON d.parent_id = sd.dept_id ', ' '), '
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = ', @task_id, ')  -- 动态task_id
            AND dd.dict_code = ', @dict_code, '  -- 动态dict_code
            AND ( @company_id = 1 OR d.parent_id = ', @company_id, ' )  -- 动态parent_id
        GROUP BY ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ', s.name
    ) AS src ON all_depts.dept_name = src.dept_name
    GROUP BY all_depts.dept_name
    ');
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_table_detail
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_table_detail`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_table_detail`(IN taskId INT, 
		IN parentId INT,   
    IN deptName VARCHAR(100),  
    IN subjectName VARCHAR(100))
BEGIN
    
    IF parentId = 1 THEN
        
        SELECT
            date_format(t.zzrq, '%Y-%m-%d') zzrq, t.kjkmsm, t.rjztsm, t.zy,
            IFNULL(t.actual_incurred, 0) AS total, IFNULL(t.bwbdf, 0) as bwddf
        FROM
            `t_actual_costs` t
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id  
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228 
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = taskId )
            
            AND t.dept_id IN (
                SELECT d.dept_id 
                FROM sys_dept d 
                WHERE d.parent_id = ( 
                    SELECT d.dept_id 
                    FROM sys_dept d 
                    WHERE d.del_flag = '0' 
                      AND d.dept_name COLLATE utf8mb4_general_ci = deptName COLLATE utf8mb4_general_ci
                      AND d.parent_id = parentId
                )
            ) 
            AND t.subject_id = (
                SELECT bs.id 
                FROM t_budget_subject bs 
                WHERE bs.del_flag = '0' 
                  AND bs.`name` COLLATE utf8mb4_general_ci = subjectName COLLATE utf8mb4_general_ci
            ) order by t.zzrq;
    ELSE
        
        SELECT
            date_format(t.zzrq, '%Y-%m-%d') zzrq, t.kjkmsm, t.rjztsm, t.zy,
            IFNULL(t.actual_incurred, 0) AS total, IFNULL(t.bwbdf, 0) as bwddf
        FROM
            `t_actual_costs` t
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id  
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228 
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = taskId )
            AND t.dept_id = (
                SELECT d.dept_id 
                FROM sys_dept d 
                WHERE d.del_flag = '0' 
                  AND d.dept_name COLLATE utf8mb4_general_ci = deptName COLLATE utf8mb4_general_ci
                  AND d.parent_id = parentId
            ) 
            AND t.subject_id = (
                SELECT bs.id 
                FROM t_budget_subject bs 
                WHERE bs.del_flag = '0' 
                  AND bs.`name` COLLATE utf8mb4_general_ci = subjectName COLLATE utf8mb4_general_ci
            ) order by t.zzrq;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_table_detail_back
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_table_detail_back`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_table_detail_back`(IN taskId INT, 
		IN parentId INT,   
    IN deptName VARCHAR(100),  
    IN subjectName VARCHAR(100))
BEGIN
    
    IF parentId = 1 THEN
        
        SELECT
            d.dept_name as deptName,
            IFNULL(SUM(IFNULL(t.actual_incurred, 0) - IFNULL(t.bwbdf, 0)), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id  
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228 
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = taskId )
            
            AND t.dept_id IN (
                SELECT d.dept_id 
                FROM sys_dept d 
                WHERE d.parent_id = ( 
                    SELECT d.dept_id 
                    FROM sys_dept d 
                    WHERE d.del_flag = '0' 
                      AND d.dept_name COLLATE utf8mb4_general_ci = deptName COLLATE utf8mb4_general_ci
                      AND d.parent_id = parentId
                )
            ) 
            AND t.subject_id = (
                SELECT bs.id 
                FROM t_budget_subject bs 
                WHERE bs.del_flag = '0' 
                  AND bs.`name` COLLATE utf8mb4_general_ci = subjectName COLLATE utf8mb4_general_ci
            )
        GROUP BY d.dept_name;
    ELSE
        
        SELECT
            t.kjkmsm,
            IFNULL(t.actual_incurred, 0) - IFNULL(t.bwbdf, 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id  
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228 
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = taskId )
            AND t.dept_id = (
                SELECT d.dept_id 
                FROM sys_dept d 
                WHERE d.del_flag = '0' 
                  AND d.dept_name COLLATE utf8mb4_general_ci = deptName COLLATE utf8mb4_general_ci
                  AND d.parent_id = parentId
            ) 
            AND t.subject_id = (
                SELECT bs.id 
                FROM t_budget_subject bs 
                WHERE bs.del_flag = '0' 
                  AND bs.`name` COLLATE utf8mb4_general_ci = subjectName COLLATE utf8mb4_general_ci
            );
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_ygcb_table
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_ygcb_table`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_ygcb_table`(IN taskId INT,          
    IN companyId INT)
BEGIN
    
    SET @sql = NULL;
		
		
    SET @company_id = companyId;
		
		SET @task_id = taskId;
		
		SET @dict_code = 53;
    
    
    SET @sql = CONCAT('
    SELECT
        ', IF(@company_id = 1, 'all_depts.dept_name AS `公司名称`', 'all_depts.dept_name AS `部门名称`'), ',
        SUM(CASE WHEN src.name = ''员工成本-工资'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-工资`,
        SUM(CASE WHEN src.name = ''员工成本-劳务费'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-劳务费`,
        SUM(CASE WHEN src.name = ''员工成本-福利费-其他'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-福利费-其他`,
        SUM(CASE WHEN src.name = ''员工成本-社会保险费-基本医疗保险'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-社会保险费-基本医疗保险`,
        SUM(CASE WHEN src.name = ''员工成本-社会保险费-基本养老保险'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-社会保险费-基本养老保险`,
        SUM(CASE WHEN src.name = ''员工成本-社会保险费-失业保险'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-社会保险费-失业保险`,
        SUM(CASE WHEN src.name = ''员工成本-社会保险费-工伤保险'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-社会保险费-工伤保险`,
        SUM(CASE WHEN src.name = ''员工成本-社会保险费-生育保险'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-社会保险费-生育保险`,
        SUM(CASE WHEN src.name = ''员工成本-职工住房公积金'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-职工住房公积金`,
        SUM(CASE WHEN src.name = ''员工成本-职工教育经费'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-职工教育经费`,
        SUM(CASE WHEN src.name = ''员工成本-工会经费'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-工会经费`,
        SUM(CASE WHEN src.name = ''员工成本-劳动保护费'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-劳动保护费`,
        SUM(CASE WHEN src.name = ''员工成本-企业年金'' THEN IFNULL(total, 0) ELSE 0 END) AS `员工成本-企业年金`,
        SUM(CASE WHEN src.name = ''研究开发费用-人工成本'' THEN IFNULL(total, 0) ELSE 0 END) AS `研究开发费用-人工成本`
    FROM
        -- 获取所有符合条件的部门（动态company_id）
        (SELECT dept_name FROM sys_dept WHERE del_flag = 0 AND parent_id = ', @company_id, ') AS all_depts
    -- 左连接数据汇总结果
    LEFT JOIN (
        SELECT
            ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ',
            s.name,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
            JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
						', IF(@company_id = 1, ' LEFT JOIN sys_dept sd ON d.parent_id = sd.dept_id ', ' '), '
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = ', @task_id, ')  -- 动态task_id
            AND dd.dict_code = ', @dict_code, '  -- 动态dict_code
            AND ( @company_id = 1 OR d.parent_id = ', @company_id, ' )  -- 动态parent_id
        GROUP BY ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ', s.name
    ) AS src ON all_depts.dept_name = src.dept_name
    GROUP BY all_depts.dept_name
    ');
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_ywtz_table
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_ywtz_table`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_ywtz_table`(IN taskId INT,          
    IN companyId INT)
BEGIN
    
    SET @sql = NULL;
		
		
    SET @company_id = companyId;
		
		SET @task_id = taskId;
		
		SET @dict_code = 63;
    
    
    SET @sql = CONCAT('
    SELECT
        ', IF(@company_id = 1, 'all_depts.dept_name AS `公司名称`', 'all_depts.dept_name AS `部门名称`'), ',
        SUM(CASE WHEN src.name = ''市场推广费-广告费'' THEN IFNULL(total, 0) ELSE 0 END) AS `市场推广费-广告费`,
        SUM(CASE WHEN src.name = ''市场推广费-市场推广费用'' THEN IFNULL(total, 0) ELSE 0 END) AS `市场推广费-市场推广费用`
    FROM
        -- 获取所有符合条件的部门（动态company_id）
        (SELECT dept_name FROM sys_dept WHERE del_flag = 0 AND parent_id = ', @company_id, ') AS all_depts
    -- 左连接数据汇总结果
    LEFT JOIN (
        SELECT
            ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ',
            s.name,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
            JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
						', IF(@company_id = 1, ' LEFT JOIN sys_dept sd ON d.parent_id = sd.dept_id ', ' '), '
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = ', @task_id, ')  -- 动态task_id
            AND dd.dict_code = ', @dict_code, '  -- 动态dict_code
            AND ( @company_id = 1 OR d.parent_id = ', @company_id, ' )  -- 动态parent_id
        GROUP BY ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ', s.name
    ) AS src ON all_depts.dept_name = src.dept_name
    GROUP BY all_depts.dept_name
    ');
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_actual_cost_zctz_table
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_actual_cost_zctz_table`;
delimiter ;;
CREATE PROCEDURE `get_actual_cost_zctz_table`(IN taskId INT,          
    IN deptId INT)
BEGIN
    
    SET @sql = NULL;
		
		
    SET @company_id = deptId;
		
		SET @task_id = taskId;
		
		SET @dict_code = 61;
    
    
    SET @sql = CONCAT('
    SELECT
        ', IF(@company_id = 1, 'all_depts.dept_name AS `公司名称`', 'all_depts.dept_name AS `部门名称`'), ',
        SUM(CASE WHEN src.name = ''摊销费用'' THEN IFNULL(total, 0) ELSE 0 END) AS `摊销费用`,
        SUM(CASE WHEN src.name = ''折旧费用'' THEN IFNULL(total, 0) ELSE 0 END) AS `折旧费用`,
        SUM(CASE WHEN src.name = ''研究开发费用-摊销费用'' THEN IFNULL(total, 0) ELSE 0 END) AS `研究开发费用-摊销费用`,
        SUM(CASE WHEN src.name = ''研究开发费用-折旧费用'' THEN IFNULL(total, 0) ELSE 0 END) AS `研究开发费用-折旧费用`
    FROM
        -- 获取所有符合条件的部门（动态company_id）
        (SELECT dept_name FROM sys_dept WHERE del_flag = 0 AND parent_id = ', @company_id, ') AS all_depts
    -- 左连接数据汇总结果
    LEFT JOIN (
        SELECT
            ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ',
            s.name,
            IFNULL(SUM(t.actual_incurred), 0) - IFNULL(SUM(t.bwbdf), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN t_budget_subject s ON t.subject_id = s.id 
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
            JOIN sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = "item_type"
						', IF(@company_id = 1, ' LEFT JOIN sys_dept sd ON d.parent_id = sd.dept_id ', ' '), '
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = ', @task_id, ')  -- 动态task_id
            AND dd.dict_code = ', @dict_code, '  -- 动态dict_code
            AND ( @company_id = 1 OR d.parent_id = ', @company_id, ' )  -- 动态parent_id
        GROUP BY ', IF(@company_id = 1, 'sd.dept_name', 'd.dept_name'), ', s.name
    ) AS src ON all_depts.dept_name = src.dept_name
    GROUP BY all_depts.dept_name
    ');
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Aqsc_by_company
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Aqsc_by_company`;
delimiter ;;
CREATE PROCEDURE `get_Aqsc_by_company`(IN p_taskId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND ( dd.dict_label = '与安全生产相关费用' or t.table_name = 'meter' )
            AND s.name IN ('无','维修保养费-庭院管', '维修保养费-试验检验费', '维修保养费-燃气流量表','安全生产费-安全生产推广应用类','安全生产费-设备安全防护支出类', '安全生产费-安全设备检验检测类','安全生产费-人员防护支出类','安全生产费-设备安全防护支出类','安全生产费-安全生产责任保险支出','安全生产费-安全宣传教育及培训类','维修保养费-办公维修（电脑耗材）','维修及保养费-建筑物及其附属物','安全生产费-应急设备支出演练类','维修及保养费-其他','安全生产费-其他安全生产费','维修保养费-设备-一般性维修','安全生产费-管网维护整改评估类','安全生产费-安全生产检查咨询类','维修及保养费-其他');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id) 
    SELECT d.dept_id, d2.dept_name, d.parent_id 
    FROM sys_dept d
		LEFT JOIN sys_dept d2 ON d.parent_id = d2.dept_id
    WHERE d.del_flag = '0' AND d.`level` = 2;

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        IF table_name = 'meter' THEN
            
            SET @sql = CONCAT('
                INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
                SELECT 
                    t.dept_id,
                    d.dept_name,
                    CASE 
                        WHEN t.reporting_type = 2 AND t.project_type = 2 THEN ''维修保养费-设备-一般性维修''
                        WHEN t.reporting_type = 2 AND t.project_type = 3 THEN ''维修保养费-庭院管''
                        WHEN t.reporting_type = 2 AND t.project_type = 1 THEN ''维修保养费-试验检验费''
                        WHEN t.reporting_type = 1 AND t.project_type != 1 THEN ''安全生产费-设备安全防护支出类''
                        WHEN t.reporting_type = 1 AND t.project_type = 1 THEN ''安全生产费-安全设备检验检测类''
                        ELSE ''未分类费用''
                    END AS subject_name,
                    ROUND(SUM(t.budget), 2) AS total
                FROM 
                    t_reporting_table_meter t
                LEFT JOIN 
                    sys_dept d ON t.dept_id = d.dept_id
                ', where_condition, '
                AND t.reporting_type IS NOT NULL
                AND t.project_type IS NOT NULL
                GROUP BY 
                    t.dept_id, d.dept_name, subject_name');
        ELSE
            
            SET @sql = CONCAT('
                INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
                SELECT 
                    d.dept_id,
                    d.dept_name,
                    ''', subject_name, ''' AS subject_name,
                    IFNULL(SUM(t.budget), 0) AS dept_total
                FROM 
                    temp_all_depts d
                LEFT JOIN 
                    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
                ', where_condition, '
                GROUP BY 
                    d.dept_id, d.dept_name');
        END IF;

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;

    
    SET @pivot_sql = '
			SELECT
			t.`公司名称`, 
			IFNULL(SUM(t.`安全生产费-设备安全防护支出类`), 0) as "安全生产费-设备安全防护支出类",
			IFNULL(SUM(t.`安全生产费-应急设备支出演练类`), 0) as "安全生产费-应急设备支出演练类",
			IFNULL(SUM(t.`安全生产费-管网维护整改评估类`), 0) as "安全生产费-管网维护整改评估类",
			IFNULL(SUM(t.`安全生产费-安全生产检查咨询类`), 0) as "安全生产费-安全生产检查咨询类",
			IFNULL(SUM(t.`安全生产费-人员防护支出类`), 0) as "安全生产费-人员防护支出类",
			IFNULL(SUM(t.`安全生产费-安全宣传教育及培训类`), 0) as "安全生产费-安全宣传教育及培训类",
			IFNULL(SUM(t.`安全生产费-安全生产推广应用类`), 0) as "安全生产费-安全生产推广应用类",
			IFNULL(SUM(t.`安全生产费-安全设备检验检测类`), 0) as "安全生产费-安全设备检验检测类",
			IFNULL(SUM(t.`安全生产费-安全生产责任保险支出`), 0) as "安全生产费-安全生产责任保险支出",
			IFNULL(SUM(t.`安全生产费-其他安全生产费`), 0) as "安全生产费-其他安全生产费",
			IFNULL(SUM(t.`维修保养费-设备-一般性维修`), 0) as "维修保养费-设备-一般性维修",
			IFNULL(SUM(t.`维修保养费-主干管`), 0) as "维修保养费-主干管",
			IFNULL(SUM(t.`维修及保养费-建筑物及其附属物`), 0) as "维修及保养费-建筑物及其附属物",
			IFNULL(SUM(t.`维修保养费-庭院管`), 0) as "维修保养费-庭院管",
			IFNULL(SUM(t.`维修保养费-办公维修（电脑耗材）`), 0) as "维修保养费-办公维修（电脑耗材）",
			IFNULL(SUM(t.`维修及保养费-其他`), 0) as "维修及保养费-其他",
			IFNULL(SUM(t.`维修保养费-试验检验费`), 0) as "维修保养费-试验检验费",
			IFNULL(SUM(t.`维修保养费-燃气流量表`), 0) as "维修保养费-燃气流量表"
			FROM
			(
        SELECT           
            a.dept_name AS "公司名称",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-设备-一般性维修" THEN total_money END), 0) AS "维修保养费-设备-一般性维修",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-主干管" THEN total_money END), 0) AS "维修保养费-主干管",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修及保养费-建筑物及其附属物" THEN total_money END), 0) AS "维修及保养费-建筑物及其附属物",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-庭院管" THEN total_money END), 0) AS "维修保养费-庭院管",
            COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-办公维修（电脑耗材）" THEN total_money END), 0) AS "维修保养费-办公维修（电脑耗材）",
            COALESCE(SUM(CASE WHEN t.subject_name = "维修及保养费-其他" THEN total_money END), 0) AS "维修及保养费-其他",
            COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-试验检验费" THEN total_money END), 0) AS "维修保养费-试验检验费",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-燃气流量表" THEN total_money END), 0) AS "维修保养费-燃气流量表",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-设备安全防护支出类" THEN total_money END), 0) AS "安全生产费-设备安全防护支出类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-应急设备支出演练类" THEN total_money END), 0) AS "安全生产费-应急设备支出演练类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-管网维护整改评估类" THEN total_money END), 0) AS "安全生产费-管网维护整改评估类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全生产检查咨询类" THEN total_money END), 0) AS "安全生产费-安全生产检查咨询类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-人员防护支出类" THEN total_money END), 0) AS "安全生产费-人员防护支出类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全宣传教育及培训类" THEN total_money END), 0) AS "安全生产费-安全宣传教育及培训类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全生产推广应用类" THEN total_money END), 0) AS "安全生产费-安全生产推广应用类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全设备检验检测类" THEN total_money END), 0) AS "安全生产费-安全设备检验检测类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全生产责任保险支出" THEN total_money END), 0) AS "安全生产费-安全生产责任保险支出",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-其他安全生产费" THEN total_money END), 0) AS "安全生产费-其他安全生产费" 
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "安全生产费-设备安全防护支出类","安全生产费-安全设备检验检测类"  desc
				) t
				GROUP BY t.`公司名称`
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Aqsc_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Aqsc_by_dept`;
delimiter ;;
CREATE PROCEDURE `get_Aqsc_by_dept`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND ( dd.dict_label = '与安全生产相关费用' or t.table_name = 'meter' )
            AND s.name IN ('无','维修保养费-庭院管', '维修保养费-试验检验费', '维修保养费-燃气流量表','安全生产费-安全生产推广应用类','安全生产费-设备安全防护支出类', '安全生产费-安全设备检验检测类','安全生产费-人员防护支出类','安全生产费-设备安全防护支出类','安全生产费-安全生产责任保险支出','安全生产费-安全宣传教育及培训类','维修保养费-办公维修（电脑耗材）','维修及保养费-建筑物及其附属物','安全生产费-应急设备支出演练类','维修及保养费-其他','安全生产费-其他安全生产费','维修保养费-设备-一般性维修','安全生产费-管网维护整改评估类','安全生产费-安全生产检查咨询类','维修及保养费-其他');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id)
    SELECT dept_id, dept_name, parent_id 
    FROM sys_dept 
    WHERE del_flag = '0' 
    AND (p_deptId IS NULL OR parent_id = p_deptId );

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        IF table_name = 'meter' THEN
            
            SET @sql = CONCAT('
                INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
                SELECT 
                    t.dept_id,
                    d.dept_name,
                    CASE 
                        WHEN t.reporting_type = 2 AND t.project_type = 2 THEN ''维修保养费-设备-一般性维修''
                        WHEN t.reporting_type = 2 AND t.project_type = 3 THEN ''维修保养费-庭院管''
                        WHEN t.reporting_type = 2 AND t.project_type = 1 THEN ''维修保养费-试验检验费''
                        WHEN t.reporting_type = 1 AND t.project_type != 1 THEN ''安全生产费-设备安全防护支出类''
                        WHEN t.reporting_type = 1 AND t.project_type = 1 THEN ''安全生产费-安全设备检验检测类''
                        ELSE ''未分类费用''
                    END AS subject_name,
                    ROUND(SUM(t.budget), 2) AS total
                FROM 
                    t_reporting_table_meter t
                LEFT JOIN 
                    sys_dept d ON t.dept_id = d.dept_id
                ', where_condition, '
                AND t.reporting_type IS NOT NULL
                AND t.project_type IS NOT NULL
                GROUP BY 
                    t.dept_id, d.dept_name, subject_name');
        ELSE
            
            SET @sql = CONCAT('
                INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
                SELECT 
                    d.dept_id,
                    d.dept_name,
                    ''', subject_name, ''' AS subject_name,
                    IFNULL(SUM(t.budget), 0) AS dept_total
                FROM 
                    temp_all_depts d
                LEFT JOIN 
                    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
                ', where_condition, '
                GROUP BY 
                    d.dept_id, d.dept_name');
        END IF;

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;

    
    SET @pivot_sql = '
        SELECT           
            a.dept_name AS "部门名称",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-设备安全防护支出类" THEN total_money END), 0) AS "安全生产费-设备安全防护支出类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-应急设备支出演练类" THEN total_money END), 0) AS "安全生产费-应急设备支出演练类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-管网维护整改评估类" THEN total_money END), 0) AS "安全生产费-管网维护整改评估类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全生产检查咨询类" THEN total_money END), 0) AS "安全生产费-安全生产检查咨询类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-人员防护支出类" THEN total_money END), 0) AS "安全生产费-人员防护支出类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全宣传教育及培训类" THEN total_money END), 0) AS "安全生产费-安全宣传教育及培训类",
						COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全生产推广应用类" THEN total_money END), 0) AS "安全生产费-安全生产推广应用类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全设备检验检测类" THEN total_money END), 0) AS "安全生产费-安全设备检验检测类",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-安全生产责任保险支出" THEN total_money END), 0) AS "安全生产费-安全生产责任保险支出",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-其他安全生产费" THEN total_money END), 0) AS "安全生产费-其他安全生产费",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-设备-一般性维修" THEN total_money END), 0) AS "维修保养费-设备-一般性维修",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-主干管" THEN total_money END), 0) AS "维修保养费-主干管",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修及保养费-建筑物及其附属物" THEN total_money END), 0) AS "维修及保养费-建筑物及其附属物",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-庭院管" THEN total_money END), 0) AS "维修保养费-庭院管",
            COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-办公维修（电脑耗材）" THEN total_money END), 0) AS "维修保养费-办公维修（电脑耗材）",
            COALESCE(SUM(CASE WHEN t.subject_name = "维修及保养费-其他" THEN total_money END), 0) AS "维修及保养费-其他",
            COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-试验检验费" THEN total_money END), 0) AS "维修保养费-试验检验费",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修保养费-燃气流量表" THEN total_money END), 0) AS "维修保养费-燃气流量表"
  
        FROM 
            temp_all_depts a 
        LEFT JOIN 
            temp_dept_summary t ON t.dept_id = a.dept_id		
        GROUP BY 
            a.dept_id, a.dept_name
        ORDER BY "安全生产费-设备安全防护支出类" DESC, "安全生产费-安全设备检验检测类" DESC
    ';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_company
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_company`;
delimiter ;;
CREATE PROCEDURE `get_company`()
BEGIN
	DECLARE v_sql VARCHAR(1000);
    
	
	SET v_sql = '(SELECT 1 AS id, "郑州区域" AS name)
								UNION ALL
								(SELECT dept_id AS id, dept_name AS name
								 FROM `sys_dept`
								 WHERE del_flag = "0" AND `status` = "0" AND level = 1)
								ORDER BY id';
	
	
	SET @sql = v_sql;
	PREPARE stmt FROM @sql;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_previous_year_budgets
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_previous_year_budgets`;
delimiter ;;
CREATE PROCEDURE `get_previous_year_budgets`(IN p_current_task_id INT,
	IN p_deptId INT)
BEGIN
    DECLARE v_current_year INT;
    DECLARE v_prev_year INT;
    DECLARE v_prev_task_id INT;
    DECLARE where_condition VARCHAR(1000) DEFAULT '';
		 
    -- 1. 获取当前年份
    SELECT `year` INTO v_current_year 
    FROM t_reporting_version 
    WHERE task_id = p_current_task_id;
    
    -- 2. 计算前一年份
    SET v_prev_year = v_current_year;
    
    -- 3. 查找前一年的任务ID
    SELECT task_id INTO v_prev_task_id
    FROM t_reporting_version 
    WHERE `year` = v_prev_year
		ORDER BY `year` DESC
		LIMIT 1 ;
    
    -- 4. 调用三个存储过程获取不同类型的前一年预算
    IF v_prev_task_id IS NOT NULL THEN
        -- 获取实际费用
        SELECT CONCAT('Fetching ', v_prev_year, ' actual costs') AS status;
        CALL calculate_actual_cost(v_prev_task_id);
        
        -- 获取类型1预算
        SELECT CONCAT('Fetching ', v_prev_year, ' budget type 1') AS status;
        CALL calculate_total_money1(v_prev_task_id);
        
        -- 获取类型2预算
        SELECT CONCAT('Fetching ', v_prev_year, ' budget type 2') AS status;
        CALL calculate_total_money2(v_prev_task_id);
    ELSE
        SELECT CONCAT('No budget data found for year ', v_prev_year) AS result;
    END IF ;
				-- 如果传入了deptId，添加部门筛选条件
    IF p_deptId IS NOT NULL THEN
        SET where_condition = CONCAT(where_condition,'AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', p_deptId, ')');
     END IF;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Qtkk_by_company
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Qtkk_by_company`;
delimiter ;;
CREATE PROCEDURE `get_Qtkk_by_company`(IN p_taskId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
		DECLARE sql_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '其他可控费用'
            AND s.name IN ('A组织工作经费', '办公费-文具印刷费', '其他-绿化养护费', '办公费-文具印刷费', '办公费-图书资料费', '清洁费用','其他-消防费用（物业管理费）', '物业管理费用（大楼物业费）', '安保服务费', '车辆费用', '办公费-通讯费', '交际应酬费', '会议费', '其他-机物料消耗', '办公费-其他-其他办公费', '差旅费', '交通费', '其他-协会会费', '车辆费用', '办公费-邮电费', '耗用品费用', '耗用品费用', '耗用品费用', '耗用品费用', '其他-其他');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id) 
    SELECT d.dept_id, d2.dept_name, d.parent_id 
    FROM sys_dept d
		LEFT JOIN sys_dept d2 ON d.parent_id = d2.dept_id
    WHERE d.del_flag = '0' AND d.`level` = 2;

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
			IF table_name IN ('dynamic30') THEN
				SET @sql = CONCAT('
					INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
					SELECT 
							d.dept_id,
							d.dept_name,
							''', subject_name, ''' AS subject_name,
							IFNULL(SUM(e.field2), 0) + IFNULL(SUM(e.field3), 0) + IFNULL(SUM(e.field4), 0) + IFNULL(SUM(e.field5), 0) + IFNULL(SUM(e.field6), 0) + IFNULL(SUM(COALESCE(e.field7, 0) + COALESCE(e.field9, 0)), 0) + IFNULL(SUM(e.field8), 0) AS dept_total
					FROM 
							 temp_all_depts d
					LEFT JOIN t_reporting_table_', table_name, ' t ON t.dept_id = d.dept_id 
					LEFT JOIN t_reporting_table_dynamic30_extension e ON t.id = e.id '
												, where_condition, '
					GROUP BY 
							d.dept_id, d.dept_name');
			ELSE 
				
				IF table_name IN ('pipeline', 'station', 'housing') THEN
            
            SET sql_condition = ' IFNULL(SUM(t.expected_settlement), 0) AS dept_total ';
        ELSEIF table_name IN ('research', 'information_system') THEN
            
            SET sql_condition = ' IFNULL(SUM(t.intangible_assets) + SUM(t.fixed_assets), 0) AS dept_total ';
				ELSEIF table_name IN ('dynamic30') THEN
						
						SET sql_condition = ' IFNULL(SUM(t.intangible_assets) + SUM(t.fixed_assets), 0) AS dept_total ';
        ELSE
            
            SET sql_condition = ' IFNULL(SUM(t.budget), 0) AS dept_total ';
        END IF;
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                ', sql_condition ,'
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');
			END IF;
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		

    
    SET @pivot_sql = '
			SELECT
			t.`公司名称`, 
			IFNULL(SUM(t.`A组织工作经费`), 0) as "A组织工作经费",
			IFNULL(SUM(t.`办公费-文具印刷费`), 0) as "办公费-文具印刷费",
			IFNULL(SUM(t.`其他-绿化养护费`), 0) as "其他-绿化养护费",
			IFNULL(SUM(t.`办公费-图书资料费`), 0) as "办公费-图书资料费",
			IFNULL(SUM(t.`清洁费用`), 0) as "清洁费用",
			IFNULL(SUM(t.`其他-消防费用（物业管理费）`), 0) as "其他-消防费用（物业管理费）",
			IFNULL(SUM(t.`物业管理费用（大楼物业费）`), 0) as "物业管理费用（大楼物业费）",
			IFNULL(SUM(t.`安保服务费`), 0) as "安保服务费",
			IFNULL(SUM(t.`车辆费用`), 0) as "车辆费用",
			IFNULL(SUM(t.`办公费-通讯费`), 0) as "办公费-通讯费",
			IFNULL(SUM(t.`交际应酬费`), 0) as "交际应酬费",
			IFNULL(SUM(t.`会议费`), 0) as "会议费",
			IFNULL(SUM(t.`其他-机物料消耗`), 0) as "其他-机物料消耗",
			IFNULL(SUM(t.`办公费-其他-其他办公费`), 0) as "办公费-其他-其他办公费",
			IFNULL(SUM(t.`差旅费`), 0) as "差旅费",
			IFNULL(SUM(t.`交通费`), 0) as "交通费",
			IFNULL(SUM(t.`其他-协会会费`), 0) as "其他-协会会费",
			IFNULL(SUM(t.`办公费-邮电费`), 0) as "办公费-邮电费",
			IFNULL(SUM(t.`耗用品费用`), 0) as "耗用品费用",
			IFNULL(SUM(t.`其他-其他`), 0) as "其他-其他"
			FROM
			(
        SELECT           
            a.dept_name AS "公司名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "A组织工作经费" THEN total_money END), 0) AS "A组织工作经费",
            COALESCE(SUM(CASE WHEN t.subject_name = "办公费-文具印刷费" THEN total_money END), 0) AS "办公费-文具印刷费",
            COALESCE(MAX(CASE WHEN t.subject_name = "其他-绿化养护费" THEN total_money END), 0) AS "其他-绿化养护费",
            COALESCE(MAX(CASE WHEN t.subject_name = "办公费-图书资料费" THEN total_money END), 0) AS "办公费-图书资料费",
            COALESCE(MAX(CASE WHEN t.subject_name = "清洁费用" THEN total_money END), 0) AS "清洁费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "其他-消防费用（物业管理费）" THEN total_money END), 0) AS "其他-消防费用（物业管理费）",
            COALESCE(MAX(CASE WHEN t.subject_name = "物业管理费用（大楼物业费）" THEN total_money END), 0) AS "物业管理费用（大楼物业费）",
            COALESCE(MAX(CASE WHEN t.subject_name = "安保服务费" THEN total_money END), 0) AS "安保服务费",
            COALESCE(SUM(CASE WHEN t.subject_name = "车辆费用" THEN total_money END), 0) AS "车辆费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "办公费-通讯费" THEN total_money END), 0) AS "办公费-通讯费",
            COALESCE(MAX(CASE WHEN t.subject_name = "交际应酬费" THEN total_money END), 0) AS "交际应酬费",
            COALESCE(MAX(CASE WHEN t.subject_name = "会议费" THEN total_money END), 0) AS "会议费",
						COALESCE(MAX(CASE WHEN t.subject_name = "其他-机物料消耗" THEN total_money END), 0) AS "其他-机物料消耗",
						COALESCE(MAX(CASE WHEN t.subject_name = "办公费-其他-其他办公费" THEN total_money END), 0) AS "办公费-其他-其他办公费",
						COALESCE(MAX(CASE WHEN t.subject_name = "差旅费" THEN total_money END), 0) AS "差旅费",
						COALESCE(MAX(CASE WHEN t.subject_name = "交通费" THEN total_money END), 0) AS "交通费",
						COALESCE(MAX(CASE WHEN t.subject_name = "其他-协会会费" THEN total_money END), 0) AS "其他-协会会费",
						COALESCE(MAX(CASE WHEN t.subject_name = "办公费-邮电费" THEN total_money END), 0) AS "办公费-邮电费",
						COALESCE(SUM(CASE WHEN t.subject_name = "耗用品费用" THEN total_money END), 0) AS "耗用品费用",
						COALESCE(MAX(CASE WHEN t.subject_name = "其他-其他" THEN total_money END), 0) AS "其他-其他"
           
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "营业外支出","A组织工作经费"  desc
												) t
				GROUP BY t.`公司名称`
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Qtkk_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Qtkk_by_dept`;
delimiter ;;
CREATE PROCEDURE `get_Qtkk_by_dept`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
		DECLARE sql_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '其他可控费用'
            AND s.name IN ('A组织工作经费', '办公费-文具印刷费', '其他-绿化养护费', '办公费-文具印刷费', '办公费-图书资料费', '清洁费用','其他-消防费用（物业管理费）', '物业管理费用（大楼物业费）', '安保服务费', '车辆费用', '办公费-通讯费', '交际应酬费', '会议费', '其他-机物料消耗', '办公费-其他-其他办公费', '差旅费', '交通费', '其他-协会会费', '车辆费用', '办公费-邮电费', '耗用品费用', '耗用品费用', '耗用品费用', '耗用品费用', '其他-其他');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id)
    SELECT dept_id, dept_name, parent_id 
    FROM sys_dept 
    WHERE del_flag = '0' 
    AND (p_deptId IS NULL OR parent_id = p_deptId );

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
			IF table_name IN ('dynamic30') THEN
				SET @sql = CONCAT('
					INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
					SELECT 
							d.dept_id,
							d.dept_name,
							''', subject_name, ''' AS subject_name,
							IFNULL(SUM(e.field2), 0) + IFNULL(SUM(e.field3), 0) + IFNULL(SUM(e.field4), 0) + IFNULL(SUM(e.field5), 0) + IFNULL(SUM(e.field6), 0) + IFNULL(SUM(COALESCE(e.field7, 0) + COALESCE(e.field9, 0)), 0) + IFNULL(SUM(e.field8), 0) AS dept_total
					FROM 
							 temp_all_depts d
					LEFT JOIN t_reporting_table_', table_name, ' t ON t.dept_id = d.dept_id 
					LEFT JOIN t_reporting_table_dynamic30_extension e ON t.id = e.id '
												, where_condition, '
					GROUP BY 
							d.dept_id, d.dept_name');
			ELSE 
				
				IF table_name IN ('pipeline', 'station', 'housing') THEN
            
            SET sql_condition = ' IFNULL(SUM(t.expected_settlement), 0) AS dept_total ';
        ELSEIF table_name IN ('research', 'information_system') THEN
            
            SET sql_condition = ' IFNULL(SUM(t.intangible_assets) + SUM(t.fixed_assets), 0) AS dept_total ';
				ELSEIF table_name IN ('dynamic30') THEN
						
						SET sql_condition = ' IFNULL(SUM(t.intangible_assets) + SUM(t.fixed_assets), 0) AS dept_total ';
        ELSE
            
            SET sql_condition = ' IFNULL(SUM(t.budget), 0) AS dept_total ';
        END IF;
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                ', sql_condition ,'
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');
			END IF;
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		

    
    SET @pivot_sql = '
        SELECT           
            a.dept_name AS "部门名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "A组织工作经费" THEN total_money END), 0) AS "A组织工作经费",
            COALESCE(SUM(CASE WHEN t.subject_name = "办公费-文具印刷费" THEN total_money END), 0) AS "办公费-文具印刷费",
            COALESCE(MAX(CASE WHEN t.subject_name = "其他-绿化养护费" THEN total_money END), 0) AS "其他-绿化养护费",
            COALESCE(MAX(CASE WHEN t.subject_name = "办公费-图书资料费" THEN total_money END), 0) AS "办公费-图书资料费",
            COALESCE(MAX(CASE WHEN t.subject_name = "清洁费用" THEN total_money END), 0) AS "清洁费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "其他-消防费用（物业管理费）" THEN total_money END), 0) AS "其他-消防费用（物业管理费）",
            COALESCE(MAX(CASE WHEN t.subject_name = "物业管理费用（大楼物业费）" THEN total_money END), 0) AS "物业管理费用（大楼物业费）",
            COALESCE(MAX(CASE WHEN t.subject_name = "安保服务费" THEN total_money END), 0) AS "安保服务费",
            COALESCE(SUM(CASE WHEN t.subject_name = "车辆费用" THEN total_money END), 0) AS "车辆费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "办公费-通讯费" THEN total_money END), 0) AS "办公费-通讯费",
            COALESCE(MAX(CASE WHEN t.subject_name = "交际应酬费" THEN total_money END), 0) AS "交际应酬费",
            COALESCE(MAX(CASE WHEN t.subject_name = "会议费" THEN total_money END), 0) AS "会议费",
						COALESCE(MAX(CASE WHEN t.subject_name = "其他-机物料消耗" THEN total_money END), 0) AS "其他-机物料消耗",
						COALESCE(MAX(CASE WHEN t.subject_name = "办公费-其他-其他办公费" THEN total_money END), 0) AS "办公费-其他-其他办公费",
						COALESCE(MAX(CASE WHEN t.subject_name = "差旅费" THEN total_money END), 0) AS "差旅费",
						COALESCE(MAX(CASE WHEN t.subject_name = "交通费" THEN total_money END), 0) AS "交通费",
						COALESCE(MAX(CASE WHEN t.subject_name = "其他-协会会费" THEN total_money END), 0) AS "其他-协会会费",
						COALESCE(MAX(CASE WHEN t.subject_name = "办公费-邮电费" THEN total_money END), 0) AS "办公费-邮电费",
						COALESCE(SUM(CASE WHEN t.subject_name = "耗用品费用" THEN total_money END), 0) AS "耗用品费用",
						COALESCE(MAX(CASE WHEN t.subject_name = "其他-其他" THEN total_money END), 0) AS "其他-其他"
           
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "营业外支出","A组织工作经费"  desc
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Rcsc_by_company
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Rcsc_by_company`;
delimiter ;;
CREATE PROCEDURE `get_Rcsc_by_company`(IN p_taskId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '与日常生产经营相关的费用'
            AND s.name IN ('保险费', '能源费-电费', '租赁费用-房屋租赁费', '专业机构费', '专业机构费', '专业机构费', '专业机构费', '能源费-气费', '运输费用及装卸驳运费', '能源费-水费', '租赁费用-土地租赁', '租赁费用-设备租赁', '租赁费用-车辆租赁');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id) 
    SELECT d.dept_id, d2.dept_name, d.parent_id 
    FROM sys_dept d
		LEFT JOIN sys_dept d2 ON d.parent_id = d2.dept_id
    WHERE d.del_flag = '0' AND d.`level` = 2;

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                IFNULL(SUM(t.budget), 0) AS dept_total
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		

    
    SET @pivot_sql = '
				SELECT
				t.`公司名称`, 
				IFNULL(SUM(t.`保险费`), 0) as "保险费",
				IFNULL(SUM(t.`能源费-水费`), 0) as "能源费-水费",
				IFNULL(SUM(t.`能源费-电费`), 0) as "能源费-电费",
				IFNULL(SUM(t.`能源费-气费`), 0) as "能源费-气费",
				IFNULL(SUM(t.`专业机构费`), 0) as "专业机构费",
				IFNULL(SUM(t.`运输费用及装卸驳运费`), 0) as "运输费用及装卸驳运费",
				IFNULL(SUM(t.`租赁费用-房屋租赁费`), 0) as "租赁费用-房屋租赁费",
				IFNULL(SUM(t.`租赁费用-土地租赁`), 0) as "租赁费用-土地租赁",
				IFNULL(SUM(t.`租赁费用-设备租赁`), 0) as "租赁费用-设备租赁",
				IFNULL(SUM(t.`租赁费用-车辆租赁`), 0) as "租赁费用-车辆租赁"
				FROM
				(
        SELECT           
            a.dept_name AS "公司名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "保险费" THEN total_money END), 0) AS "保险费",
            COALESCE(MAX(CASE WHEN t.subject_name = "能源费-电费" THEN total_money END), 0) AS "能源费-电费",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-房屋租赁费" THEN total_money END), 0) AS "租赁费用-房屋租赁费",
            COALESCE(SUM(CASE WHEN t.subject_name = "专业机构费" THEN total_money END), 0) AS "专业机构费",
            COALESCE(MAX(CASE WHEN t.subject_name = "能源费-气费" THEN total_money END), 0) AS "能源费-气费",
            COALESCE(MAX(CASE WHEN t.subject_name = "运输费用及装卸驳运费" THEN total_money END), 0) AS "运输费用及装卸驳运费",
            COALESCE(MAX(CASE WHEN t.subject_name = "能源费-水费" THEN total_money END), 0) AS "能源费-水费",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-土地租赁" THEN total_money END), 0) AS "租赁费用-土地租赁",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-设备租赁" THEN total_money END), 0) AS "租赁费用-设备租赁",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-车辆租赁" THEN total_money END), 0) AS "租赁费用-车辆租赁"
           
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "保险费","能源费-水费"  desc
								) t
				GROUP BY t.`公司名称`
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Rcsc_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Rcsc_by_dept`;
delimiter ;;
CREATE PROCEDURE `get_Rcsc_by_dept`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '与日常生产经营相关的费用'
            AND s.name IN ('保险费', '能源费-电费', '租赁费用-房屋租赁费', '专业机构费', '专业机构费', '专业机构费', '专业机构费', '能源费-气费', '运输费用及装卸驳运费', '能源费-水费', '租赁费用-土地租赁', '租赁费用-设备租赁', '租赁费用-车辆租赁');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id)
    SELECT dept_id, dept_name, parent_id 
    FROM sys_dept 
    WHERE del_flag = '0' 
    AND (p_deptId IS NULL OR parent_id = p_deptId );

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                IFNULL(SUM(t.budget), 0) AS dept_total
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		

    
    SET @pivot_sql = '
        SELECT           
            a.dept_name AS "部门名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "保险费" THEN total_money END), 0) AS "保险费",
            COALESCE(MAX(CASE WHEN t.subject_name = "能源费-水费" THEN total_money END), 0) AS "能源费-水费",
            COALESCE(MAX(CASE WHEN t.subject_name = "能源费-电费" THEN total_money END), 0) AS "能源费-电费",
            COALESCE(MAX(CASE WHEN t.subject_name = "能源费-气费" THEN total_money END), 0) AS "能源费-气费",
            COALESCE(SUM(CASE WHEN t.subject_name = "专业机构费" THEN total_money END), 0) AS "专业机构费",
            COALESCE(MAX(CASE WHEN t.subject_name = "运输费用及装卸驳运费" THEN total_money END), 0) AS "运输费用及装卸驳运费",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-房屋租赁费" THEN total_money END), 0) AS "租赁费用-房屋租赁费",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-土地租赁" THEN total_money END), 0) AS "租赁费用-土地租赁",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-设备租赁" THEN total_money END), 0) AS "租赁费用-设备租赁",
            COALESCE(MAX(CASE WHEN t.subject_name = "租赁费用-车辆租赁" THEN total_money END), 0) AS "租赁费用-车辆租赁"
           
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "保险费","能源费-水费"  desc
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_wage_statistics_by_company
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_wage_statistics_by_company`;
delimiter ;;
CREATE PROCEDURE `get_wage_statistics_by_company`(IN p_task_id INT)
BEGIN
SELECT
    d2.dept_name AS "公司",
    ROUND(IFNULL(SUM(w.staff), 0), 2) AS "员工成本-工资",
    ROUND(IFNULL(SUM(w.labor), 0), 2) AS "员工成本-劳务费",
    ROUND(IFNULL(SUM(w.welfare), 0), 2) AS "员工成本-福利费-其他",
    ROUND(IFNULL(SUM(w.education), 0), 2) AS "员工成本-职工教育经费",
    ROUND(IFNULL(SUM(w.union_funds), 0), 2) AS "员工成本-工会经费",
    ROUND(IFNULL(SUM(w.endowment), 0), 2) AS "员工成本-社会保险费-基本养老保险",
    ROUND(IFNULL(SUM(w.work_injury), 0), 2) AS "员工成本-社会保险费-工伤保险",
    ROUND(IFNULL(SUM(w.unemployment), 0), 2) AS "员工成本-社会保险费-失业保险",
    ROUND(IFNULL(SUM(w.medical), 0), 2) AS "员工成本-社会保险费-基本医疗保险",
    ROUND(IFNULL(SUM(w.maternity), 0), 2) AS "员工成本-社会保险费-生育保险",
    ROUND(IFNULL(SUM(w.social), 0), 2) AS "员工成本-社会保险费",
    ROUND(IFNULL(SUM(w.provident), 0), 2) AS "员工成本-职工住房公积金",
    ROUND(IFNULL(SUM(w.annuity), 0), 2) AS "员工成本-企业年金",
    ROUND(IFNULL(SUM(w.research), 0), 2) AS "研究开发费用-人工成本",
    ROUND(IFNULL(SUM(d10.budget), 0), 2) AS "员工成本-劳动保护费"
FROM sys_dept d
LEFT JOIN (
    SELECT 
        dept_id,
        SUM(staff) AS staff,
        SUM(labor) AS labor,
        SUM(welfare) AS welfare,
        SUM(education) AS education,
        SUM(union_funds) AS union_funds,
        SUM(endowment) AS endowment,
        SUM(work_injury) AS work_injury,
        SUM(unemployment) AS unemployment,
        SUM(medical) AS medical,
        SUM(maternity) AS maternity,
        SUM(social) AS social,
        SUM(provident) AS provident,
        SUM(annuity) AS annuity,
        SUM(research) AS research
    FROM t_reporting_table_wages
    WHERE del_flag = "0" 
        AND (status = 2 OR status = 5)
        AND task_id = p_task_id
    GROUP BY dept_id
) w ON w.dept_id = d.dept_id
LEFT JOIN (
    SELECT 
        dept_id,
        SUM(budget) AS budget
    FROM t_reporting_table_dynamic10
    WHERE (status = 3 OR status = 5)
        AND del_flag = "0"
        AND task_id = p_task_id
    GROUP BY dept_id
) d10 ON d10.dept_id = d.dept_id
LEFT JOIN sys_dept d2 ON d.parent_id = d2.dept_id
WHERE d.level = 2
GROUP BY d.parent_id, d2.dept_name  
ORDER BY d.parent_id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_wage_statistics_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_wage_statistics_by_dept`;
delimiter ;;
CREATE PROCEDURE `get_wage_statistics_by_dept`(IN p_task_id INT,
    IN p_parent_id INT)
BEGIN
    SELECT
        d.dept_name AS "部门",
        IFNULL(w.staff, 0) AS "员工成本-工资",
        IFNULL(w.labor, 0) AS "员工成本-劳务费",
        IFNULL(w.welfare, 0) AS "员工成本-福利费-其他",
        IFNULL(w.education, 0) AS "员工成本-职工教育经费",
        IFNULL(w.union_funds, 0) AS "员工成本-工会经费",
        IFNULL(w.endowment, 0) AS "员工成本-社会保险费-基本养老保险",
        IFNULL(w.work_injury, 0) AS "员工成本-社会保险费-工伤保险",
        IFNULL(w.unemployment, 0) AS "员工成本-社会保险费-失业保险",
        IFNULL(w.medical, 0) AS "员工成本-社会保险费-基本医疗保险",
        IFNULL(w.maternity, 0) AS "员工成本-社会保险费-生育保险",
        IFNULL(w.social, 0) AS "员工成本-社会保险费",
        IFNULL(w.provident, 0) AS "员工成本-职工住房公积金",
        IFNULL(w.annuity, 0) AS "员工成本-企业年金",
        IFNULL(w.research, 0) AS "研究开发费用-人工成本",
        IFNULL(d10.total_budget, 0) AS "员工成本-劳动保护费"
    FROM
        sys_dept d
    
    LEFT JOIN (
        SELECT 
            dept_id,
            SUM(staff) AS staff,
            SUM(labor) AS labor,
            SUM(welfare) AS welfare,
            SUM(education) AS education,
            SUM(union_funds) AS union_funds,
            SUM(endowment) AS endowment,
            SUM(work_injury) AS work_injury,
            SUM(unemployment) AS unemployment,
            SUM(medical) AS medical,
            SUM(maternity) AS maternity,
            SUM(social) AS social,
            SUM(provident) AS provident,
            SUM(annuity) AS annuity,
            SUM(research) AS research
        FROM t_reporting_table_wages
        WHERE del_flag = "0" 
            AND (STATUS = 2 OR STATUS = 5) 
            AND task_id = p_task_id
        GROUP BY dept_id
    ) w ON w.dept_id = d.dept_id
    
    LEFT JOIN (
        SELECT 
            dept_id,
            SUM(budget) AS total_budget
        FROM t_reporting_table_dynamic10
        WHERE `status` = 3 OR `status` = 5
            AND del_flag = "0"
            AND task_id = p_task_id
        GROUP BY dept_id
    ) d10 ON d10.dept_id = d.dept_id
    WHERE
        d.parent_id != 0 
        AND d.parent_id = p_parent_id
    ORDER BY 
        d.dept_id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Ywtz_by_company
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Ywtz_by_company`;
delimiter ;;
CREATE PROCEDURE `get_Ywtz_by_company`(IN p_taskId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '与业务拓展相关的费用'
            AND s.name IN ('市场推广费-广告费', '市场推广费-市场推广费用');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id) 
    SELECT d.dept_id, d2.dept_name, d.parent_id 
    FROM sys_dept d
		LEFT JOIN sys_dept d2 ON d.parent_id = d2.dept_id
    WHERE d.del_flag = '0' AND d.`level` = 2;

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                IFNULL(SUM(t.budget), 0) AS dept_total
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		

    
    SET @pivot_sql = '
			SELECT
			t.`公司名称`, 
			IFNULL(SUM(t.`市场推广费-市场推广费用`), 0) as "市场推广费-市场推广费用",
			IFNULL(SUM(t.`市场推广费-广告费`), 0) as "市场推广费-广告费"
			FROM
			(
        SELECT           
            a.dept_name AS "公司名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "市场推广费-广告费" THEN total_money END), 0) AS "市场推广费-广告费",
            COALESCE(MAX(CASE WHEN t.subject_name = "市场推广费-市场推广费用" THEN total_money END), 0) AS "市场推广费-市场推广费用"
           
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "市场推广费-广告费","市场推广费-市场推广费用"  desc
				) t
				GROUP BY t.`公司名称` ';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Ywtz_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Ywtz_by_dept`;
delimiter ;;
CREATE PROCEDURE `get_Ywtz_by_dept`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '与业务拓展相关的费用'
            AND s.name IN ('市场推广费-广告费', '市场推广费-市场推广费用');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id)
    SELECT dept_id, dept_name, parent_id 
    FROM sys_dept 
    WHERE del_flag = '0' 
    AND (p_deptId IS NULL OR parent_id = p_deptId );

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                IFNULL(SUM(t.budget), 0) AS dept_total
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		

    
    SET @pivot_sql = '
        SELECT           
            a.dept_name AS "部门名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "市场推广费-广告费" THEN total_money END), 0) AS "市场推广费-广告费",
            COALESCE(MAX(CASE WHEN t.subject_name = "市场推广费-市场推广费用" THEN total_money END), 0) AS "市场推广费-市场推广费用"
           
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "市场推广费-广告费","市场推广费-市场推广费用"  desc
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Zctz_by_company
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Zctz_by_company`;
delimiter ;;
CREATE PROCEDURE `get_Zctz_by_company`(IN p_taskId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '与资产投资相关费用'
            AND s.name IN ('摊销费用', '研究开发费用-折旧费用', '研究开发费用-摊销费用', '折旧费用');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id) 
    SELECT d.dept_id, d2.dept_name, d.parent_id 
    FROM sys_dept d
		LEFT JOIN sys_dept d2 ON d.parent_id = d2.dept_id
    WHERE d.del_flag = '0' AND d.`level` = 2;

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                IFNULL(SUM(t.budget), 0) AS dept_total
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		
		
		
		

    
    SET @pivot_sql = '
			SELECT
			t.`公司名称`, IFNULL(SUM(t.`折旧费用`), 0) as "折旧费用", 
			IFNULL(SUM(t.`摊销费用`), 0) as "摊销费用", IFNULL(SUM(t.`研究开发费用-折旧费用`), 0) as "研究开发费用-折旧费用", 
			IFNULL(SUM(t.`研究开发费用-摊销费用`), 0) as "研究开发费用-摊销费用"
			FROM
			(
        SELECT           
            a.dept_name AS "公司名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "摊销费用" THEN total_money END), 0) AS "摊销费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "研究开发费用-折旧费用" THEN total_money END), 0) AS "研究开发费用-折旧费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "研究开发费用-摊销费用" THEN total_money END), 0) AS "研究开发费用-摊销费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "折旧费用" THEN total_money END), 0) AS "折旧费用"
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "摊销费用","折旧费用"  desc
				) t
				GROUP BY t.`公司名称`		';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for get_Zctz_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `get_Zctz_by_dept`;
delimiter ;;
CREATE PROCEDURE `get_Zctz_by_dept`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND dd.dict_label = '与资产投资相关费用'
            AND s.name IN ('摊销费用', '研究开发费用-折旧费用', '研究开发费用-摊销费用', '折旧费用');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id)
    SELECT dept_id, dept_name, parent_id 
    FROM sys_dept 
    WHERE del_flag = '0' 
    AND (p_deptId IS NULL OR parent_id = p_deptId );

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                IFNULL(SUM(t.budget), 0) AS dept_total
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		
		
		
		

    
    SET @pivot_sql = '
        SELECT           
            a.dept_name AS "部门名称",
            COALESCE(MAX(CASE WHEN t.subject_name = "摊销费用" THEN total_money END), 0) AS "摊销费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "研究开发费用-折旧费用" THEN total_money END), 0) AS "研究开发费用-折旧费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "研究开发费用-摊销费用" THEN total_money END), 0) AS "研究开发费用-摊销费用",
            COALESCE(MAX(CASE WHEN t.subject_name = "折旧费用" THEN total_money END), 0) AS "折旧费用"
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "摊销费用","折旧费用"  desc
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ldbhfy
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ldbhfy`;
delimiter ;;
CREATE PROCEDURE `Ldbhfy`(IN p_task_id INT,
    IN p_parent_id INT)
BEGIN
    
    SET @sql = 'SELECT 
    IFNULL(SUM(total), 0) as total_money
		FROM(
		SELECT  
		IFNULL(SUM(t.budget), 0) as total 
		FROM t_reporting_table_dynamic10 t 
		LEFT JOIN sys_dept d on t.dept_id = d.dept_id 
		WHERE d.parent_id != 0 and (t.`status` = 3 or t.`status` = 5 ) and t.del_flag = "0" ';
    
    
    IF p_task_id IS NOT NULL THEN
        SET @sql = CONCAT(@sql, ' AND t.task_id = ', p_task_id);
    END IF;
    
    IF p_parent_id != 1 THEN
        SET @sql = CONCAT(@sql, ' AND d.parent_id = ', p_parent_id);
    END IF;
    
    
    SET @sql = CONCAT(@sql, ' GROUP BY d.parent_id ) as total');
    
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Qt
-- ----------------------------
DROP PROCEDURE IF EXISTS `Qt`;
delimiter ;;
CREATE PROCEDURE `Qt`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND t.type = 1
						AND dd.dict_label = '其他可控费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
				
				
				
        IF table_name IN ('pipeline', 'station', 'housing') THEN
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(expected_settlement), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, where_condition);
        ELSEIF table_name IN ('research', 'information_system') THEN
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(intangible_assets) + SUM(fixed_assets), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, where_condition);
        ELSE
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, where_condition);
        END IF;
												 
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Qtkk
-- ----------------------------
DROP PROCEDURE IF EXISTS `Qtkk`;
delimiter ;;
CREATE PROCEDURE `Qtkk`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND t.type != 1
						AND t.table_name != 'dynamic30'
						AND t.table_name != 'meter'
						AND t.table_name != 'charity'
						AND t.table_name != 'dynamic6'
						AND dd.dict_label = '其他可控费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
				
				
				
        IF table_name IN ('pipeline', 'station', 'housing') THEN
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(expected_settlement), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, where_condition);
        ELSEIF table_name IN ('research', 'information_system') THEN
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(intangible_assets) + SUM(fixed_assets), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, where_condition);
        ELSE
            
            SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                             table_name, where_condition);
        END IF;
												 
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Qtkkcf
-- ----------------------------
DROP PROCEDURE IF EXISTS `Qtkkcf`;
delimiter ;;
CREATE PROCEDURE `Qtkkcf`(IN p_task_id INT,
    IN p_parent_id INT)
BEGIN
    
    SET @sql = '
        select
	IFNULL(SUM(total_txf + total_tcf + total_rlf + total_xlf + total_nsf + total_qtf + total_bxf), 0) AS total
from (

SELECT 
    IFNULL(SUM(txf), 0) AS total_txf,
    IFNULL(SUM(tcf), 0) AS total_tcf,
    IFNULL(SUM(rlf), 0) AS total_rlf,
    IFNULL(SUM(xlf), 0) AS total_xlf,
    IFNULL(SUM(nsf), 0) AS total_nsf,
    IFNULL(SUM(qtf), 0) AS total_qtf,
    IFNULL(SUM(bxf), 0) AS total_bxf
FROM (
    SELECT 
        d.parent_id AS dept_id,
        IFNULL(SUM(t.budget), 0) AS budget,
        IFNULL(SUM(e.field2), 0) AS txf,
        IFNULL(SUM(e.field3), 0) AS tcf,
        IFNULL(SUM(e.field4), 0) AS rlf,
        IFNULL(SUM(e.field5), 0) AS xlf,
        IFNULL(SUM(e.field6), 0) AS nsf,
        IFNULL(SUM(COALESCE(e.field7, 0) + COALESCE(e.field9, 0)), 0) AS qtf,
        IFNULL(SUM(e.field8), 0) AS bxf
    FROM 
        `t_reporting_table_dynamic30` t
    LEFT JOIN 
        sys_dept d ON t.dept_id = d.dept_id
    LEFT JOIN 
        t_reporting_table_dynamic30_extension e ON t.id = e.id
    WHERE 
        d.parent_id != 0 
        AND (t.`status` = 3 OR t.`status` = 5) 
        AND t.del_flag = "0" ';
    
    
    IF p_task_id IS NOT NULL THEN
        SET @sql = CONCAT(@sql, ' AND t.task_id = ', p_task_id);
    END IF;
    
    IF p_parent_id != 1 THEN
        SET @sql = CONCAT(@sql, ' AND d.parent_id = ', p_parent_id);
    END IF;
    
    
    SET @sql = CONCAT(@sql, '
                    GROUP BY 
        d.parent_id
) AS subquery
) as 	sum');
    
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Rcscjy
-- ----------------------------
DROP PROCEDURE IF EXISTS `Rcscjy`;
delimiter ;;
CREATE PROCEDURE `Rcscjy`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND dd.dict_label = '与日常生产经营相关的费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                         table_name, where_condition);
												 
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for sp_get_actual_costs
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_get_actual_costs`;
delimiter ;;
CREATE PROCEDURE `sp_get_actual_costs`(IN task_id INT, 
		IN parent_id INT,   
    IN dept_name VARCHAR(100),  
    IN subject_name VARCHAR(100))
BEGIN
    
    IF parent_id = 1 THEN
        
        SELECT
            d.dept_name,
            IFNULL(SUM(IFNULL(t.actual_incurred, 0) - IFNULL(t.bwbdf, 0)), 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id  
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228 
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = task_id )
            
            AND t.dept_id IN (
                SELECT d.dept_id 
                FROM sys_dept d 
                WHERE d.parent_id = ( 
                    SELECT d.dept_id 
                    FROM sys_dept d 
                    WHERE d.del_flag = '0' 
                      AND d.dept_name COLLATE utf8mb4_general_ci = dept_name COLLATE utf8mb4_general_ci
                      AND d.parent_id = parent_id
                )
            ) 
            AND t.subject_id = (
                SELECT bs.id 
                FROM t_budget_subject bs 
                WHERE bs.del_flag = '0' 
                  AND bs.`name` COLLATE utf8mb4_general_ci = subject_name COLLATE utf8mb4_general_ci
            )
        GROUP BY d.dept_name;
    ELSE
        
        SELECT
            d.dept_name,
            IFNULL(t.actual_incurred, 0) - IFNULL(t.bwbdf, 0) AS total
        FROM
            `t_actual_costs` t
            LEFT JOIN sys_dept d ON t.dept_id = d.dept_id  
        WHERE
            t.del_flag = "0" 
            AND t.zzrq IS NOT NULL 
            AND t.subject_id != 228 
            AND t.subject_id IS NOT NULL 
            AND t.`year` = (SELECT budget_year FROM t_reporting_task WHERE id = task_id )
            AND t.dept_id = (
                SELECT d.dept_id 
                FROM sys_dept d 
                WHERE d.del_flag = '0' 
                  AND d.dept_name COLLATE utf8mb4_general_ci = dept_name COLLATE utf8mb4_general_ci
                  AND d.parent_id = parent_id
            ) 
            AND t.subject_id = (
                SELECT bs.id 
                FROM t_budget_subject bs 
                WHERE bs.del_flag = '0' 
                  AND bs.`name` COLLATE utf8mb4_general_ci = subject_name COLLATE utf8mb4_general_ci
            );
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for test_fydb_sjfy
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_fydb_sjfy`;
delimiter ;;
CREATE PROCEDURE `test_fydb_sjfy`(IN p_task_id INT, IN p_dept_id INT, IN typeCode INT)
BEGIN
    DECLARE v_sql VARCHAR(1000);
    DECLARE v_where_condition VARCHAR(500) DEFAULT '';
    
    -- 构建WHERE条件
    IF p_task_id IS NOT NULL THEN
        -- 当传入task_id时，添加年份条件
        SET v_where_condition = CONCAT(' and t.year = (SELECT t.budget_year 
                                      FROM t_reporting_task t 
                                      WHERE t.id = ', p_task_id, ')');
        
        -- 如果同时传入dept_id，添加部门条件
        IF p_dept_id IS NOT NULL THEN
            SET v_where_condition = CONCAT(v_where_condition, 
                                         ' AND t.dept_id IN (SELECT dept_id 
                                                           FROM sys_dept 
                                                           WHERE parent_id = ', 
                                                           p_dept_id, ')');
        END IF;
    ELSE
        -- 当未传入task_id时，检查是否传入dept_id
        IF p_dept_id IS NOT NULL THEN
            SET v_where_condition = CONCAT(' and t.dept_id IN (SELECT dept_id 
                                                             FROM sys_dept 
                                                             WHERE parent_id = ', 
                                                             p_dept_id, ')');
        END IF;
    END IF;
    
    -- 构建完整SQL
    SET v_sql = CONCAT('SELECT IFNULL(SUM(t.actual_incurred), 0) AS total, SUBSTRING(t.zzrq, 6, 2) AS month, dd.dict_label as itemType
												from t_actual_costs t
												left JOIN t_budget_subject s on t.subject_id = s.id
												left JOIN sys_dict_data dd on s.budget_type = dd.dict_value AND dd.dict_type = "item_type" and s.del_flag = 0
												where t.del_flag = 0 and dd.dict_code = ', typeCode, v_where_condition, ' GROUP BY SUBSTRING(t.zzrq, 6, 2) ORDER BY month ');
     
    -- 准备并执行动态SQL
    SET @sql = v_sql;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for test_get_Aqsc_by_dept
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_get_Aqsc_by_dept`;
delimiter ;;
CREATE PROCEDURE `test_get_Aqsc_by_dept`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(1000);
    DECLARE subject_name VARCHAR(1000);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
            t.table_name,
            s.name
        FROM
            t_budget_item t
        LEFT JOIN
            t_budget_subject s ON t.subject_id = s.id
        LEFT JOIN
            sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
        WHERE
            t.table_name IS NOT NULL
            AND t.table_name != ''
            AND t.del_flag = 0
            AND ( dd.dict_label = '与安全生产相关费用' or t.table_name = 'meter' )
            AND s.name IN ('无', '安全生产费-设备安全防护支出类', '安全生产费-安全设备检验检测类','安全生产费-人员防护支出类','安全生产费-设备安全防护支出类','安全生产费-安全生产责任保险支出','安全生产费-安全宣传教育及培训类','维修保养费-办公维修（电脑耗材）','维修及保养费-建筑物及其附属物','安全生产费-应急设备支出演练类','维修及保养费-其他','安全生产费-其他安全生产费','维修保养费-设备-一般性维修','安全生产费-管网维护整改评估类','安全生产费-安全生产检查咨询类','维修及保养费-其他');
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    
    DROP TEMPORARY TABLE IF EXISTS temp_all_depts;
    CREATE TEMPORARY TABLE temp_all_depts (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        parent_id BIGINT,
        PRIMARY KEY (dept_id)
    );

    
    INSERT INTO temp_all_depts (dept_id, dept_name, parent_id)
    SELECT dept_id, dept_name, parent_id 
    FROM sys_dept 
    WHERE del_flag = '0' 
    AND (p_deptId IS NULL OR parent_id = p_deptId );

    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_summary;
    CREATE TEMPORARY TABLE temp_dept_summary (
        dept_id BIGINT,
        dept_name VARCHAR(100),
        subject_name VARCHAR(255),
        total_money DECIMAL(20,2) DEFAULT 0,
        KEY (dept_id, subject_name)
    );

    
    OPEN table_cursor;

    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name, subject_name;

        
        IF done THEN
            LEAVE read_loop;
        END IF;

        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';

        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;

        
        SET @sql = CONCAT('
            INSERT INTO temp_dept_summary (dept_id, dept_name, subject_name, total_money)
            SELECT 
                d.dept_id,
                d.dept_name,
                ''', subject_name, ''' AS subject_name,
                IFNULL(SUM(t.budget), 0) AS dept_total
            FROM 
                 temp_all_depts d
            LEFT JOIN 
						    t_reporting_table_', table_name, ' t
                ON t.dept_id = d.dept_id
            ', where_condition, '
            GROUP BY 
                d.dept_id, d.dept_name');

        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;

    
    CLOSE table_cursor;
		

    
    SET @pivot_sql = '
        SELECT           
            a.dept_name AS "部门名称",
						COALESCE(SUM(CASE WHEN t.subject_name = "无" THEN total_money END), 0) AS "无",
            COALESCE(SUM(CASE WHEN t.subject_name = "安全生产费-设备安全防护支出类" THEN total_money END), 0) AS "安全生产费-设备安全防护支出类",
            COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-安全设备检验检测类" THEN total_money END), 0) AS "安全生产费-安全设备检验检测类",
						COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-人员防护支出类" THEN total_money END), 0) AS "安全生产费-人员防护支出类",
						
						COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-安全生产责任保险支出" THEN total_money END), 0) AS "安全生产费-安全生产责任保险支出",
						
						 COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-安全宣传教育及培训类" THEN total_money END), 0) AS "安全生产费-安全宣传教育及培训类",
            COALESCE(MAX(CASE WHEN t.subject_name = "维修保养费-办公维修（电脑耗材）" THEN total_money END), 0) AS "维修保养费-办公维修（电脑耗材）",
						COALESCE(MAX(CASE WHEN t.subject_name = "维修及保养费-建筑物及其附属物" THEN total_money END), 0) AS "维修及保养费-建筑物及其附属物",
            COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-应急设备支出演练类" THEN total_money END), 0) AS "安全生产费-应急设备支出演练类",
						COALESCE(SUM(CASE WHEN t.subject_name = "维修及保养费-其他" THEN total_money END), 0) AS "维修及保养费-其他",
						
						 COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-其他安全生产费" THEN total_money END), 0) AS "安全生产费-其他安全生产费",
            COALESCE(MAX(CASE WHEN t.subject_name = "维修保养费-设备-一般性维修" THEN total_money END), 0) AS "维修保养费-设备-一般性维修",
						COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-管网维护整改评估类" THEN total_money END), 0) AS "安全生产费-管网维护整改评估类",
            COALESCE(MAX(CASE WHEN t.subject_name = "安全生产费-安全生产检查咨询类" THEN total_money END), 0) AS "安全生产费-安全生产检查咨询类"
           
        FROM 
            temp_all_depts a 
			  left join 
				      temp_dept_summary t on t.dept_id = a.dept_id		
				    	
        GROUP BY 
            a.dept_id, a.dept_name
				ORDER BY "安全生产费-设备安全防护支出类","安全生产费-安全设备检验检测类"  desc
						';

    
    PREPARE stmt FROM @pivot_sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for test_get_previous_year_budgets
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_get_previous_year_budgets`;
delimiter ;;
CREATE PROCEDURE `test_get_previous_year_budgets`(IN p_current_task_id INT,
  IN p_deptId INT)
BEGIN
    DECLARE v_current_year INT;
    DECLARE v_prev_year INT;
    DECLARE v_prev_task_id INT;
    DECLARE v_yggc DECIMAL(18,2) DEFAULT 0;     -- 工资费用
    DECLARE v_ldbhfy DECIMAL(18,2) DEFAULT 0;   -- 劳动保护费用
    DECLARE v_money1 DECIMAL(18,2) DEFAULT 0;    -- 类型1预算
    DECLARE v_money2 DECIMAL(18,2) DEFAULT 0;    -- 类型2预算
    DECLARE where_condition VARCHAR(1000) DEFAULT '';
    DECLARE result_table_name VARCHAR(100) DEFAULT 'temp_result';
    DECLARE done INT DEFAULT FALSE;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET done = TRUE;
		 
    -- 1. 获取当前年份
    SELECT `year` INTO v_current_year 
    FROM t_reporting_version 
    WHERE task_id = p_current_task_id;
    
    -- 2. 计算前一年份
    SET v_prev_year = v_current_year;
    
    -- 3. 查找前一年的任务ID
    SELECT task_id INTO v_prev_task_id
    FROM t_reporting_version 
    WHERE `year` = v_prev_year
    ORDER BY `year` DESC
    LIMIT 1;
    
    -- 4. 调用存储过程获取各类型预算（使用用户变量捕获结果）
		-- 获取工资费用（假设Yggc存储过程输出单条结果）
		CALL Ygcb(v_prev_task_id, p_deptId);
		SET v_yggc = (SELECT total_money FROM (SELECT * FROM (SELECT 1) a) b JOIN (SELECT total FROM (SELECT 1) x) c ON 1=1);
		
		-- 获取劳动保护费用
		-- CALL Ldbhfy(v_prev_task_id, p_deptId);
		-- SET v_ldbhfy = (SELECT total_money FROM (SELECT * FROM (SELECT 1) a) b JOIN (SELECT total FROM (SELECT 1) x) c ON 1=1);
		
		-- 获取类型1预算
		CALL calculate_total_money1(v_prev_task_id, p_deptId);
		SET v_money1 = (SELECT total_money FROM (SELECT * FROM (SELECT 1) a) b JOIN (SELECT total_money FROM (SELECT 1) x) c ON 1=1);
		
		-- 获取类型2预算（假设calculate_total_money2结构类似）
		CALL calculate_total_money2(v_prev_task_id, p_deptId);
		SET v_money2 = (SELECT total_money FROM (SELECT * FROM (SELECT 1) a) b JOIN (SELECT total_money FROM (SELECT 1) x) c ON 1=1);


    -- 5. 计算总预算
    SET @total_budget = v_yggc + v_ldbhfy + v_money1 + v_money2;
    
    -- 6. 输出结果
    SELECT 
        v_yggc AS '工资费用',
        v_ldbhfy AS '劳动保护费用',
        v_money1 AS '类型1预算',
        v_money2 AS '类型2预算'
    FROM DUAL;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for test_Ygcb
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_Ygcb`;
delimiter ;;
CREATE PROCEDURE `test_Ygcb`(IN p_task_id INT,
    IN p_parent_id INT)
BEGIN
    -- 声明SQL语句变量
    SET @sql = '
        SELECT 
            ROUND(SUM(total_staff + total_labor + total_welfare + total_social + 
                      total_provident + total_education + total_union_funds + 
                      total_annuity + total_research), 2) as total_money
        FROM (
            SELECT 
                ROUND(SUM(staff), 2) as total_staff,
                ROUND(SUM(labor), 2) as total_labor,
                ROUND(SUM(welfare), 2) as total_welfare,
                ROUND(SUM(social), 2) as total_social,
                ROUND(SUM(provident), 2) as total_provident,
                ROUND(SUM(education), 2) as total_education,
                ROUND(SUM(union_funds), 2) as total_union_funds,
                ROUND(SUM(annuity), 2) as total_annuity,
                ROUND(SUM(research), 2) as total_research
            FROM (
                SELECT  
                    d.parent_id as deptParentId,  
                    ROUND(SUM(t.staff), 2) as staff,  
                    ROUND(SUM(t.labor), 2) as labor,  
                    ROUND(SUM(t.welfare), 2) as welfare,  
                    ROUND(SUM(t.education), 2) as education,  
                    ROUND(SUM(t.union_funds), 2) as union_funds,  
                    ROUND(SUM(t.endowment), 2) as endowment,  
                    ROUND(SUM(t.work_injury), 2) as work_injury,  
                    ROUND(SUM(t.unemployment), 2) as unemployment,  
                    ROUND(SUM(t.medical), 2) as medical,  
                    ROUND(SUM(t.maternity), 2) as maternity,  
                    ROUND(SUM(t.social), 2) as social,  
                    ROUND(SUM(t.provident), 2) as provident,  
                    ROUND(SUM(t.annuity), 2) as annuity,  
                    ROUND(SUM(t.research), 2) as research
                FROM  
                    `t_reporting_table_wages` t
                LEFT JOIN 
                    sys_dept d ON t.dept_id = d.dept_id
                WHERE 
                    d.parent_id != 0 
                    AND t.del_flag = "0" 
                    AND (t.status = 2 OR t.status = 5)';
    
    -- 根据参数动态添加查询条件
    IF p_task_id IS NOT NULL THEN
        SET @sql = CONCAT(@sql, ' AND t.task_id = ', p_task_id);
    END IF;
    
    IF p_parent_id IS NOT NULL THEN
        SET @sql = CONCAT(@sql, ' AND d.parent_id = ', p_parent_id);
    END IF;
    
    -- 完成SQL语句构建
    SET @sql = CONCAT(@sql, '
                GROUP BY 
                    d.parent_id
            ) AS subquery
        ) AS subquery_sum');
    
    -- 准备并执行动态SQL
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ygcb
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ygcb`;
delimiter ;;
CREATE PROCEDURE `Ygcb`(IN p_task_id INT,
    IN p_parent_id INT)
BEGIN
    
    SET @sql = '
        SELECT 
            IFNULL(SUM(total_staff + total_labor + total_welfare + total_social + 
                      total_provident + total_education + total_union_funds + 
                      total_annuity + total_research), 0) as total_money
        FROM (
            SELECT 
                IFNULL(SUM(staff), 0) as total_staff,
                IFNULL(SUM(labor), 0) as total_labor,
                IFNULL(SUM(welfare), 0) as total_welfare,
                IFNULL(SUM(social), 0) as total_social,
                IFNULL(SUM(provident), 0) as total_provident,
                IFNULL(SUM(education), 0) as total_education,
                IFNULL(SUM(union_funds), 0) as total_union_funds,
                IFNULL(SUM(annuity), 0) as total_annuity,
                IFNULL(SUM(research), 0) as total_research
            FROM (
                SELECT  
                    d.parent_id as deptParentId,  
                    IFNULL(SUM(t.staff), 0) as staff,  
                    IFNULL(SUM(t.labor), 0) as labor,  
                    IFNULL(SUM(t.welfare), 0) as welfare,  
                    IFNULL(SUM(t.education), 0) as education,  
                    IFNULL(SUM(t.union_funds), 0) as union_funds,  
                    IFNULL(SUM(t.endowment), 0) as endowment,  
                    IFNULL(SUM(t.work_injury), 0) as work_injury,  
                    IFNULL(SUM(t.unemployment), 0) as unemployment,  
                    IFNULL(SUM(t.medical), 0) as medical,  
                    IFNULL(SUM(t.maternity), 0) as maternity,  
                    IFNULL(SUM(t.social), 0) as social,  
                    IFNULL(SUM(t.provident), 0) as provident,  
                    IFNULL(SUM(t.annuity), 0) as annuity,  
                    IFNULL(SUM(t.research), 0) as research
                FROM  
                    `t_reporting_table_wages` t
                LEFT JOIN 
                    sys_dept d ON t.dept_id = d.dept_id
                WHERE 
                    d.parent_id != 0 
                    AND t.del_flag = "0" 
                    AND (t.status = 2 OR t.status = 5)';
    
    
    IF p_task_id IS NOT NULL THEN
        SET @sql = CONCAT(@sql, ' AND t.task_id = ', p_task_id);
    END IF;
    
    IF p_parent_id != 1 THEN
        SET @sql = CONCAT(@sql, ' AND d.parent_id = ', p_parent_id);
    END IF;
    
    
    SET @sql = CONCAT(@sql, '
                GROUP BY 
                    d.parent_id
            ) AS subquery
        ) AS subquery_sum');
    
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ygcbsjfy
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ygcbsjfy`;
delimiter ;;
CREATE PROCEDURE `Ygcbsjfy`(IN p_task_id INT, IN p_dept_id INT)
BEGIN
    DECLARE v_sql VARCHAR(1000);
    DECLARE v_where_condition VARCHAR(500) DEFAULT '';
		
    
    
    IF p_task_id IS NOT NULL THEN
        
        SET v_where_condition = CONCAT(' and t.year = (SELECT t.budget_year 
                                      FROM t_reporting_task t 
                                      WHERE t.id = ', p_task_id, ')');
        
        
        IF p_dept_id IS NOT NULL THEN
            SET v_where_condition = CONCAT(v_where_condition, 
                                         ' AND t.dept_id IN (SELECT dept_id 
                                                           FROM sys_dept 
                                                           WHERE parent_id = ', 
                                                           p_dept_id, ')');
        END IF;
    ELSE
        
        IF p_dept_id IS NOT NULL THEN
            SET v_where_condition = CONCAT(' and t.dept_id IN (SELECT dept_id 
                                                             FROM sys_dept 
                                                             WHERE parent_id = ', 
                                                             p_dept_id, ')');
        END IF;
    END IF;
		
    
    
    SET v_sql = CONCAT('SELECT IFNULL(SUM(t.actual_incurred), 0) AS sjfy 
                       FROM t_actual_costs t where t.kjkmsm like "%员工成本%" ', v_where_condition);
    
    
    SET @sql = v_sql;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_aqsc
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_aqsc`;
delimiter ;;
CREATE PROCEDURE `Ystj_aqsc`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND ( dd.dict_label = '与安全生产相关费用' or t.table_name = 'meter' ) ;
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
    CREATE TEMPORARY TABLE temp_dept_budget (
        dept_id INT,
        dept_name VARCHAR(255),
        total_budget DECIMAL(20,2) DEFAULT 0,
				PRIMARY KEY (dept_id)
    );
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, IFNULL(SUM(t.budget), 0)
                          FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
		
		
    IF p_deptId != 1 THEN
        
				SELECT 
				t.dept_id AS Id,
				t.dept_name AS areaName,
				t.total_budget AS total
				FROM temp_dept_budget t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
				ORDER BY t.dept_id;
    ELSE
				
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total_budget) AS total
        FROM temp_dept_budget t
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY company.dept_id, company.dept_name
        ORDER BY company.dept_id;
		END IF;
    
    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_dtys
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_dtys`;
delimiter ;;
CREATE PROCEDURE `Ystj_dtys`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT t.table_name 
        FROM t_budget_item t 
        WHERE t.reporting_type = '1' AND t.del_flag = 0;
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
    CREATE TEMPORARY TABLE temp_dept_budget (
        dept_id INT,
        dept_name VARCHAR(255),
        total_budget DECIMAL(20,2) DEFAULT 0,
				PRIMARY KEY (dept_id)
    );
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, IFNULL(SUM(t.budget), 0)
                          FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
		
		
    IF p_deptId IS NULL THEN
        
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            IFNULL(SUM(t.total_budget), 0) AS total
        FROM temp_dept_budget t
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY company.dept_id, company.dept_name
        ORDER BY company.dept_id;
    ELSE
				
				SELECT 
				t.dept_id AS Id,
				t.dept_name AS areaName,
				IFNULL(t.total_budget, 0) AS total
				FROM temp_dept_budget t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
				ORDER BY t.dept_id;
		END IF;
    
    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_gdys
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_gdys`;
delimiter ;;
CREATE PROCEDURE `Ystj_gdys`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
		DECLARE short_sql VARCHAR(255);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT t.table_name 
        FROM t_budget_item t 
        WHERE t.reporting_type = '2' AND t.del_flag = 0;
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
    CREATE TEMPORARY TABLE temp_dept_budget (
        dept_id INT,
        dept_name VARCHAR(255),
        total_budget DECIMAL(20,2) DEFAULT 0,
				PRIMARY KEY (dept_id)
    );
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
				
				
        IF table_name IN ('pipeline', 'station', 'housing') THEN
            
            SET short_sql = ' IFNULL(SUM(t.expected_settlement), 0) ';
						
        ELSEIF table_name IN ('research', 'information_system') THEN
            
            SET short_sql = ' IFNULL(SUM(t.intangible_assets) + SUM(t.fixed_assets), 0) ';
        ELSE
            
            SET short_sql = ' IFNULL(SUM(t.budget), 0) ';
        END IF;
        
        
        SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, ', short_sql,
                          ' FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
		
		
    IF p_deptId IS NULL THEN
        
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            IFNULL(SUM (t.total_budget), 0)AS total
        FROM temp_dept_budget t
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY company.dept_id, company.dept_name
        ORDER BY company.dept_id;
    ELSE
				
				SELECT 
				t.dept_id AS Id,
				t.dept_name AS areaName,
				IFNULL(t.total_budget, 0) AS total
				FROM temp_dept_budget t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
				ORDER BY t.dept_id;
		END IF;
    
    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_gz
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_gz`;
delimiter ;;
CREATE PROCEDURE `Ystj_gz`(IN p_task_id INT, IN p_parent_id INT)
BEGIN
    
    SET @sql = '';
    
    
    SET @base_subquery = '
    SELECT 
        t.dept_id, 
        d.dept_name, 

        IFNULL(SUM(t.staff), 0) + 
        IFNULL(SUM(t.labor), 0) + 
        IFNULL(SUM(t.welfare), 0) + 
				IFNULL(SUM(t.social), 0) + 
				IFNULL(SUM(t.provident), 0) + 
        IFNULL(SUM(t.education), 0) + 
        IFNULL(SUM(t.union_funds), 0) + 
        IFNULL(SUM(t.annuity), 0) + 
        IFNULL(SUM(t.research), 0) AS total_budget
    FROM  
        `t_reporting_table_wages` t
    LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
    WHERE 
        d.parent_id != 0 
        AND t.del_flag = "0"
        AND (t.status = 2 OR t.status = 5) ';
    
    
    SET @conditions = '';
    IF p_task_id IS NOT NULL THEN
        SET @conditions = CONCAT(@conditions, ' AND t.task_id = ', p_task_id);
    END IF;
    IF p_parent_id != 1 THEN
        SET @conditions = CONCAT(@conditions, ' AND d.parent_id = ', p_parent_id);
    END IF;
    
    
    SET @subquery = CONCAT(@base_subquery, @conditions, ' GROUP BY t.dept_id, d.dept_name');
    
    
    IF p_parent_id != 1 THEN
        
        SET @sql = CONCAT('
        SELECT
            t.dept_id AS Id,
            t.dept_name AS areaName,
            t.total_budget AS total
        FROM (', @subquery, ') t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
        ORDER BY t.dept_id;');
    ELSE
        
        SET @sql = CONCAT('
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total_budget) AS total
        FROM (', @subquery, ') t  
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY 
            company.dept_id, company.dept_name
        ORDER BY 
            company.dept_id');
    END IF;
    
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_ldbh
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_ldbh`;
delimiter ;;
CREATE PROCEDURE `Ystj_ldbh`(IN p_task_id INT, IN p_parent_id INT)
BEGIN
    
    SET @sql = '';
    
    
    SET @base_subquery = '
    SELECT 
        t.dept_id, 
        d.dept_name, 
        IFNULL(SUM(t.budget), 0) AS total_budget
    FROM 
        t_reporting_table_dynamic10 t 
    LEFT JOIN sys_dept d ON t.dept_id = d.dept_id 
    WHERE 
        d.parent_id != 0 
        AND (t.`status` = 3 OR t.`status` = 5) 
        AND t.del_flag = "0" ';	
    
    
    SET @conditions = '';
    IF p_task_id IS NOT NULL THEN
        SET @conditions = CONCAT(@conditions, ' AND t.task_id = ', p_task_id);
    END IF;
    IF p_parent_id != 1 THEN
        SET @conditions = CONCAT(@conditions, ' AND d.parent_id = ', p_parent_id);
    END IF;
    
    
    SET @subquery = CONCAT(@base_subquery, @conditions, ' GROUP BY t.dept_id, d.dept_name ');
    
    
    IF p_parent_id != 1 THEN
        
        SET @sql = CONCAT('
        SELECT
            t.dept_id AS Id,
            t.dept_name AS areaName,
            t.total_budget AS total
        FROM (', @subquery, ') t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
        ORDER BY t.dept_id;');
    ELSE
        
        SET @sql = CONCAT('
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total_budget) AS total
        FROM (', @subquery, ') t  
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY 
            company.dept_id, company.dept_name
        ORDER BY 
            company.dept_id');
    END IF;
    
    
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_qt
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_qt`;
delimiter ;;
CREATE PROCEDURE `Ystj_qt`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND t.type != 1
						AND t.table_name != 'meter'
						AND t.table_name != 'charity'
						AND t.table_name != 'dynamic6'
						AND dd.dict_label = '其他可控费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
    CREATE TEMPORARY TABLE temp_dept_budget (
        dept_id INT,
        dept_name VARCHAR(255),
        total_budget DECIMAL(20,2) DEFAULT 0,
				PRIMARY KEY (dept_id)
    );
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
				IF table_name IN ('dynamic30') THEN
				SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, IFNULL(SUM(e.field2), 0) + IFNULL(SUM(e.field3), 0) + IFNULL(SUM(e.field4), 0) + IFNULL(SUM(e.field5), 0) + IFNULL(SUM(e.field6), 0) + IFNULL(SUM(COALESCE(e.field7, 0) + COALESCE(e.field9, 0)), 0) + IFNULL(SUM(e.field8), 0) as budget
													
                          FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
													LEFT JOIN t_reporting_table_dynamic30_extension e ON t.id = e.id 
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
        
				ELSE
				
				SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, IFNULL(SUM(t.budget), 0)
                          FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
				
				END IF;
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
		
		
    IF p_deptId != 1 THEN
        
				SELECT 
				t.dept_id AS Id,
				t.dept_name AS areaName,
				t.total_budget AS total
				FROM temp_dept_budget t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
				ORDER BY t.dept_id;
    ELSE
				
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total_budget) AS total
        FROM temp_dept_budget t
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY company.dept_id, company.dept_name
        ORDER BY company.dept_id;
		END IF;
    
    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_rcsc
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_rcsc`;
delimiter ;;
CREATE PROCEDURE `Ystj_rcsc`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND dd.dict_label = '与日常生产经营相关的费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
    CREATE TEMPORARY TABLE temp_dept_budget (
        dept_id INT,
        dept_name VARCHAR(255),
        total_budget DECIMAL(20,2) DEFAULT 0,
				PRIMARY KEY (dept_id)
    );
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, IFNULL(SUM(t.budget), 0)
                          FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
		
		
    IF p_deptId != 1 THEN
        
				SELECT 
				t.dept_id AS Id,
				t.dept_name AS areaName,
				t.total_budget AS total
				FROM temp_dept_budget t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
				ORDER BY t.dept_id;
    ELSE
				
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total_budget) AS total
        FROM temp_dept_budget t
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY company.dept_id, company.dept_name
        ORDER BY company.dept_id;
		END IF;
    
    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_ywtz
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_ywtz`;
delimiter ;;
CREATE PROCEDURE `Ystj_ywtz`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND dd.dict_label = '与业务拓展相关的费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
    CREATE TEMPORARY TABLE temp_dept_budget (
        dept_id INT,
        dept_name VARCHAR(255),
        total_budget DECIMAL(20,2) DEFAULT 0,
				PRIMARY KEY (dept_id)
    );
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, IFNULL(SUM(t.budget), 0)
                          FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
		
		
    IF p_deptId != 1 THEN
        
				SELECT 
				t.dept_id AS Id,
				t.dept_name AS areaName,
				t.total_budget AS total
				FROM temp_dept_budget t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
				ORDER BY t.dept_id;
    ELSE
				
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total_budget) AS total
        FROM temp_dept_budget t
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY company.dept_id, company.dept_name
        ORDER BY company.dept_id;
		END IF;
    
    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ystj_zctz
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ystj_zctz`;
delimiter ;;
CREATE PROCEDURE `Ystj_zctz`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND dd.dict_label = '与资产投资相关费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
    CREATE TEMPORARY TABLE temp_dept_budget (
        dept_id INT,
        dept_name VARCHAR(255),
        total_budget DECIMAL(20,2) DEFAULT 0,
				PRIMARY KEY (dept_id)
    );
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE t.del_flag = "0" AND (t.status = 3 OR t.status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND t.task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND t.dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('INSERT INTO temp_dept_budget (dept_id, dept_name, total_budget)
                          SELECT t.dept_id, d.dept_name, IFNULL(SUM(t.budget), 0)
                          FROM t_reporting_table_', table_name, ' t
                          LEFT JOIN sys_dept d ON t.dept_id = d.dept_id
                          ', where_condition, '
                          GROUP BY t.dept_id, d.dept_name
                          ON DUPLICATE KEY UPDATE total_budget = total_budget + VALUES(total_budget)');
        
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
		
		
    IF p_deptId != 1 THEN
        
				SELECT 
				t.dept_id AS Id,
				t.dept_name AS areaName,
				t.total_budget AS total
				FROM temp_dept_budget t
				LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
				LEFT JOIN sys_dept company ON company.parent_id = dept.dept_id
				ORDER BY t.dept_id;
    ELSE
				
        SELECT 
            company.dept_id AS Id,
            company.dept_name AS areaName,
            SUM(t.total_budget) AS total
        FROM temp_dept_budget t
        LEFT JOIN sys_dept dept ON t.dept_id = dept.dept_id
        LEFT JOIN sys_dept company ON dept.parent_id = company.dept_id
        GROUP BY company.dept_id, company.dept_name
        ORDER BY company.dept_id;
		END IF;
    
    
    DROP TEMPORARY TABLE IF EXISTS temp_dept_budget;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Ywtz
-- ----------------------------
DROP PROCEDURE IF EXISTS `Ywtz`;
delimiter ;;
CREATE PROCEDURE `Ywtz`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND dd.dict_label = '与业务拓展相关的费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                         table_name, where_condition);
												 
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for Zctz
-- ----------------------------
DROP PROCEDURE IF EXISTS `Zctz`;
delimiter ;;
CREATE PROCEDURE `Zctz`(IN p_taskId INT, IN p_deptId INT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(255);
    DECLARE total DECIMAL(20,2) DEFAULT 0;
    DECLARE current_sum DECIMAL(20,2);
    DECLARE where_condition VARCHAR(1000);
    
    
    DECLARE table_cursor CURSOR FOR 
        SELECT
						t.table_name
				FROM
						t_budget_item t
				LEFT JOIN
						t_budget_subject s ON t.subject_id = s.id
				LEFT JOIN
						sys_dict_data dd ON s.budget_type = dd.dict_value AND dd.dict_type = 'item_type'
				WHERE
						t.table_name IS NOT NULL
						AND t.table_name != ''
						AND t.del_flag = 0
						AND dd.dict_label = '与资产投资相关费用';
    
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    
    OPEN table_cursor;
    
    
    read_loop: LOOP
        
        FETCH table_cursor INTO table_name;
        
        
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        
        SET where_condition = ' WHERE del_flag = "0" AND (status = 3 OR status = 5)';
        
        
        IF p_taskId IS NOT NULL THEN
            SET where_condition = CONCAT(where_condition, ' AND task_id = ', p_taskId);
        END IF;
        
        
        IF p_deptId != 1 THEN
            SET where_condition = CONCAT(where_condition, 
                                       ' AND dept_id IN (SELECT dept_id FROM sys_dept WHERE parent_id = ', 
                                       p_deptId, ')');
        END IF;
        
        
        SET @sql = CONCAT('SELECT IFNULL(SUM(budget), 0) INTO @current_sum FROM t_reporting_table_', 
                         table_name, where_condition);
												 
        
        PREPARE stmt FROM @sql;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        
        
        SET total = total + @current_sum;
    END LOOP;
    
    
    CLOSE table_cursor;
    
    
    SELECT total AS total_money;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
