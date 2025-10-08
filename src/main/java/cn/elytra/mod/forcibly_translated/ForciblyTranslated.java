package cn.elytra.mod.forcibly_translated;

import com.mojang.logging.LogUtils;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.ChapterGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.util.TextUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNullByDefault;
import org.slf4j.Logger;

import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
@NotNullByDefault
@Mod(ForciblyTranslated.MODID)
public class ForciblyTranslated {

    public static final String MODID = "ftb_quests_forcibly_translated";
    private static final Logger LOGGER = LogUtils.getLogger();

    /// when `true`, the injected text won't be translated but return a translation key. and if the key is missing from
    /// the I18n, it will be italic (unless modified).
    public static boolean showKeys = false;

    @SuppressWarnings("removal")
    public ForciblyTranslated() {
        LOGGER.info("HELLO WORLD FROM {}", MODID);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForciblyTranslatedConfig.Internal.SPEC);
    }

    @ApiStatus.Internal
    public static Component tryGetComponent(String key, Supplier<Component> defaultSupplier) {
        if (showKeys) {
            return Component.literal(key).withStyle(Style.EMPTY.withItalic(!I18n.exists(key)));
        }

        return I18n.exists(key) ? translateKeyToComponent(key) : defaultSupplier.get();
    }

    private static Component translateKeyToComponent(String key) {
        // return Component.translatable(key);
        return TextUtils.parseRawText("{" + key + "}");
    }

    @ApiStatus.Internal
    public static String keyForQuestTitle(Quest quest) {
        Chapter chapter = quest.getQuestChapter();
        if (chapter == null) {
            return "ftbquests.unknown_chapter.quest%s.title".formatted(quest.id);
        }

        String chapterName = chapter.getFilename();
        int questOrdinal = chapter.getQuests().indexOf(quest) + 1;
        return ForciblyTranslatedConfig.patternQuestTitle.formatted(chapterName, questOrdinal);
    }

    @ApiStatus.Internal
    public static String keyForQuestSubtitle(Quest quest) {
        Chapter chapter = quest.getQuestChapter();
        if (chapter == null) {
            return "ftbquests.unknown_chapter.quest%s.subtitle".formatted(quest.id);
        }

        String chapterName = chapter.getFilename();
        int questOrdinal = chapter.getQuests().indexOf(quest) + 1;
        return ForciblyTranslatedConfig.patternQuestSubtitle.formatted(chapterName, questOrdinal);
    }

    @ApiStatus.Internal
    public static String keyForQuestDescription(Quest quest, int descIndex) {
        Chapter chapter = quest.getQuestChapter();
        if (chapter == null) {
            return "ftbquests.unknown_chapter.quest%s.description%s".formatted(quest.id, descIndex + 1);
        }

        String chapterName = chapter.getFilename();
        int questOrdinal = chapter.getQuests().indexOf(quest) + 1;

        return ForciblyTranslatedConfig.patternQuestDesc.formatted(chapterName, questOrdinal, descIndex + 1);
    }

    @ApiStatus.Internal
    public static String keyForChapterTitle(Chapter chapter) {
        return ForciblyTranslatedConfig.patternChapterTitle.formatted(chapter.getFilename());
    }

    @ApiStatus.Internal
    public static String keyForChapterGroupTitle(ChapterGroup chapterGroup) {
        return ForciblyTranslatedConfig.patternChapterGroupTitle.formatted(chapterGroup.id);
    }

    @Mod.EventBusSubscriber
    static class ClientEvents {

        @SubscribeEvent
        static void onRegisterClientCommand(RegisterClientCommandsEvent event) {
            ForciblyTranslatedCommand.register(event.getDispatcher());
        }

    }

}
