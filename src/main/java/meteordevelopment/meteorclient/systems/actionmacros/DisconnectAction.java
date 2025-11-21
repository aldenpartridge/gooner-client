/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.nbt.NbtCompound;
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
        super(tag.getLong("timestamp"));
        this.reason = tag.getString("reason");
    }

    @Override
    public boolean execute() {
        if (mc.world == null) return false;

        mc.world.disconnect();

        if (mc.getNetworkHandler() != null) {
            mc.getNetworkHandler().getConnection().disconnect(Text.literal(reason));
        }

        mc.disconnect(new DisconnectedScreen(
            new MultiplayerScreen(new TitleScreen()),
            Text.literal("Disconnected"),
            Text.literal(reason)
        ));
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
