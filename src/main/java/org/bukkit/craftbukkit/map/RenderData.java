package org.bukkit.craftbukkit.map;

import org.bukkit.map.MapCursor;

import java.util.ArrayList;
import java.util.List;

public class RenderData {

    public final List<MapCursor> cursors = new ArrayList<>();
    public byte[] buffer = new byte[128 * 128];
}
