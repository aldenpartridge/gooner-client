/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.nbt.NbtCompound;

/**
 * Base class for all recorded actions in a macro.
 * Each action stores its type and timestamp relative to the start of recording.
 */
public abstract class RecordedAction {
    protected long timestamp; // Milliseconds since recording started

    public RecordedAction(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Execute this action in the game.
     * @return true if the action was executed successfully
     */
    public abstract boolean execute();

    /**
     * Get the type identifier for this action.
     */
    public abstract ActionType getType();

    /**
     * Serialize this action to NBT.
     */
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("type", getType().name());
        tag.putLong("timestamp", timestamp);
        writeToTag(tag);
        return tag;
    }

    /**
     * Write action-specific data to NBT.
     */
    protected abstract void writeToTag(NbtCompound tag);

    /**
     * Get a human-readable description of this action.
     */
    public abstract String getDescription();

    public enum ActionType {
        MOVE,
        ROTATION,
        ATTACK,
        MINE_START,
        MINE_STOP,
        PLACE_BLOCK,
        DROP_ITEM,
        INTERACT_BLOCK,
        INTERACT_ITEM,
        DISCONNECT
    }
}
