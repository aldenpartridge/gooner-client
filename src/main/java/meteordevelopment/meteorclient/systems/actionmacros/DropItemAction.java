/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.SlotActionType;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays dropping items.
 */
public class DropItemAction extends RecordedAction {
    private int slot;
    private boolean dropAll; // true = drop entire stack, false = drop one item

    public DropItemAction(long timestamp, int slot, boolean dropAll) {
        super(timestamp);
        this.slot = slot;
        this.dropAll = dropAll;
    }

    public DropItemAction(NbtCompound tag) {
        super(tag.getLong("timestamp"));
        this.slot = tag.getInt("slot");
        this.dropAll = tag.getBoolean("dropAll");
    }

    @Override
    public boolean execute() {
        if (mc.player == null || mc.interactionManager == null) return false;

        // Use the interaction manager to drop items from the slot
        mc.interactionManager.clickSlot(
            mc.player.currentScreenHandler.syncId,
            slot,
            dropAll ? 1 : 0,
            SlotActionType.THROW,
            mc.player
        );
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.DROP_ITEM;
    }

    @Override
    protected void writeToTag(NbtCompound tag) {
        tag.putInt("slot", slot);
        tag.putBoolean("dropAll", dropAll);
    }

    @Override
    public String getDescription() {
        return String.format("Drop %s from slot %d", dropAll ? "stack" : "item", slot);
    }
}
