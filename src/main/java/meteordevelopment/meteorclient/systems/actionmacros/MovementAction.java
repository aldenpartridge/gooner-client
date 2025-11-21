/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.MacroRecorder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays player movement using smooth velocity-based movement.
 * This is more anticheat-safe than teleporting.
 */
public class MovementAction extends RecordedAction {
    private Vec3d targetPosition;
    private Vec3d velocity;
    private boolean onGround;

    public MovementAction(long timestamp, Vec3d position, Vec3d velocity, boolean onGround) {
        super(timestamp);
        this.targetPosition = position;
        this.velocity = velocity;
        this.onGround = onGround;
    }

    public MovementAction(NbtCompound tag) {
        super(tag.getLong("timestamp").orElse(0L));
        this.targetPosition = new Vec3d(
            tag.getDouble("x").orElse(0.0),
            tag.getDouble("y").orElse(0.0),
            tag.getDouble("z").orElse(0.0)
        );
        this.velocity = new Vec3d(
            tag.getDouble("vx").orElse(0.0),
            tag.getDouble("vy").orElse(0.0),
            tag.getDouble("vz").orElse(0.0)
        );
        this.onGround = tag.getBoolean("onGround").orElse(false);
    }

    @Override
    public boolean execute() {
        if (mc.player == null) return false;

        // Get smoothness setting from MacroRecorder (lower = smoother/more legit)
        MacroRecorder recorder = Modules.get().get(MacroRecorder.class);
        double smoothness = recorder != null ? recorder.getMovementSmoothness() : 0.5;

        // Apply velocity for smooth, more realistic movement
        // This is much more anticheat-safe than teleporting
        Vec3d currentPos = mc.player.getEntityPos();
        Vec3d direction = targetPosition.subtract(currentPos);
        double distance = direction.length();

        // If we're very close, just set the position
        if (distance < 0.1) {
            mc.player.setPosition(targetPosition);
            mc.player.setVelocity(Vec3d.ZERO);
        } else {
            // Apply velocity toward target, scaled by smoothness multiplier
            // Lower smoothness = slower, more gradual movement (more legit)
            // Higher smoothness = faster, more aggressive movement
            double velocityMagnitude = Math.min(distance, velocity.length() * smoothness);
            Vec3d smoothVelocity = direction.normalize().multiply(velocityMagnitude);
            mc.player.setVelocity(smoothVelocity);
        }

        mc.player.setOnGround(onGround);
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.MOVE;
    }

    @Override
    protected void writeToTag(NbtCompound tag) {
        tag.putDouble("x", targetPosition.x);
        tag.putDouble("y", targetPosition.y);
        tag.putDouble("z", targetPosition.z);
        tag.putDouble("vx", velocity.x);
        tag.putDouble("vy", velocity.y);
        tag.putDouble("vz", velocity.z);
        tag.putBoolean("onGround", onGround);
    }

    @Override
    public String getDescription() {
        return String.format("Move to (%.1f, %.1f, %.1f) with velocity (%.2f, %.2f, %.2f)",
            targetPosition.x, targetPosition.y, targetPosition.z,
            velocity.x, velocity.y, velocity.z);
    }
}
