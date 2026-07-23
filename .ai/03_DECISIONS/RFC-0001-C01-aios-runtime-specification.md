# RFC-0001 Chapter-01
# AIOS Runtime Specification
## Chapter-01：Overview & Runtime Architecture

| Item | Value |
|------|------|
| Document ID | RFC-0001-C01 |
| Title | AI Runtime Specification |
| Version | v1.0.0 |
| Status | Stable |
| Author | AIOS Architecture Team |
| Updated | 2026-07-23 |

---

# 1. Purpose

本规范定义 AiAgent-Java 项目的 AI Runtime（AI运行时）规范。

AI Runtime 是整个 AIOS (AI Operating System) 的入口。

任何 AI（Qoder、Cursor、Claude Code、Copilot、OpenAI Codex 等）开始工作之前，都必须完成 Runtime 初始化。

AI Runtime 的职责不是生成代码，而是：

- 理解项目
- 理解架构
- 理解规则
- 理解上下文
- 理解当前开发阶段
- 规划执行路径
- 验证依赖
- 执行开发任务

因此，本规范属于整个 AIOS 的最高级规范之一。

---

# 2. Goals

## Goal-001：统一 AI 启动流程

所有 AI 工具必须采用统一的启动流程。禁止不同 AI 使用不同的开发规范。

## Goal-002：统一上下文

任何 AI 在开始开发之前必须获得完全一致的上下文。包括：Project, Architecture, Rules, Knowledge, Execution, Runtime State。任何 AI 不得依赖聊天上下文。

## Goal-003：统一开发流程

Understand → Plan → Validate → Implement → Review → Test → Commit → Update Documentation

## Goal-004：统一项目规范

所有规范均来源于 Runtime。Runtime 是唯一入口。

---

# 3. Runtime Position

```
                Human
                  │
                  ▼
           AI Runtime
                  │
    ┌─────────────┼─────────────┐
    ▼             ▼             ▼
 Manifest     Constitution   Knowledge
    │             │             │
    └─────────────┼─────────────┘
                  ▼
            Execution Graph
                  │
                  ▼
            Current Node
                  │
                  ▼
             Source Code
```

---

# 4. Runtime Responsibilities

## 4.1 Project Loader
读取 PROJECT.md, ROADMAP.md, CHANGELOG.md, README.md

## 4.2 Architecture Loader
读取 SYSTEM_ARCHITECTURE.md, MODULE_ARCHITECTURE.md, PACKAGE_ARCHITECTURE.md, DATABASE_ARCHITECTURE.md, API_ARCHITECTURE.md

## 4.3 Rule Loader
读取 JAVA_RULES.md, DIRECTORY_RULES.md, CODE_STYLE.md, SECURITY_RULES.md, TEST_RULES.md

## 4.4 Knowledge Loader
读取 Knowledge Base, ADR, FAQ, Best Practice, Pattern, Design Guide

## 4.5 Execution Loader
读取 Execution Graph, Current Milestone, Current Node, Task Status, Dependencies

## 4.6 Context Loader
构建上下文：Project, Current Branch, Current Version, Current Milestone, Current Node, Current Goal, Current Constraints, Current Required Reading

---

# 5. Runtime Lifecycle

Initialize → Load Manifest → Load Constitution → Load Architecture → Load Rules → Load Knowledge → Load Execution Graph → Resolve Dependencies → Build Context → Validate → Execute → Review → Test → Commit → Update Documents → Finish

任何阶段失败必须终止。禁止继续开发。

---

# 6. Runtime State

Runtime 维护统一状态：Runtime State, Execution State, Context State, Knowledge State, Architecture State, Validation State, Review State, Testing State, Release State。任何状态变化必须记录。

---

# 7. Runtime Principles

- Principle-001: Single Source of Truth
- Principle-002: Documentation First
- Principle-003: Architecture First
- Principle-004: Dependency First
- Principle-005: Validation First
- Principle-006: Review Required
- Principle-007: Test Required

---

# 8. Runtime Boot Sequence

Step-01 读取 manifest.yaml → Step-02 读取 PROJECT.md → Step-03 读取 Architecture → Step-04 读取 Rules → Step-05 读取 Knowledge → Step-06 读取 Execution Graph → Step-07 定位当前 Node → Step-08 读取 Required Reading → Step-09 验证 Dependencies → Step-10 构建 Context → Step-11 开始开发

---

# 9-13. Context Model / Validation / Forbidden / Outputs / Summary

Runtime 维护 10 种 Context 统一管理。开发前必须验证 Repository, Manifest, Architecture, Rules, Knowledge, Execution Graph, Current Node, Required Reading, Dependencies, Branch。禁止跳过 Required Reading, Architecture, Validation, Review, Test。输出必须包含代码、文档、Execution Graph、Task 状态、CHANGELOG、ADR、Review/Test 结果、Commit Message。

---

End of Chapter-01
