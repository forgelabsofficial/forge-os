# Forge OS — UI Redesign Plan

**Created:** 2026-08-01
**Updated:** 2026-08-01 (post-audit rewrite)
**Status:** PLANNING → AWAITING APPROVAL

---

## Design Philosophy

**"Quiet Power"** — A calm, confident interface that gets out of the way. The AI is the star; the chrome is invisible. Inspired by Linear's precision, Arc's warmth, and Things' restraint.

### Core Principles
1. **One accent** — Ember orange (#FF6B3D) is the ONLY brand color. Everything else is neutral.
2. **Semantic color only** — Green = success, Red = danger, Amber = warning, Blue = info. No decorative color.
3. **Depth through layering** — Subtle elevation, not borders. Cards float on the background.
4. **Typography does the work** — Clear hierarchy replaces color as the organizing force.
5. **8pt grid** — All spacing is a multiple of 4. No exceptions.
6. **Consistent radius** — 12dp for cards, 8dp for chips/small elements, 20dp for sheets/modals.

---

## Key Audit Finding

**Every mockup screen already exists in the app.** This is an *upgrade path*, not new-screen creation. The work is restyling, refining, and aligning existing screens to the "Quiet Power" system — not building from scratch.

### Screen Mapping (Mockup → Existing App)

| # | Mockup Screen | Existing App Screen | File | Gap |
|---|---|---|---|---|
| 1 | Chat — Empty | `ModernChatScreen` | `screens/chat/ModernChatScreen.kt` | Add branded header, suggestion chips, `+` composer button, remove FAB stack |
| 2 | Chat — Active | `ModernChatScreen` | `screens/chat/ModernChatScreen.kt` | Single-bubble activity pattern, remove floaters, ember ambience |
| 3 | Hub — Modules | `ModernHubScreen` | `screens/hub/ModernHubScreen.kt` | Neutral tiles (remove per-module colors), section grouping, single ember accent |
| 4 | Nav Drawer | `MainActivity` drawer | `MainActivity.kt` | Cleaner grouping, ember active indicator, remove emoji icons |
| 5 | Status — Health | `ModernStatusScreen` | `screens/ModernStatusScreen.kt` | Semantic dots stay, consistent card padding, rounded progress bars |
| 6 | Settings | `SettingsScreen` | `screens/SettingsScreen.kt` | Section grouping, ember toggles, consistent row height |
| 7 | Files | `WorkspaceScreen` + `FileViewerScreen` | `screens/WorkspaceScreen.kt` | Restyle to match system |
| 8 | Conversations | `ConversationsScreen` | `screens/conversations/ConversationsScreen.kt` | Restyle to match system |
| 9 | Tools | `ToolsScreen` | `screens/tools/ToolsScreen.kt` | Restyle to match system |
| 10 | Plugins | `PluginsScreen` | `screens/plugins/PluginsScreen.kt` | Restyle to match system |
| 11 | Memory | `MemoryScreen` | `screens/memory/MemoryScreen.kt` | Restyle to match system |
| 12 | Projects | `ProjectsScreen` | `screens/projects/ProjectsScreen.kt` | Restyle to match system |
| 13 | Browser | `BrowserScreen` | `screens/BrowserScreen.kt` | Restyle to match system |
| 14 | Companion | `CompanionScreen` | `screens/companion/CompanionScreen.kt` | Restyle to match system |
| 15 | Cron | `CronScreen` | `screens/cron/CronScreen.kt` | Restyle to match system |
| 16 | Agents | `AgentsScreen` | `screens/agents/AgentsScreen.kt` | Restyle to match system |

### Extra Real Screens (not in mockup, also need token migration)
Skills, Snapshots, MCP, Cost, External API, Alarms, Server, Doctor, Channels, Android, Debugger, Diagnostics, Backup, ModelRouting, Personality, Persona, CompanionCheckIns/Memory/Conversations, CronSession, PluginTile.

### Shared Infrastructure (already exists)
- `components/ModernComponents.kt` — ModernHeader, ModernCard, ModernButton, ForgeLogo, StatusBadge, SectionHeader, LoadingState, EmptyState
- Theme system — `Color.kt`, `ForgePalette.kt`, `Type.kt`, `Theme.kt`, `Spacing.kt` (already partially modified)

---

## Phase UI-1: Design Token Overhaul

### What changes
- **`Color.kt`** — Prune legacy aliases. Add `SurfaceGlass` (semi-transparent overlay). Tighten the neutral ramp.
- **`ForgePalette.kt`** — Remove `neuralPulse`, `thinking` legacy fields. Add `surfaceGlass`, `divider` (softer than border).
- **`Spacing.kt`** — Already good (8pt scale). No changes needed.
- **`Type.kt`** — Increase contrast between title and body weights. Titles go to Bold (700), body stays Normal (400). Add `headlineSmall` usage guidance.
- **`Theme.kt`** — No structural changes, just updated mappings.

### Files touched
- `theme/Color.kt`
- `theme/ForgePalette.kt`
- `theme/Type.kt`
- `theme/Theme.kt`

---

## Phase UI-2: Chat Screen Redesign

**Highest impact.** The chat screen is the app's front door.

### What changes
- **Header** — Two variants: branded (logo + title + status + model pill) for chat; simple (back + title) for all secondary screens. Move mic and settings into the side drawer. **Remove FAB stack** (clear + browser floaters).
- **Composer** — Add `+` button (ChatGPT-style) for attachments/actions. Floating pill shape, subtle shadow, send button uses ember accent when text is present.
- **Message bubbles** — User: subtle ember-tinted background, no border. AI: surface color, no border. Tool calls: compact inline chips with monospace, not full cards.
- **Activity pattern** — Single-bubble streaming indicator (subtle pulsing dot), not a full "Thinking..." text block.
- **Empty state** — Centered logo mark + "What can I help you build?" with suggestion chips.
- **Ambience** — Subtle ember/spark particle animation behind the message list (matches mockup phone ambience).

### Files touched
- `screens/chat/ModernChatScreen.kt`
- `components/ModernComponents.kt` (header variants, composer)

---

## Phase UI-3: Hub Screen Redesign

### What changes
- **Tile grid** — Remove per-module accent colors (currently 10+ distinct hues). All tiles use the same neutral surface with a single ember icon. Differentiate by icon shape, not color.
- **Search bar** — Integrated into the header, not a separate card.
- **Section headers** — Group tiles: "Core", "Agent", "Data", "Companion", "Tools", "Advanced". Subtle uppercase labels.
- **Tile design** — Icon + title + subtitle. Consistent 12dp radius. Subtle press feedback (scale + alpha).

### Files touched
- `screens/hub/ModernHubScreen.kt`
- `screens/hub/HubViewModel.kt` (if tile model changes)

---

## Phase UI-4: Navigation & Drawer

### What changes
- **Side drawer** — Cleaner section grouping. Remove emoji icons, use consistent line icons or no icons. Active route highlighted with ember left-border.
- **Status bar** — Match background color, no harsh contrast.
- **Header system** — Implement branded vs. simple header variants in `ModernComponents.kt` and apply across all screens.

### Files touched
- `MainActivity.kt` (drawer composable)
- `components/ModernComponents.kt` (header variants, drawer items)

---

## Phase UI-5: Status & Settings Screens

### What changes
- **Status screen** — Health dots stay semantic (green/amber/red). Cards get consistent padding. Progress bars get rounded ends.
- **Settings screen** — Group into sections with headers. Toggles use ember accent. Remove hardcoded colors, use palette.
- **Consistent list items** — All settings rows: same height, same padding, same typography.

### Files touched
- `screens/ModernStatusScreen.kt`
- `screens/SettingsScreen.kt`

---

## Phase UI-6: Module Screens Color Migration

### What changes
- **Systematic sweep** — Replace ALL hardcoded `Color(0xFF...)` with `forgePalette.*` references across all module screens.
- **Apply simple header variant** to all secondary screens.
- **Consistent card/list styling** per the design system.

### Files touched (all with hardcoded colors)
- `screens/companion/CompanionScreen.kt`
- `screens/companion/CompanionMemoryScreen.kt`
- `screens/voice/VoiceModeOverlay.kt`
- `screens/pulse/PulseScreen.kt`
- `screens/DiagnosticsScreen.kt`
- `screens/external/ExternalApiScreen.kt`
- `screens/channels/ChannelSessionViewScreen.kt`
- `screens/settings/ModelRoutingScreen.kt`
- `screens/tools/AdvancedOverridesScreen.kt`
- `screens/alarms/AlarmsScreen.kt`
- `screens/BrowserScreen.kt`
- `screens/MarkdownText.kt`
- Plus all module screens from the mapping table (Tools, Plugins, Memory, Projects, Cron, Agents, Conversations, Workspace)

---

## Phase UI-7: Micro-interactions & Polish

### What changes
- **Press states** — All clickable elements get subtle scale (0.97) + alpha (0.8) feedback.
- **Transitions** — Screen transitions: fade + slide (200ms). No jarring jumps.
- **Skeleton loading** — Replace generic spinners with content-shaped skeletons.
- **Haptic feedback** — Subtle tick on send, toggle, delete actions.
- **Ember ambience** — Reusable particle modifier for chat and hub backgrounds.

### Files touched
- `components/ModernComponents.kt`
- Various screen files for loading states

---

## Execution Order

```
UI-1 → UI-2 → UI-3 → UI-4 → UI-5 → UI-6 → UI-7
(tokens)  (chat)  (hub)  (nav)  (status)  (sweep)  (polish)
```

Each phase is independently shippable. Chat and Hub are the highest-impact.

---

## Progress Tracker

| Phase | Status | Files | Notes |
|-------|--------|-------|-------|
| UI-1 | ⬜ pending | 4 | Design tokens |
| UI-2 | ⬜ pending | 2 | Chat screen (highest impact) |
| UI-3 | ⬜ pending | 2 | Hub screen |
| UI-4 | ⬜ pending | 2 | Navigation & headers |
| UI-5 | ⬜ pending | 2 | Status & Settings |
| UI-6 | ⬜ pending | 20+ | Color migration sweep |
| UI-7 | ⬜ pending | 2+ | Polish & micro-interactions |
