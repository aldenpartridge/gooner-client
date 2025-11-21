/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import meteordevelopment.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a macro containing a sequence of recorded actions.
 */
public class ActionMacro implements ISerializable<ActionMacro> {
    public String name = "";
    public String description = "";
    private List<RecordedAction> actions = new ArrayList<>();

    public ActionMacro() {}

    public ActionMacro(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addAction(RecordedAction action) {
        actions.add(action);
    }

    public void clearActions() {
        actions.clear();
    }

    public List<RecordedAction> getActions() {
        return actions;
    }

    public int getActionCount() {
        return actions.size();
    }

    public long getDuration() {
        if (actions.isEmpty()) return 0;
        return actions.get(actions.size() - 1).getTimestamp();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("name", name);
        tag.putString("description", description);

        NbtList actionsTag = new NbtList();
        for (RecordedAction action : actions) {
            actionsTag.add(action.toTag());
        }
        tag.put("actions", actionsTag);

        return tag;
    }

    @Override
    public ActionMacro fromTag(NbtCompound tag) {
        name = tag.getString("name");
        description = tag.getString("description");

        actions.clear();
        NbtList actionsTag = tag.getList("actions", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < actionsTag.size(); i++) {
            NbtCompound actionTag = actionsTag.getCompound(i);
            RecordedAction action = deserializeAction(actionTag);
            if (action != null) {
                actions.add(action);
            }
        }

        return this;
    }

    private RecordedAction deserializeAction(NbtCompound tag) {
        String typeStr = tag.getString("type");
        RecordedAction.ActionType type;

        try {
            type = RecordedAction.ActionType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            return null;
        }

        return switch (type) {
            case MOVE -> new MovementAction(tag);
            case ROTATION -> new RotationAction(tag);
            case MINE_START, MINE_STOP -> new MineBlockAction(tag);
            case DROP_ITEM -> new DropItemAction(tag);
            case PLACE_BLOCK -> new PlaceBlockAction(tag);
            case INTERACT_BLOCK -> new InteractBlockAction(tag);
            case DISCONNECT -> new DisconnectAction(tag);
            default -> null;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionMacro that = (ActionMacro) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
