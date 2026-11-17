# RyHa Client

> Professional, modular Minecraft Java Edition cheat client built on Fabric.

---

## Features

| Category | Modules |
|----------|---------|
| **Combat** | KillAura, AimAssist, AutoClicker, Reach, Criticals, Velocity, AntiKnockback, TriggerBot, Hitbox, AutoTotem, AutoArmor |
| **Movement** | Sprint, Fly, Speed, NoSlow, Step, Spider, Jesus, HighJump, LongJump, SafeWalk |
| **Player** | AutoEat, FastPlace, FastBreak, AutoTool, ChestStealer, InventoryMove, Freecam |
| **Render** | PlayerESP, MobESP, ItemESP, ChestESP, Tracers, NameTags, FullBright, XRay, HUD, Breadcrumbs |
| **World** | Nuker, Scaffold, Timer, FastMine, AutoFarm |
| **Misc** | AntiAFK, DiscordRPC, Notifications, ToggleMessages, Coordinates, ServerInfo |

---

## Requirements

- **Minecraft Java Edition 1.21.4**
- **Java 21**
- **Fabric Loader ≥ 0.16.0**
- **Fabric API**

---

## Build

```bash
# Clone the repo
git clone https://github.com/yourname/ryha-client.git
cd ryha-client

# Build the mod JAR
./gradlew build

# Output JAR location:
# build/libs/ryha-client-1.0.0.jar
```

---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for Minecraft 1.21.4
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) and place it in `mods/`
3. Place `ryha-client-1.0.0.jar` in your `.minecraft/mods/` folder
4. Launch Minecraft with the Fabric profile

---

## Usage

| Action | Key |
|--------|-----|
| Open ClickGUI | **Right Shift** |
| Toggle module | **Left Click** in ClickGUI |
| Open module settings | **Right Click** in ClickGUI |
| Close ClickGUI | **Right Shift** or **ESC** |

---

## ClickGUI

- **Search bar** at the top — type to filter modules
- **Category tabs** — Combat, Movement, Player, Render, World, Misc
- **Module list** — left-click to toggle, right-click for settings
- **Settings sidebar** — sliders (scroll to adjust), toggles, mode cycles, keybinds
- **Drag** the panel by the header

---

## HUD

The HUD renders in the top-right corner:
- RGB/gradient coloured active module list
- FPS, CPS (L/R), Ping, XYZ, Speed, Time stats in bottom-left

Configure it via the **HUD** module in the Render category.

---

## Configuration

Settings are saved automatically to `.minecraft/ryha/config.properties` on game exit
(or by pressing **F3+S** — hook configurable).

---

## Project Structure

```
src/main/java/com/ryha/client/
├── RyHaClient.java              Main entrypoint
├── config/
│   └── ConfigManager.java
├── event/
│   ├── EventBus.java
│   ├── Event.java
│   ├── EventListener.java
│   ├── Subscribe.java
│   └── events/               TickEvent, RenderEvent, KeyEvent, AttackEvent, MoveEvent, PacketEvent
├── gui/
│   ├── ClickGUI.java
│   └── hud/HUDRenderer.java
├── module/
│   ├── Module.java             Base class
│   ├── ModuleManager.java
│   ├── Category.java
│   ├── setting/               BooleanSetting, SliderSetting, ModeSetting, KeybindSetting
│   ├── combat/                11 modules
│   ├── movement/              10 modules
│   ├── player/                7 modules
│   ├── render/                10 modules
│   ├── world/                 5 modules
│   └── misc/                  6 modules
├── mixin/
│   ├── inject/                9 Mixin classes
│   └── accessor/              3 Accessor interfaces
└── util/
    ├── Logger.java
    └── RenderUtil.java
```

---

## License

MIT — do whatever you want, don't sue me.
