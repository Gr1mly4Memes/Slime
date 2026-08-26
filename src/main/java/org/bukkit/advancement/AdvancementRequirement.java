package org.bukkit.advancement;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface AdvancementRequirement {

    /**
     * Get all required criteria.
     *
     * @return the list of required criteria for this requirement.
     */
    @NotNull
    List<String> getRequiredCriteria();

    /**
     * Check if the requirement is strict.
     *
     * @return true if requirement list contains one criteria, false if
     * multiple.
     */
    boolean isStrict();
}
