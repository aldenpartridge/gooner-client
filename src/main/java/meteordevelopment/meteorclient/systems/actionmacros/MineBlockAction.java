/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays block mining actions.
 */
public class MineBlockAction extends RecordedAction {
    private BlockPos blockPos;
    private Direction direction;
    private boolean isStart; // true = start mining, false = stop mining

    public MineBlockAction(long timestamp, BlockPos blockPos, Direction direction, boolean isStart) {
        super(timestamp);
        this.blockPos = blockPos;
        this.direction = direction;
        this.isStart = isStart;
    }

    public MineBlockAction(NbtCompound tag) {
        super(tag.getLong("timestamp").orElse(0L));
        this.blockPos = new BlockPos(
            tag.getInt("x").orElse(0),
            tag.getInt("y").orElse(0),
            tag.getInt("z").orElse(0)
        );
        int dirId = tag.getInt("direction").orElse(0);
        this.direction = Direction.values()[dirId];
        this.isStart = tag.getBoolean("isStart").orElse(false);
    }

    @Override
    public boolean execute() {
        if (mc.player == null || mc.getNetworkHandler() == null) return false;

        PlayerActionC2SPacket.Action action = isStart
            ? PlayerActionC2SPacket.Action.START_DESTROY_BLOCK
            : PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK;

        mc.getNetworkHandler().sendPacket(
            new PlayerActionC2SPacket(action, blockPos, direction)
        );
        return true;
    }

    @Override
    public ActionType getType() {
        return isStart ? ActionType.MINE_START : ActionType.MINE_STOP;
    }

    @Override
    protected void writeToTag(NbtCompound tag) {
        tag.putInt("x", blockPos.getX());
        tag.putInt("y", blockPos.getY());
        tag.putInt("z", blockPos.getZ());
        tag.putInt("direction", direction.ordinal());
        tag.putBoolean("isStart", isStart);
    }

    @Override
    public String getDescription() {
        return String.format("%s mining block at (%d, %d, %d)",
            isStart ? "Start" : "Stop",
            blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}
