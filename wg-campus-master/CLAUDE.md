# 微光校园互助项目

## 项目信息

- 项目路径：`D:\graduation project\wg-campus-master`
- 项目名称：微光校园互助
- 原项目名：福U跑腿（fuu-run），已全部改名为“微光校园互助”
- 技术栈：Spring Boot 2.7.6 + Vue（Uni-app）+ MySQL + Redis
- 后端多模块结构：
  - `wg-admin`（启动入口）
  - `wg-common`（通用工具）
  - `wg-framework`（框架核心）
  - `wg-order`（订单模块）
  - `wg-oss`（对象存储）
  - `wg-payment`（支付模块，暂时保留）
  - `wg-system`（系统模块）
- Java 包名：`com.wg`
- 根 `pom.xml`：`groupId=com.wg`，`artifactId=wg-campus`，`version=1.0.0`
- 启动类：`com.wg.WgAdminApplication`
- 数据库：`campus_mutual_aid`（已导入 22 张表）
- 前端小程序目录：`wg-campus-uni`
- 前端后台管理目录：`wg-campus-admin`
- 数据库连接信息（`application-dev.yml`）：
  - `url: jdbc:mysql://localhost:3306/campus_mutual_aid`
  - 用户：root，密码：本地 MySQL 密码
- Redis：`localhost:6379`，无密码
- 后端端口：8081（IDEA 里运行 `WgAdminApplication` 启动）
- 前端运行方式：HBuilderX → 运行到微信开发者工具

## 已完成

### 1. 项目改名（全部完成）
- 包名 `com.karrecy` → `com.wg`
- 模块名 `fuu-*` → `wg-*`（7 个后端模块）
- 根 `pom.xml`：`fuu-run` → `wg-campus`，`groupId` → `com.wg`
- 启动类 `FuuAdminApplication` → `WgAdminApplication`
- 最外层文件夹 `fuu-run-master` → `wg-campus-master`，`fuu-run` → `wg-campus`，`fuu-run-admin` → `wg-campus-admin`，`fuu-run-uni` → `wg-campus-uni`
- 数据库名 `fuudb` → `campus_mutual_aid`

### 2. 前端改造（全部完成）
- 首页宫格：`帮取/帮送/帮买/万能` → `跑腿帮带 / 生活应急 / 学习技能 / 校园活动`
- 发单页面字段改成校园互助语义：
  - `物品类型` → `任务标题`（复用 `tag` 字段，varchar(10)）
  - `请输入详细描述` → `任务描述`
  - `追加金额/追加赏金` → `悬赏金额`
  - `配送时间` → `截止时间`
  - `起点/终点` → `任务起点/任务终点`
  - `提交订单` → `发布任务`
  - `自动取消` → `无人接单自动取消`
  - `补充物品图/附件图片` → `补充任务图片/任务附件`
- 全项目“跑腿” → “互助”清扫（11 个文件）：
  - `pages.json`：导航栏标题改成“互助介绍/互助申请/互助中心”
  - `detail.vue`、`runner.vue`、`chat.vue`、`introduce.vue`、`capitalflow.vue`、`profile.vue`、`my.vue`、`index.vue`、`runner.js`、`bangmai.vue`
  - 静态资源 `跑腿中心.png` → `互助中心.png`
- 修复了一个崩溃：`confirmTag()` 里 `activeTagIndex == -1` 时抛 TypeError

### 3. 文档改造（部分完成）
- `README.md` 改成“微光校园互助”，目录结构、技术栈、功能描述全部对齐现在的项目
- 保留了原作者“福U跑腿（fuu-run）”的署名
- 删掉了原作者的招商内容（商业版推广、客服微信、体验二维码等）

### 4. 后端 Java 残留清扫（全部完成，2026-10-17）
- 三个 `Fuu` 类改名：`FuuConfig` → `WgConfig`（含 7 个文件的 import / 字段类型 / 字段名 `fuuConfig` → `wgConfig`）、`FuuAdminApplicationTests` → `WgAdminApplicationTests`、`FuuSystemApplicationTests` → `WgSystemApplicationTests`
- 后端 Java 全量“跑腿” → “互助”：实际 **72 处 / 24 个文件**（原估约 40 处）。替换前已逐条确认无等值匹配或落库用途（`CapitalType.getType()` 全项目从未被调用，Redis key 和枚举值都是英文，安全）
- `application.yml` 配置前缀 `fuu:` → `wg:`（与 `WgConfig` 上的 `@ConfigurationProperties(prefix = "wg")` 成对修改），`name: Fuu-Run` → `name: 微光校园互助`
- `OrderSubmitDTO` 校验消息 4 条：物品类型→任务标题、详情→任务描述、指定时间→截止时间、追加金额→悬赏金额
- ⚠️ **有意保留未改**：`application.yml` 的 `jwt-secret-key`（改了会让所有已签发 JWT 失效、强制全员登出）和 `token`（是微信小程序后台配置的 token，改了会导致微信消息校验失败）——都等发布前统一处理
- ⚠️ `UserWxServiceImpl.java:45` 有个硬编码头像 URL 含 `karrecy-fuu-run`，指向外部图床的**真实图片**，改了会 404，保持不动

## 🔄 当前主线：积分体系改造（一期进行中，2026-10-17）

> **下次开工先看这一节。** 一期目标是「最小闭环」：注册送分 → 发单冻结 → 接单 → 验收划转 → 取消解冻。
> 充值入口和积分商店页面放**二期**。

### 已确认的设计决策（不要再重新讨论）

1. **平台和代理不抽积分**，积分全额给接单者；平台/代理抽成只对真实货币生效
2. **取消时扣的手续费全部给接单者**，积分必须守恒、不能凭空消失
3. **充值走模拟接口** `POST /payment/recharge/mock`，直接加分不扣钱，**不做真实微信支付**
4. 积分取整用 `totalAmount.intValue()`（直接截断小数位，如 6.50 → 6）

### 进度：第 1–3 块已完成并验证；第 4 块已并入第 2 块；第 5–9 块待做

**✅ 第 1 块：数据库 + 实体（已执行、已验证）**

- 新增迁移脚本 `migration_phase1_points.sql`（根目录，与 `init.sql` 并排）——**已在库上执行成功**
- `wallet` 加 `points` / `frozen_points`；`order_main` 加 `reward_points`；`capital_flow` 加 `points`
- ⚠️ `wallet.balance` / `withdrawn` 的注释原来**是反的**，已改对：`balance` = 当前可用余额（真实货币），`withdrawn` = 累计已提现。**别再被旧注释误导**
- `CapitalType` 加 5 个值：`POINTS_RECHARGE(3)` `POINTS_GIFT(4)` `POINTS_FREEZE(5)` `POINTS_RELEASE(6)` `POINTS_REFUND(7)`
- 实体同步：`Wallet`(+points/frozenPoints)、`OrderMain`(+rewardPoints)、`CapitalFlow`(+points)
- `init.sql` 已同步（供全新导入用）；**存量库只跑迁移脚本，别拿 `init.sql` 覆盖**
- 存量用户已补 100 积分（`UPDATE wallet SET points = 100 WHERE points = 0`，幂等）

**✅ 第 2 块：`submit()` 改为积分冻结（已编译、已验证，2026-10-25）**

- 查 `wallet.points` → 不足抛「积分不足，请先接单赚取积分或去积分商店充值」
- `points -= N`，`frozen_points += N`，写 `POINTS_FREEZE` 流水（`points = -N`）
- 订单直接落 `PENDING_ACCEPTANCE`；`order_payment.payment_status` 置 `PAID` + `payment_time`
- 延时队列从 `ORDER_PAY_CANCEL` 换成 `ORDER_PENDING_ACCEPT_CANCEL`
- **`submit()` 返回类型 `PayedVO` → `Long`**（`IOrderMainService` 和 `OrderController.add` 已同步改）
- 顺手清理：`init.sql` 里 `capital_flow` 的 `跑腿id/跑腿收益` → `互助员id/互助员收益`

**✅ 第 3 块：注册赠送积分（已完成、已用集成测试验证，2026-10-25）**

- `WgConfig` 加 `registerGiftPoints`；`application.yml` 加 `wg.register-gift-points: 100`
- 新增接口 `wg-system/.../service/IPointsService.java`（只声明 `grantRegisterGift(Long uid)`）
- 新增实现 `wg-payment/.../service/impl/PointsServiceImpl.java`：读配置 → `wallet.points += N` → 写 `capital_flow`（`type=4 POINTS_GIFT`、`points=+N`、`user_id=uid`、`order_id=null`）
- `LoginService.xcxLogin()` 在 `walletMapper.insert(wallet)` 之后调用 `pointsService.grantRegisterGift(uid)`
- **为什么接口放 system、实现放 payment**：`wg-payment` 依赖 `wg-system`（反向依赖会循环）。`LoginService` 属于 system，只能面向 system 里的接口编程；实际 Bean 由 payment 提供，wg-admin 启动时全量扫描 `com.wg`，能装配上
- 配置为 `0` 或负数 = 关闭赠送；PC 后台「新增用户」（`UserPcController.addAgent`）**不送**积分，只针对小程序自动注册
- ⚠️ **顺带发现（延后到第 9 块处理）**：`CapitalFlowServiceImpl.listCurr()` 给小程序用户只按 `runner_id` 过滤，而积分流水（含第 2 块的 `POINTS_FREEZE`）记的是 `user_id` → **小程序资金明细里看不到积分流水**。第 9 块加积分类型 picker 时要把查询条件改成 `runner_id = uid OR user_id = uid`（或统一字段），否则 picker 选了积分类型也是空列表

**⏳ 待做（第 4–9 块）**

4. ~~订单状态机~~ 已并入第 2 块（`OrderStatus.PENDING_PAYMENT` 枚举值等残留，划入第 6/7 块清理）
5. `confirm()` 验收划转：发布者 `frozen_points -=` → 接单者 `points +=`，写 `POINTS_RELEASE`；顺手删 `confirmTest()` 死代码
6. `cancel()` 及相关共 **8 处** `payService.refund()` 改为「解冻退回积分」；手续费给接单者；写 `POINTS_REFUND`
7. 删除支付设施：`PayListener`、`ORDER_PAY_*` 延时队列常量、`/notify/payNotify` 对订单的处理、`payAgain()`、订单链路的 `PayedVO`（**`wg-payment` 模块保留**，二期充值复用）
8. 前端 4 个发单页（qusong / bangmai / wanneng / yingji）：删 `uni.requestPayment` + `requestSubscribeMessage`，`desc="微信支付"` → 积分，`元` → 积分，提交前校验余额
9. 前端 `detail.vue` 删「继续付款」；`runner/center/center.vue` 加积分余额展示；`capitalflow.vue` 流水类型 picker 加积分类型

### ⚠️ 中间态风险（做到第 8 块之前一直存在）

1. **超时/取消路径会把积分永久锁死。** `OrderListener.pendingAcceptCancel()` 和 `cancel()` 目前**仍在调 `payService.refund()`**（微信退款），但积分订单根本没走过微信 → 退款必失败，而**解冻逻辑要等第 6 块才写**。
   → **现在不要测**「超时未接单自动取消」和手动取消。
   真锁死了用这条 SQL 救：
   ```sql
   UPDATE wallet w JOIN order_main o ON o.user_id = w.uid
   SET w.points = w.points + o.reward_points,
       w.frozen_points = w.frozen_points - o.reward_points
   WHERE o.status IN (5, 11) AND o.reward_points > 0;
   ```
2. **小程序发单会显示「支付失败」。** `submit` 的返回从预支付参数变成了订单号，前端还在读 `res.data.timeStamp` → `undefined` → `requestPayment` 报错。**但订单其实已创建成功、积分已冻结。** 第 8 块之前请用接口验证，别用小程序。

### 第 2 块的验证方法（下次开工第一件事）

前置：账号必须绑手机号 `UPDATE user_wx SET phone='13800000000' WHERE uid=<你的uid>;`

`POST /order/order/add`（带 token）：

```json
{"schoolId":4,"serviceType":0,"tag":"积分测试","detail":"验证积分冻结",
 "isTimed":0,"autoCancelTtl":1800,"gender":2,"additionalAmount":5.5,
 "endAddress":{"title":"福州大学旗山校区某宿舍楼"}}
```

> `floor_price 1.00 + 5.5 = 6.50` → `intValue()` 截断 → **`rewardPoints = 6`**（正好验证取整）

```sql
SELECT uid, points, frozen_points FROM wallet WHERE uid = <你的uid>;               -- 可用 -6，冻结 +6
SELECT id, reward_points, status FROM order_main ORDER BY create_time DESC LIMIT 1; -- status = 1
SELECT * FROM capital_flow ORDER BY id DESC LIMIT 1;                                -- type = 5, points = -6
```

### 第 3 块的验证方法（已自动跑通，2026-10-25）

> 从这一块开始，验证由 Claude 直接跑集成测试，不再让用户手工造新号。以后每块都按这个套路补一个 IT。

- 测试类：`wg-campus/wg-admin/src/test/java/com/wg/web/RegisterGiftIT.java`
- 做法：`@MockBean WxMaService` 让 `xcxLogin` 拿到固定 openid，走真实 HTTP + 真实 MySQL/Redis，断言钱包和流水，`@AfterEach` 清理测试账号
- 覆盖：首次登录送 100 → 钱包 `points=100`、`frozen_points=0`、`capital_flow` 一条 `type=4 / points=+100 / order_id=NULL`；同 openid 再登录不重复赠送
- 跑法（前提：MySQL + Redis 已启动）：
  ```bash
  mvn -Pit -pl wg-admin -am test -Dtest=RegisterGiftIT -Dsurefire.failIfNoSpecifiedTests=false
  ```
  `pom.xml` 里 surefire 默认 `<skip>true</skip>`（日常构建不跑测试），`-Pit` 是为此新增的测试 profile
- 2026-10-25 实跑结果：`Tests run: 1, Failures: 0, Errors: 0`，测试数据已清理（库里无 `type=4` 流水残留）

### 改完要做的操作

| 块 | 操作 |
|---|---|
| 第 1 块 | 跑 `migration_phase1_points.sql` → 重新构建 → 重启（**已完成**） |
| 第 2 块 | 重新构建（`Build → Rebuild Project`）+ 重启后端；`pom.xml` 没动 → **不需要刷新 Maven**；本轮**无数据库改动** |
| 第 3 块 | 重新构建 + 重启后端（改了 Java + `application.yml`）；`pom.xml` / 数据库都没动 |

## 待办

### 1. ~~部署文档改造~~ ✅ 已作废（2026-10-17）：`deployment/` 文件夹已删除，该任务不再适用

### 2. ~~后端残留（待统一处理）~~ ✅ 已完成，见「已完成 / 4」

### 3. 截图替换（需要手动做）
- `docImgs/mini/互助中心.jpg` 和 `互助介绍.jpg` 里拍的还是旧版“跑腿中心”界面，需要重新截图替换

### 4. 积分体系改造 —— 🔄 进行中，见上面「🔄 当前主线：积分体系改造」一节

### 5. 亮点功能（待做）
- AI 智能互助助手（自然语言检索任务）
- ECharts 数据大屏
- AOP 自动审计切面
- 防恶意不确认 / 防爽约机制

## 注意事项

- `unpackage/dist/` 是构建产物，改完源码后需要在 HBuilderX 重新编译，小程序才会生效
- 备份在 `D:\graduation project\wg-campus-master\_bak-20260929\`，确认没问题后可以删
- 微信开发者工具如果提示“需要重新登录”，手动打开工具扫码登录即可
- Redis 用 `start.bat` 启动，看到 `Ready to accept connections tcp` 就正常

## 常用命令

- 启动后端：IDEA 里运行 `WgAdminApplication`
- 启动 Redis：双击 `start.bat`
- 启动小程序：HBuilderX 右键 `wg-campus-uni` → 运行到微信开发者工具
- 数据库导入：IDEA 里打开 `init.sql`，右上角选 `campus_mutual_aid`，点绿色三角形执行
## 更新记录（2026-09-29 早）

### 本轮新增完成
- 发单页导航栏标题修复：`pages.json` 里 qusong → “互助帮带”、bangmai → “学习技能”、wanneng → “校园活动”
- 首页宫格文字已改成“互助帮带 / 生活应急 / 学习技能 / 校园活动”
- “我的”页面已改成“申请互助 / 我的订单 / 我的地址”等

### 本轮发现但未处理的问题
- ~~**首页宫格与发单页对不上**~~：✅ 2026-10-17 已修（新增 `yingji` 页面，见「已完成 / 4」与「优先级 1」）
- **订单列表页暂时无法验证**：下单流程依赖手机号绑定和微信支付，目前未打通，看不到订单状态文案。
- **`pages/API/order/test/test` 撞名**：这个测试页的标题也是“万能帮”，不在正式流程里，后续可以清掉。

## 待办（按优先级排序）

### 优先级 1：修复首页 4 宫格 —— 前端 ✅ 已完成（2026-10-17），后端 service_type 仍待办
- 已新建 `pages/API/order/yingji/yingji.vue`（生活应急），`pages.json` 已注册（标题“生活应急”），首页 `index.vue` 的“生活应急”宫格已改跳 `yingji`
- 已验证：`qusong.vue` 里**没有任何**硬编码“互助帮带”，全文只剩两处正确位置（首页宫格文字 + `pages.json` 里 qusong 的标题）
- **仍未做**：`yingji.vue` 目前仍提交 `serviceType: 0`，代码里有 TODO 注释。**不能只在前端改成 3**，原因：
  - 后端不校验 `serviceType`，只是原样存库并按值分支。`OrderMainServiceImpl` 里**只有 `serviceType == 0` 会保存起点地址**
  - `getListTag(serviceType)` 按该值过滤标签，库里只有 0/1/2 的种子数据
  - 所以前端直接发 3 → 「起点地址被静默丢弃 + 任务标题标签列表为空」
- **改动清单**：
  1. `ServiceType` 枚举加 `LIFE_EMERGENCY(3)`
  2. `Tags` 表加 service_type=3 的种子数据
  3. `OrderMainServiceImpl` 的起点地址分支支持 3
  4. 顺手修 bug：`OrderMainServiceImpl:221` 第三个分支写的是 `PURCHASE(1)`（与上面重复），应为 `GENERAL_HELP(2)`，导致 serviceType=2 现在没有任何特殊处理
  5. 前端 `yingji.vue` 的 `serviceType` 改成 3
- **顺带发现**：订单列表/详情页仍是旧服务名 —— `pages/API/order/list/list.vue`、`detail/detail.vue`、`runner/runner.vue`、`pages/tabBar/order/order.vue` 里都还是「帮取送 / 帮买 / 万能帮」，没跟着改成「互助帮带 / 学习技能 / 校园活动」

### 优先级 2：后端 Java 残留清扫 —— ✅ 已完成（2026-10-17）
详见「已完成 / 4」。

### 优先级 3：~~部署文档改造~~ ✅ 已作废（2026-10-17）：`deployment/` 文件夹已删除，不再有旧标识需要清扫

### 优先级 4：截图替换（需要手动做）
- `docImgs/mini/互助中心.jpg` 和 `互助介绍.jpg` 里拍的还是旧版“跑腿中心”界面，需要重新截图替换

### 优先级 5：积分体系改造 —— 🔄 进行中，见上面「🔄 当前主线：积分体系改造」一节

### 优先级 6：亮点功能（待做）
- AI 智能互助助手（自然语言检索任务）
- ECharts 数据大屏
- AOP 自动审计切面
- 防恶意不确认 / 防爽约机制

## 常用命令速查

- 启动后端：IDEA 里运行 `WgAdminApplication`
- 启动 Redis：双击 `start.bat`（看到 `Ready to accept connections tcp` 就正常）
- 启动小程序：HBuilderX 右键 `wg-campus-uni` → 运行到微信开发者工具
- 数据库导入：IDEA 里打开 `init.sql`，右上角选 `campus_mutual_aid`，点绿色三角形执行
- 集成测试（需 MySQL + Redis 已启动）：`mvn -Pit -pl wg-admin -am test -Dtest=<测试类> -Dsurefire.failIfNoSpecifiedTests=false`
- 微信开发者工具登录过期：手动打开工具扫码登录

## 注意事项

- `unpackage/dist/` 是构建产物，改完源码后需要在 HBuilderX 重新编译，小程序才会生效
- 备份在 `D:\graduation project\wg-campus-master\_bak-20260929\`，确认没问题后可以删
- 微信开发者工具如果提示“需要重新登录”，手动打开工具扫码登录即可
- `pages.json` 是 JSONC 格式（带注释），标准 `JSON.parse` 解析会报错，属于正常现象

## 更新记录（2026-10-17）

### 本轮完成
- **前端**：新增「生活应急」发单页 `pages/API/order/yingji/yingji.vue`，`pages.json` 已注册（标题「生活应急」），首页宫格改跳 `yingji`。点「生活应急」的导航栏标题不再显示「互助帮带」
- **后端**：三个 `Fuu` 类改名、全量「跑腿」→「互助」（72 处 / 24 文件）、`OrderSubmitDTO` 校验消息 4 条、`application.yml` 前缀 `fuu:` → `wg:` + `name: 微光校园互助`
- 详见「已完成 / 4」和「优先级 1 / 2」
- **项目结构变动**：`deployment/` 文件夹已删除（原 `deployment/快速部署服务器.md` 不再存在），「部署文档改造」任务作废；`README.md` 里 3 处指向它的引用（项目结构树 1 处 + 「🚀 服务器部署」整个章节）已一并清除，README 无死链

### ⚠️ 两条容易踩的坑（下次开工先看这里）

1. **源码路径比本文档开头写的深一层**
   真实工程在 `D:\graduation project\wg-campus-master\wg-campus-master\`（注意是**双层同名目录**）。外层那个目录下只有工程本体 + `_bak-20260929\` 备份。按本文档开头的路径直接拼会 `No such file or directory`。

2. **后端改完不是「刷新 Maven」，而是「清 target → 重新编译 → 重启后端」**
   - 类改名后 `target/` 里还残留旧 .class：`FuuConfig.class`、`FuuAdminApplicationTests.class`、`FuuSystemApplicationTests.class`，另外还有 **268 个旧包名 `com/karrecy/` 下的 .class**
   - **增量编译不会删除源文件已消失的 .class**。而 `FuuConfig` 和 `WgConfig` 都是 `@Component` + `@ConfigurationProperties`，新旧两个会同时被扫到
   - 所以必须 `mvn clean` 或 IDEA 的 `Build → Rebuild Project`，然后重启 `WgAdminApplication`
   - 本轮没改 `pom.xml`，`Reload Maven Project` 解决不了这个问题

## 更新记录（2026-10-17 晚 · 积分改造一期第 1、2 块）

### 完成
- **第 1 块（已执行 + 已验证）**：`migration_phase1_points.sql` 已在库上跑成功。`wallet` 加 `points`/`frozen_points`、`order_main` 加 `reward_points`、`capital_flow` 加 `points`；`CapitalType` 加 `POINTS_*` 3–7；`Wallet`/`OrderMain`/`CapitalFlow` 三个实体同步；存量用户补 100 积分；`wallet.balance`/`withdrawn` 反了的注释已改对
- **第 2 块（已编译 + 已验证，2026-10-25）**：`submit()` 改为「查积分 → 不足拦截 → 扣可用 / 加冻结 → 写 `POINTS_FREEZE` 流水 → 订单直接落 `PENDING_ACCEPTANCE`」；延时队列换成 `ORDER_PENDING_ACCEPT_CANCEL`；`submit()` 返回类型 `PayedVO` → `Long`
- 三个已确认的设计决策、第 3–9 块清单、中间态风险、验证 SQL → 全部记在上面「🔄 当前主线：积分体系改造」一节

## 更新记录（2026-10-25 · 积分改造一期第 3 块）

### 完成
- **第 2 块验证通过**：发单后 `points -6 / frozen_points +6`，`capital_flow type=5, points=-6`，订单 `status=1`，取整正确
- **第 3 块完成并自动验证通过**：注册送积分 + 配置项，`RegisterGiftIT` 实跑 `Tests run: 1, Failures: 0, Errors: 0`，测试账号已清理
- **新增集成测试设施**：`-Pit` profile + `RegisterGiftIT`（mock 微信登录 → 真实 HTTP/MySQL/Redis → 断言 → 清理），以后每块照此补 IT，用户不用再手工验证
- 发现并记下：小程序资金明细按 `runner_id` 过滤，看不到记在 `user_id` 上的积分流水，**延后到第 9 块一起修**

### 下次开工第一步
1. 直接进第 5 块：`confirm()` 验收划转（第 4 块已并入第 2 块）
2. 实现后补一个 `ConfirmReleaseIT`（参考 `RegisterGiftIT`），用 Claude 直接跑通再交付
3. 用户侧只需要 `Build → Rebuild Project` + 重启后端（第 3 块改了 Java + `application.yml`；**无数据库改动**）

### 遗留（本次没做，与积分改造无关）
- `init.sql` 里还有约 15 处“跑腿”字样（`order_main.runner_id`、`school.profit_runner`、`statistics_daily`、`user_wx`、`perm` 表的菜单名等）。本次只按需清理了 `capital_flow` 的两处，其余未动