/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import meteordevelopment.meteorclient.utils.player.Rotations;
import net.minecraft.nbt.NbtCompound;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays player rotation using smooth, anticheat-safe rotation.
 */
public class RotationAction extends RecordedAction {
    private float yaw;
    private float pitch;

    public RotationAction(long timestamp, float yaw, float pitch) {
        super(timestamp);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public RotationAction(NbtCompound tag) {
        super(tag.getLong("timestamp").orElse(0L));
        this.yaw = tag.getFloat("yaw").orElse(0f);
        this.pitch = tag.getFloat("pitch").orElse(0f);
    }

    @Override
    public boolean execute() {
        if (mc.player == null) return false;

        // Use Rotations utility for smooth, anticheat-safe rotation
        // This smoothly rotates the player instead of instantly snapping
        Rotations.rotate(yaw, pitch);
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.ROTATION;
    }

    @Override
    protected void writeToTag(NbtCompound tag) {
        tag.putFloat("yaw", yaw);
        tag.putFloat("pitch", pitch);
    }

    @Override
    public String getDescription() {
        return String.format("Look (yaw: %.1f, pitch: %.1f)", yaw, pitch);
    }
}
