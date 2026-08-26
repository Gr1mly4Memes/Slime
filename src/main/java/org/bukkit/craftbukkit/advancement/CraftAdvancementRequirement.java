package org.bukkit.craftbukkit.advancement;

import org.bukkit.advancement.AdvancementRequirement;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class CraftAdvancementRequirement implements AdvancementRequirement {

    private final List<String> requirements;

    public CraftAdvancementRequirement(List<String> list) {
        this.requirements = list;
    }

    @NotNull
    @Override
    public List<String> getRequiredCriteria() {
        return Collections.unmodifiableList(this.requirements);
    }

    @Override
    public boolean isStrict() {
        return this.requirements.size() == 1;
    }
}
