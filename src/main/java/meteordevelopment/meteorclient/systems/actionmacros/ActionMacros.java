/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.actionmacros;

import meteordevelopment.meteorclient.systems.System;
import meteordevelopment.meteorclient.systems.Systems;
import meteordevelopment.meteorclient.utils.misc.NbtUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * System for managing action macros (recorded sequences of player actions).
 */
public class ActionMacros extends System<ActionMacros> implements Iterable<ActionMacro> {
    private List<ActionMacro> macros = new ArrayList<>();

    public ActionMacros() {
        super("action-macros");
    }

    public static ActionMacros get() {
        return Systems.get(ActionMacros.class);
    }

    public void add(ActionMacro macro) {
        macros.add(macro);
        save();
    }

    public ActionMacro get(String name) {
        for (ActionMacro macro : macros) {
            if (macro.name.equalsIgnoreCase(name)) return macro;
        }
        return null;
    }

    public List<ActionMacro> getAll() {
        return macros;
    }

    public void remove(ActionMacro macro) {
        if (macros.remove(macro)) {
            save();
        }
    }

    public boolean isEmpty() {
        return macros.isEmpty();
    }

    @Override
    public @NotNull Iterator<ActionMacro> iterator() {
        return macros.iterator();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.put("macros", NbtUtils.listToTag(macros));
        return tag;
    }

    @Override
    public ActionMacros fromTag(NbtCompound tag) {
        macros.clear();
        for (NbtElement element : tag.getListOrEmpty("macros")) {
            if (element instanceof NbtCompound) {
                ActionMacro macro = new ActionMacro();
                macro.fromTag((NbtCompound) element);
                macros.add(macro);
            }
        }
        return this;
    }
}
