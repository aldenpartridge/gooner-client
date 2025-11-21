/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays block interactions (buttons, levers, chests, etc.).
 */
public class InteractBlockAction extends RecordedAction {
    private BlockPos blockPos;
    private Direction direction;
    private Hand hand;

    public InteractBlockAction(long timestamp, BlockPos blockPos, Direction direction, Hand hand) {
        super(timestamp);
        this.blockPos = blockPos;
        this.direction = direction;
        this.hand = hand;
    }

    public InteractBlockAction(NbtCompound tag) {
        super(tag.getLong("timestamp"));
        this.blockPos = new BlockPos(
            tag.getInt("x"),
            tag.getInt("y"),
            tag.getInt("z")
        );
        this.direction = Direction.byId(tag.getInt("direction"));
        this.hand = tag.getBoolean("mainHand") ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }

    @Override
    public boolean execute() {
        if (mc.player == null || mc.interactionManager == null) return false;

        BlockHitResult hitResult = new BlockHitResult(
            Vec3d.ofCenter(blockPos),
            direction,
            blockPos,
            false
        );

        mc.interactionManager.interactBlock(mc.player, hand, hitResult);
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.INTERACT_BLOCK;
    }

    @Override
    protected void writeToTag(NbtCompound tag) {
        tag.putInt("x", blockPos.getX());
        tag.putInt("y", blockPos.getY());
        tag.putInt("z", blockPos.getZ());
        tag.putInt("direction", direction.getId());
        tag.putBoolean("mainHand", hand == Hand.MAIN_HAND);
    }

    @Override
    public String getDescription() {
        return String.format("Interact with block at (%d, %d, %d)", blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}
