/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.nbt.NbtCompound;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays player rotation (looking around).
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
        super(tag.getLong("timestamp"));
        this.yaw = tag.getFloat("yaw");
        this.pitch = tag.getFloat("pitch");
    }

    @Override
    public boolean execute() {
        if (mc.player == null) return false;
        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
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
