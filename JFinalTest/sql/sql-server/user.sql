/*
Navicat SQL Server Data Transfer

Source Server         : test
Source Server Version : 105000
Source Host           : localhost:1433
Source Database       : test
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2017-04-12 17:44:03
*/


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE [dbo].[user]
GO
CREATE TABLE [dbo].[user] (
[user_id] int NOT NULL identity(1,1) ,
[user_name] varchar(20) NULL ,
[phone] varchar(11) NULL ,
[password] varchar(50) NULL ,
[regist_time] datetime NULL ,
[is_valid] int NULL 
)


GO

-- ----------------------------
-- Indexes structure for table user
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE [dbo].[user] ADD PRIMARY KEY ([user_id])
GO
