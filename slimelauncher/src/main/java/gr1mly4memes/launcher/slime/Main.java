/*
 * Copyright (C) Gr1mly4Memes.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package gr1mly4memes.launcher.slime;

import gr1mly4memes.launcher.slime.action.Action;
import gr1mly4memes.launcher.slime.feature.DefaultLibraries;
import gr1mly4memes.launcher.slime.util.DataParser;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final boolean DEBUG = Boolean.getBoolean("youer.debug");
    public static final List<String> mainArgs = new ArrayList<>();
    public static String MCVERSION;
    //public static i18n i18n;

    public static String getVersion() {
        return (Main.class.getPackage().getImplementationVersion() != null) ? Main.class.getPackage().getImplementationVersion() : MCVERSION;
    }

    @SneakyThrows
    static void main(String[] args) {
        System.setProperty("org.jline.terminal.disableDeprecatedProviderWarning", "true");
        mainArgs.addAll(List.of(args));
        DataParser.parseVersions();
        DataParser.parseLaunchArgs();

        long startTime = System.currentTimeMillis();
        if (System.getProperty("log4j2.configurationFile") == null) {
            System.setProperty("log4j2.configurationFile", "log4j2_youer.xml");
        }
        System.out.println(" ");
        System.out.println(("Deployment environment..."));
        DefaultLibraries.run();
        var action = new Action();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double seconds = duration / 1000.0;
        System.out.println(String.format("Deployment completed, (duration: %s seconds)", seconds));
        action.start();
    }
}
