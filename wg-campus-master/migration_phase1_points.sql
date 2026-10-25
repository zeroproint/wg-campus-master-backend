-- ============================================================
-- 一期：积分体系改造 —— 数据库迁移
-- 目标库：campus_mutual_aid
-- 执行方式：IDEA 里打开本文件 → 右上角数据源选 campus_mutual_aid → 点绿色三角形
-- ⚠️ ALTER 部分不可重复执行（MySQL 8 不支持 ADD COLUMN IF NOT EXISTS），
--    已执行过请勿再跑前半段；最后的 UPDATE 是幂等的，可安全重跑。
-- ============================================================

-- ------------------------------------------------------------
-- 1) wallet：新增积分字段，并修正 balance / withdrawn 反了的注释
--    注意：字段名和注释原本是反的 —— 实际语义是
--      balance   = 当前可用余额（真实货币）
--      withdrawn = 累计已提现
-- ------------------------------------------------------------
ALTER TABLE `wallet`
  ADD COLUMN `points` int NOT NULL DEFAULT 0 COMMENT '可用积分' AFTER `balance`,
  ADD COLUMN `frozen_points` int NOT NULL DEFAULT 0 COMMENT '冻结积分（发单托管中）' AFTER `points`;

ALTER TABLE `wallet`
  MODIFY COLUMN `withdrawn` decimal(10,2) NOT NULL COMMENT '已提现',
  MODIFY COLUMN `balance` decimal(10,2) NOT NULL COMMENT '当前余额（真实货币，互助员收益可提现）';

-- ------------------------------------------------------------
-- 2) order_main：记录本单消耗的积分
-- ------------------------------------------------------------
ALTER TABLE `order_main`
  ADD COLUMN `reward_points` int NOT NULL DEFAULT 0 COMMENT '本单消耗积分（发布者支付）' AFTER `total_amount`;

-- ------------------------------------------------------------
-- 3) capital_flow：新增积分数值列，并扩充 type 的含义说明
--    新增 type 取值：3 积分充值 4 注册赠送 5 积分冻结 6 积分解冻划转 7 积分退回
-- ------------------------------------------------------------
ALTER TABLE `capital_flow`
  ADD COLUMN `points` int NULL DEFAULT NULL COMMENT '积分变动（正为收入，负为支出）' AFTER `type`;

ALTER TABLE `capital_flow`
  MODIFY COLUMN `type` tinyint UNSIGNED NULL DEFAULT NULL
  COMMENT '类型 0订单收益 1互助提现 2代理提现 3积分充值 4注册赠送 5积分冻结 6积分解冻划转 7积分退回';

-- ------------------------------------------------------------
-- 4) 数据订正：给存量用户补 100 积分
--    幂等：只补当前为 0 的，重跑不会重复加
-- ------------------------------------------------------------
UPDATE `wallet` SET `points` = 100 WHERE `points` = 0;

-- ------------------------------------------------------------
-- 5) 校验
-- ------------------------------------------------------------
SELECT uid, balance, withdrawn, points, frozen_points FROM `wallet`;
