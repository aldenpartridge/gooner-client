# Action Macros System

A comprehensive system for recording and playing back sequences of player actions in Meteor Client with **anticheat-safe playback**.

## Overview

The Action Macros system allows you to:
- Record sequences of actions (movement, mining, dropping items, etc.)
- Save macros with persistent storage
- Play back recorded macros with smooth, anticheat-safe movement and rotation
- Adjust playback speed and smoothness to avoid detection
- Loop macro playback
- Integrate macros with other modules (e.g., trigger macros when player detected)

## ⚠️ Anticheat Safety

The macro system is designed to be anticheat-safe:

### Movement
- **Does NOT teleport** - Uses smooth velocity-based movement instead of instant position changes
- **Respects physics** - Applies velocity gradually to simulate realistic player movement
- **Configurable smoothness** - Adjust `movement-smoothness` (0.1-2.0) based on anticheat strictness
  - Lower values (0.1-0.5) = Smoother, more legit-looking movement (recommended for strict anticheats)
  - Higher values (1.0-2.0) = Faster, more aggressive movement (use only on lenient servers)

### Rotation
- **Smooth rotation** - Uses the built-in `Rotations` utility for gradual, realistic camera movement
- **No instant snapping** - Rotations are interpolated over time instead of snapping instantly

### Recommendations
1. **Test in singleplayer first** to see how the macro looks
2. **Start with low smoothness (0.3-0.5)** and increase if needed
3. **Reduce playback speed (0.5x)** for extra safety on strict anticheats
4. **Avoid extreme movements** - Don't record macros with impossible speeds or actions
5. **Use realistic timings** - Record macros at normal playing speed

## Components

### 1. MacroRecorder Module
Location: `Misc` category

The main module for recording and playing back macros.

**Settings:**
- **macro-name**: Name of the macro to record
- **record-movement**: Record player movement
- **record-rotation**: Record player rotation (looking around)
- **record-mining**: Record block mining actions
- **record-drops**: Record item dropping
- **record-interactions**: Record block interactions
- **movement-interval**: Ticks between movement recordings (lower = more precise)
- **playback-macro**: Name of the macro to play back
- **playback-speed**: Speed multiplier for playback (1.0 = normal speed)
- **loop-playback**: Loop the macro continuously
- **movement-smoothness**: Controls playback smoothness (0.1-2.0). Lower = smoother/more legit, higher = faster. Default: 0.5

### 2. ActionMacro Command
Command: `.action-macro`, `.amacro`, or `.am`

**Subcommands:**

#### Record a macro
```
.am record <name>
```
Starts recording a new macro with the specified name.

#### Stop recording
```
.am stop
```
Stops the current recording and saves the macro.

#### Play a macro
```
.am play <name>
```
Plays back the specified macro.

#### Stop playback
```
.am stop-playback
```
Stops the current macro playback.

#### List saved macros
```
.am list
```
Shows all saved macros with their action counts and durations.

#### Get macro info
```
.am info <name>
```
Shows detailed information about a specific macro, including the first 5 actions.

#### Delete a macro
```
.am delete <name>
```
Permanently deletes the specified macro.

## Usage Examples

### Example 1: Basic Recording
```
1. Type: .am record my_first_macro
2. Perform actions (walk, mine blocks, drop items, etc.)
3. Type: .am stop
4. Type: .am play my_first_macro (to play it back)
```

### Example 2: Auto-logout Macro
```
1. Type: .am record emergency_logout
2. Mine a few blocks
3. Pick up the items
4. Walk to a safe location
5. Type: .am stop

# Now you can trigger this macro from other modules
```

### Example 3: Integration with Notifier
You can use the MacroRecorder module's public API from other modules:

```java
MacroRecorder recorder = Modules.get().get(MacroRecorder.class);

// In your module's event handler:
@EventHandler
private void onPlayerDetected(EntityAddedEvent event) {
    if (event.entity instanceof PlayerEntity && shouldEvade) {
        recorder.startPlayback("emergency_logout");
    }
}
```

### Example 4: Adding Custom Actions
You can add custom actions to a recording:

```java
MacroRecorder recorder = Modules.get().get(MacroRecorder.class);

if (recorder.isRecording()) {
    // Add a disconnect action to the current recording
    recorder.addDisconnectAction();
}
```

## Action Types

The system supports the following action types:

1. **MovementAction**: Records player position and ground state
2. **RotationAction**: Records yaw and pitch
3. **MineBlockAction**: Records starting/stopping block mining
4. **DropItemAction**: Records dropping items from inventory
5. **PlaceBlockAction**: Records block placement
6. **InteractBlockAction**: Records interacting with blocks (chests, buttons, etc.)
7. **DisconnectAction**: Records disconnecting from the server

## File Storage

Macros are automatically saved to: `<meteor-folder>/action-macros.nbt`

The system uses NBT (Named Binary Tag) format for efficient serialization.

## Advanced Features

### Playback Speed Control
Adjust the playback speed in the MacroRecorder module settings:
- 0.5x = Half speed (slower)
- 1.0x = Normal speed
- 2.0x = Double speed (faster)

### Loop Playback
Enable "loop-playback" in the module settings to continuously repeat a macro.

### Movement Precision
Adjust "movement-interval" to control recording precision:
- Lower values (1-2): Very precise but larger file sizes
- Higher values (10-20): Less precise but smaller files
- Default (5): Good balance

## API for Module Developers

### Check Recording Status
```java
MacroRecorder recorder = Modules.get().get(MacroRecorder.class);
boolean isRecording = recorder.isRecording();
boolean isPlaying = recorder.isPlaying();
```

### Trigger Playback
```java
recorder.startPlayback("macro_name");
recorder.stopPlayback();
```

### Add Custom Actions
```java
if (recorder.isRecording()) {
    recorder.addDisconnectAction();
}
```

### Access Saved Macros
```java
ActionMacros macros = ActionMacros.get();
ActionMacro macro = macros.get("macro_name");

if (macro != null) {
    int actionCount = macro.getActionCount();
    long duration = macro.getDuration(); // milliseconds
    List<RecordedAction> actions = macro.getActions();
}
```

## Tips

1. **Test in safe environments first**: Always test macros in single-player or safe multiplayer areas before using them in production.

2. **Keep macros short**: Shorter macros are more reliable and easier to debug.

3. **Use descriptive names**: Name your macros clearly (e.g., "emergency_logout", "mine_obsidian", "collect_items").

4. **Adjust movement interval**: If your macro isn't precise enough, lower the movement-interval setting.

5. **Combine with other modules**: The real power comes from triggering macros based on events from other modules like Notifier, AutoLog, etc.

## Troubleshooting

**Macro doesn't play back correctly:**
- Ensure the environment is similar to where it was recorded
- Lower the movement-interval setting for more precision
- Check that all required blocks/items are available

**Recording is too large:**
- Increase the movement-interval setting
- Disable recording of actions you don't need (e.g., rotation if not necessary)

**Playback is too fast/slow:**
- Adjust the playback-speed setting
- Default is 1.0 (normal speed)

## Future Enhancements

Possible future additions:
- GUI for managing macros
- Import/export macros as JSON
- Macro editing capabilities
- Conditional actions (if-then-else)
- Variables and loops
- Macro chaining (run macro A, then macro B)
