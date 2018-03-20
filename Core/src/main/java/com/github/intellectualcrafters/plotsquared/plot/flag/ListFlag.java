package com.github.intellectualcrafters.plotsquared.plot.flag;

import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import java.util.Collection;

public abstract class ListFlag<V extends Collection<?>> extends Flag<V> {

  public ListFlag(String name) {
    super(name);
  }

  public boolean contains(Plot plot, Object value) {
    V existing = plot.getFlag(this, null);
    return existing != null && existing.contains(value);
  }
}
