/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.systems.actionmacros.ActionMacro;
import meteordevelopment.meteorclient.systems.actionmacros.ActionMacros;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.MacroRecorder;
import meteordevelopment.meteorclient.utils.render.color.Color;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ActionMacroCommand extends Command {
    public ActionMacroCommand() {
        super("action-macro", "Control action macro recording and playback.", "amacro", "am");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder
            .then(literal("record")
                .then(argument("name", StringArgumentType.string())
                    .executes(context -> {
                        String name = StringArgumentType.getString(context, "name");
                        MacroRecorder recorder = Modules.get().get(MacroRecorder.class);

                        if (recorder.isRecording()) {
                            error("Already recording a macro. Stop current recording first.");
                            return SINGLE_SUCCESS;
                        }

                        if (recorder.isPlaying()) {
                            error("Cannot record while playing back a macro.");
                            return SINGLE_SUCCESS;
                        }

                        // Set the macro name and start recording
                        recorder.macroName.set(name);

                        if (!recorder.isActive()) {
                            recorder.toggle();
                        } else {
                            recorder.startRecording();
                        }

                        info("Started recording macro: %s", name);
                        return SINGLE_SUCCESS;
                    })
                )
            )
            .then(literal("stop")
                .executes(context -> {
                    MacroRecorder recorder = Modules.get().get(MacroRecorder.class);

                    if (!recorder.isRecording()) {
                        error("No recording in progress.");
                        return SINGLE_SUCCESS;
                    }

                    recorder.stopRecording();
                    return SINGLE_SUCCESS;
                })
            )
            .then(literal("play")
                .then(argument("name", StringArgumentType.string())
                    .executes(context -> {
                        String name = StringArgumentType.getString(context, "name");
                        MacroRecorder recorder = Modules.get().get(MacroRecorder.class);

                        ActionMacro macro = ActionMacros.get().get(name);
                        if (macro == null) {
                            error("Macro '%s' not found.", name);
                            return SINGLE_SUCCESS;
                        }

                        recorder.startPlayback(name);
                        return SINGLE_SUCCESS;
                    })
                )
            )
            .then(literal("stop-playback")
                .executes(context -> {
                    MacroRecorder recorder = Modules.get().get(MacroRecorder.class);

                    if (!recorder.isPlaying()) {
                        error("No playback in progress.");
                        return SINGLE_SUCCESS;
                    }

                    recorder.stopPlayback();
                    return SINGLE_SUCCESS;
                })
            )
            .then(literal("list")
                .executes(context -> {
                    if (ActionMacros.get().isEmpty()) {
                        info("No action macros saved.");
                        return SINGLE_SUCCESS;
                    }

                    info("Saved action macros:");
                    for (ActionMacro macro : ActionMacros.get()) {
                        mc.player.sendMessage(Text.literal(String.format("  §7- §f%s §7(%d actions, %.1fs)",
                            macro.name, macro.getActionCount(), macro.getDuration() / 1000.0)), false);
                    }
                    return SINGLE_SUCCESS;
                })
            )
            .then(literal("delete")
                .then(argument("name", StringArgumentType.string())
                    .executes(context -> {
                        String name = StringArgumentType.getString(context, "name");
                        ActionMacro macro = ActionMacros.get().get(name);

                        if (macro == null) {
                            error("Macro '%s' not found.", name);
                            return SINGLE_SUCCESS;
                        }

                        ActionMacros.get().remove(macro);
                        info("Deleted macro: %s", name);
                        return SINGLE_SUCCESS;
                    })
                )
            )
            .then(literal("info")
                .then(argument("name", StringArgumentType.string())
                    .executes(context -> {
                        String name = StringArgumentType.getString(context, "name");
                        ActionMacro macro = ActionMacros.get().get(name);

                        if (macro == null) {
                            error("Macro '%s' not found.", name);
                            return SINGLE_SUCCESS;
                        }

                        info("Macro: %s", macro.name);
                        info("  Description: %s", macro.description.isEmpty() ? "None" : macro.description);
                        info("  Actions: %d", macro.getActionCount());
                        info("  Duration: %.1fs", macro.getDuration() / 1000.0);

                        if (macro.getActionCount() > 0) {
                            info("  First 5 actions:");
                            int count = Math.min(5, macro.getActionCount());
                            for (int i = 0; i < count; i++) {
                                mc.player.sendMessage(Text.literal(String.format("    §7[%.1fs] §f%s",
                                    macro.getActions().get(i).getTimestamp() / 1000.0,
                                    macro.getActions().get(i).getDescription())), false);
                            }
                        }

                        return SINGLE_SUCCESS;
                    })
                )
            );
    }
}
