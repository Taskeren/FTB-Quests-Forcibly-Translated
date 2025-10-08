package cn.elytra.mod.forcibly_translated;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ForciblyTranslatedConfig {

    /// when `true`, empty lines in the quest description will also be given a translation key.
    public static boolean generateKeysForEmptyDescription = false;

    public static String patternQuestTitle = "ftbquests.chapter.%s.quest%s.title";
    public static String patternQuestSubtitle = "ftbquests.chapter.%s.quest%s.subtitle";
    public static String patternQuestDesc = "ftbquests.chapter.%s.quest%s.description%s";
    public static String patternChapterTitle = "ftbquests.chapter.%s.title";
    public static String patternChapterGroupTitle = "ftbquests.chapter_groups_%s.title";

    @Mod.EventBusSubscriber(modid = ForciblyTranslated.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    static class Internal {

        private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        public static final ForgeConfigSpec.BooleanValue VALUE_GENERATE_KEYS_FOR_EMPTY_DESC = BUILDER.comment(
                "Whether generate translation keys for empty quest descriptions.")
            .define("misc.generateKeysForEmptyDescription", false);

        public static final ForgeConfigSpec.ConfigValue<String> VALUE_PATTERN_QUEST_TITLE = BUILDER.comment(
                "The pattern of translation keys of Quest Titles (format: chapter filename, n-th quest start at 1)")
            .define("pattern.questTitle", "ftbquests.chapter.%s.quest%s.title");

        public static final ForgeConfigSpec.ConfigValue<String> VALUE_PATTERN_QUEST_SUBTITLE = BUILDER.comment(
                "The pattern of translation keys of Quest Subtitles (format: chapter filename, n-th quest of the chapter start at 1)")
            .define("pattern.questSubtitle", "ftbquests.chapter.%s.quest%s.subtitle");

        public static final ForgeConfigSpec.ConfigValue<String> VALUE_PATTERN_QUEST_DESC = BUILDER.comment(
                "The pattern of translation keys of Quest Descriptions (format: chapter filename, n-th quest of the chapter start at 1, n-th line of descriptions start at 1)")
            .define("pattern.questDesc", "ftbquests.chapter.%s.quest%s.description%s");

        public static final ForgeConfigSpec.ConfigValue<String> VALUE_PATTERN_CHAPTER_TITLE = BUILDER.comment(
                "The pattern of translation keys of Chapter Titles (format: chapter filename)")
            .define("pattern.chapterTitle", "ftbquests.chapter.%s.title");

        public static final ForgeConfigSpec.ConfigValue<String> VALUE_PATTERN_CHAPTER_GROUP_TITLE = BUILDER.comment(
                "The pattern of translation keys of Chapter Group Titles (format: chapter group id)")
            .define("pattern.chapterGroupTitle", "ftbquests.chapter_groups_%s.title");

        public static final ForgeConfigSpec SPEC = BUILDER.build();

        @SubscribeEvent
        static void onConfigReload(ModConfigEvent event) {
            generateKeysForEmptyDescription = VALUE_GENERATE_KEYS_FOR_EMPTY_DESC.get();

            patternQuestTitle = VALUE_PATTERN_QUEST_TITLE.get();
            patternQuestSubtitle = VALUE_PATTERN_QUEST_SUBTITLE.get();
            patternQuestDesc = VALUE_PATTERN_QUEST_DESC.get();
            patternChapterTitle = VALUE_PATTERN_CHAPTER_TITLE.get();
            patternChapterGroupTitle = VALUE_PATTERN_CHAPTER_GROUP_TITLE.get();
        }

    }

}
