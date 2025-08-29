package org.earthstudio.bedwars1058nextgen.configuration;

import com.andrei1058.bedwars.api.language.Language;
import org.jetbrains.annotations.NotNull;

public class Message {

    public static final String PATH = "addons.nextgen.";
    public static final String EXAMPLE_TRANSLATION = PATH + "example-translation";

    public static void setupMessages() {
        for (Language l : Language.getLanguages()) {
            addDefault(l, EXAMPLE_TRANSLATION, "This is a test translation", "这是一个测试用翻译");
        }
    }

    private static void addDefault(@NotNull Language l, String path, Object english, Object schinese) {
        if (!l.exists(path)) {
            l.set(path, l.getIso().equals("zh_cn") ? schinese : english);
        }
    }

}
