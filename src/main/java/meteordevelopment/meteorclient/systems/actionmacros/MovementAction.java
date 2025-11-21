/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays player movement.
 */
public class MovementAction extends RecordedAction {
    private Vec3d position;
    private boolean onGround;

    public MovementAction(long timestamp, Vec3d position, boolean onGround) {
        super(timestamp);
        this.position = position;
        this.onGround = onGround;
    }

    public MovementAction(NbtCompound tag) {
        super(tag.getLong("timestamp").orElse(0L));
        this.position = new Vec3d(
            tag.getDouble("x").orElse(0.0),
            tag.getDouble("y").orElse(0.0),
            tag.getDouble("z").orElse(0.0)
        );
        this.onGround = tag.getBoolean("onGround").orElse(false);
    }

    @Override
    public boolean execute() {
        if (mc.player == null) return false;
        mc.player.setPosition(position);
        mc.player.setOnGround(onGround);
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.MOVE;
    }

    @Override
    protected void writeToTag(NbtCompound tag) {
        tag.putDouble("x", position.x);
        tag.putDouble("y", position.y);
        tag.putDouble("z", position.z);
        tag.putBoolean("onGround", onGround);
    }

    @Override
    public String getDescription() {
        return String.format("Move to (%.1f, %.1f, %.1f)", position.x, position.y, position.z);
    }
}
