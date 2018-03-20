package com.plotsquared.sponge.util;

import com.intellectualcrafters.plot.util.TaskManager;
import com.plotsquared.sponge.SpongeMain;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.spongepowered.api.scheduler.Task;

public class SpongeTaskManager extends TaskManager {

  private final AtomicInteger i = new AtomicInteger();

  private final HashMap<Integer, Task> tasks = new HashMap<>();
  private final SpongeMain spongeMain;

  public SpongeTaskManager(SpongeMain spongeMain) {
    this.spongeMain = spongeMain;
  }

  @Override
  public int taskRepeat(Runnable runnable, int interval) {
    int val = this.i.incrementAndGet();
    Task.Builder builder = this.spongeMain.getGame().getScheduler().createTaskBuilder();
    Task.Builder built = builder.delayTicks(interval).intervalTicks(interval).execute(runnable);
    Task task = built.submit(this.spongeMain.getPlugin());
    this.tasks.put(val, task);
    return val;
  }

  @Override
  public int taskRepeatAsync(Runnable runnable, int interval) {
    int val = this.i.incrementAndGet();
    Task.Builder builder = this.spongeMain.getGame().getScheduler().createTaskBuilder();
    Task.Builder built = builder.delayTicks(interval).async().intervalTicks(interval)
        .execute(runnable);
    Task task = built.submit(this.spongeMain.getPlugin());
    this.tasks.put(val, task);
    return val;
  }

  @Override
  public void taskAsync(Runnable runnable) {
    Task.Builder builder = this.spongeMain.getGame().getScheduler().createTaskBuilder();
    builder.async().execute(runnable).submit(this.spongeMain.getPlugin());
  }

  @Override
  public void task(Runnable runnable) {
    Task.Builder builder = this.spongeMain.getGame().getScheduler().createTaskBuilder();
    builder.execute(runnable).submit(this.spongeMain.getPlugin());
  }

  @Override
  public void taskLater(Runnable runnable, int delay) {
    Task.Builder builder = this.spongeMain.getGame().getScheduler().createTaskBuilder();
    builder.delayTicks(delay).execute(runnable).submit(this.spongeMain.getPlugin());
  }

  @Override
  public void taskLaterAsync(Runnable runnable, int delay) {
    Task.Builder builder = this.spongeMain.getGame().getScheduler().createTaskBuilder();
    builder.async().delayTicks(delay).execute(runnable).submit(this.spongeMain.getPlugin());
  }

  @Override
  public void cancelTask(int i) {
    Task task = this.tasks.remove(i);
    if (task != null) {
      task.cancel();
    }
  }

}
