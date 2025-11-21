/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.entity.DropItemsEvent;
import meteordevelopment.meteorclient.events.entity.player.InteractBlockEvent;
import meteordevelopment.meteorclient.events.entity.player.PlaceBlockEvent;
import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.actionmacros.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.List;

/**
 * Module for recording and playing back action macros.
 */
public class MacroRecorder extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRecording = settings.createGroup("Recording");
    private final SettingGroup sgPlayback = settings.createGroup("Playback");

    // General settings
    public final Setting<String> macroName = sgGeneral.add(new StringSetting.Builder()
        .name("macro-name")
        .description("Name of the current macro.")
        .defaultValue("my_macro")
        .build()
    );

    private final Setting<List<String>> savedMacros = sgGeneral.add(new StringListSetting.Builder()
        .name("saved-macros")
        .description("List of saved macros.")
        .defaultValue()
        .build()
    );

    // Recording settings
    private final Setting<Boolean> recordMovement = sgRecording.add(new BoolSetting.Builder()
        .name("record-movement")
        .description("Record player movement.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> recordRotation = sgRecording.add(new BoolSetting.Builder()
        .name("record-rotation")
        .description("Record player rotation (looking around).")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> recordMining = sgRecording.add(new BoolSetting.Builder()
        .name("record-mining")
        .description("Record block mining.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> recordDrops = sgRecording.add(new BoolSetting.Builder()
        .name("record-drops")
        .description("Record dropping items.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> recordInteractions = sgRecording.add(new BoolSetting.Builder()
        .name("record-interactions")
        .description("Record block interactions.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> movementInterval = sgRecording.add(new IntSetting.Builder()
        .name("movement-interval")
        .description("Interval in ticks between movement/rotation recordings (lower = more precise, higher = smaller file).")
        .defaultValue(5)
        .min(1)
        .sliderMax(20)
        .build()
    );

    // Playback settings
    private final Setting<String> playbackMacro = sgPlayback.add(new StringSetting.Builder()
        .name("playback-macro")
        .description("Name of the macro to play back.")
        .defaultValue("my_macro")
        .build()
    );

    private final Setting<Double> playbackSpeed = sgPlayback.add(new DoubleSetting.Builder()
        .name("playback-speed")
        .description("Speed multiplier for playback (1.0 = normal speed).")
        .defaultValue(1.0)
        .min(0.1)
        .sliderMax(5.0)
        .build()
    );

    private final Setting<Boolean> loopPlayback = sgPlayback.add(new BoolSetting.Builder()
        .name("loop-playback")
        .description("Loop the macro playback continuously.")
        .defaultValue(false)
        .build()
    );

    // Recording state
    private boolean isRecording = false;
    private ActionMacro currentMacro;
    private long recordingStartTime;
    private int movementTickCounter = 0;
    private float lastYaw, lastPitch;

    // Playback state
    private boolean isPlaying = false;
    private ActionMacro playingMacro;
    private long playbackStartTime;
    private int currentActionIndex = 0;

    public MacroRecorder() {
        super(Categories.Misc, "macro-recorder", "Records and plays back action macros.");
    }

    @Override
    public void onActivate() {
        if (!isRecording && !isPlaying) {
            startRecording();
        }
    }

    @Override
    public void onDeactivate() {
        if (isRecording) {
            stopRecording();
        }
        if (isPlaying) {
            stopPlayback();
        }
    }

    public void startRecording() {
        if (isPlaying) {
            ChatUtils.error("Cannot start recording while playing back a macro.");
            return;
        }

        isRecording = true;
        currentMacro = new ActionMacro(macroName.get(), "Recorded macro");
        recordingStartTime = System.currentTimeMillis();
        movementTickCounter = 0;

        if (mc.player != null) {
            lastYaw = mc.player.getYaw();
            lastPitch = mc.player.getPitch();
        }

        ChatUtils.info("Started recording macro: %s", macroName.get());
    }

    public void stopRecording() {
        if (!isRecording) return;

        isRecording = false;
        ActionMacros.get().add(currentMacro);

        ChatUtils.info("Stopped recording. Saved macro '%s' with %d actions (%.1fs duration).",
            currentMacro.name, currentMacro.getActionCount(), currentMacro.getDuration() / 1000.0);

        currentMacro = null;
    }

    public void startPlayback() {
        startPlayback(playbackMacro.get());
    }

    public void startPlayback(String name) {
        if (isRecording) {
            ChatUtils.error("Cannot start playback while recording.");
            return;
        }

        ActionMacro macro = ActionMacros.get().get(name);
        if (macro == null) {
            ChatUtils.error("Macro '%s' not found.", name);
            return;
        }

        if (macro.getActionCount() == 0) {
            ChatUtils.error("Macro '%s' has no actions.", name);
            return;
        }

        isPlaying = true;
        playingMacro = macro;
        playbackStartTime = System.currentTimeMillis();
        currentActionIndex = 0;

        ChatUtils.info("Started playback of macro '%s' (%d actions, %.1fs duration).",
            name, macro.getActionCount(), macro.getDuration() / 1000.0);
    }

    public void stopPlayback() {
        if (!isPlaying) return;

        isPlaying = false;
        playingMacro = null;
        currentActionIndex = 0;

        ChatUtils.info("Stopped playback.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        // Handle recording
        if (isRecording) {
            movementTickCounter++;

            if (movementTickCounter >= movementInterval.get()) {
                movementTickCounter = 0;

                long timestamp = System.currentTimeMillis() - recordingStartTime;

                // Record movement
                if (recordMovement.get()) {
                    currentMacro.addAction(new MovementAction(
                        timestamp,
                        mc.player.getEntityPos(),
                        mc.player.isOnGround()
                    ));
                }

                // Record rotation
                if (recordRotation.get()) {
                    float yaw = mc.player.getYaw();
                    float pitch = mc.player.getPitch();

                    if (yaw != lastYaw || pitch != lastPitch) {
                        currentMacro.addAction(new RotationAction(timestamp, yaw, pitch));
                        lastYaw = yaw;
                        lastPitch = pitch;
                    }
                }
            }
        }

        // Handle playback
        if (isPlaying && playingMacro != null) {
            long elapsedTime = (long) ((System.currentTimeMillis() - playbackStartTime) * playbackSpeed.get());

            // Execute all actions that should have happened by now
            while (currentActionIndex < playingMacro.getActionCount()) {
                RecordedAction action = playingMacro.getActions().get(currentActionIndex);

                if (action.getTimestamp() <= elapsedTime) {
                    action.execute();
                    currentActionIndex++;
                } else {
                    break;
                }
            }

            // Check if playback is complete
            if (currentActionIndex >= playingMacro.getActionCount()) {
                if (loopPlayback.get()) {
                    // Restart playback
                    playbackStartTime = System.currentTimeMillis();
                    currentActionIndex = 0;
                    ChatUtils.info("Looping macro playback...");
                } else {
                    stopPlayback();
                }
            }
        }
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent event) {
        if (isRecording && recordMining.get()) {
            long timestamp = System.currentTimeMillis() - recordingStartTime;
            currentMacro.addAction(new MineBlockAction(timestamp, event.blockPos, event.direction, true));
        }
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (!isRecording) return;

        // Record stop mining
        if (event.packet instanceof PlayerActionC2SPacket packet) {
            if (recordMining.get() && packet.getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
                long timestamp = System.currentTimeMillis() - recordingStartTime;
                currentMacro.addAction(new MineBlockAction(
                    timestamp,
                    packet.getPos(),
                    packet.getDirection(),
                    false
                ));
            }
        }
    }

    @EventHandler
    private void onDropItems(DropItemsEvent event) {
        if (isRecording && recordDrops.get()) {
            long timestamp = System.currentTimeMillis() - recordingStartTime;
            // Find which slot the item came from (simplified - uses selected slot)
            if (mc.player != null) {
                int slot = mc.player.getInventory().getSelectedSlot();
                currentMacro.addAction(new DropItemAction(timestamp, slot, false));
            }
        }
    }

    @EventHandler
    private void onInteractBlock(InteractBlockEvent event) {
        if (isRecording && recordInteractions.get()) {
            long timestamp = System.currentTimeMillis() - recordingStartTime;
            currentMacro.addAction(new InteractBlockAction(
                timestamp,
                event.result.getBlockPos(),
                event.result.getSide(),
                event.hand
            ));
        }
    }

    @EventHandler
    private void onPlaceBlock(PlaceBlockEvent event) {
        if (isRecording && recordInteractions.get() && mc.player != null) {
            long timestamp = System.currentTimeMillis() - recordingStartTime;
            // Use player's facing direction and active hand
            currentMacro.addAction(new PlaceBlockAction(
                timestamp,
                event.blockPos,
                mc.player.getHorizontalFacing(),
                mc.player.getActiveHand()
            ));
        }
    }

    // Public API for other modules
    public boolean isRecording() {
        return isRecording;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void addDisconnectAction() {
        if (isRecording) {
            long timestamp = System.currentTimeMillis() - recordingStartTime;
            currentMacro.addAction(new DisconnectAction(timestamp, "Macro disconnect"));
        }
    }
}
