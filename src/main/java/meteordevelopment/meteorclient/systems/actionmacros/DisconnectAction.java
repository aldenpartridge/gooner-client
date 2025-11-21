/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;

import static meteordevelopment.meteorclient.MeteorClient.mc;

/**
 * Records and replays disconnecting from the server.
 */
public class DisconnectAction extends RecordedAction {
    private String reason;

    public DisconnectAction(long timestamp, String reason) {
        super(timestamp);
        this.reason = reason;
    }

    public DisconnectAction(NbtCompound tag) {
        super(tag.getLong("timestamp").orElse(0L));
        this.reason = tag.getString("reason").orElse("Macro disconnect");
    }

    @Override
    public boolean execute() {
        if (mc.player == null || mc.player.networkHandler == null) return false;

        Text text = Text.literal("[Macro] ").append(Text.literal(reason));
        mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(text));
        return true;
    }

    @Override
    public ActionType getType() {
        return ActionType.DISCONNECT;
    }

    @Override
    protected void writeToTag(NbtCompound tag) {
        tag.putString("reason", reason);
    }

    @Override
    public String getDescription() {
        return "Disconnect: " + reason;
    }
}
