package com.github.intellectualcrafters.plotsquared.plot.commands;

import com.github.intellectualcrafters.plotsquared.commands.Command;
import com.github.intellectualcrafters.plotsquared.commands.CommandDeclaration;
import com.github.intellectualcrafters.plotsquared.plot.config.C;
import com.github.intellectualcrafters.plotsquared.plot.database.DBFunc;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal2;
import com.github.intellectualcrafters.plotsquared.plot.object.RunnableVal3;
import com.github.intellectualcrafters.plotsquared.plot.util.ByteArrayUtilities;
import com.github.intellectualcrafters.plotsquared.plot.util.MainUtil;
import com.github.intellectualcrafters.plotsquared.plot.util.Permissions;
import com.github.intellectualcrafters.plotsquared.plot.util.UUIDHandler;
import java.util.UUID;

@CommandDeclaration(
    command = "grant",
    category = CommandCategory.CLAIMING,
    usage = "/plot grant <check|add> [player]",
    permission = "plots.grant",
    requiredType = RequiredType.NONE)
public class Grant extends Command {

  public Grant() {
    super(MainCommand.getInstance(), true);
  }

  @Override
  public void execute(final PlotPlayer player, String[] args,
      RunnableVal3<Command, Runnable, Runnable> confirm,
      RunnableVal2<Command, CommandResult> whenDone) throws CommandException {
    checkTrue(args.length >= 1 && args.length <= 2, C.COMMAND_SYNTAX, getUsage());
    final String arg0 = args[0].toLowerCase();
    switch (arg0) {
      case "add":
      case "check":
        if (!Permissions.hasPermission(player, C.PERMISSION_GRANT.f(arg0))) {
          C.NO_PERMISSION.send(player, C.PERMISSION_GRANT.f(arg0));
          return;
        }
        if (args.length > 2) {
          break;
        }
        final UUID uuid =
            args.length == 2 ? UUIDHandler.getUUIDFromString(args[1]) : player.getUUID();
        if (uuid == null) {
          C.INVALID_PLAYER.send(player, args[1]);
          return;
        }
        MainUtil.getPersistentMeta(uuid, "grantedPlots", new RunnableVal<byte[]>() {
          @Override
          public void run(byte[] array) {
            if (arg0.equals("check")) { // check
              int granted = array == null ? 0 : ByteArrayUtilities.bytesToInteger(array);
              C.GRANTED_PLOTS.send(player, granted);
            } else { // add
              int amount = 1 + (array == null ? 0 : ByteArrayUtilities.bytesToInteger(array));
              boolean replace = array != null;
              String key = "grantedPlots";
              byte[] rawData = ByteArrayUtilities.integerToBytes(amount);
              PlotPlayer online = UUIDHandler.getPlayer(uuid);
              if (online != null) {
                online.setPersistentMeta(key, rawData);
              } else {
                DBFunc.addPersistentMeta(uuid, key, rawData, replace);
              }
            }
          }
        });
    }
    C.COMMAND_SYNTAX.send(player, getUsage());
  }
}
