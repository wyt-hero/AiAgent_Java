# TASK_INDEX

> Document ID: AIOS-TASK-001
>
> Version: 1.0.0
>
> Status: Active
>
> Owner: AI Architecture Team

---

# Purpose

TASK_INDEX 是整个 AiAgent-Java 项目的任务中心。

所有 AI（Qoder、Cursor、Claude Code、Copilot 等）必须以 TASK_INDEX 作为唯一任务入口。

任何新的开发任务必须首先登记到 TASK_INDEX。

禁止 AI 自行创建未登记任务。

---

# Principles

遵循以下原则：

1. Task First
2. Dependency First
3. Documentation First
4. Architecture First
5. Review Required
6. Test Required

---

# Task Lifecycle

所有任务必须经历以下生命周期：

```
TODO → READY → DOING → REVIEW → TESTING → DONE → RELEASED
```

任何任务不得跳过 REVIEW。

任何核心模块不得跳过 TESTING。

---

# Priority Definition

| Priority | Description | Examples |
|----------|-------------|----------|
| P0 | 阻塞项目。必须立即完成。 | Agent Kernel, Prompt Engine, Memory |
| P1 | 核心功能。 | MCP, Workflow, RAG |
| P2 | 增强功能。 | Admin, Monitoring, Dashboard |
| P3 | 优化。 | Refactor, Performance, UI, Documentation |

---

# Task Metadata

每个 Task 必须包含：

- Task ID
- Title
- Version
- Priority
- Milestone
- Owner
- Status
- Dependencies
- Required Reading
- Deliverables
- Acceptance Criteria
- Commit Message
- Next Task

---

# Dependency Rules

任何 Task 必须声明 Dependencies。

示例：

```
TASK-0005
  Dependencies: TASK-0001, TASK-0002, TASK-0003
```

如果依赖未完成：禁止执行。

---

# Required Reading Rules

任何 Task 必须声明 Required Reading。

示例：

```
Required Reading:
  - .ai/00_PROJECT/PROJECT.md
  - .ai/01_ARCHITECTURE/SYSTEM_ARCHITECTURE.md
  - .ai/02_RULES/JAVA_RULES.md
  - .ai/02_RULES/DIRECTORY_RULES.md
```

如果 Required Reading 缺失：禁止继续开发。

---

# Status Definition

| Status | Description |
|--------|-------------|
| TODO | 未开始 |
| READY | 依赖已完成，可以开始 |
| DOING | AI 开发中 |
| REVIEW | 等待 Review |
| TESTING | 测试中 |
| DONE | 完成 |
| RELEASED | 已发布 |

---

# Task Table

| Task | Title | Milestone | Priority | Status | Dependencies |
|------|-------|-----------|----------|--------|--------------|
| TASK-0001 | Initialize AIOS | M1 | P0 | DONE | None |
| TASK-0002 | Maven Multi Module | M1 | P0 | DONE | TASK-0001 |
| TASK-0003 | Common Module | M1 | P0 | DONE | TASK-0002 |
| TASK-0004 | Dependency Management | M1 | P0 | DONE | TASK-0002 |
| TASK-0005 | Code Style | M1 | P1 | TODO | TASK-0002 |
| TASK-0006 | Logging System | M1 | P1 | DONE | TASK-0003 |
| TASK-0007 | Exception Framework | M1 | P1 | DONE | TASK-0003 |
| TASK-0008 | Unified Result | M1 | P1 | TODO | TASK-0003 |
| TASK-0009 | API Specification | M1 | P1 | TODO | TASK-0004 |
| TASK-0010 | ADR System | M1 | P2 | TODO | TASK-0001 |

---

# AI Execution Rules

AI 执行 Task 必须遵循以下流程：

```
① 阅读 Required Reading
        ↓
② 检查 Dependencies
        ↓
③ 检查 Status
        ↓
④ 开始开发
        ↓
⑤ 更新文档
        ↓
⑥ 更新测试
        ↓
⑦ 更新 CHANGELOG
        ↓
⑧ 提交 Commit
        ↓
⑨ 更新 TASK_INDEX
```

---

# Forbidden

禁止：

- ❌ 跳过 Required Reading
- ❌ 修改未声明模块
- ❌ 修改 Architecture
- ❌ 修改 API
- ❌ 创建循环依赖
- ❌ 修改其他 Milestone
- ❌ 不更新文档
- ❌ 不更新 TASK_INDEX

---

# Completion Definition (Definition of Done)

Task 完成必须满足：

- ✓ Deliverables 完成
- ✓ Java Compile
- ✓ 单元测试通过
- ✓ Markdown 更新
- ✓ Mermaid 可渲染
- ✓ Commit 完成
- ✓ CHANGELOG 更新
- ✓ TASK_INDEX 更新

否则不得标记 DONE。

---

# Milestone Mapping

| Milestone | Name | Tasks |
|-----------|------|-------|
| Milestone-001 | Project Bootstrap | TASK-0001 ~ TASK-0010 |
| Milestone-002 | Agent Kernel | TASK-0011 ~ TASK-0020 |
| Milestone-003 | Prompt Engine | TASK-0021 ~ TASK-0030 |
| Milestone-004 | Memory | TASK-0031 ~ TASK-0045 |
| Milestone-005 | Workflow | TASK-0046 ~ TASK-0060 |
| Milestone-006 | MCP | TASK-0061 ~ TASK-0080 |
| Milestone-007 | RAG | TASK-0081 ~ TASK-0095 |
| Milestone-008 | Admin | TASK-0096 ~ TASK-0110 |
| Milestone-009 | Deployment | TASK-0111 ~ TASK-0125 |

---

# Future

后续将支持：

- 自动生成 Task Graph
- 自动生成 Mermaid Dependency Graph
- GitHub Issue Mapping
- GitHub Project Mapping
- Jira Mapping
- Azure DevOps Mapping
- AI 自动规划下一任务
