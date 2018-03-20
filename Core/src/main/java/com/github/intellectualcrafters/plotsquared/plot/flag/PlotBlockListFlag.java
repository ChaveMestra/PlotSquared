package com.github.intellectualcrafters.plotsquared.plot.flag;

import com.github.intellectualcrafters.plotsquared.plot.object.PlotBlock;
import com.github.intellectualcrafters.plotsquared.plot.util.StringComparison;
import com.github.intellectualcrafters.plotsquared.plot.util.StringMan;
import com.github.intellectualcrafters.plotsquared.plot.util.WorldUtil;
import java.util.HashSet;

public class PlotBlockListFlag extends ListFlag<HashSet<PlotBlock>> {

  public PlotBlockListFlag(String name) {
    super(name);
  }

  @Override
  public String valueToString(Object value) {
    return StringMan.join((HashSet<PlotBlock>) value, ",");
  }

  @Override
  public HashSet<PlotBlock> parseValue(String value) {
    HashSet<PlotBlock> list = new HashSet<>();
    for (String item : value.split(",")) {
      PlotBlock block;
      try {
        String[] split = item.split(":");
        byte data;
        if (split.length == 2) {
          if ("*".equals(split[1])) {
            data = -1;
          } else {
            data = Byte.parseByte(split[1]);
          }
        } else {
          data = -1;
        }
        short id = Short.parseShort(split[0]);
        block = PlotBlock.get(id, data);
      } catch (NumberFormatException ignored) {
        StringComparison<PlotBlock>.ComparisonResult str = WorldUtil.IMP.getClosestBlock(value);
        if (str == null || str.match > 1) {
          continue;
        }
        block = str.best;
      }
      list.add(block);
    }
    return list;
  }

  @Override
  public String getValueDescription() {
    return "Flag value must be a block list";
  }
}
